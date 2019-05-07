package admin.model.exam_entries;

/**
 * This class is for Template
 * @author Adeem Adil Khatri<br>
 *         Ziya Mukhtarov
 * @version 07/05/2019
 */
public class Template extends Entry
{
	// properties

	// constructors
	public Template( String title, String content)
	{
		super( title, content);
	}

	// methods
	@Override
	public String toString()
	{
		return "[Title= " + super.getTitle() + ", content= " + super.getContent() + "]";
	}
}
