package admin.view.monitoring;

import java.util.Optional;

import org.tbee.javafx.scene.layout.MigPane;

import admin.model.Examinee;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ExamineeScreenController
{
	private Examinee examinee;
	@FXML
	private Pane root;
	@FXML
	TitledPane titlePane;
	@FXML
	private ImageView screen;
	
	public void setExaminee( Examinee e)
	{
		examinee = e;
		screen.imageProperty().bindBidirectional( e.getScreenImageProperty());
		titlePane.setText(e.getName());
	}
	
	@FXML
	public void openNotes()
	{	
		Dialog<String> noteDialog = new Dialog<String>();
		noteDialog.setHeaderText("Notes");
		noteDialog.getDialogPane().getButtonTypes().add( ButtonType.APPLY);
		noteDialog.getDialogPane().getButtonTypes().add( ButtonType.CANCEL);
		
		MigPane content = new MigPane();
		Label note = new Label("Note: ");
		TextArea text = new TextArea( examinee.getNotes());
		
		content.add(note, "left");
		content.add(text, "grow, span");
		
		noteDialog.getDialogPane().setContent(content);
		
		noteDialog.setResultConverter( button -> {
			if ( button == ButtonType.APPLY)
			{
				return text.getText();
			}
			return null;
		});
		
		Optional<String> result = noteDialog.showAndWait();
		result.ifPresent( c -> examinee.setNotes( result.get()));
	}

	public void setWidth(int width)
	{
		if (examinee != null)
		{
			titlePane.setPrefWidth( width);
			examinee.setScreenWidth( width);
		}
	}
}
