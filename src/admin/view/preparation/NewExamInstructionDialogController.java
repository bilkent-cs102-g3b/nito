package admin.view.preparation;

import java.util.ArrayList;

import admin.model.Model;
import admin.model.exam_entries.Container;
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

		exams.getItems().addAll( find( Exam.class, Model.getInstance().getEntries()));
		exams.getSelectionModel().selectedItemProperty().addListener( ( o, oldVal, newVal) -> okButton.setDisable( newVal == null));
		root.setResultConverter( button -> {
			if ( button == ButtonType.OK)
			{
				return (Exam) exams.getSelectionModel().getSelectedItem();
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
}
