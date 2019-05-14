package admin.model;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.TreeMap;

/**
 * This class handles object IDs for Nito. It takes care for unique IDs. It uses
 * singleton pattern
 * @author Ziya Mukhtarov
 * @version 14/05/2019
 */
public class IDHandler implements Serializable
{
	private static final long serialVersionUID = 1404894489076444232L;

	public static final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789";
	public static final int LENGTH = 10;

	private static IDHandler instance;

	private TreeMap<String, Object> map;

	/**
	 * Creates new handler for identifiers
	 */
	private IDHandler()
	{
		map = new TreeMap<>();
	}

	/**
	 * @return the instance of this class
	 */
	public static IDHandler getInstance()
	{
		if ( instance == null)
		{
			instance = new IDHandler();
		}
		return instance;
	}

	/**
	 * Generates a unique random ID for the specified object and marks this ID as
	 * used
	 * @param o The object this id will represent
	 * @return Unique random ID
	 */
	public String generate( Object o)
	{
		String id;

		id = randomGenerate( o.getClass().getName());
		while ( map.containsKey( id))
		{
			id = randomGenerate( o.getClass().getName());
		}

		map.put( id, o);
		return id;
	}

	/**
	 * Randomly generates an ID without checking for uniqueness
	 * @param className The name of the class this id will represent
	 * @return Random ID
	 */
	private String randomGenerate( String className)
	{
		String id;

		id = className + "#";
		while ( id.length() < LENGTH + className.length())
		{
			id += ALLOWED_CHARS.charAt( (int) (Math.random() * ALLOWED_CHARS.length()));
		}

		return id;
	}

	/**
	 * @param id The identifier of the object to return
	 * @return The object identified by the specified id
	 */
	public Object getByID( String id)
	{
		return map.get( id);
	}

	/**
	 * Deserialization method
	 */
	private void readObject( ObjectInputStream ois) throws Exception
	{
		ois.defaultReadObject();
		instance = this;
	}
}
