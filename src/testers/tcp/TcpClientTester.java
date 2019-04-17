package testers.tcp;

import java.net.UnknownHostException;
import java.util.Scanner;

import network.*;

public class TcpClientTester
{
	public static void main(String[] args) throws UnknownHostException
	{
		@SuppressWarnings("resource")
		Scanner scan = new Scanner (System.in);
		String addr = "localhost";
//		addr = scan.next();
		Client client = new Client (addr) {
			public void messageReceived(String msg)
			{
				System.out.println ("Message from the server: " + msg);
			}
		};
		String s;
		
		while (true)
		{
			s = scan.nextLine();
			client.sendMessage(s);
		}
	}
}
