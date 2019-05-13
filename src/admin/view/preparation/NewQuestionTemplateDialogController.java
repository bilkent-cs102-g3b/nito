package admin.view.preparation;

import java.util.ArrayList;

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

		Entry lastExam = Model.getInstance().getLastExam();
		exams.getItems().addAll( Model.getInstance().getEntries().findAll( Exam.class));
		exams.getItems().remove( lastExam);

		questions.setDisable( true);
		parts.setDisable( true);

		exams.getSelectionModel().selectedItemProperty().addListener( ( o, oldVal, newVal) -> examSelected( newVal));
		questions.getSelectionModel().selectedItemProperty().addListener( ( o, oldVal, newVal) -> questionSelected( newVal));
		parts.getSelectionModel().selectedItemProperty().addListener( ( o, oldVal, newVal) -> okButton.setDisable( newVal == null));

		root.setOnShown( e -> {
			Object userData = root.getDialogPane().getUserData();
			if ( userData != null && userData instanceof Entry)
			{
				Entry entryUserData = (Entry) userData;
				Entry selectedExam = entryUserData.findFirstAncestor( Exam.class);
				if ( selectedExam != null && selectedExam != lastExam)
				{
					exams.getSelectionModel().select( selectedExam);
				}

				Entry selectedQuestion = entryUserData.findFirstAncestor( Question.class);
				if ( selectedQuestion != null)
				{
					questions.getSelectionModel().select( selectedQuestion);
				}

				Entry selectedPart = entryUserData.findFirstAncestor( QuestionPart.class);
				if ( selectedPart != null && parts.getItems().contains( selectedPart))
				{
					parts.getSelectionModel().select( selectedPart);
				}
			}
		});

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
		ArrayList<Entry> questionsPartList = question.findAll( QuestionPart.class);
		questionsPartList.removeIf( e -> ((QuestionPart) e).hasTemplate());
		parts.getItems().setAll( questionsPartList);
		parts.setPromptText( "Select a part");
		parts.setDisable( false);
	}
}
