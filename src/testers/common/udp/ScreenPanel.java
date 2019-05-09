package testers.common.udp;

import java.awt.Graphics;

import javax.swing.JPanel;

import common.network.Screenshot;

public class ScreenPanel extends JPanel
{
	private static final long serialVersionUID = 4418933199627236391L;
	private Screenshot screen;

	public void show( Screenshot screen)
	{
		this.screen = screen;
		repaint();
	}

	@Override
	protected void paintComponent( Graphics g)
	{
		super.paintComponent( g);
		g.drawImage( screen.getImage(), 0, 0, null);
	}
}
