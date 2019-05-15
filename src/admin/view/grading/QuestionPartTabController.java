package admin.view.grading;

import admin.model.Examinee;
import admin.model.exam_entries.QuestionPart;
import common.NumberedEditor;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class QuestionPartTabController
{
	private Examinee examinee;
	private QuestionPart questionPart;
	
	@FXML
	private Label maxPointLabel;
	@FXML
	private Tab root;
	@FXML
	private NumberedEditor editor;
	@FXML
	private TextField grade;
	@FXML
	private TextArea notes;

	public void initialize()
	{
		notes.textProperty().addListener( ( o, oldVal, newVal) -> {
			examinee.setNotes( newVal);
		});
		
		grade.textProperty().addListener( (o, oldVal, newVal) -> {
			examinee.setGrade( questionPart, newVal);
		});
		
		editor.disableEditor();
	}

	public void setExamineeAndPart( Examinee e, QuestionPart part)
	{
		examinee = e;
		questionPart = part;

		notes.textProperty().bindBidirectional( e.notesProperty());
		maxPointLabel.setText("Maximum points: " + part.getMaxPoints() + " points");
		editor.setText( e.getSolutions().get( part));
		root.setText( part.getTitle());
		grade.setText( (e.getGrade( part) == null ? "" : e.getGrade( part)));
	}
}
