package common.network;

import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

/**
 * A class for working with screenshots
 * @author Ziya Mukhtarov
 * @version 01/05/2019
 */
public class Screenshot implements Serializable, Cloneable
{
	private static final long serialVersionUID = -1789450675592222566L;

	/** The maximum bytes the screenshot can have for sending due to UDP limits */
	public static final int MAX_SIZE = 64000;

	private transient BufferedImage img;
	private double scale;

	/**
	 * Creates a screenshot for sending. The given width will be used to determine
	 * the scaling. The scale may be reduced if this scaling leads to a byte size
	 * bigger than {@value #MAX_SIZE}
	 * @param scale the intended scaling during sending
	 * @throws AWTException if the screen cannot be captured
	 */
	public Screenshot( int width) throws AWTException
	{
		this( Math.min( 1.0 * width / Toolkit.getDefaultToolkit().getScreenSize().width, 1.0));
		System.out.println( Toolkit.getDefaultToolkit().getScreenSize().width);
	}

	/**
	 * Creates a screenshot for sending. The scale may be reduced if this scaling
	 * leads to a byte size bigger than {@value #MAX_SIZE}
	 * @param scale the intended scaling during sending
	 * @throws AWTException if the screen cannot be captured
	 */
	public Screenshot( double scale) throws AWTException
	{
		img = new Robot().createScreenCapture( new Rectangle( Toolkit.getDefaultToolkit().getScreenSize()));

		if ( scale == 0)
			scale = 1;
		
		if ( scale < 0 || scale > 1)
		{
			throw new IllegalArgumentException( "The scale value should be in the range (0,1].");
		}
		this.scale = scale;
	}

	/**
	 * Writes object to ObjectOutputStream during serialization
	 * @param out The output stream
	 */
	private void writeObject( ObjectOutputStream out)
	{
		try
		{
			out.defaultWriteObject();
			ImageIO.write( img, "png", out);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Reads object from ObjectInputStream during deserialization
	 * @param in The input stream
	 */
	private void readObject( ObjectInputStream in)
	{
		try
		{
			in.defaultReadObject();
			img = ImageIO.read( in);
		}
		catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Serializes the Screenshot into byte array
	 * @return The byte array denoting this object
	 */
	public byte[] serialize()
	{
		byte[] result;
		ByteArrayOutputStream baos;
		ObjectOutputStream oos;

		try
		{
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream( baos);

			oos.writeObject( this);
			oos.flush();
			result = baos.toByteArray();

			oos.close();
			baos.close();

			return result;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Deserializes the Screenshot from byte array
	 * @param data The byte array containing serialization of a Screenshot
	 * @return The deserialized screenshot
	 */
	public static Screenshot deserialize( byte[] data)
	{
		Screenshot result;
		ByteArrayInputStream bais;
		ObjectInputStream ois;

		try
		{
			bais = new ByteArrayInputStream( data);
			ois = new ObjectInputStream( bais);

			result = (Screenshot) ois.readObject();

			ois.close();
			bais.close();

			return result;
		}
		catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns the byte size of serialized current screenshot
	 * @return The byte size of serialized current screenshot
	 */
	public int getSize()
	{
		return serialize().length;
	}

	@Override
	public Object clone()
	{
		try
		{
			Screenshot s;
			s = (Screenshot) super.clone();

			ColorModel cm = img.getColorModel();
			s.img = new BufferedImage( cm, img.copyData( null), cm.isAlphaPremultiplied(), null);

			return s;
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Updates the value of scale to the biggest possible scaling that does not
	 * exceed UDP limits. This method uses Binary Search algorithm to work
	 * efficiently
	 */
	private void updateScale()
	{
		double l, r, m;
		Screenshot tmp = (Screenshot) this.clone();
		tmp.scale( scale);
		if ( tmp.getSize() <= MAX_SIZE)
		{
			return;
		}

		l = 0;
		r = scale - 0.01;
		while ( r - l > 0.01)
		{
			m = (l + r) / 2;
			tmp = (Screenshot) this.clone();
			tmp.scale( m);
			if ( tmp.getSize() > MAX_SIZE)
				r = m;
			else
				l = m;
		}
		scale = l;
	}

	/**
	 * Prepares the screenshot for sending over UDP. Scales the image to specified
	 * scale or the maximum possible scaling. The original screenshot will be lost
	 * and replaced with the scaled one<br>
	 * <strong>Note:</strong> Always call before sending
	 */
	public void prepareForSending()
	{
		updateScale();
		scale( scale);
	}

	/**
	 * Scales the screenshot
	 * @param scale A double value that will be multiplied with the current
	 *              dimensions
	 */
	public void scale( double scale)
	{
		int newWidth = (int) (img.getWidth() * scale);
		int newHeight = (int) (img.getHeight() * scale);
		scale( newWidth, newHeight);
	}

	/**
	 * Scales the screenshot
	 * @param newWidth  The width of the resulting screenshot
	 * @param newHeight The height of the resulting screenshot
	 */
	public void scale( int newWidth, int newHeight)
	{
		Image result = img.getScaledInstance( newWidth, newHeight, Image.SCALE_SMOOTH);
		img = new BufferedImage( newWidth, newHeight, BufferedImage.TYPE_3BYTE_BGR);

		// Converting Image to BufferedImage
		Graphics2D g2d = img.createGraphics();
		g2d.drawImage( result, 0, 0, null);
		g2d.dispose();
	}

	/**
	 * Returns the screenshot itself
	 * @return The screenshot itself
	 */
	public BufferedImage getImage()
	{
		return img;
	}
}
