package admin.model.exam_entries;

import admin.model.Examinee;
import admin.model.Model;

/**
 * This class is for Exam
 * @author Adeem Adil Khatri<br>
 *         Ziya Mukhtarov
 * @version 07/05/2019
 */
public class Exam extends Entry
{
	// properties
	private static final long serialVersionUID = -6393169094783192139L;

	/**
	 * Duration of the exam in seconds
	 */
	private int length;
	private int timeLeft;
	private boolean hasInstructions;

	// constructors
	/**
	 * Creates an Exam
	 * @param title
	 * @param length
	 */
	public Exam( String title, int length)
	{
		super( title);
		hasInstructions = false;
		this.length = length;
		timeLeft = 0;
	}

	// methods

	@Override
	public void send( Examinee e, Model m)
	{
		m.sendMessage( "exam", id + Model.MESSAGE_SEPERATOR + title + Model.MESSAGE_SEPERATOR + length, e);
		sendAll( e, m);
	}

	/**
	 * Starts this exam
	 */
	public void start()
	{
		timeLeft = length;
		new Thread( new Runnable() {
			@Override
			public void run()
			{
				while ( timeLeft > 0)
				{
					try
					{
						Thread.sleep( 1000);
						timeLeft -= 1;
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
						Thread.currentThread().interrupt();
					}
				}
			}
		}).start();
	}

	@Override
	public void add( Entry entry)
	{
		if ( entry instanceof Instruction)
		{
			if ( !hasInstructions)
			{
				super.add( entry);
				hasInstructions = true;
			}
		}
		else
			super.add( entry);
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasInstructions()
	{
		return hasInstructions;
	}
	
	/**
	 * @return The time left until the end of this exam in seconds.
	 */
	public int getTimeLeft()
	{
		return timeLeft;
	}

	/**
	 * @return The time elapsed from the beginning of this exam in seconds.
	 */
	public int getTimeElapsed()
	{
		return length - timeLeft;
	}
}
