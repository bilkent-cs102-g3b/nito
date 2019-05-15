package admin.model.exam_entries;

import java.io.ObjectInputStream;

import admin.model.Examinee;
import admin.model.Model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * This class is for Exam
 * @author Adeem Adil Khatri<br>
 *         Ziya Mukhtarov
 * @version 14/05/2019
 */
public class Exam extends Entry
{
	// properties
	private static final long serialVersionUID = -6393169094783192139L;

	/**
	 * Duration of the exam in seconds
	 */
	private int length;
	private transient IntegerProperty timeLeft;
	private boolean hasInstructions;
	private transient boolean running = false;

	// constructors
	/**
	 * Creates an Exam
	 * @param title
	 * @param length
	 */
	public Exam( String title, int length)
	{
		super( title);
		running = false;
		hasInstructions = false;
		this.length = length;
		timeLeft = new SimpleIntegerProperty( length);
	}

	// methods

	@Override
	public void send( Examinee e, Model m)
	{
		m.sendMessage( "exam", id + Model.MESSAGE_SEPERATOR + title + Model.MESSAGE_SEPERATOR + length, e);
		sendAll( e, m);
		m.sendMessage( "data_end", "", e);
	}

	/**
	 * Starts this exam
	 */
	public void start()
	{
		timeLeft.set( length);
		running = true;
		new Thread( new Runnable() {
			@Override
			public void run()
			{
				while ( timeLeft.get() > 0)
				{
					try
					{
						Thread.sleep( 1000);
						timeLeft.set( timeLeft.get() - 1);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
						Thread.currentThread().interrupt();
					}
				}
				running = false;
				Model.getInstance().endCurrentExam();
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

	public void stop()
	{
		running = false;
	}

	public boolean hasInstructions()
	{
		return hasInstructions;
	}

	/**
	 * @return The time left until the end of this exam in seconds.
	 */
	public int getTimeLeft()
	{
		return timeLeft.get();
	}

	/**
	 * @return The time left until the end of this exam in seconds.
	 */
	public IntegerProperty timeLeftProperty()
	{
		return timeLeft;
	}

	/**
	 * @return The time elapsed from the beginning of this exam in seconds.
	 */
	public int getTimeElapsed()
	{
		return length - timeLeft.get();
	}

	/**
	 * @return true if the exam is running now
	 */
	public boolean isRunning()
	{
		return running;
	}
	
	/**
	 * Deserialization method
	 */
	private void readObject( ObjectInputStream ois) throws Exception
	{
		ois.defaultReadObject();
		timeLeft = new SimpleIntegerProperty( length);
		running = false;
	}
}
