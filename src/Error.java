import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * A class for showing fatal errors
 * @author Ziya Mukhtarov
 * @version 16/02/2019
 */
public class Error
{
	public static void die(String error)
	{
		JFrame frame = new JFrame("Error!");

		JButton button = new JButton("Close");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				System.exit(0);
			}
		});

		frame.setSize(500, 500);
		frame.add(button);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main(String[] args)
	{
		die("Problem cixdiye :(");
	}
}
