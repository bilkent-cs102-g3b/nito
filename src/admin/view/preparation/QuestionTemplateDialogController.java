package admin.view.preparation;

import java.util.ArrayList;

import admin.model.Model;
import admin.model.exam_entries.Container;
import admin.model.exam_entries.Entry;
import admin.model.exam_entries.Exam;
import admin.model.exam_entries.Question;
import admin.model.exam_entries.QuestionPart;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;

public class QuestionTemplateDialogController
{
	@FXML
	Dialog<QuestionPart> root;
	@FXML
	ComboBox<Entry> exams;
	@FXML
	ComboBox<Entry> questions;
	@FXML
	ComboBox<Entry> parts;

	public void initialize()
	{
		Node okButton = root.getDialogPane().lookupButton( ButtonType.OK);
		okButton.setDisable( true);

		exams.getItems().addAll( find( Exam.class, Model.getInstance().getEntries()));
		questions.setDisable( true);
		parts.setDisable( true);

		exams.getSelectionModel().selectedItemProperty().addListener( ( o, oldVal, newVal) -> examSelected( newVal));
		questions.getSelectionModel().selectedItemProperty().addListener( ( o, oldVal, newVal) -> questionSelected( newVal));
		parts.getSelectionModel().selectedItemProperty().addListener( ( o, oldVal, newVal) -> okButton.setDisable( newVal == null));

		root.setResultConverter( button -> {
			if ( button == ButtonType.OK)
			{
				return (QuestionPart) parts.getSelectionModel().getSelectedItem();
			}
			return null;
		});
	}

	private ArrayList<Entry> find( Class<?> type, Container container)
	{
		ArrayList<Entry> result = new ArrayList<>();

		if ( container != null)
		{
			container.getAll().stream().forEachOrdered( e -> result.addAll( find( type, e)));
			container.getAll().stream().filter( e -> e.getClass() == type).forEachOrdered( result::add);
		}

		return result;
	}

	private void examSelected( Entry exam)
	{
		questions.getItems().setAll( find( Question.class, exam));
		questions.setPromptText( "Select a question");
		questions.setDisable( false);

		parts.setDisable( true);
		parts.setPromptText( "Select a question to continue");
	}

	private void questionSelected( Entry question)
	{
		parts.getItems().setAll( find( QuestionPart.class, question));
		parts.setPromptText( "Select a part");
		parts.setDisable( false);
	}
}
