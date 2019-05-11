package admin.model.exam_entries;

import admin.model.Examinee;
import admin.model.Model;

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
	 * This constructs an instance of Criteria using superclass's constructor, with
	 * the given title.
	 * @param title
	 */
	public Template( String title)
	{
		super( title);
	}

	@Override
	public void send( Examinee e, Model m)
	{
		m.sendMessage( "template", id + Model.MESSAGE_SEPERATOR + content + Model.MESSAGE_SEPERATOR + parent.id, e);
	}
}
