package examinee.view;

import java.net.MalformedURLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SubmitController
{
	@FXML
	Button quit;

	public void initialize() throws MalformedURLException
	{
	}

	public void buttonAction()
	{
		if ( quit.isPressed())
		{
			System.exit( 0);
		}
	}
}
