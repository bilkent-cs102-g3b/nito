package testers.examinee.view;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import examinee.model.Model;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

/*
 * @author Javid Baghirov
 * @version 14/05/2019
 */
public class LoginTester extends Application
{
	@Override
	public void start( Stage stage) throws IOException
	{
		Image logo = new Image( getClass().getResourceAsStream( "/logo_nito.png"));

		/********************************
		 * LOGIN DIALOG
		 ****************************************/
		boolean success = false;
		while ( !success)
		{
			Dialog<Pair<String, String>> loginDialog = FXMLLoader.load( getClass().getResource( "/examinee/view/fxml/Login.fxml"));
			((Stage) loginDialog.getDialogPane().getScene().getWindow()).getIcons().add( logo);
			Optional<Pair<String, String>> result = loginDialog.showAndWait();
			if ( result.isPresent())
			{
				success = Model.getInstance().login( result.get().getKey(), result.get().getValue());
			}
			else
			{
				System.exit( 0);
			}

			if ( !success)
			{
				Alert alert = new Alert( AlertType.ERROR, "Some error occured. Please try again!");
				alert.showAndWait();
			}
		}
	}

	public static void main( String[] args)
	{
		launch( args);
	}
}
