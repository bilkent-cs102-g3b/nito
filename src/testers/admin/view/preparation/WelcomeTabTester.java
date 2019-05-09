package testers.admin.view.preparation;

import java.io.IOException;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class WelcomeTabTester extends Application
{

	@Override
	public void start( Stage stage) throws MalformedURLException, IOException
	{
		TabPane root;
		Tab w;
		String resource;

		resource = "/admin/view/fxml/preparation/WelcomeTab.fxml";
		w = FXMLLoader.load( getClass().getResource( resource));
		root = new TabPane( w);
		Scene scene = new Scene( root, 1000, 600);
		stage.setScene( scene);

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
