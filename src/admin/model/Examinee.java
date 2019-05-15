package admin.model;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.TreeMap;

import admin.model.exam_entries.QuestionPart;
import common.network.Screenshot;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * This class is for one examinee during an exam
 * @author Ziya Mukhtarov
 * @version 12/05/2019
 */
public class Examinee implements Serializable
{
	private static final long serialVersionUID = 663825998181836230L;

	public static int STATUS_DISCONNECTED = 1;
	public static int STATUS_CONNECTED = 2;

	private transient Socket socket;
	private transient Screenshot screen;
	private transient SimpleObjectProperty<Image> screenImageProperty;
	private transient int screenWidth;

	private transient IntegerProperty status;
	private transient StringProperty notesProperty;

	private String id;
	private String name;
	private String notes;
	private TreeMap<QuestionPart, String> solutions;
	private TreeMap<QuestionPart, String> grades;

	/**
	 * Creates a new examinee with the specified name
	 * @param name   The name of the examinee
	 * @param socket The socket to this examinee
	 */
	public Examinee( String name, Socket socket)
	{
		screenWidth = -1;
		solutions = new TreeMap<QuestionPart, String>();
		screenImageProperty = new SimpleObjectProperty<>();
		id = IDHandler.getInstance().generate( getClass().getName());
		setName( name);
		this.socket = socket;
		status = new SimpleIntegerProperty( STATUS_CONNECTED);
		notes = "";
		notesProperty = new SimpleStringProperty( notes);
	}

	/**
	 * Saves the submitted solution
	 * @param part     The question part that this solution belongs to
	 * @param solution The submitted solution
	 */
	public void addSolution( QuestionPart part, String solution)
	{
		solutions.put( part, solution);
	}

	public void setGrade( QuestionPart part, String grade)
	{
		grades.put( part, grade);
	}

	public String getGrade( QuestionPart part)
	{
		return grades.get( part);
	}
	
	/**
	 * @return the screenWidth
	 */
	public int getScreenWidth()
	{
		return screenWidth;
	}

	/**
	 * @param screenWidth the screenWidth to set
	 */
	public void setScreenWidth( int screenWidth)
	{
		this.screenWidth = screenWidth;
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

	@Override
	public String toString()
	{
		return name;
	}

	/**
	 * @return The status property
	 */
	public IntegerProperty statusProperty()
	{
		return status;
	}

	public void setStatus( int status)
	{
		this.status.set( status);
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
		if ( screenWidth != -1)
			screen.scale( 1.0 * screenWidth / screen.getImage().getWidth());
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

	public StringProperty notesProperty()
	{
		return notesProperty;
	}

	/**
	 * @param notes The notes to set
	 */
	public void setNotes( String notes)
	{
		this.notes = notes;
		notesProperty.set( notes);
	}

	/**
	 * @return The solutions
	 */
	public TreeMap<QuestionPart, String> getSolutions()
	{
		return solutions;
	}

	/**
	 * Deserialization method
	 */
	private void readObject( ObjectInputStream ois) throws Exception
	{
		ois.defaultReadObject();
		notesProperty = new SimpleStringProperty( notes);
	}
}
