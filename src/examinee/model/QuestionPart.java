package examinee.model;

import common.network.*;

public class QuestionPart extends ExamEntry implements Submitable
{
	// properties
	private String solution;

	public QuestionPart( String id, String title, String content, boolean markable, boolean editable)
	{
		super( id, title, content, markable, editable);
		solution = "";
	}

	public QuestionPart( String id, String title, String content, boolean markable, boolean editable, String answerTemplate)
	{
		super( id, title, content, markable, editable);
		solution = answerTemplate;
	}

	public void updateSolution( String s)
	{
		solution = s;
	}

	@Override
	public void submit( Client c)
	{
		c.sendMessage( SECRET + ":::" + "solution" + ":::" + id + solution);
	}

}
