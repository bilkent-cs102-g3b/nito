package admin.view.preparation;

import java.io.IOException;

import admin.model.Model;
import admin.model.exam_entries.Entry;
import admin.model.exam_entries.Exam;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

/**
 * @author Ziya Mukhtarov
 * @version 09/05/2019
 */
public class NewQuestionDialogController
{
	@FXML
	Dialog<?> root;
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

		nextButton.addEventFilter( ActionEvent.ACTION, e -> {
			try
			{
				Dialog<?> d = FXMLLoader.load( getClass().getResource( "/admin/view/fxml/preparation/NewQuestionPartDialog.fxml"));
				root.hide();
				d.showAndWait();
				root.show();
				e.consume();
			}
			catch (IOException ex)
			{
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		});
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
