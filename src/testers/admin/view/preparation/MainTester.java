package testers.admin.view.preparation;

import java.io.IOException;
import java.net.MalformedURLException;

import admin.model.Model;
import admin.model.exam_entries.Exam;
import admin.model.exam_entries.Question;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainTester extends Application
{

	Exam e, e2;
	Question q, q2, q3, q4;

	@Override
	public void start( Stage stage) throws MalformedURLException, IOException
	{
		Model.getInstance().createExam( "Exam1", 50);
		e2 = Model.getInstance().createExam( "Exam2", 50);
		Model.getInstance().createExam( "Exam3", 50);
		e = Model.getInstance().createExam( "Exam4", 50);

		q = Model.getInstance().createQuestion( e, "Question1");
		q2 = Model.getInstance().createQuestion( e, "Question2");

		q3 = Model.getInstance().createQuestion( e2, "Question1");
		q4 = Model.getInstance().createQuestion( e2, "Question2");

		Model.getInstance().createQuestionPart( q, "Part1", 50);
		Model.getInstance().createQuestionPart( q, "Part2", 50);

		Model.getInstance().createQuestionPart( q2, "Part1", 50);
		Model.getInstance().createQuestionPart( q2, "Part2", 50);

		Model.getInstance().createQuestionPart( q3, "Part1", 50);
		Model.getInstance().createQuestionPart( q3, "Part2", 50);

		Model.getInstance().createQuestionPart( q4, "Part1", 50);
		Model.getInstance().createQuestionPart( q4, "Part2", 50);

		Pane root;
		String resource;

		resource = "/admin/view/fxml/preparation/Main.fxml";
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
