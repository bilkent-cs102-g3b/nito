package admin.model;

/**
 * This class is for the Entry of an Exam.
 * @author Adeem Adil Khatri
 * @version 19/04/2019
 */

public class Entry 
{
	
	//Constants
	public static int VISIBLE_ALWAYS = 1;
	public static int DURING_EXAM = 1;
	public static int VISIBLE_NEVER = 1;
	
	//Properties
	private int visible;
	private String title;
	private String id;
	private String content;
	
	//Constructors
	/**
	 * Constructs a basic new Entry with default values
	 */
	public Entry()
	{
		visible = 0;
		title = "";
		id = "";
		content = "";
	}
	
	/**
	 * Constructs a basic new Entry with provided values
	 */
	public Entry( int visible, String title, String id, String content)
	{
		this.visible = visible;
		this.title = title;
		this.content = content;
		this.id = id;
	}
	
	//methods
	
	/**
	 * @return visible
	 */
	public int getVisible() 
	{
		return visible;
	}

	/**
	 * @param visible
	 */
	public void setVisible( int visible) 
	{
		this.visible = visible;
	}

	/**
	 * @return title
	 */
	public String getTitle() 
	{
		return title;
	}

	/**
	 * @param title
	 */
	public void setTitle( String title) 
	{
		this.title = title;
	}

	/**
	 * @return id
	 */
	public String getId() 
	{
		return id;
	}

	/**
	 * @param id
	 */
	public void setId( String id) 
	{
		this.id = id;
	}

	/**
	 * @return content
	 */
	public String getContent() 
	{
		return content;
	}

	/**
	 * @param content
	 */
	public void setContent( String content) 
	{
		this.content = content;
	}

}
