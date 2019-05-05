package admin.model;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.ArrayList;

import admin.NitoAdminView;
import common.network.Screenshot;
import common.network.Server;

/**
 * The main model class for admin interface
 * @author Ziya Mukhtarov
 * @version 04/05/2019
 */
public class Model
{
	private static final String secret = "24DdwVJljT28m6MSOfvMnj7iZbL8bNMmo7xnLKsZSyurflOLg2JFtq0hsY09";
	public static final int STATUS_PREPARATION = 1;
	public static final int STATUS_EXAM_MODE = 2;
	public static final int STATUS_GRADING = 3;

	private int status;
	private ArrayList<NitoAdminView> views;
	private Examinees examinees;
	private Server server;
	private Container entries;

	/**
	 * Creates new Model for Nito admin interface
	 */
	public Model()
	{
		examinees = new Examinees();
		entries = new Container();
		status = STATUS_PREPARATION;
	}

	/**************************** PREPARATION ****************************/
	// TODO

	/**************************** MONITORING *****************************/
	/**
	 * Start the specified exam
	 * @param e the exam to start TODO
	 */
	public void startExam( Exam exam)
	{
		Model _this = this;
		try
		{
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
					String[] parts = msg.split( ":::");

					if ( !parts[0].equals( secret))
					{
						// Not from Nito. Ignore
						return;
					}

					if ( parts[1].equals( "name"))
					{
						// Connection request
						Examinee examinee = createExaminee( parts[2], socket);
						exam.send( examinee, _this);
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
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		status = STATUS_EXAM_MODE;
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
	 * Sends the message to the specified examinee
	 * @param msg      The message to send
	 * @param examinee The examinee to send the message to
	 */
	public void sendMessage( String msg, String type, Examinee examinee)
	{
		server.sendMessage( secret + ":::" + type + ":::" + msg, examinee.getSocket().getInetAddress());
	}

	/**
	 * @return The examinees
	 */
	public Examinees getExaminees()
	{
		return examinees;
	}

	/****************************** GRADING ******************************/
	// TODO

	/****************************** GENERAL ******************************/
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

	/**
	 * @return Current status of this model
	 */
	public int getStatus()
	{
		return status;
	}
}
