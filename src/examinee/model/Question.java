package examinee.model;

public class Question extends ExamEntry
{

	public Question(String id, String title, String content, boolean markable, boolean editable)
	{
		super(id, title, content, markable, editable);
	}
	
	public void save()
	{
		//TODO: File saving system
	}
}
