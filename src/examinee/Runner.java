package examinee;

import java.io.IOException;
import java.util.Optional;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import common.Resources;
import examinee.model.KeyListener;
import examinee.model.Model;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import me.coley.simplejna.hook.key.KeyHookManager;

public class Runner extends Application
{
	@Override
	public void start( Stage stage) throws IOException
	{
		/*********** LOGIN DIALOG *************/
		boolean success = false;
		while ( !success)
		{
			Dialog<Pair<String, String>> loginDialog = FXMLLoader.load( getClass().getResource( "/examinee/view/fxml/Login.fxml"));
			((Stage) loginDialog.getDialogPane().getScene().getWindow()).getIcons().add( Resources.logo);
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

		/*************************** MAIN ************************************/
		if ( success)
		{
			SplitPane mainScreen = FXMLLoader.load( getClass().getResource( "/examinee/view/fxml/MainScreen.fxml"));
			Scene scene = new Scene( mainScreen);
			stage.setScene( scene);
			stage.setFullScreenExitHint( "");
			stage.setFullScreenExitKeyCombination( KeyCombination.NO_MATCH);
			stage.setFullScreen( true);
			stage.setTitle( "Nito - Examinee");
			stage.show();
		}
		Model.getInstance().getStatus().addListener( ( o, oldVal, newVal) -> {
			if ( newVal.intValue() == Model.STATUS_FINISHED)
			{
				Platform.runLater( () -> {
					stage.setFullScreen( false);
					stage.setMaximized( true);
					stage.setTitle( "Nito - Exam Ended");
				});

				stage.setOnCloseRequest( new EventHandler<WindowEvent>() {
					@Override
					public void handle( WindowEvent event)
					{
						System.exit( 0);
					}
				});
			}
		});
	}

	public static void main( String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		// Blocking possible shortcuts
		KeyHookManager khm = new KeyHookManager();
		khm.hook( new KeyListener( khm));

		UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());
		launch( args);
	}
}
