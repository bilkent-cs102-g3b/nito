package admin.model.exam_entries;

import admin.model.Examinee;
import admin.model.Model;

/**
 * This class is for Question
 * @author Adeem Adil Khatri<br>
 *         Ziya Mukhtarov
 * @version 07/05/2019
 */
public class Question extends Entry
{
	private static final long serialVersionUID = -2378536027988908676L;

	// Properties
	private int maxPoints;

	// Constructors
	/**
	 * Creates a Question
	 * @param title
	 */
	public Question( String title)
	{
		super( title);
	}

	// Methods
	@Override
	public void send( Examinee e, Model m)
	{
		m.sendMessage( "question", id + Model.MESSAGE_SEPERATOR + title + Model.MESSAGE_SEPERATOR + content + Model.MESSAGE_SEPERATOR + parent.id, e);
		sendAll( e, m);
	}

	/**
	 * @return maxPoints
	 */
	public int getMaxPoints()
	{
		return maxPoints;
	}

	@Override
	public void add( Entry entry)
	{
		super.add( entry);
		if ( entry instanceof QuestionPart)
		{
			maxPoints += ((QuestionPart) entry).getMaxPoints();
		}
	}
}
