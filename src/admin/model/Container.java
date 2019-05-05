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
	 * @param entry
	 */
	public void add( Entry entry)
	{
		children.add( entry);
	}
	
	public ArrayList<Entry> getAll() 
	{
		return children;
	}
	
	public void remove( Entry entry)
	{
		children.remove( entry);
	}
}
