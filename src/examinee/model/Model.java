package examinee.model;

import java.io.IOException;
import java.net.UnknownHostException;

import network.*;

public class Model
{
	//properties
	
	private static final String SECRET = "24DdwVJljT28m6MSOfvMnj7iZbL8bNMmo7xnLKsZSyurflOLg2JFtq0hsY09";
	public final int STATUS_CONNECTED = 1;
	public final int STATUS_DISCONNECTED = 2;
	public final int STATUS_LOGIN = 3;
	public final int STATUS_SUSPENDED = 4;
	public final int STATUS_BANNED = 5;
	public final int STATUS_FINISHED = 6;
	
	
	private String username;
	private String adminIP;
	private int timeRemain;
	private int timeTotal;
	private int status;
	private Client client;
	private ExamEntry examData;
	
	//constructors
	
	public Model()
	{
		status = 0;
		timeTotal = 120;
		timeRemain = timeTotal;
		
	}
	
	public boolean login( String name, String ip)
	{
		//TODO
		
		adminIP = ip;
		
		try
		{
			client = new Client(adminIP) {
				
				
				@Override
				public void messageReceived(String msg)
				{
					handleMessage(msg);
					// TODO Auto-generated method stub
				}
				
			};
			
			client.sendMessage( SECRET + ":::" + "name" + ":::" + name);
			
			return true;
		} catch (IOException e)
		{
			// TODO Login failed
			return false;
//			e.printStackTrace();
		}
		
		
	}
	
	private void handleMessage( String msg)
	{
		String parts[] = msg.split(":::");
		
		if ( parts[1].equals("instruction") )
		{
			// TODO
		}
		if ( parts[1].equals("question") )
		{
			// TODO
		}
		if ( parts[1].equals("part") )
		{
			// TODO
		}
		
		if ( parts[1].equals("exam") )
		{
			// TODO
		}
		
		if ( parts[1].equals("ban") )
		{
			// TODO
			status = STATUS_BANNED;
		}
		
		if ( parts[1].equals("suspend") )
		{
			// TODO
			status = STATUS_SUSPENDED;
		}
		//TODO
	}
	
}
