package testers.admin.view.monitoring.allInOne;

import java.io.IOException;

import admin.model.Examinee;
import admin.model.Model;
import admin.model.exam_entries.Exam;
import admin.view.monitoring.MonitoringViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AdminRunner extends Application
{
	@Override
	public void start( Stage stage) throws IOException
	{
		VBox root;
		String resource;

		resource = "/admin/view/fxml/monitoring/MonitoringView.fxml";
		FXMLLoader loader = new FXMLLoader( getClass().getResource( resource));
		root = loader.load();
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

		Exam e = Model.getInstance().createExam( "Test", 60);
		System.out.println( e);
		Model.getInstance().startSendingExam( e);
		System.out.println( "Ready");

		new Thread( new Runnable() {

			@Override
			public void run()
			{
				while ( true)
				{
					Examinee[] es = Model.getInstance().getExaminees().search( "");
					if ( es.length > 0)
					{
						System.out.println( es[0]);
						Platform.runLater( () -> ((MonitoringViewController) loader.getController()).addExaminee( es[0]));
						break;
					}
				}
			}
		}).start();

		new Thread( new Runnable() {

			@Override
			public void run()
			{
				while ( true)
				{
					Examinee[] es = Model.getInstance().getExaminees().search( "");
					if ( es.length > 1)
					{
						System.out.println( es[0]);
						Platform.runLater( () -> ((MonitoringViewController) loader.getController()).addExaminee( es[0]));
						break;
					}
				}
			}
		}).start();
	}

	public static void main( String[] args)
	{
		launch( args);
	}
}
