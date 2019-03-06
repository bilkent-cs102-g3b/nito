package network;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import javax.imageio.ImageIO;

/**
 * A wrapper class for sending and receiving images from UDP
 * @author Ziya Mukhtarov
 * @version 24/02/2019
 */
public class Screenshot
{
	// Common
	private BufferedImage img;

	// For receiving
	private InetAddress ip;
	private int port;

	// For sending
	/**The maximum bytes the screenshot can have for sending due to UDP limits*/
	public static final int MAX_SIZE = 64000;
	private double scale;

	/**
	 * Creates a screenshot from the received datagram packet<br>
	 * <strong>Note:</strong> To be used only for received screenshots
	 * @param packet - The DatagramPacket containing the image byte data
	 */
	public Screenshot(DatagramPacket packet)
	{
		ip = packet.getAddress();
		port = packet.getPort();

		ByteArrayInputStream input = new ByteArrayInputStream(packet.getData());
		try
		{
			img = ImageIO.read(input);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Creates a screenshot for sending. The scale may be reduced if this scaling leads to a image byte size bigger than {@value #MAX_SIZE}<br>
	 * <strong>Note:</strong> To be used only for sending screenshots
	 * @param img - the screenshot image
	 * @param scale - the intended scaling during sending
	 */
	public Screenshot(BufferedImage img, double scale)
	{
		if (scale <= 0 || scale > 1)
			throw new IllegalArgumentException("The scale value should be in the range (0,1].");
		this.img = img;
		this.scale = scale;
	}

	/**
	 * Returns the current screenshot as a byte array
	 * @return the current screenshot as a byte array
	 */
	public byte[] toByteArray()
	{
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try
		{
			ImageIO.write(img, "jpg", output);
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try
		{
			output.flush();
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return output.toByteArray();
	}

	public Screenshot clone()
	{
		Screenshot clone = new Screenshot(img, scale);
		clone.ip = ip;
		clone.port = port;
		return clone;
	}

	private void updateScale()
	{
		double l, r, m;
		byte[] data;
		Screenshot tmp = this.clone();
		tmp.scale(scale);
		if (tmp.toByteArray().length <= MAX_SIZE)
		{
			return;
		}

		l = 0;
		r = scale - 0.01;
		while (r - l > 0.01)
		{
			m = (l + r) / 2;
			tmp = this.clone();
			tmp.scale(m);
			data = tmp.toByteArray();
			if (data.length > MAX_SIZE)
				r = m;
			else
				l = m;
		}
		scale = l;
	}

	/**
	 * Prepares the screenshot for sending over UDP. Scales the image to specified scale or the maximum possible scaling
	 * The original screenshot will be lost and replaced with the scaled one<br>
	 * <strong>Note:</strong> To be used only for sending screenshots, always before sending
	 */
	public void prepareForSending()
	{
		updateScale();
		scale(scale);
	}

	/**
	 * Scales the screenshot
	 * @param scale - A double value that will be multiplied with the current dimensions
	 */
	public void scale(double scale)
	{
		int newWidth = (int) (img.getWidth() * scale);
		int newHeight = (int) (img.getHeight() * scale);
		scale(newWidth, newHeight);
	}

	/**
	 * Scales the screenshot
	 * @param newWidth - The width of the resulting screenshot
	 * @param newHeight - The height of the resulting screenshot
	 */
	public void scale(int newWidth, int newHeight)
	{
		Image result = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		img = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_3BYTE_BGR);

		// Converting Image to BufferedImage
		Graphics2D g2d = img.createGraphics();
		g2d.drawImage(result, 0, 0, null);
		g2d.dispose();
	}

	/**
	 * Returns the IP that the screenshot sent from<br>
	 * <strong>Note:</strong> To be used only for received screenshots
	 * @return The IP that the screenshot sent from
	 */
	public InetAddress getIP()
	{
		return ip;
	}

	/**
	 * Returns the port that the screenshot sent from<br>
	 * <strong>Note:</strong> To be used only for received screenshots
	 * @return The port that the screenshot sent from
	 */
	public int getPort()
	{
		return port;
	}

	/**
	 * Returns the screenshot
	 * @return The screenshot
	 */
	public BufferedImage getImage()
	{
		return img;
	}
}
