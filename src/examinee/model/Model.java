package examinee.model;

import java.io.IOException;
import java.net.UnknownHostException;

import common.network.*;

// TODO: Make it proper singleton
/**
 * Model class for examinee
 * @author Alper Sari
 * @version 09/05/2019
 */
public class Model
{
	// properties

	private static final String SECRET = "24DdwVJljT28m6MSOfvMnj7iZbL8bNMmo7xnLKsZSyurflOLg2JFtq0hsY09";
	public final int STATUS_CONNECTED = 1;
	public final int STATUS_DISCONNECTED = 2;
	public final int STATUS_LOGIN = 3;
	public final int STATUS_SUSPENDED = 4;
	public final int STATUS_BANNED = 5;
	public final int STATUS_FINISHED = 6;

	private static Model instance;

	private String username;
	private String adminIP;
	private String examTitle;
	private int timeRemain;
	private int timeTotal;
	private int status;
	private Client client;
	private ExamEntry reference;
	private ExamContainer examData;

	// constructors

	public Model()
	{
		instance = this;
		status = 0;
		reference = null;
	}

	public static Model getInstance()
	{
		return instance;
	}

	/**
	 * Create client with network package and connect to admin ip
	 * @param name Name of user
	 * @param ip Admin ip to connect to
	 * @return true if connection successful
	 */
	public boolean login( String name, String ip)
	{
		adminIP = ip;

		try
		{
			client = new Client( adminIP) {

				@Override
				public void messageReceived( String msg)
				{
					handleMessage( msg); // Pass message to handle method
				}

			};

			client.sendMessage( SECRET + ":::" + "name" + ":::" + name);

			return true;
		}
		catch (IOException e)
		{
			//Login failed
			return false;
		}

	}

	// private void createEntry( String id, String title, String content, boolean
	// markable, boolean editable)
	// {
	// examData = new ExamEntry();
	// }
	
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
			reference = new Instruction( parts[2], parts[3], parts[4], false, false);
			examData.add( reference);
			reference.setParent( examData);
			// TODO
		}
		
		// Create a Question, goes into exam
		if ( parts[1].equals( "question"))
		{
			reference = new Question( parts[2], parts[3], parts[4], true, false);
			examData.add( reference);
			reference.setParent( examData);
			// TODO
		}
		
		// Create part, goes into Question
		if ( parts[1].equals( "part"))
		{
			reference = new QuestionPart( parts[2], parts[3], parts[4], true, true);
			examData.add( reference);
			// TODO
		}
		
		// Create an exam, everything else is placed within this container
		if ( parts[1].equals( "exam"))
		{
			examTitle = parts[2];
			timeTotal = Integer.parseInt(parts[3]);
			timeRemain = timeTotal;
		}
		
		// Change status to banned
		if ( parts[1].equals( "ban"))
		{
			status = STATUS_BANNED;
		}
		
		// Change status to suspended
		if ( parts[1].equals( "suspend"))
		{
			status = STATUS_SUSPENDED;
		}
		// TODO
	}

}
