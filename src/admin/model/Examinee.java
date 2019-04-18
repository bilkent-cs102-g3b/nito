package admin.model;

/**
 * This class is for one examinee during an exam
 * @author Ziya Mukhtarov
 * @version 18/04/2019
 */
public class Examinee
{
	public static int STATUS_NOT_CONNECTED = 1;
	public static int STATUS_CONNECTED = 2;
	public static int STATUS_BANNED = 3;
	public static int STATUS_SUSPENDED = 4;
	public static int STATUS_DONE = 5;

	private String id;
	private String name;
	// private Exam exam;
	private int status;
	private Group group;

	/**
	 * Creates a new examinee with the specified name
	 * @param name - The name of the examinee
	 */
	public Examinee( String name)
	{
		this( name, Group.DEFAULT);
	}

	/**
	 * Creates a new examinee with the specified name and assigns this to the group
	 * @param name  - The name of the examinee
	 * @param group - The group to add this examinee to
	 */
	public Examinee( String name, Group group)
	{
		id = IDGenerator.generate( getClass().getName());
		this.name = name;
		setGroup( group);
		status = STATUS_NOT_CONNECTED;
	}

	/**
	 * @return The name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return The group
	 */
	public Group getGroup()
	{
		return group;
	}

	/**
	 * @param group - The group to set
	 */
	public void setGroup( Group group)
	{
		if ( this.group != null)
		{
			this.group.remove( this);
		}
		this.group = group;
		group.add( this);
	}

	/**
	 * @return The id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @return The status
	 */
	public int getStatus()
	{
		return status;
	}

	@Override
	public String toString()
	{
		return "Examinee [id=" + id + ", name=" + name + ", status=" + status + ", group=" + group.getTitle() + "]";
	}
}
