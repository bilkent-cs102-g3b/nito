package admin.view.preparation;

import java.util.ArrayList;

import admin.model.Model;
import admin.model.exam_entries.Entry;
import admin.model.exam_entries.Exam;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;

public class NewExamInstructionDialogController
{
	@FXML
	Dialog<Exam> root;
	@FXML
	ComboBox<Entry> exams;
	@FXML
	final int USE_PREF_SIZE = 50;

	public void initialize()
	{
		Node okButton = root.getDialogPane().lookupButton( ButtonType.OK);
		okButton.setDisable( true);

		Entry lastExam = Model.getInstance().getLastExam();
		ArrayList<Entry> examsList = Model.getInstance().getEntries().findAll( Exam.class);
		examsList.removeIf( e -> ((Exam) e).hasInstructions() || e == lastExam);
		exams.getItems().addAll( examsList);

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
			}
		});

		exams.getSelectionModel().selectedItemProperty().addListener( ( o, oldVal, newVal) -> okButton.setDisable( newVal == null));
		root.setResultConverter( button -> {
			if ( button == ButtonType.OK)
			{
				return (Exam) exams.getSelectionModel().getSelectedItem();
			}
			return null;
		});
	}
}
