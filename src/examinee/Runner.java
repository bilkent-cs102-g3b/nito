package examinee;

import java.io.IOException;
import java.util.Optional;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import examinee.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Runner extends Application
{

	@Override
	public void start(Stage stage) throws IOException
	{
		Image logo = new Image( getClass().getResourceAsStream( "/logo_nito.png"));
		
		/******************************** LOGIN DIALOG ****************************************/
		boolean success = false;
		while ( !success)
		{
			Dialog<Pair<String, String>> loginDialog = FXMLLoader.load( getClass().getResource( "/examinee/view/fxml/Login.fxml"));
			Optional<Pair<String, String>> result = loginDialog.showAndWait();
			if ( result.isPresent())
			{
				success = Model.getInstance().login( result.get().getKey(), result.get().getValue());
			}
			else
			{
				System.exit(0);
			}
			
			if (!success)
			{
				Alert alert = new Alert( AlertType.ERROR, "Some error occured. Please try again!");
				alert.showAndWait();
			}
		}
		
		
		/*************************** MAIN ************************************/
		System.out.println("Success!");
		if ( success)
		{
			SplitPane mainScreen = FXMLLoader.load( getClass().getResource( "/examinee/view/fxml/MainScreen.fxml"));
			Scene scene = new Scene( mainScreen);
			stage.setScene( scene);
//			stage.initStyle( StageStyle.UNDECORATED);
			stage.setFullScreenExitHint("");
//			stage.setFullScreenExitKeyCombination( KeyCombination.NO_MATCH);
			stage.setFullScreen( true);
			stage.setTitle( "Nito - Examinee");
			stage.show();
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());
		launch(args);
	}
}
