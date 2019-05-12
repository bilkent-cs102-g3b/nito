package testers.common.tcp;

import java.io.IOException;
import java.util.Scanner;

import common.network.Client;

/**
 * @author Ziya Mukhtarov
 * @version 01/05/2019
 */
public class TcpClientTester
{
	public static void main( String[] args) throws IOException
	{
		@SuppressWarnings("resource")
		Scanner scan = new Scanner( System.in);
		String addr = "192.168.43.105";
		// addr = scan.next();
		Client client = new Client( addr) {
			public void messageReceived( String msg)
			{
				System.out.println( "Message from the server: " + msg);
			}
		};
		String s;

		while ( true)
		{
			s = scan.nextLine();
			client.sendMessage( s);
		}
	}
}
