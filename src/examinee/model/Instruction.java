package examinee.model;

/**
 * Instruction class
 * @author Alper Sari
 * @version 09/05/2019
 */
public class Instruction extends ExamEntry
{

	public Instruction( String id, String title, String content, boolean markable, boolean editable)
	{
		super( id, title, content, markable, editable);
	}
}
