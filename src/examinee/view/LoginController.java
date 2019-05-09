package examinee.view;

import java.net.MalformedURLException;

import examinee.model.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class LoginController
{
	@FXML
	Button login;
	@FXML
	TextField id;
	@FXML
	TextField ip;

	public void initialize() throws MalformedURLException
	{
	}

	public void buttonAction() throws MalformedURLException
	{
		if ( login.isPressed())
		{
			Model.getInstance().login( id.getText(), ip.getText());
		}
	}
}
