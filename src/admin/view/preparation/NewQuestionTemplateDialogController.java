package admin.view.preparation;

import admin.model.Model;
import admin.model.exam_entries.Entry;
import admin.model.exam_entries.Exam;
import admin.model.exam_entries.Question;
import admin.model.exam_entries.QuestionPart;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;

public class NewQuestionTemplateDialogController
{
	@FXML
	Dialog<QuestionPart> root;
	@FXML
	ComboBox<Entry> exams;
	@FXML
	ComboBox<Entry> questions;
	@FXML
	ComboBox<Entry> parts;
	@FXML
	final int USE_PREF_SIZE = 50;

	public void initialize()
	{
		Node okButton = root.getDialogPane().lookupButton( ButtonType.OK);
		okButton.setDisable( true);

		exams.getItems().addAll( Model.getInstance().getEntries().findAll( Exam.class));
		questions.setDisable( true);
		parts.setDisable( true);

		exams.getSelectionModel().selectedItemProperty().addListener( ( o, oldVal, newVal) -> examSelected( newVal));
		questions.getSelectionModel().selectedItemProperty().addListener( ( o, oldVal, newVal) -> questionSelected( newVal));
		parts.getSelectionModel().selectedItemProperty().addListener( ( o, oldVal, newVal) -> okButton.setDisable( newVal == null));

		root.setResultConverter( button -> {
			if ( button == ButtonType.OK)
			{
				return (QuestionPart) parts.getValue();
			}
			return null;
		});
	}

	private void examSelected( Entry exam)
	{
		questions.getItems().setAll( exam.findAll( Question.class));
		questions.setPromptText( "Select a question");
		questions.setDisable( false);

		parts.setDisable( true);
		parts.setPromptText( "Select a question to continue");
	}

	private void questionSelected( Entry question)
	{
		parts.getItems().setAll( question.findAll( QuestionPart.class));
		parts.setPromptText( "Select a part");
		parts.setDisable( false);
	}
}
