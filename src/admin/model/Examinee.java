package admin.model;

import java.net.Socket;

import admin.model.exam_entries.Exam;
import common.network.Screenshot;
import javafx.beans.property.SimpleObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * This class is for one examinee during an exam
 * @author Ziya Mukhtarov
 * @version 11/05/2019
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
	private SimpleObjectProperty<Image> screenImageProperty;

	private String id;
	private String name;
	private Exam exam;
	private int status;
	private String notes;

	/**
	 * Creates a new examinee with the specified name
	 * @param name   The name of the examinee
	 * @param socket The socket to this examinee
	 */
	public Examinee( String name, Socket socket)
	{
		screenImageProperty = new SimpleObjectProperty<>();
		id = IDHandler.getInstance().generate( getClass().getName());
		setName( name);
		this.socket = socket;
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
		return "Examinee [id=" + id + ", name=" + name + ", status=" + status + "]";
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
	 * @return The screen image property
	 */
	public SimpleObjectProperty<Image> getScreenImageProperty()
	{
		return screenImageProperty;
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
		screenImageProperty.set( SwingFXUtils.toFXImage( screen.getImage(), null));
	}

	/**
	 * @return The socket
	 */
	public Socket getSocket()
	{
		return socket;
	}

	/**
	 * @return The notes
	 */
	public String getNotes()
	{
		return notes;
	}

	/**
	 * @param notes The notes to set
	 */
	public void setNotes( String notes)
	{
		this.notes = notes;
	}
}
