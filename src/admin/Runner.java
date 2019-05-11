package admin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Optional;

import admin.model.Workspace;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Runner extends Application
{
	@Override
	public void start( Stage stage) throws MalformedURLException, IOException, URISyntaxException
	{
		Image logo = new Image( getClass().getResourceAsStream( "/logo_nito.png"));
		
		@SuppressWarnings("unchecked")
		Dialog<Pair<File, Boolean>> d = (Dialog<Pair<File, Boolean>>) FXMLLoader.load( getClass().getResource( "/admin/view/fxml/preparation/WorkspaceDialog.fxml").toURI().toURL());
		((Stage) d.getDialogPane().getScene().getWindow()).getIcons().add( logo);
		Optional<Pair<File, Boolean>> result = d.showAndWait();
		if ( !result.isPresent())
		{
			System.exit( 0);
		}
		boolean success = Workspace.getInstance().set( result.get().getKey());
		if ( !success)
		{
			Alert alert = new Alert( AlertType.ERROR, "In this version, in order to prevent fatal problems, you can not select non-empty folder as a workspace folder. Please try again with an empty folder or an already existing workspace folder");
			alert.setHeaderText( "Non-empty folder!");
			alert.showAndWait();
			System.exit( 0);
		}

		Pane root = FXMLLoader.load( getClass().getResource( "/admin/view/fxml/preparation/Main.fxml"));
		Scene scene = new Scene( root, Screen.getScreens().get( 0).getBounds().getWidth() * 0.8, Screen.getScreens().get( 0).getBounds().getHeight() * 0.8);

		stage.setScene( scene);
		stage.setTitle( "Sample frame");
		stage.getIcons().add( logo);
		stage.setOnCloseRequest( e -> {
			Workspace.getInstance().stopAutoSave();
			Workspace.getInstance().save();
			System.exit( 0);
		});
		stage.show();

		Tab welcomeTab = FXMLLoader.load( getClass().getResource( "/admin/view/fxml/preparation/WelcomeTab.fxml"));
		((TabPane) root.lookup( ".tab-pane")).getTabs().add( welcomeTab);
	}

	public static void main( String[] args)
	{
		launch( args);
	}
}
