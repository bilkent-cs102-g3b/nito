package testers.admin.view.monitoring;

import java.io.IOException;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MonitoringViewTester extends Application
{
	@Override
	public void start( Stage stage) throws MalformedURLException, IOException
	{
		VBox root;
		String resource;

		resource = "/admin/view/fxml/monitoring/MonitoringView.fxml";
		root = FXMLLoader.load( getClass().getResource( resource));
		Scene scene = new Scene( root, 1200, 675);
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
