package network;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

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
	 * Sends the image to the server
	 * @param screenshot The image to be sent
	 */
	public void sendImage(Screenshot screenshot)
	{
		screenshot.prepareForSending();
		byte[] data = screenshot.toByteArray();

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

	public void close()
	{
		socket.close();
	}

	//TODO
	/**
	 * @deprecated Use sendImage instead
	 * @param img The image to send
	 */
	public void sendImageByParts(BufferedImage img)
	{
		try
		{
			final int MAX_DATA_SIZE = 63995;
			DatagramPacket packet;
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			ImageIO.write(img, "jpg", output);
			output.flush();
			byte[] data = output.toByteArray();
			byte[] curData = new byte[64000];
			int len;

			packet = new DatagramPacket(
					ByteBuffer.allocate(4).putInt((int) Math.ceil(1.0 * data.length / MAX_DATA_SIZE)).array(), 4,
					serverAddress, serverPort);
			socket.send(packet);
			for (int i = 0; i < data.length; i += MAX_DATA_SIZE)
			{
				len = 0;
				for (int j = i; j < Math.min(data.length, i + MAX_DATA_SIZE); j++)
				{
					len++;
					curData[j - i + 4] = data[j];
				}
				for (int j = 0; j < 4; j++)
				{
					curData[j] = ByteBuffer.allocate(4).putInt(i / MAX_DATA_SIZE).array()[j];
				}
				packet = new DatagramPacket(curData, len + 4, serverAddress, serverPort);
				System.out.println(output.size());
				socket.send(packet);
			}

			packet = new DatagramPacket(data, data.length);
			socket.receive(packet);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
