package testers.admin.view.preparation;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainTester extends Application
{

	@Override
	public void start( Stage stage) throws MalformedURLException, IOException
	{
		Pane root;
		String path;

		path = "src/admin/view/Main.fxml";
		root = FXMLLoader.load( new File( path).toURI().toURL());
		Scene scene = new Scene( root, 1200, 675);
		stage.setScene( scene);

		// TODO Delete this?
		stage.setOnCloseRequest( new EventHandler<WindowEvent>() {
			@Override
			public void handle( WindowEvent event)
			{
				System.exit( 0);
			}
		});

		stage.setTitle( "Sample frame");
		stage.show();
	}

	public static void main( String[] args)
	{
		launch( args);
	}
}
