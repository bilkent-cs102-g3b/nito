package admin.view.preparation;

import java.util.ArrayList;

import admin.model.Model;
import admin.model.exam_entries.Container;
import admin.model.exam_entries.Entry;
import admin.model.exam_entries.Exam;
import admin.model.exam_entries.Question;
import admin.model.exam_entries.QuestionPart;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class QuestionTemplateDialogController {
	
	@FXML
	ChoiceBox<Exam> exams;
	@FXML
	ChoiceBox<Question> questions;
	@FXML
	ChoiceBox<QuestionPart> parts;
	
	private Container c = Model.getInstance().getEntries();
	private Exam e;
	private Question q;
	
	public void initialize()
	{
		questions.setDisable(true);
		parts.setDisable(true);
		findExams();
	}
	
	private void findExams()
	{
		ArrayList<Entry> entries = c.getAll();
		for ( Entry entry : entries)
		{
			if (entry instanceof Exam)
			{
				exams.getItems().add((Exam)entry);
			}
		}
		exams.getSelectionModel().selectedItemProperty().addListener(listener);
	}
	
	private void findQuestions() 
	{
		questions.getItems().clear();
		parts.getItems().clear();
		ArrayList<Entry> entries = e.getAll();
			for (Entry questionEntry : entries)
			{
				if (questionEntry instanceof Question)
				{
					questions.getItems().add((Question) questionEntry);
				}
			}
		questions.getSelectionModel().selectedItemProperty().addListener(questionsListener);
	}
	
	private void findParts() 
	{
		parts.getItems().clear();
		ArrayList<Entry> entries = q.getAll();
		for (Entry questionPartEntry : entries)
		{
			if (questionPartEntry instanceof QuestionPart)
			{
				parts.getItems().add((QuestionPart) questionPartEntry);
			}
		}
	}
	
	ChangeListener<Exam> listener = new ChangeListener<Exam>() {
		 
        @Override
        public void changed(ObservableValue<? extends Exam> observable, //
                Exam oldValue, Exam newValue) {
            if (newValue != null) {
            	e = exams.getValue();
            	questions.setDisable(false);
                findQuestions();
            }
        }
	};
	
	ChangeListener<Question> questionsListener = new ChangeListener<Question>() {
		 
        @Override
        public void changed(ObservableValue<? extends Question> observable, //
        		Question oldValue, Question newValue) {
            if (newValue != null) {
            	q = questions.getValue();
            	parts.setDisable(false);
                findParts();
            }
        }
	};

}
