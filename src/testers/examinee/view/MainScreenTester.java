package testers.examinee.view;

import java.io.File;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/*
 * @author Javid Baghirov
 * 
 * @version 12/05/2019
 */
public class MainScreenTester extends Application
{
	@Override
	public void start( Stage stage)
	{
		try
		{
			SplitPane root;
			String path;
			stage.initStyle( StageStyle.UNDECORATED);
			stage.setFullScreenExitHint( "");
			// stage.setFullScreenExitKeyCombination( KeyCombination.NO_MATCH);
			stage.setFullScreen( true);
			path = "src/examinee/view/fxml/MainScreen.fxml";
			root = (SplitPane) FXMLLoader.load( new File( path).toURI().toURL());
			Scene scene = new Scene( root);
			stage.setScene( scene);

			// TODO Delete this?
			stage.setOnCloseRequest( new EventHandler<WindowEvent>() {
				@Override
				public void handle( WindowEvent event)
				{
					System.exit( 0);
				}
			});

			stage.show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main( String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());
		launch( args);
	}
}
