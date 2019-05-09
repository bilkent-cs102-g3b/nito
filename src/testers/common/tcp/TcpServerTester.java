package testers.common.tcp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.Scanner;

import common.network.Screenshot;
import common.network.Server;

/**
 * @author Ziya Mukhtarov
 * @version 15/02/2019
 */
public class TcpServerTester
{
	public static void main( String[] args) throws IOException
	{
		@SuppressWarnings("resource")
		Scanner scan = new Scanner( System.in);
		Server server = new Server() {
			public void connectionTerminated( Socket socket)
			{
				System.out.println( "Connection lost with " + socket.getInetAddress());
			}

			public void messageReceived( String msg, Socket socket)
			{
				System.out.println( "Message from " + socket.getInetAddress() + ": " + msg);
			}

			public void screenshotReceived( Screenshot img, DatagramPacket packet)
			{
			}

			@Override
			public void connectionEstablished( Socket socket)
			{
				System.out.println( "Connection established with " + socket.getInetAddress().getHostAddress());
			}
		};
		String s;

		while ( true)
		{
			s = scan.nextLine();
			server.sendMessageToAll( s);
		}
	}
}
