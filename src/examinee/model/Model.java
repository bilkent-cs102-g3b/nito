package examinee.model;

import java.io.IOException;
import java.net.UnknownHostException;

import common.network.*;

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
	private ExamEntry examData;

	// constructors

	private Model()
	{
		status = 0;
		reference = null;
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
			status = STATUS_CONNECTED;
			
			return true;
		}
		catch (IOException e)
		{
			//Login failed
			return false;
		}

	}
	
	// TODO Make submit all
	
	/**
	 * Get total exam time
	 * @return Total time
	 */
	public int getTimeTotal()
	{
		return timeTotal;
	}
	
	/**
	 * Get remaining exam time
	 * @return Remaining time
	 */
	public int getTimeRemain()
	{
		return timeRemain;
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
			reference = new Instruction( parts[2], parts[3], parts[4], false, false);
			examData.add( reference);
			reference.setParent( examData);
			// TODO
		}
		
		// Create a Question, goes into exam
		if ( parts[1].equals( "question") )
		{
			reference = new Question( parts[2], parts[3], parts[4], true, false);
			examData.add( reference);
			reference.setParent( examData);
			// TODO
		}
		
		// Create part, goes into Question
		if ( parts[1].equals( "part") && reference.getId() == "question" )
		{
			ExamEntry tmp = new QuestionPart( parts[2], parts[3], parts[4], true, true);
			reference.add( tmp);
			// TODO
		}
		
		// Create an exam, everything else is placed within this container
		if ( parts[1].equals( "exam") && reference == null )
		{
			examData = new ExamEntry( parts[2], parts[3], "", false, false);
			timeTotal = Integer.parseInt(parts[4]); // Time in seconds
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
		
		
		//******************************************************
		// Test
		//******************************************************
		
		examData = new ExamEntry( "123", "test", "", false, false);
		timeTotal = 6000; // Time in seconds
		timeRemain = timeTotal;
		
		reference = new Instruction( "001", "Instruction", "Don't Cheat", false, false);
		examData.add( reference);
		reference.setParent( examData);
		
		reference = new Question( "002", "Question 1", "Why are we here?", true, false);
		examData.add( reference);
		reference.setParent(examData);
		
		
		reference.add( new QuestionPart( "101", "part 1", "test1", true, true));
		
		reference.add( new QuestionPart( "102", "part 2", "test2", true, true));
		
		//******************************************************
	}

}
