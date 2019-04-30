package examinee.model;

import network.*;

public class QuestionPart extends ExamEntry implements Sendable
{
	// properties
	private String solution;
	
	public QuestionPart(String id, String title, String content, boolean markable, boolean editable)
	{
		super(id, title, content, markable, editable);
		solution = "";
	}
	
	public QuestionPart(String id, String title, String content, boolean markable
			, boolean editable, String answerTemplate)
	{
		super(id, title, content, markable, editable);
		solution = answerTemplate;
	}


	@Override
	public void send()
	{
		
	}
	
	public void updateSolution(String s)
	{
		solution = s;
	}
	
	public void save()
	{
		
	}
	
	public void submit( Client c)
	{
		this.content = solution;
		super.send(c);
	}

}
