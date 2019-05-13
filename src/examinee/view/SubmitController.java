package examinee.view;

import java.net.MalformedURLException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SubmitController
{
	@FXML
	private Button quit;
	
	public void initialize() throws MalformedURLException
	{
		quit.setOnAction( new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle( ActionEvent event)
			{
				System.exit(0);
			}
		});
	}

}
