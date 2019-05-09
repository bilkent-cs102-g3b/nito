package testers.examinee.view;

import java.awt.Dimension;
import java.io.File;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainScreenTester extends Application
{
	@Override
	public void start( Stage stage)
	{
		try
		{
			SplitPane root;
			String path;
			new Dimension();
			stage.setFullScreen( true);
			path = "src/examinee/view/fxml/MainScreen.fxml";
			root = (SplitPane) FXMLLoader.load( new File( path).toURI().toURL());
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
			stage.show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main( String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName ());
		launch( args);
	}
}
