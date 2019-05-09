package examinee.model;

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

	public void setDone()
	{
		done = !done;
	}

	public boolean isDone()
	{
		return done;
	}

	public void setParent( ExamContainer examContainer)
	{
		parent = examContainer;

	}

	public ExamContainer getParent()
	{
		return parent;
	}
}
