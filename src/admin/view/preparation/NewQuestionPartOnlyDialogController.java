package admin.view.preparation;

import admin.model.Model;
import admin.model.exam_entries.Entry;
import admin.model.exam_entries.Exam;
import admin.model.exam_entries.Question;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.util.Pair;

public class NewQuestionPartOnlyDialogController
{
	@FXML
	Dialog<Pair<Question, Pair<String, Integer>>> root;
	@FXML
	ComboBox<Entry> exams;
	@FXML
	ComboBox<Entry> questions;
	@FXML
	ComboBox<Entry> parts;
	@FXML
	TextField title;
	@FXML
	TextField points;
	@FXML
	final int USE_PREF_SIZE = 50;

	public void initialize()
	{
		Node okButton = root.getDialogPane().lookupButton( ButtonType.OK);
		okButton.setDisable( true);

		exams.getItems().addAll( Model.getInstance().getEntries().findAll( Exam.class));
		questions.setDisable( true);
		
		ChangeListener<? super Object> listener = ( ObservableValue<?> o, Object oldVal, Object newVal) -> okButton.setDisable( !isValid());
		exams.valueProperty().addListener( listener);
		questions.valueProperty().addListener( listener);
		title.textProperty().addListener( listener);
		points.textProperty().addListener( listener);
		
		exams.getSelectionModel().selectedItemProperty().addListener( ( o, oldVal, newVal) -> examSelected( newVal));

		root.setResultConverter( button -> {
			if ( button == ButtonType.OK)
			{
				Pair<String, Integer> intermediate =  new Pair<>(title.getText(), Integer.parseInt(points.getText()));
				return new Pair<>((Question)questions.getValue(), intermediate);
			}
			return null;
		});
	}

	private void examSelected( Entry exam)
	{
		questions.getItems().setAll( exam.findAll( Question.class));
		if(!exam.findAll( Question.class).isEmpty())
		{
			questions.setPromptText( "Select a question");
			questions.setDisable( false);
		}
		else
		{
			questions.setPromptText( "Exam has no questions");
		}
	}
	
	private boolean isValid()
	{
		try
		{
			int numOfParts = Integer.parseInt( points.getText());
			return numOfParts >= 1 && title.getText().trim().length() > 0 && exams.getValue() != null && questions.getValue() != null;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
	}
}
