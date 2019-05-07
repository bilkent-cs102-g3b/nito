package admin.model;

import java.util.*;

/**
 * This class is for a Container 
 * @author Adeem Adil Khatri
 * @version 19/04/2019
 */

public class Container
{
	//properties
	private ArrayList<Entry> children;
	
	//constructors
	/**
	 * Constructs a basic new Container with default values
	 */
	public Container() 
	{
		children = new ArrayList<Entry>();
	}		
	
	//methods
	
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
}
