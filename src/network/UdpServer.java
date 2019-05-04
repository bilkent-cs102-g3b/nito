package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Simple UDP Server for receiving images
 * @author Ziya Mukhtarov
 * @version 01/05/2019
 */
public abstract class UdpServer
{
	private DatagramSocket socket;
	private Thread listenerThread;

	/**
	 * Opens a new socket for receiving UDP connections
	 * @param port The port to listen on
	 * @throws SocketException if the socket could not be opened, or the socket
	 *                         could not bind to the specified local port.
	 */
	public UdpServer( int port) throws SocketException
	{
		socket = new DatagramSocket( port);

		listenerThread = new Thread( new Runnable() {
			public void run()
			{
				readScreenshot();
			}
		});
		listenerThread.start();
	}

	/**
	 * Handles the received Screenshot
	 * @param img    the Screenshot received via the packet
	 * @param packet The Datagram Packet
	 */
	public abstract void screenshotReceived( Screenshot img, DatagramPacket packet);

	/**
	 * Receives the screenshot sent to this server
	 */
	private void readScreenshot()
	{
		while ( true)
		{
			byte[] buf = new byte[Screenshot.MAX_SIZE + 1];
			DatagramPacket packet = new DatagramPacket( buf, buf.length);
			try
			{
				socket.receive( packet);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			screenshotReceived( Screenshot.deserialize( packet.getData()), packet);
		}
	}

	/**
	 * Closes the UDP server
	 */
	public void close()
	{
		listenerThread.interrupt();
		socket.close();
	}
}
