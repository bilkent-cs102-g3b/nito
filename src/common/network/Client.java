package common.network;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * A client for TCP and UDP connection
 * @author Ziya Mukhtarov
 * @version 01/05/2019
 */
public abstract class Client
{
	private TcpClient tcp;
	private UdpClient udp;

	/**
	 * Connects the client to the specified server
	 * @param serverAddress The IP address of the server
	 * @throws IOException          if an I/O error occurs when creating the socket.
	 * @throws UnknownHostException if no IP address for the host could be found, or
	 *                              if a scope_id was specified for a global
	 *                              IPv6address
	 */
	public Client( String serverAddress) throws UnknownHostException, IOException
	{
		tcp = new TcpClient( serverAddress, Server.TCP_PORT) {
			public void received( String msg)
			{
				messageReceived( msg);
			}
		};

		udp = new UdpClient( serverAddress, Server.UDP_PORT);
	}

	/**
	 * Processes the received message
	 * @param msg The contents of the message
	 */
	public abstract void messageReceived( String msg);

	/**
	 * Sends a message to the server
	 * @param msg The message to be sent
	 */
	public void sendMessage( String msg)
	{
		tcp.sendMessage( msg);
	}

	/**
	 * Sends a Screenshot to the server
	 * @param screenshot The Screenshot to be sent
	 */
	public void sendImage( Screenshot screenshot)
	{
		udp.sendScreenshot( screenshot);
	}

	/**
	 * Closes the connection with the server
	 */
	public void close()
	{
		udp.close();
		tcp.close();
	}
}
