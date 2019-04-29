package admin.model;

import java.net.DatagramPacket;
import java.net.Socket;
import java.util.ArrayList;

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
	private String secret = "24DdwVJljT28m6MSOfvMnj7iZbL8bNMmo7xnLKsZSyurflOLg2JFtq0hsY09";

	private ArrayList<NitoAdminView> views;

	private ArrayList<Group> groups;
	private Examinees examinees;

	private Server server;

	/**
	 * Creates new Model for Nito admin interface
	 */
	public Model()
	{
		groups = new ArrayList<>();
		groups.add( Group.DEFAULT);

		examinees = new Examinees();

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

				if ( !parts[0].equals( secret))
				{
					// Not from Nito. Ignore
					return;
				}

				if ( parts[1].equals( "name"))
				{
					// Connection request
					createExaminee( parts[2], socket);
				}
				else
				{
					// TODO
				}
			}

			@Override
			public void screenshotReceived( Screenshot img, DatagramPacket packet)
			{
				Examinee e = examinees.getByIP( packet.getAddress().getHostAddress());
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
	}

	/**
	 * Creates new group for examinees with the specified title
	 * @param title The title
	 * @return The reference to created Group
	 */
	public Group createGroup( String title)
	{
		groups.add( new Group( title));
		updateViews();
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
		Examinee e = examinees.newExaminee( name, socket);
		updateViews();
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
		updateViews();
	}

	/**
	 * Sends the message to the specified examinee
	 * @param msg      The message to send
	 * @param examinee The examinee to send the message to
	 */
	public void sendMessage( String msg, Examinee examinee)
	{
		server.sendMessage( secret + ":message:" + msg, examinee.getSocket().getInetAddress());
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
	public Examinees getExaminees()
	{
		return examinees;
	}

	@Override
	public String toString()
	{
		return "Model [groups=" + groups + "]";
	}

	/**
	 * Adds a view to this model and immediately calls updateView method. The
	 * updateView method of this view will be automatically called whenever it is
	 * necessary
	 * @param view The view to add to this model
	 */
	public void addView( NitoAdminView view)
	{
		views.add( view);
		view.updateView( this);
	}

	/**
	 * Calls the update view method of all views added to this model
	 */
	private void updateViews()
	{
		Model ref = this;
		for ( NitoAdminView view : views)
		{
			new Thread( new Runnable() {
				@Override
				public void run()
				{
					view.updateView( ref);
				}
			}).start();
		}
	}
}
