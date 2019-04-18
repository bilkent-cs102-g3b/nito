package admin.model;

import java.util.ArrayList;

/**
 * The main model class for admin interface
 * @author Ziya Mukhtarov
 * @version 18/04/2019
 */
public class Model
{
	private ArrayList<Group> groups;
	private ArrayList<Examinee> examinees;

	/**
	 * Creates new Model for Nito admin interface
	 */
	public Model()
	{
		groups = new ArrayList<>();
		groups.add( Group.DEFAULT);

		examinees = new ArrayList<>();
	}

	/**
	 * Creates new group for examinees with the specified title
	 * @param title - The title
	 * @return The reference to created Group
	 */
	public Group createGroup( String title)
	{
		groups.add( new Group( title));
		return groups.get( groups.size() - 1);
	}

	/**
	 * Creates new examinee with the specified name
	 * @param name - The name of the examinee
	 * @return The reference to created Examinee
	 */
	public Examinee createExaminee( String name)
	{
		examinees.add( new Examinee( name));
		return examinees.get( examinees.size() - 1);
	}

	/**
	 * Changes the group of an examinee
	 * @param e - The examinee to change the group of
	 * @param g - The new group of the examinee
	 */
	public void changeExamineeGroup( Examinee e, Group g)
	{
		e.setGroup( g);
	}

	/**
	 * @return The groups
	 */
	public ArrayList<Group> getGroups()
	{
		return groups;
	}

	/**
	 * @return The examinees
	 */
	public ArrayList<Examinee> getExaminees()
	{
		return examinees;
	}

	@Override
	public String toString()
	{
		return "Model [groups=" + groups + "]";
	}
}
