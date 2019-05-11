package admin.model.exam_entries;

import java.io.Serializable;
import java.util.ArrayList;

import admin.model.Examinee;
import admin.model.IDHandler;
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
	protected String id;

	// constructors
	/**
	 * Constructs a basic new Container with default values
	 */
	public Container()
	{
		children = new ArrayList<Entry>();
		id = IDHandler.getInstance().generate( this);
	}

	// methods

	/**
	 * This method adds an entry to the Container of children
	 * @param entry to be added to the list of entries
	 */
	public void add( Entry entry)
	{
		children.add( entry);
		entry.setParent( this);
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
		entry.setParent( null);
	}

	/**
	 * Calls {@link Entry#send(Examinee, Model)} method of all children of this
	 * container
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

	/**
	 * Finds all entries of the given type in this container. Includes result from
	 * subcontainers too.
	 * @param type The type of entries to search for
	 * @return An arraylist containing all entries of the specified type
	 */
	public ArrayList<Entry> findAll( Class<?> type)
	{
		ArrayList<Entry> result = new ArrayList<>();

		getAll().stream().forEachOrdered( e -> result.addAll( e.findAll( type)));
		getAll().stream().filter( e -> e.getClass() == type).forEachOrdered( result::add);

		return result;
	}

	@Override
	public boolean equals( Object e)
	{
		if ( e == null || !(e instanceof Container))
			return false;
		return id.equals( ((Container) e).id);
	}

	/**
	 * @return The id
	 */
	public String getId()
	{
		return id;
	}
}
