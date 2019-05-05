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
		ArrayList<Entry> entries  = new ArrayList<Entry>();
		for( int x = 0; x < children.size(); x++)
		{
			entries.add( children.get( x));
		}
		return entries;
	}
	
	public void remove( Entry entry)
	{
		children.remove( entry);
	}
}
