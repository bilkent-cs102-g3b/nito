package network;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;

/**
 * Simple UDP Server for receiving images
 * @author Ziya Mukhtarov
 * @version 11/01/2019
 */
public abstract class UdpServer
{
	private DatagramSocket socket;
	private Thread listenerThread;

	/**
	 * Opens a new socket for receiving UDP connections
	 * @param port The port to listen on
	 */
	public UdpServer(int port)
	{
		try
		{
			socket = new DatagramSocket(port);
		}
		catch (SocketException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		listenerThread = new Thread(new Runnable()
		{
			public void run()
			{
				readImage();
			}
		});
		listenerThread.start();
	}

	/**
	 * Handles the received Screenshot
	 * @param img - the Screenshot received
	 */
	public abstract void screenshotReceived(Screenshot img);

	/**
	 * Receives the screenshot sent to this server
	 * @return The Screenshot object containing the data about the sender and the image
	 */
	private void readImage()
	{
		while (true)
		{
			byte[] buf = new byte[64000];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try
			{
				socket.receive(packet);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			screenshotReceived(new Screenshot(packet));
		}
	}

	/**
	 * Closes the UDP server
	 */
	public void close()
	{
		socket.close();
	}

	//TODO
	/**
	 * @deprecated Do not use this. Use readImage instead.
	 */
	public BufferedImage readImageByParts()
	{
		DatagramPacket packet = null;
		int size = 0;
		int order = 0;
		int totalLen = 0;
		int[] lens = new int[100];
		byte[] sizeBuf = new byte[4];
		byte[] orderBuf = new byte[4];
		byte[][] buf = new byte[100][64000];
		byte[][] sortedBuf = new byte[100][64000];
		boolean sizeReceived = false;
		int cur = 0;

		while (!sizeReceived || cur <= size)
		{
			packet = new DatagramPacket(buf[cur], buf[cur].length);
			try
			{
				socket.receive(packet);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (packet.getLength() == 4)
			{
				sizeReceived = true;
				for (int i = 0; i < 4; i++)
				{
					sizeBuf[i] = buf[cur][i];
				}
				size = ByteBuffer.wrap(sizeBuf).getInt();
			} else
			{
				for (int i = 0; i < 4; i++)
				{
					orderBuf[i] = buf[cur][i];
				}
				order = ByteBuffer.wrap(orderBuf).getInt();
				for (int i = 4; i < packet.getLength(); i++)
				{
					sortedBuf[order][i - 4] = buf[cur][i];
				}
				lens[order] = packet.getLength() - 4;
				totalLen += lens[order];
			}
			cur++;
		}
		System.out.println(packet.getPort());
		packet = new DatagramPacket(buf[0], buf[0].length, packet.getAddress(), packet.getPort());
		try
		{
			socket.send(packet);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] imgBuf = new byte[totalLen];
		cur = 0;
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < lens[i]; j++)
			{
				imgBuf[cur] = sortedBuf[i][j];
				cur++;
			}
		}

		ByteArrayInputStream input = new ByteArrayInputStream(imgBuf);
		try
		{
			return ImageIO.read(input);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
