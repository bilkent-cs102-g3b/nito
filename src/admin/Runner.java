package admin;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Optional;

import admin.model.Workspace;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Runner extends Application
{
	@Override
	public void start( Stage stage) throws MalformedURLException, IOException, URISyntaxException
	{
		@SuppressWarnings("unchecked")
		Dialog<Pair<File, Boolean>> d = (Dialog<Pair<File, Boolean>>) FXMLLoader.load( getClass().getResource( "/admin/view/fxml/preparation/WorkspaceDialog.fxml").toURI().toURL());
		Optional<Pair<File, Boolean>> result = d.showAndWait();
		if (!result.isPresent())
		{
			System.exit(0);
		}
		Workspace.getInstance().set( result.get().getKey());
	}

	public static void main( String[] args)
	{
		launch( args);
	}
}
