package network;

import java.net.UnknownHostException;

/**
 * A client for TCP and UDP connection
 * @author Ziya Mukhtarov
 * @version 21/02/2019
 */
public abstract class Client
{
	private TcpClient tcp;
	private UdpClient udp;

	/**
	 * Connects the client to the specified server
	 * @param serverAddress - The IP address of the server
	 * @throws UnknownHostException if no IP address for the server could be found, or if a scope_id was specified for a global IPv6 address
	 */
	public Client(String serverAddress) throws UnknownHostException
	{
		tcp = new TcpClient(serverAddress, Server.TCP_PORT)
		{
			public void received(String msg)
			{
				messageReceived(msg);
			}
		};

		udp = new UdpClient(serverAddress, Server.UDP_PORT);
	}

	/**
	 * Processes the received message
	 * @param msg - the contents of the message
	 */
	public abstract void messageReceived(String msg);

	/**
	 * Sends a message to the server
	 * @param msg - The message to be sent
	 */
	public void sendMessage(String msg)
	{
		tcp.sendMessage(msg);
	}

	/**
	 * Sends a Screenshot to the server
	 * @param screenshot - The Screenshot to be sent
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
