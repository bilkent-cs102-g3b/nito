package testers.admin.view.monitoring.allInOne;

import java.awt.AWTException;
import java.io.IOException;
import java.net.UnknownHostException;

import common.network.Client;
import common.network.Screenshot;

/**
 * @author Ziya Mukhtarov
 * @version 12/05/2019
 */
public class ExamineeSimulator
{
	public static void main( String[] args) throws UnknownHostException, IOException, AWTException
	{
		Client c = new Client( "localhost") {
			@Override
			public void messageReceived( String msg)
			{
			}
		};
		
		c.sendMessage( "24DdwVJljT28m6MSOfvMnj7iZbL8bNMmo7xnLKsZSyurflOLg2JFtq0hsY09:::name:::Ziya Mukhtarov");
		
		while (true)
		{
			Screenshot s = new Screenshot( 0.25);
			c.sendImage( s);
		}
	}
}
