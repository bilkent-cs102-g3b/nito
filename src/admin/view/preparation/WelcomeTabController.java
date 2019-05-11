package admin.view.preparation;

import org.tbee.javafx.scene.layout.fxml.MigPane;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;

public class WelcomeTabController {

	@FXML
	Hyperlink newExam;
	@FXML
	MigPane newExamPane;
	
	public void initialise()
	{
		System.out.println("Hello");
		newExamPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent)
			{
				System.out.println("Hello");
			}
		});
	}
	
}
