package admin.view;

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
	ChoiceBox<String> questions;
	@FXML
	ChoiceBox<QuestionPart> parts;

	public void initialize()
	{
		questions.setDisable(true);
		parts.setDisable(true);
		findExams();
	}
	
	private void findExams()
	{
		Container c = Model.getInstance().getEntries();
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
	
	ChangeListener<Exam> listener = new ChangeListener<Exam>() {
		 
        @Override
        public void changed(ObservableValue<? extends Exam> observable, //
                Exam oldValue, Exam newValue) {
            if (newValue != null) {
            	questions.setDisable(false);
                questions.getItems().add("Test");
            }
        }
	};

}
