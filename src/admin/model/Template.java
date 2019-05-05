package admin.model;
/**
 * This class is for Template
 * @author Adeem Adil Khatri
 * @version 5/05/2019
 */
public class Template extends Entry
{
	private String title;
	private String content;
	
	public Template()
	{
		title = "";
		content = "";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContentl(String content) {
		this.content = content;
	}
	
	@Override
	public String toString()
	{
		return "[Title= " + title + ", content= " + content + "]";
	}
}
