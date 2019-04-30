package examinee.model;

import network.*;

public class Model
{
	//properties
	
	public final int STATUS_CONNECTED = 1;
	public final int STATUS_DISCONNECTED = 2;
	public final int STATUS_LOGIN = 3;
	public final int STATUS_SUSPENDED = 4;
	public final int STATUS_BANNED = 5;
	public final int STATUS_FINISHED = 6;
	
	private String username;
	private String password;
	private String adminIP;
	private int timeRemain;
	private int timeTotal;
	private int status;
	private Client client;
	private ExamEntry examData;
	
	//constructors
	
	public Model( String username, String password, String adminIP)
	{
		this.username = username;
		this.password = password;
		this.adminIP = adminIP;
		status = 0;
		timeTotal = 120;
		timeRemain = timeTotal;
	}
	
	public void getExamData()
	{
		//client.messageReceived(msg);
	}
	
}
