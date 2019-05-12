package testers.admin.view.preparation;

import java.io.IOException;
import java.net.MalformedURLException;

import admin.view.preparation.MainEditorController;
import admin.view.preparation.WelcomeTabController;
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
	private MainEditorController mainController;
	private WelcomeTabController welcomeTabController;

	@Override
	public void start( Stage stage) throws MalformedURLException, IOException
	{
		TabPane root2;
		Tab w;
		String resource, resource2;

		FXMLLoader loader = new FXMLLoader();
		resource = "/admin/view/fxml/preparation/WelcomeTab.fxml";
		loader.setLocation( getClass().getResource( resource));
		w = loader.load();
		welcomeTabController = loader.getController();

		FXMLLoader loader2 = new FXMLLoader();
		resource2 = "/admin/view/fxml/preparation/Main.fxml";
		loader2.setLocation( getClass().getResource( resource2));
		loader2.load();
		mainController = loader2.getController();

		welcomeTabController.controllerInstance( mainController);

		root2 = new TabPane( w);
		Scene scene = new Scene( root2, 1000, 600);
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