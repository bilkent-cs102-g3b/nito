package examinee.view;

import java.net.MalformedURLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * @author Javid Baghirov
 * @version 14/05/2019
 */
public class SubmitController
{
	@FXML
	private Button quit;

	/**
	 * Sets up the view
	 * @throws MalformedURLException
	 */
	public void initialize() throws MalformedURLException
	{
		// The listener for the button
		quit.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle( ActionEvent event)
			{
				System.exit( 0);
			}
		});
	}

}
