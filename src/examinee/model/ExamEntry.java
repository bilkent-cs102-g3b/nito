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
	private String content;
	protected final String SECRET = "24DdwVJljT28m6MSOfvMnj7iZbL8bNMmo7xnLKsZSyurflOLg2JFtq0hsY09";

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
	
	/**
	 * Set parent property to another container and add this object to said container
	 * @param examContainer - container to add object to
	 */
	public void setParent( ExamContainer examContainer)
	{
		parent = examContainer;
		parent.add(this);
	}

	/**
	 * Return parent
	 * @return parent
	 */
	public ExamContainer getParent()
	{
		return parent;
	}
}
