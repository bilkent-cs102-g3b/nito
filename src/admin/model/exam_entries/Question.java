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
	public Question( String title, int maxPoints)
	{
		super( title);
		this.maxPoints = maxPoints;
	}

	// Methods
	@Override
	public void send( Examinee e, Model m)
	{
		m.sendMessage( "question", id + Model.MESSAGE_SEPERATOR + title, e);
		sendAll( e, m);
	}

	/**
	 * @param points
	 */
	public void setMaxPoints( int points)
	{
		maxPoints = points;
	}

	/**
	 * @return maxPoints
	 */
	public int getMaxPoints()
	{
		return maxPoints;
	}
}
