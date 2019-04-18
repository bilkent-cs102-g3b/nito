package admin.model;

import java.util.ArrayList;

/**
 * This class handles object IDs for Nito. It takes care for unique IDs
 * @author Ziya Mukhtarov
 * @version 18/04/2019
 */
public class IDGenerator
{
	public static final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789";
	public static final int LENGTH = 10;

	private static ArrayList<String> ids = new ArrayList<>();

	/**
	 * Generates a unique random ID for the specified className and marks this ID as
	 * used
	 * @param className - The name of the class this id will represent
	 * @return Unique random ID
	 */
	public static String generate( String className)
	{
		String id;

		id = randomGenerate( className);
		while ( ids.indexOf( id) != -1)
		{
			id = randomGenerate( className);
		}

		ids.add( id);
		return id;
	}

	/**
	 * Randomly generates an ID without checking for uniqueness
	 * @param className
	 * @return Random ID
	 */
	private static String randomGenerate( String className)
	{
		String id;

		id = className + "#";
		while ( id.length() < LENGTH + className.length())
		{
			id += ALLOWED_CHARS.charAt( (int) (Math.random() * ALLOWED_CHARS.length()));
		}

		return id;
	}
}
