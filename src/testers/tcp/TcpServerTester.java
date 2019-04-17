package testers.tcp;

import java.net.DatagramPacket;
import java.net.Socket;
import java.util.Scanner;

import network.*;

/**
 * @author Ziya Mukhtarov
 * @version 15/02/2019
 */
public class TcpServerTester
{
	public static void main(String[] args)
	{
		@SuppressWarnings("resource")
		Scanner scan = new Scanner (System.in);
		Server server = new Server() {
			public void connectionLost( Socket socket)
			{
				System.out.println( "Connection lost with " + socket.getInetAddress());
			}

			public void messageReceived( String msg, Socket socket)
			{
				System.out.println ("Message from " + socket.getInetAddress()+ ": " + msg);
			}

			public void screenshotReceived( Screenshot img, DatagramPacket packet)
			{
			}
		};
		String s;
		
		while (true)
		{
			s = scan.nextLine();
			server.sendMessageToAll(s);
		}
	}
}
