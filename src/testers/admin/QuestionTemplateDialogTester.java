package testers.admin;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import admin.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

public class QuestionTemplateDialogTester extends Application {

	@Override
	public void start(Stage primaryStage) throws MalformedURLException, IOException {
		Model.getInstance().createExam("Hello World", 50);
		Model.getInstance().createExam("Hello Dude", 50);
		Dialog<?> d = (Dialog<?>) FXMLLoader.load( new File( "src/admin/view/fxml/preparation/QuestionTemplateDialog.fxml").toURI().toURL());
		d.setResizable( true);
		System.out.println(d.showAndWait());
	}

	public static void main(String[] args) {
		launch(args);
	}
}

