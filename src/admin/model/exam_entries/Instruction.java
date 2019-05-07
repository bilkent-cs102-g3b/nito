package admin.model.exam_entries;

import admin.model.Examinee;
import admin.model.Model;

/**
 * This class is for Instruction
 * @author Adeem Adil Khatri<br>
 *         Ziya Mukhtarov
 * @version 07/05/2019
 */
public class Instruction extends Entry
{
	private static final long serialVersionUID = 6619354670367450793L;

	public Instruction( String title)
	{
		super( title);
	}

	@Override
	public void send( Examinee e, Model m)
	{
		m.sendMessage( "instruction", id + Model.MESSAGE_SEPERATOR + title + Model.MESSAGE_SEPERATOR + content, e);
	}
}
