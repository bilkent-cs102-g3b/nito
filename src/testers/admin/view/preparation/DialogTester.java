package testers.admin.view.preparation;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

public class DialogTester extends Application
{

	@Override
	public void start( Stage primaryStage) throws MalformedURLException, IOException
	{
		Dialog<?> d = (Dialog<?>) FXMLLoader.load( new File( "src/admin/view/fxml/preparation/NewQuestionDialog.fxml").toURI().toURL());
		d.setResizable( true);
		System.out.println( d.showAndWait());
	}

	public static void main( String[] args)
	{
		launch( args);
	}
}
