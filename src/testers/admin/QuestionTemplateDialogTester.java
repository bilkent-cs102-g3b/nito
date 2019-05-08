package testers.admin;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import admin.model.Model;
import admin.model.exam_entries.Exam;
import admin.model.exam_entries.Question;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

public class QuestionTemplateDialogTester extends Application
{

	Exam e;
	Question q;

	@Override
	public void start( Stage primaryStage) throws MalformedURLException, IOException
	{
		Model.getInstance().createExam( "Exam1", 50);
		Model.getInstance().createExam( "Exam2", 50);
		Model.getInstance().createExam( "Exam3", 50);
		e = Model.getInstance().createExam( "Exam", 50);
		
		q = Model.getInstance().createQuestion( e, "Question", 50);
		Model.getInstance().createQuestion( e, "Question 2", 50);
		
		Model.getInstance().createQuestionPart( q, "Part", 50);
		Dialog<?> d = (Dialog<?>) FXMLLoader.load( new File( "src/admin/view/fxml/preparation/QuestionTemplateDialog.fxml").toURI().toURL());
		d.setResizable( true);
		System.out.println( d.showAndWait());
	}

	public static void main( String[] args)
	{
		launch( args);
	}
}
