package common;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import me.coley.simplejna.hook.key.KeyHookManager;

public class RunnerClient
{
	public static void main(String[] args)
	{
		KeyHookManager khm = new KeyHookManager();
		GlobalKeyListener keyHook = new GlobalKeyListener(khm);
		khm.hook(keyHook);

		JFrame fr = new JFrame();
		JButton btn = new JButton();
		fr.setSize(500, 500);
		fr.add(btn);
		btn.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0)
			{
				System.exit(0);
			}

		});
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setUndecorated(true);
		fr.setVisible(true);

		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		gd.setFullScreenWindow(fr);
	}
}
