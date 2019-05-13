package testers.examinee.view;

import java.io.File;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoginTester extends Application
{
	@Override
	public void start( Stage stage)
	{
		try
		{
			Pane root;
			String path;

			path = "src/examinee/view/fxml/Login.fxml";
			root = (Pane) FXMLLoader.load( new File( path).toURI().toURL());
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

			System.out.println( new File( "Resources/logo.png").toURI().toURL().toString());
			stage.getIcons().add( new Image( new File( "Resources/logo.png").toURI().toURL().toString()));
			stage.setTitle( "Sample frame");
			stage.setMaxHeight( 600);
			stage.setMaxWidth( 800);
			stage.setResizable( false);
			stage.show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main( String[] args)
	{
		launch( args);
	}
}
