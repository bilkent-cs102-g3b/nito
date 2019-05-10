package admin.view.preparation;

import admin.model.Model;
import admin.model.exam_entries.Entry;
import admin.model.exam_entries.Exam;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.util.Pair;

/**
 * @author Ziya Mukhtarov
 * @version 09/05/2019
 */
public class NewQuestionDialogController
{
	@FXML
	Dialog<Pair<Entry, Pair<String, Integer>>> root;
	@FXML
	ComboBox<Entry> examsBox;
	@FXML
	TextField titleField;
	@FXML
	TextField numOfPartsField;

	public void initialize()
	{
		Node nextButton = root.getDialogPane().lookupButton( ButtonType.NEXT);
		nextButton.setDisable( true);

		examsBox.getItems().setAll( Model.getInstance().getEntries().findAll( Exam.class));

		ChangeListener<? super Object> listener = ( ObservableValue<?> o, Object oldVal, Object newVal) -> nextButton.setDisable( !isValid());
		examsBox.valueProperty().addListener( listener);
		titleField.textProperty().addListener( listener);
		numOfPartsField.textProperty().addListener( listener);

		root.setResultConverter( button -> {
			if ( button == ButtonType.NEXT)
			{
				return new Pair<Entry, Pair<String, Integer>>(examsBox.getValue(), new Pair<String, Integer>(titleField.getText(), Integer.parseInt( numOfPartsField.getText())));
			}
			return null;
		});
	}
	
	public void openQuestionParts()
	{
		
	}

	private boolean isValid()
	{
		try
		{
			int numOfParts = Integer.parseInt( numOfPartsField.getText());
			return numOfParts >= 1 && titleField.getText().trim().length() > 0 && examsBox.getValue() != null;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
	}
}
