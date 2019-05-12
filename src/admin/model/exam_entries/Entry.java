package admin.model.exam_entries;

import admin.model.Examinee;
import admin.model.Model;

/**
 * This class is for the Entry of an Exam.
 * @author Adeem Adil Khatri<br>
 *         Ziya Mukhtarov
 * @version 07/05/2019
 */
public class Entry extends Container
{
	// Constants
	private static final long serialVersionUID = 6649922102661498183L;

	// Properties
	protected String title;
	protected String content;
	protected Container parent;

	// Constructors
	/**
	 * Constructs a basic new Entry with provided values
	 */
	public Entry( String title)
	{
		this.title = title;
		content = "";
		parent = null;
	}

	// methods
	/**
	 * This method sends the data of this Entry to Examinee from the specified
	 * Model. This method should be overridden if this entry should be sendable.
	 * Otherwise, this method does not do anything.
	 * @param e The Examinee to which the data is to be send
	 * @param m The model from which the data is to be send
	 */
	public void send( Examinee e, Model m)
	{
	}

	/**
	 * Finds the first ancestor of this entry of the specified type. If this entry
	 * has the specified type, then it will be returned
	 * @param type The type of the ancestor to look for
	 * @return The specified ancestor, or null if that could not be found
	 */
	public Entry findFirstAncestor( Class<?> type)
	{
		if ( getClass() == type)
		{
			return this;
		}
		if ( parent == null || !(parent instanceof Entry))
		{
			return null;
		}
		return ((Entry) parent).findFirstAncestor( type);
	}

	/**
	 * @return The title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title - The title to set
	 */
	public void setTitle( String title)
	{
		this.title = title;
	}

	/**
	 * @return The content
	 */
	public String getContent()
	{
		return content;
	}

	/**
	 * @param content - The content to set
	 */
	public void setContent( String content)
	{
		this.content = content;
	}

	@Override
	public String toString()
	{
		return title;
	}

	/**
	 * @return The parent
	 */
	public Container getParent()
	{
		return parent;
	}

	/**
	 * @param parent The parent to set
	 */
	public void setParent( Container parent)
	{
		this.parent = parent;
	}
}
