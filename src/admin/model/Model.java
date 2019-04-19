package admin.model;

import java.net.DatagramPacket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

import admin.NitoAdminView;
import network.Screenshot;
import network.Server;

/**
 * The main model class for admin interface
 * @author Ziya Mukhtarov
 * @version 18/04/2019
 */
public class Model
{
	private ArrayList<NitoAdminView> views;

	private ArrayList<Group> groups;

	private Server server;
	private TreeMap<String, Examinee> socketMap;

	/**
	 * Creates new Model for Nito admin interface
	 */
	public Model()
	{
		groups = new ArrayList<>();
		groups.add( Group.DEFAULT);

		server = new Server() {
			@Override
			public void connectionEstablished( Socket socket)
			{
			}

			@Override
			public void connectionTerminated( Socket socket)
			{
				// TODO Auto-generated method stub
			}

			@Override
			public void messageReceived( String msg, Socket socket)
			{
				String[] parts = msg.split( ":");

				if ( parts[0].equals( "name"))
				{
					// Connection request
					createExaminee( parts[1], socket);
				}
				else
				{
					// TODO
				}
			}

			@Override
			public void screenshotReceived( Screenshot img, DatagramPacket packet)
			{
				Examinee e = socketMap.get( packet.getAddress().getHostAddress());
				if ( e != null)
				{
					e.setScreen( img);
				}
				else
				{
					System.out.println( "Received screenshot, but examinee not initialized! From IP: " + packet.getAddress().getHostAddress());
				}
			}
		};

		socketMap = new TreeMap<>();
	}

	/**
	 * Creates new group for examinees with the specified title
	 * @param title The title
	 * @return The reference to created Group
	 */
	public Group createGroup( String title)
	{
		groups.add( new Group( title));
		return groups.get( groups.size() - 1);
	}

	/**
	 * Creates new examinee with the specified name
	 * @param name   The name of the examinee
	 * @param socket The socket to this examinee
	 * @return The reference to created Examinee
	 */
	private Examinee createExaminee( String name, Socket socket)
	{
		Examinee e = new Examinee( name, socket);
		socketMap.put( socket.getInetAddress().getHostAddress(), e);
		return e;
	}

	/**
	 * Changes the group of an examinee
	 * @param e The examinee to change the group of
	 * @param g The new group of the examinee
	 */
	public void changeExamineeGroup( Examinee e, Group g)
	{
		e.setGroup( g);
	}

	/**
	 * Sends the message to the specified examinee
	 * @param msg      The message to send
	 * @param examinee The examinee to send the message to
	 */
	public void sendMessage( String msg, Examinee examinee)
	{
		server.sendMessage( msg, examinee.getSocket().getInetAddress());
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
	public Collection<Examinee> getExaminees()
	{
		return socketMap.values();
	}

	@Override
	public String toString()
	{
		return "Model [groups=" + groups + "]";
	}

	/**
	 * Adds a view to this model. The updateView method of this view will be
	 * automatically called whenever it is necessary
	 * @param view The view to add to this model
	 */
	public void addView( NitoAdminView view)
	{
		views.add( view);
	}

	/**
	 * Calls the update view method of all views added to this model
	 */
	private void updateViews()
	{
		// TODO Open new threads for updates?
		for ( NitoAdminView view : views)
		{
			view.updateView( this);
		}
	}
}
