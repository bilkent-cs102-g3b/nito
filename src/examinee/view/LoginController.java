package examinee.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.util.Pair;

public class LoginController
{
	@FXML
	Dialog<Pair<String, String>> root;
	@FXML
	Button login;
	@FXML
	TextField name;
	@FXML
	TextField ip;

	public void initialize()
	{
		ButtonType loginButton = new ButtonType( "Login", ButtonData.APPLY);
		root.getDialogPane().getButtonTypes().add( loginButton);
		
		root.setResultConverter( button -> {
			if (button == loginButton)
			{
				return new Pair<String, String>( name.getText(), ip.getText());
			}
			return null;
		});
	}
}
