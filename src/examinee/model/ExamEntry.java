package examinee.model;

import network.*;

public class ExamEntry extends ExamContainer
{
	private String id;
	private boolean markable;
	private boolean editable;
	private boolean done;
	private String title;
	protected String content;
	
	public ExamEntry( String id, String title, String content, boolean markable, boolean editable)
	{
		super();
		
		this.id = id;
		this.title = title;
		this.content = content;
		
		this.markable = markable;
		this.editable = editable;
		done = false;
	}
	
	public void send( Client c)
	{
		c.sendMessage(content);
	}
	
	public void setDone()
	{
		done = !done;
	}
	
	public boolean isDone()
	{
		return done;
	}
}
