package common.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Simple TCP client for sending and receiving messages
 * @author Ziya Mukhtarov
 * @version 01/05/2019
 */
public abstract class TcpClient
{
	private Socket socket;
	private Thread msgListenerThread;

	/**
	 * Creates a TCP client and connects it to the specified server
	 * @param serverAddress The IP address of the server
	 * @param serverPort    The port that the server is listening
	 * @throws IOException          if an I/O error occurs when creating the socket.
	 * @throws UnknownHostException if no IP address for the host could be found, or
	 *                              if a scope_id was specified for a global IPv6
	 *                              address
	 */
	public TcpClient( String serverAddress, int serverPort) throws UnknownHostException, IOException
	{
		socket = new Socket( serverAddress, serverPort);
		msgListenerThread = new Thread( new Runnable() {
			public void run()
			{
				listenForMessages();
			}
		});
		msgListenerThread.start();
	}

	/**
	 * Handles the received message
	 * @param msg The message received
	 */
	public abstract void received( String msg);

	/**
	 * Sends a message to the server
	 * @param msg The message to be sent
	 */
	public void sendMessage( String msg)
	{
		msg += Server.TERMINATION;
		byte[] data = msg.getBytes();
		try
		{
			socket.getOutputStream().write( data);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Continuously listens for any message on the socket. Calls received(message)
	 * once a message arrives. <br>
	 * Warning: This method blocks until the socket is closed or some error occurs
	 */
	private void listenForMessages()
	{
		String msg;
		InputStream stream;

		try
		{
			stream = socket.getInputStream();
		}
		catch (IOException e)
		{
			return;
		}

		while ( true)
		{
			msg = "";
			while ( !msg.contains( Server.TERMINATION))
			{
				try
				{
					msg += (char) stream.read();
				}
				catch (IOException e)
				{
					e.printStackTrace();
					return;
				}
			}

			received( msg.substring( 0, msg.length() - 3));
		}
	}

	/**
	 * Closes the TCP connection
	 */
	public void close()
	{
		msgListenerThread.interrupt();
		try
		{
			socket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
