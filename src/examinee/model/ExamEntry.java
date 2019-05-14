package examinee.model;

/**
 * A class for exam entry data, also extends container
 * @author Alper Sari
 * @version 09/05/2019
 */
public class ExamEntry extends ExamContainer
{
	protected String id;
	private boolean markable;
	private boolean editable;
	private boolean done;
	private ExamContainer parent;
	private String title;
	protected String content;

	public ExamEntry( String id, String title, String content, boolean markable, boolean editable)
	{
		super();

		this.id = id;
		this.title = title;
		this.content = content;
		parent = null;

		this.markable = markable;
		this.editable = editable;
		done = false;
	}

	/**
	 * Switch Done state
	 */
	public void setDone()
	{
		done = !done;
	}

	/**
	 * Return Done state
	 * @return Done
	 */
	public boolean isDone()
	{
		return done;
	}

	// TODO Crate get and set methods for all properties

	/**
	 * @return id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @return title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @return content
	 */
	public String getContent()
	{
		return content;
	}

	/**
	 * @return markable
	 */
	public boolean getMarkable()
	{
		return markable;
	}

	/**
	 * @return editable
	 */
	public boolean getEditable()
	{
		return editable;
	}

	/**
	 * Set parent property to another container and add this object to said
	 * container
	 * @param examContainer - container to add object to
	 */
	public void setParent( ExamContainer examContainer)
	{
		parent = examContainer;
	}

	/**
	 * Return parent
	 * @return parent
	 */
	public ExamContainer getParent()
	{
		return parent;
	}

	@Override
	public String toString()
	{
		return title;
	}
}
