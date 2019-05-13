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
	private String statement;

	public QuestionPart( String id, String title, String content, String statement, boolean markable, boolean editable)
	{
		super( id, title, content, markable, editable);
		this.setStatement(statement);
	}
	
	// Alternative constructor for creating a QuestionPart with added template
	public QuestionPart( String id, String title, String content, String statement, boolean markable, boolean editable, String answerTemplate)
	{
		super( id, title, content, markable, editable);
		this.setStatement(statement);
		this.content = answerTemplate;
	}

	/**
	 * Update solution property
	 * @param s New solution as string
	 */
	public void updateSolution( String s)
	{
		this.content = s;
	}
	
	public String getStatement()
	{
		return statement;
	}

	public void setStatement(String statement)
	{
		this.statement = statement;
	}

	/**
	 * Submit question id and solution
	 * @param c Client to use in submit action
	 */
	@Override
	public void submit( Client c)
	{
		Model.getInstance().sendMessage( "solution", id + ":::" + content);
	}

}
