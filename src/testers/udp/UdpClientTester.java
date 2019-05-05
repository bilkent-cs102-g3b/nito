package testers.udp;

import java.awt.AWTException;
import java.io.IOException;
import java.util.Scanner;

import common.network.Client;
import common.network.Screenshot;

/**
 * @author Ziya Mukhtarov
 * @version 01/05/2019
 */
public class UdpClientTester
{
	static double scale = 0.25;
	public static void main(String[] args) throws AWTException, IOException
	{
		Scanner scan = new Scanner( System.in);
		String addr = "localhost";
//		addr = scan.next();
		scan.close();
		Client client = new Client(addr) {
			public void messageReceived(String msg)
			{
				System.out.println("\"" + msg + "\"");
				scale = ((double) Integer.parseInt(msg)) / 100.0;
			}
		};
		
		while (true)
		{
//			Thread.sleep(33);
			client.sendImage( new Screenshot( scale));
		}
	}
}
