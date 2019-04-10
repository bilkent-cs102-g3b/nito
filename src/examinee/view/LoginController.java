package examinee.view;

import java.io.File;
import java.net.MalformedURLException;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoginController
{
	@FXML
	private ImageView logoNito;
	@FXML
	private ImageView logoBilkent;
	
	public void initialize() throws MalformedURLException
	{
		logoNito.setImage( new Image( new File("Resources/logo.png").toURI().toURL().toString()));
		logoBilkent.setImage( new Image( new File("Resources/bilLogo.png").toURI().toURL().toString()));
	}
}
