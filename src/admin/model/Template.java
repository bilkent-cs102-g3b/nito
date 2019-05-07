package admin.model;
/**
 * This class is for Template
 * @author Adeem Adil Khatri
 * @version 6/05/2019
 */
public class Template extends Entry
{
	//properties
	private String title;
	private String content;
	
	//constructors
	public Template()
	{
		super();
	}
	
	public Template( String title, String content)
	{
		this.title = title;
		this.content = content;
	}
	
	//methods
		
	@Override
	/**
	 * @return Template details in String form
	 */
	public String toString()
	{
		return "[Title= " + super.getTitle() + ", content= " + super.getContent() + "]";
	}
}
