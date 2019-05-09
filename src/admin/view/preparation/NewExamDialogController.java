package admin.view.preparation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.util.Pair;

/**
 * @author Ziya Mukhtarov
 * @version 08/05/2019
 */
public class NewExamDialogController
{
	public static final int MAX_SECS = 10 * 60 * 60 + 59 * 60;
	public static final int MIN_SECS = 59;

	@FXML
	Dialog<Pair<String, Integer>> root;
	@FXML
	TextField title;
	@FXML
	Spinner<Integer> hours;
	@FXML
	Spinner<Integer> mins;

	public void initialize()
	{
		Node okButton = root.getDialogPane().lookupButton( ButtonType.OK);

		okButton.addEventFilter( ActionEvent.ACTION, e -> {
			if ( !isValid())
			{
				Alert alert = new Alert( AlertType.ERROR);
				alert.setTitle( "Invalid arguments");
				alert.setHeaderText( "Invalid arguments");
				alert.setContentText( "The values you provided are invalid. Please check them and try again.");
				alert.showAndWait();

				e.consume();
			}
		});

		root.setResultConverter( button -> {
			if ( button == ButtonType.OK)
			{
				return new Pair<>( title.getText(), secs());
			}
			return null;
		});
	}

	private boolean isValid()
	{
		return hours.getValue() >= 0 && mins.getValue() >= 0 && secs() >= MIN_SECS && secs() <= MAX_SECS && title.getText().trim().length() > 0;
	}

	private int secs()
	{
		return hours.getValue() * 60 * 60 + mins.getValue() * 60;
	}
}
