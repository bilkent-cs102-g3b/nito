package admin.model;

import java.io.File;
import java.net.Socket;

import network.Screenshot;

/**
 * This class is for one examinee during an exam
 * @author Ziya Mukhtarov
 * @version 18/04/2019
 */
public class Examinee
{
	// TODO
	public static int STATUS_DISCONNECTED = 1;
	public static int STATUS_CONNECTED = 2;
	public static int STATUS_BANNED = 3;
	public static int STATUS_SUSPENDED = 4;
	public static int STATUS_DONE = 5;

	private Socket socket;
	private Screenshot screen;

	private String id;
	private String name;
	// private Exam exam;
	private int status;
	private Group group;
	private File folder;

	/**
	 * Creates a new examinee with the specified name
	 * @param name   The name of the examinee
	 * @param socket The socket to this examinee
	 */
	public Examinee( String name, Socket socket)
	{
		this( name, Group.DEFAULT, socket);
	}

	/**
	 * Creates a new examinee with the specified name and assigns this to the group
	 * @param name   The name of the examinee
	 * @param group  The group to add this examinee to
	 * @param socket The socket to this examinee
	 */
	public Examinee( String name, Group group, Socket socket)
	{
		id = IDGenerator.generate( getClass().getName());
		setName( name);
		setGroup( group);
		this.socket = socket;
		folder = Workspace.getFolderOfExaminee( this);
	}

	/**
	 * @return The name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name The name to set
	 */
	public void setName( String name)
	{
		this.name = name;
	}

	/**
	 * @return The group
	 */
	public Group getGroup()
	{
		return group;
	}

	/**
	 * @param group The group to set
	 */
	public void setGroup( Group group)
	{
		if ( this.group != null)
		{
			this.group.remove( this);
		}
		this.group = group;
		group.add( this);
	}

	/**
	 * @return The id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @return The status
	 */
	public int getStatus()
	{
		return status;
	}

	@Override
	public String toString()
	{
		return "Examinee [id=" + id + ", name=" + name + ", status=" + status + ", group=" + group.getTitle() + "]";
	}

	/**
	 * @return A string that can be used for searching. In other words, if this
	 *         returned string contains some search text, then this examinee can be
	 *         a search result
	 */
	public String getStringForSearch()
	{
		return name;
	}

	/**
	 * @return The screen
	 */
	public Screenshot getScreen()
	{
		return screen;
	}

	/**
	 * @param screen The screen to set
	 */
	public void setScreen( Screenshot screen)
	{
		this.screen = screen;
	}

	/**
	 * @return The socket
	 */
	public Socket getSocket()
	{
		return socket;
	}
}
