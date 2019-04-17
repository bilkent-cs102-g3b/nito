package testers.udp;

import java.awt.AWTException;
import java.net.UnknownHostException;
import java.util.Scanner;

import network.Client;
import network.Screenshot;

public class UdpClientTester
{
	static double scale = 0.25;
	public static void main(String[] args) throws UnknownHostException, AWTException
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
