package examinee.model;

import common.network.*;

/**
 * Question part class which can be submitted
 * @author Alper Sari
 * @version 09/05/2019
 */
public class QuestionPart extends ExamEntry implements Submitable
{
	// properties
	private String solution;

	public QuestionPart( String id, String title, String content, boolean markable, boolean editable)
	{
		super( id, title, content, markable, editable);
		solution = "";
	}
	
	// Alternative constructor for creating a QuestionPart with added template
	public QuestionPart( String id, String title, String content, boolean markable, boolean editable, String answerTemplate)
	{
		super( id, title, content, markable, editable);
		solution = answerTemplate;
	}

	/**
	 * Update solution property
	 * @param s New solution as string
	 */
	public void updateSolution( String s)
	{
		solution = s;
	}

	/**
	 * Submit question id and solution
	 * @param c Client to use in submit action
	 */
	@Override
	public void submit( Client c)
	{
		c.sendMessage( SECRET + ":::" + "solution" + ":::" + id + solution);
	}

}
