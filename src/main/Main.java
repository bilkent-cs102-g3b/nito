package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application
{
	@Override
	public void start(Stage stage)
	{
		try
		{
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("/view/admin/Main.fxml"));
			Scene scene = new Scene(root, 1200, 675);
			stage.setScene( scene);
			
			// TODO Delete this?
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event)
				{
					System.exit(0);
				}
			});
			
			stage.show();
		} catch( Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
