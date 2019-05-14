package admin.view.preparation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.util.Pair;

/**
 * @author Ziya Mukhtarov
 * @version 09/05/2019
 */
public class NewQuestionPartDialogController
{
	@FXML
	Dialog<Pair<String, Integer>> root;
	@FXML
	TextField titleField;
	@FXML
	TextField maxPointsField;

	public void initialize()
	{
		root.getDialogPane().lookupButton( ButtonType.PREVIOUS).setDisable( true);

		root.setOnShown( e -> {
			Node continueButton;
			@SuppressWarnings("unchecked")
			Pair<Integer, Boolean> userData = (Pair<Integer, Boolean>) root.getDialogPane().getUserData();
			root.setHeaderText( "Create part " + userData.getKey());
			if ( userData.getValue())
			{
				root.getDialogPane().getButtonTypes().add( ButtonType.FINISH);
				continueButton = root.getDialogPane().lookupButton( ButtonType.FINISH);
			}
			else
			{
				root.getDialogPane().getButtonTypes().add( ButtonType.NEXT);
				continueButton = root.getDialogPane().lookupButton( ButtonType.NEXT);
			}
			continueButton.setDisable( true);

			ChangeListener<? super Object> listener = ( ObservableValue<?> o, Object oldVal, Object newVal) -> continueButton.setDisable( !isValid());
			titleField.textProperty().addListener( listener);
			maxPointsField.textProperty().addListener( listener);
		});

		root.setResultConverter( button -> {
			if ( button == ButtonType.NEXT || button == ButtonType.FINISH)
			{
				return new Pair<String, Integer>( titleField.getText(), Integer.parseInt( maxPointsField.getText()));
			}
			return null;
		});
	}

	private boolean isValid()
	{
		try
		{
			int maxPoints = Integer.parseInt( maxPointsField.getText());
			return maxPoints >= 0 && titleField.getText().trim().length() > 0;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
	}
}
