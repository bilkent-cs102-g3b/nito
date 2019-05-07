package admin.model.exam_entries;

import java.io.Serializable;
import java.util.ArrayList;

import admin.model.Examinee;
import admin.model.Model;

/**
 * This class is for a Container
 * @author Adeem Adil Khatri<br>
 *         Ziya Mukhtarov
 * @version 19/04/2019
 */
public class Container implements Serializable
{
	private static final long serialVersionUID = -5805193951111349516L;

	// properties
	protected ArrayList<Entry> children;

	// constructors
	/**
	 * Constructs a basic new Container with default values
	 */
	public Container()
	{
		children = new ArrayList<Entry>();
	}

	// methods

	/**
	 * This method adds an entry to the Container of children
	 * @param entry to be added to the list of entries
	 */
	public void add( Entry entry)
	{
		children.add( entry);
	}

	/**
	 * This method gives all of the entries
	 * @return All of the ArrayList having type 'Entry'
	 */
	public ArrayList<Entry> getAll()
	{
		return children;
	}

	/**
	 * This method removes the specified entry from the list of entries
	 * @param entry to be removed from the list
	 */
	public void remove( Entry entry)
	{
		children.remove( entry);
	}

	/**
	 * Calls {@link Entry#send(Examinee, Model)} method of all children of this container
	 * @param e The Examinee to which the data is to be send
	 * @param m The model from which the data is to be send
	 */
	public void sendAll( Examinee e, Model m)
	{
		for ( Entry child : children)
		{
			child.send( e, m);
		}
	}
}
