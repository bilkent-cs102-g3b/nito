package common.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Simple TCP Server for sending and receiving messages
 * @author Ziya Mukhtarov
 * @version 01/05/2019
 */
public abstract class TcpServer
{
	private ServerSocket server;
	private ArrayList<Socket> sockets;
	private ArrayList<Thread> msgListenerThreads;
	private volatile boolean isBlocked;
	private Thread connectionListenerThread;

	/**
	 * Opens a new socket for receiving TCP connections
	 * @param port The port to listen on
	 * @throws IOException if an I/O error occurs when opening the socket.
	 */
	public TcpServer( int port) throws IOException
	{
		isBlocked = false;
		sockets = new ArrayList<>();
		msgListenerThreads = new ArrayList<>();
		server = new ServerSocket( port);
		// Listens for connections
		connectionListenerThread = new Thread( new Runnable() {
			@Override
			public void run()
			{
				listenForConnections();
			}
		});
		connectionListenerThread.start();
	}

	/**
	 * Handles the new opened connection
	 * @param socket The new socket
	 */
	public abstract void connectionEstablished( Socket socket);

	/**
	 * Handles the closed connection
	 * @param socket The socket that was closed
	 */
	public abstract void connectionTerminated( Socket socket);

	/**
	 * Processes the incoming message
	 * @param message The message received
	 * @param socket  The socket that the message received from
	 */
	public abstract void received( String message, Socket socket);

	/**
	 * Listens for connections and creates a new socket for them
	 */
	private void listenForConnections()
	{
		while ( true)
		{
			Socket socket;
			try
			{
				socket = server.accept();
			}
			catch (IOException e)
			{
				break;
			}

			// Wait until the work on sockets finishes
			while ( isBlocked)
			{
				try
				{
					Thread.sleep( 100);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
			isBlocked = true;
			sockets.add( socket);
			isBlocked = false;
			connectionEstablished( socket);

			// Listens for messages
			msgListenerThreads.add( new Thread( new Runnable() {
				public void run()
				{
					listenForMessages( socket);
				}
			}));
			msgListenerThreads.get( msgListenerThreads.size() - 1).start();
		}
	}

	/**
	 * Listens for incoming messages from the specified socket
	 * @param socket The socket to listen on
	 */
	private void listenForMessages( Socket socket)
	{
		String message = "";
		boolean isAlive = true;

		while ( isAlive)
		{
			try
			{
				message += (char) socket.getInputStream().read();
			}
			catch (IOException e)
			{
				isAlive = false;
			}

			if ( message.contains( Server.TERMINATION))
			{
				received( message.substring( 0, message.length() - 3), socket);
				message = "";
			}
		}

		connectionTerminated( socket);
		while ( isBlocked)
		{
			try
			{
				Thread.sleep( 100);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
		isBlocked = true;
		sockets.remove( socket);
		isBlocked = false;
	}

	/**
	 * Sends a message over all available connections
	 * @param msg The message to send
	 */
	public void sendMessageToAll( String msg)
	{
		ArrayList<Socket> erase = new ArrayList<>();

		byte[] data = msg.getBytes();
		for ( Socket socket : sockets)
		{
			try
			{
				socket.getOutputStream().write( data);
			}
			catch (IOException e)
			{
				connectionTerminated( socket);
				erase.add( socket);
			}
		}

		while ( isBlocked)
		{
			try
			{
				Thread.sleep( 100);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
		isBlocked = true;
		sockets.removeAll( erase);
		isBlocked = false;
	}

	/**
	 * Send a message to the specified address
	 * @param msg     The message to send
	 * @param address The address of the intended receiver
	 */
	public void sendMessage( String msg, InetAddress address)
	{
		ArrayList<Socket> erase = new ArrayList<>();

		byte[] data = msg.getBytes();
		for ( Socket socket : sockets)
		{
			if ( address.equals( socket.getInetAddress()))
			{
				try
				{
					socket.getOutputStream().write( data);
				}
				catch (IOException e)
				{
					e.printStackTrace();
					connectionTerminated( socket);
					erase.add( socket);
				}
			}
		}

		while ( isBlocked)
		{
			try
			{
				Thread.sleep( 100);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
		isBlocked = true;
		sockets.removeAll( erase);
		isBlocked = false;
	}

	/**
	 * Send a message to the specified address
	 * @param msg     The message to send
	 * @param address The address of the intended receiver
	 * @throws UnknownHostException if no IP address for the host could be found, or
	 *                              if a scope_id was specified for a global IPv6
	 *                              address
	 */
	public void sendMessage( String msg, String address) throws UnknownHostException
	{
		sendMessage( msg, InetAddress.getByName( address));
	}

	/**
	 * Closes the TCP server
	 */
	public void close()
	{
		// Closing listeners
		for ( Thread msgListenerThread : msgListenerThreads)
		{
			msgListenerThread.interrupt();
		}
		connectionListenerThread.interrupt();

		// Closing connections
		for ( Socket socket : sockets)
		{
			try
			{
				socket.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		// Closing the server
		try
		{
			server.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
