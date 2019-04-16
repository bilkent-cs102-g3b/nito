package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Simple UDP client for sending BufferedImages
 * @author Ziya Mukhtarov
 * @version 11/01/2019
 */
public class UdpClient
{
	private InetAddress serverAddress;
	private int serverPort;
	private DatagramSocket socket;

	/**
	 * Creates a UDP client and associates it with the specified server
	 * @param serverAddress The IP address of the server
	 * @param serverPort The port that the server is listening
	 * @throws UnknownHostException if no IP address for the server could be found, or if a scope_id was specified for a global IPv6 address
	 */
	public UdpClient(String serverAddress, int serverPort) throws UnknownHostException
	{
		this.serverPort = serverPort;
		this.serverAddress = InetAddress.getByName(serverAddress);
		try
		{
			socket = new DatagramSocket();
		}
		catch (SocketException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Sends the Screenshot to the server
	 * @param screenshot - The Screenshot to be sent
	 */
	public void sendScreenshot( Screenshot screenshot)
	{
		screenshot.prepareForSending();
		byte[] data = screenshot.serialize();

		DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, serverPort);
		try
		{
			socket.send(packet);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Closes the UDP socket
	 */
	public void close()
	{
		socket.close();
	}
}
