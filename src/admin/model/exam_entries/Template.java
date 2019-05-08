package admin.model.exam_entries;

/**
 * This class is for Template
 * @author Adeem Adil Khatri<br>
 *         Ziya Mukhtarov
 * @version 07/05/2019
 */
public class Template extends Entry
{
	private static final long serialVersionUID = 4189715828760784690L;
	// properties

	// constructors
	/**
	 * This constructs an instance of Criteria using superclass's constructor, with the given title.
	 * @param title
	 */
	public Template( String title)
	{
		super( title);
	}

	// methods
	
	/**
	 */
	@Override
	public String toString()
	{
		return "[Title= " + super.getTitle() + ", content= " + super.getContent() + "]";
	}
}
