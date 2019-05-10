package examinee.model;

/**
 * Question class
 * @author Alper Sari
 * @version 09/05/2019
 */
public class Question extends ExamEntry
{

	public Question( String id, String title, String content, boolean markable, boolean editable)
	{
		super( id, title, content, markable, editable);
	}
}
