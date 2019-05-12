package admin.model;

import java.net.Socket;
import java.util.TreeMap;

/**
 * For handling all the work associated with examinees during exam
 * @author Ziya Mukhtarov
 * @version 04/05/2019
 */
public class Examinees
{
	private TreeMap<String, Examinee> socketMap;

	/**
	 * Initializes examinees with empty set
	 */
	public Examinees()
	{
		socketMap = new TreeMap<>();
	}

	/**
	 * Returns the examinee according to remote IP
	 * @param ip The remote IP address of examinee
	 * @return Found examinee, or null if no examinee found
	 */
	public Examinee getByIP( String ip)
	{
		return socketMap.get( ip);
	}

	/**
	 * Returns all found examinees according to the searched text
	 * @param text The string to search for
	 * @return An array of found examinees
	 */
	public Examinee[] search( String text)
	{
		// TODO ConcurrentModificationException
		return socketMap.values().stream().filter( e -> e.getStringForSearch().toLowerCase().indexOf( text.toLowerCase()) != -1).toArray( Examinee[]::new);
	}

	/**
	 * Creates a new examinee
	 * @param name   Name of the examinee
	 * @param socket The socket to this examinee
	 * @return The reference to newly created Examinee
	 */
	public Examinee newExaminee( String name, Socket socket)
	{
		// TODO check if socket already exists in map?
		Examinee e = new Examinee( name, socket);
		socketMap.put( socket.getInetAddress().getHostAddress(), e);
		return e;
	}
}
