package testers.udp;

import java.awt.Dimension;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.Collection;
import java.util.TreeMap;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import network.Screenshot;
import network.Server;

/**
 * @author Ziya Mukhtarov
 * @version 01/05/2019
 */
public class UdpServerTester
{
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		JFrame frame = new JFrame();
		JPanel mmPanel = new JPanel();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		TreeMap<String, ScreenPanel> map = new TreeMap<>();
		
		BoxLayout box = new BoxLayout(mmPanel, BoxLayout.Y_AXIS);
		mmPanel.setLayout(box);
		
		JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 100, 25);
		slider.setMaximumSize( new Dimension( 200, 50));
		
		mmPanel.add(slider);
		JPanel mainPanel = new JPanel();

		JScrollPane scroll = new JScrollPane(mainPanel);
		mmPanel.add( scroll);
		
		Server server = new Server() {
			public void connectionTerminated( Socket socket)
			{
				System.out.println("Connection lost with " + socket.getInetAddress());
			}

			public void messageReceived( String msg, Socket socket)
			{
			}

			public void screenshotReceived( Screenshot img, DatagramPacket packet)
			{
				//System.out.println("Received: " + img.getImage().getHeight() + " x " + img.getImage().getWidth());
				ScreenPanel panel;
				String id = packet.getSocketAddress().toString();
				System.out.println(id);
				if (!map.containsKey( id))
				{
					panel = new ScreenPanel();
					panel.setPreferredSize( new Dimension (16 * 25, 9 * 25));
					mainPanel.add(panel);
					map.put( id, panel);
				}
				else
				{
					panel = map.get( id);
				}
				img.scale(panel.getPreferredSize().width, panel.getPreferredSize().height);
				panel.show(img);
				//frame.validate();
			}

			@Override
			public void connectionEstablished( Socket socket)
			{
				System.out.println("Connection established with " + socket.getInetAddress());
			}
		};
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e)
			{
				JSlider source = (JSlider) e.getSource();
				//if (!source.getValueIsAdjusting()) {
					Collection<ScreenPanel> panels = map.values();
					for (JPanel panel : panels)
					{
						panel.setPreferredSize( new Dimension( 16 * source.getValue(), 9 * source.getValue()));
						panel.revalidate();
						panel.repaint();
					}
					frame.revalidate();
					frame.repaint();
					System.out.println("\"" + source.getValue() + "\"");
					server.sendMessageToAll(source.getValue() + "");
				//}
			}
		});

		frame.add(mmPanel);
	}
}
