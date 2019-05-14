package examinee.model;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;

import common.network.Client;
import common.network.Screenshot;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Model class for examinee
 * @author Alper Sari
 * @version 09/05/2019
 */
public class Model
{
	// properties

	private static final String SECRET = "24DdwVJljT28m6MSOfvMnj7iZbL8bNMmo7xnLKsZSyurflOLg2JFtq0hsY09";
	public static final int STATUS_CONNECTED = 1;
	public static final int STATUS_START = 2;
	public static final int STATUS_FINISHED = 3;

	private static Model instance;

	private String username;
	private String adminIP;
	private boolean dataEnd;
	private boolean examStart;
	private IntegerProperty timeRemain;
	private IntegerProperty timeTotal;
	private int width;
	private IntegerProperty status;
	private Client client;
	private ExamEntry reference;
	private ExamEntry examData;
	private Thread examTimer;

	// constructors

	private Model()
	{
		status = new SimpleIntegerProperty();
		status.set( 0);
		reference = null;
		dataEnd = false;
		examStart = false;
		width = 0;
		timeTotal = new SimpleIntegerProperty();
		timeRemain = new SimpleIntegerProperty();
		examTimer = new Thread( () -> {
			while ( true)
			{
				try
				{
					Thread.sleep( 1000);
				}
				catch (InterruptedException e)
				{
					Thread.currentThread().interrupt();
					break;
				}
				timeRemain.set( timeRemain.intValue() - 1);
			}
		});
	}

	/**
	 * Get singleton object
	 * @return Instance of Model
	 */
	public static synchronized Model getInstance()
	{
		if ( instance == null)
			instance = new Model();
		return instance;
	}

	/**
	 * Send message to server using client instance
	 * @param protocol - Protocol when sending message
	 * @param msg      - Message to send
	 */
	public void sendMessage( String protocol, String msg)
	{
		client.sendMessage( SECRET + ":::" + protocol + ":::" + msg);
	}

	/**
	 * Create client with network package and connect to admin ip
	 * @param name Name of user
	 * @param ip   Admin ip to connect to
	 * @return true if connection successful
	 */
	public boolean login( String name, String ip)
	{
		adminIP = ip;
		username = name;
		examStart = !examStart;
		try
		{
			client = new Client( adminIP) {

				@Override
				public void messageReceived( String msg)
				{
					handleMessage( msg); // Pass message to handle method
				}

			};

			sendMessage( "name", username);
			status.set( STATUS_CONNECTED);

			// Send screenshots
			new Thread( () -> {
				while ( true)
				{
					try
					{
						client.sendImage( new Screenshot( width));
					}
					catch (AWTException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();

			return true;
		}
		catch (IOException e)
		{
			// Login failed
			return false;
		}

	}

	/**
	 * Submits all question part entries
	 */
	public void submitAll()
	{
		ArrayList<ExamEntry> list = examData.getAll();
		for ( int i = 0; i < list.size(); i++)
		{
			if ( list.get( i) instanceof Question)
			{
				QuestionPart part = null;
				ArrayList<ExamEntry> partList = list.get( i).getAll();
				for ( int k = 0; k < partList.size(); k++)
				{
					if ( partList.get( k) instanceof QuestionPart)
					{
						part = (QuestionPart) partList.get( k);
						part.submit( client);
					}
				}
			}
		}
		status.set( STATUS_FINISHED);
	}

	/**
	 * Get total exam time
	 * @return Total time
	 */
	public IntegerProperty getTimeTotal()
	{
		return timeTotal;
	}

	/**
	 * Get remaining exam time
	 * @return Remaining time
	 */
	public IntegerProperty getTimeRemain()
	{
		return timeRemain;
	}

	/**
	 * Get status as integer property
	 * @return status
	 */
	public IntegerProperty getStatus()
	{
		return status;
	}

	/**
	 * Get ExamEntry instance containing all other entries
	 * @return examData
	 */
	public ExamEntry getExamData()
	{
		return examData;
	}

	/**
	 * Returns true if examData has been completely filled
	 * @return dataEnd
	 */
	public boolean isExamReady()
	{
		return dataEnd;
	}

	// if ( examStart )
	// {
	// screenThread = new Thread();
	// }

	/**
	 * Searches the existing exam data for a given id !!Use only when searching
	 * parent id!!
	 * @param id ID to search for
	 * @return Found entry
	 */
	private ExamEntry searchId( String id, ExamEntry e)
	{
		if ( e.getId().equals( id))
			return e;

		ExamEntry result = null;
		for ( ExamEntry entry : e.getAll())
		{
			result = searchId( id, entry);
			if ( result != null)
				return result;
		}
		return result;
	}

	/**
	 * Ends exam, stops timers, closes client
	 */
	public void examEnd()
	{
		submitAll();
		examTimer.interrupt();
		status.set( STATUS_FINISHED);
		client.close();
	}

	/**
	 * Handles message according to protocol
	 * @param msg Received message
	 */
	private void handleMessage( String msg)
	{
		String parts[] = msg.split( ":::");

		// Create an Instruction, goes into exam
		if ( parts[1].equals( "instruction"))
		{
			reference = new Instruction( parts[2], parts[3], (parts.length >= 5 ? parts[4] : ""), false, false);
			ExamEntry parent = examData;
			parent.add( reference);
			reference.setParent( parent);
		}

		// Create a Question, goes into exam
		if ( parts[1].equals( "question"))
		{
			reference = new Question( parts[2], parts[3], parts[4], true, false);
			ExamEntry parent = searchId( parts[5], examData);
			parent.add( reference);
			reference.setParent( parent);
		}

		// Create part, goes into Question
		if ( parts[1].equals( "part"))
		{
			reference = new QuestionPart( parts[2], parts[3], "", parts[4], true, true);
			ExamEntry parent = searchId( parts[5], examData);
			parent.add( reference);
			reference.setParent( parent);
		}

		// Add template solution to question part
		if ( parts[1].equals( "template"))
		{
			QuestionPart part = (QuestionPart) searchId( parts[4], examData);
			part.updateSolution( parts[3]);
		}

		// Create an exam, everything else is placed within this container
		if ( parts[1].equals( "exam") && reference == null)
		{
			examData = new ExamEntry( parts[2], parts[3], "", false, false);
			timeTotal.set( Integer.parseInt( parts[4])); // Time in seconds
			timeRemain.set( timeTotal.getValue());
		}

		// Start exam
		if ( parts[1].equals( "start"))
		{
			status.set( STATUS_START);
			examTimer.start();
		}

		// Check if data transfer ended
		if ( parts[1].equals( "data_end"))
		{
			dataEnd = !dataEnd;
		}

		// Check if exam is finished
		if ( parts[1].equals( "exam_ended"))
			examEnd();

		// if ( parts[1].equals( "time_remain") )
		// timeRemain.set( Integer.parseInt( parts[2]));

		// Get Screenshot specification
		if ( parts[1].equals( "screenshot_width"))
		{
			width = Integer.parseInt( parts[2]);
		}
	}
}
