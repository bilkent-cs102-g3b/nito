package network;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * A server containing TCP and UDP servers
 * @author Ziya Mukhtarov
 * @version 17/04/2019
 */
public abstract class Server
{
	public static final String TERMINATION = "###";
	public static final int TCP_PORT = 4455;
	public static final int UDP_PORT = 4456;

	private TcpServer tcp;
	private UdpServer udp;

	/**
	 * Starts a TCP and UDP server
	 */
	public Server()
	{
		Server ths = this;

		tcp = new TcpServer(TCP_PORT)
		{
			public void connectionLost( Socket socket)
			{
				ths.connectionLost(socket);
			}

			public void received( String message, Socket socket)
			{
				ths.messageReceived(message, socket);
			}
		};

		udp = new UdpServer(UDP_PORT)
		{
			public void screenshotReceived( Screenshot img, DatagramPacket packet)
			{
				ths.screenshotReceived(img, packet);
			}
		};
	}

	/**
	 * Handles the closed connection
	 * @param socket - the socket that has been closed
	 */
	public abstract void connectionLost( Socket socket);

	/**
	 * Processes the received message
	 * @param msg - the contents of the message
	 * @param socket - the connection that received the message from
	 */
	public abstract void messageReceived( String msg, Socket socket);

	/**
	 * Processes the received Screenshot
	 * @param img - the received Screenshot via packet
	 * @param packet - The Datagram Packet
	 */
	public abstract void screenshotReceived( Screenshot img, DatagramPacket packet);

	/**
	 * Sends a message to all connected clients 
	 * @param msg - The message to be sent
	 */
	public void sendMessageToAll( String msg)
	{
		msg += TERMINATION;
		tcp.sendMessageToAll( msg);
	}

	/**
	 * Sends a message to the specified client
	 * @param msg - The message to be sent
	 * @param address - The InetAddress of the client
	 */
	public void sendMessage( String msg, InetAddress address)
	{
		msg += TERMINATION;
		tcp.sendMessage( msg, address);
	}

	/**
	 * Sends a message to the specified client
	 * @param msg - The message to be sent
	 * @param address - The IP of the client
	 * @throws UnknownHostException if no IP address for the host could be found, or if a scope_id was specified for a global IPv6 address
	 */
	public void sendMessage( String msg, String address) throws UnknownHostException
	{
		msg += TERMINATION;
		tcp.sendMessage( msg, address);
	}

	/**
	 * Closes the server
	 */
	public void close()
	{
		udp.close();
		tcp.close();
	}
}
