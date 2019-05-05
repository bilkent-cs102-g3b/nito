package admin.model;

import java.util.ArrayList;

/**
 * <strong> Not to be used in beta version!!! </strong><br>
 * Grouping for examinees. One example of grouping can be sections.
 * @author Ziya Mukhtarov
 * @version 18/04/2019
 */
public class Group extends ArrayList<Examinee>
{
	private static final long serialVersionUID = 3723395721432132991L;

	/**
	 * All examinees without specified group should be added to this group
	 */
	public static final Group DEFAULT = new Group( "Default");

	private String id;
	private String title;

	/**
	 * Creates new Group with the specified title.
	 * @param title The title of the group
	 */
	public Group( String title)
	{
		super();

		this.id = IDHandler.getInstance().generate( getClass().getName());
		this.title = title;
	}

	/**
	 * @return The title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title The title to set
	 */
	public void setTitle( String title)
	{
		this.title = title;
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
		return "Group [id=" + id + ", title=" + title + ", examinees=" + super.toString() + "]";
	}
}
