package admin.view.preparation;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
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
	TextField hours;
	@FXML
	TextField mins;

	public void initialize()
	{
		Node okButton = root.getDialogPane().lookupButton( ButtonType.OK);
		okButton.setDisable( true);

		title.textProperty().addListener( (o, oldVal, newVal) -> {
			okButton.setDisable( !isValid());
		});

		hours.textProperty().addListener( (o, oldVal, newVal) -> {
			okButton.setDisable( !isValid());
		});

		mins.textProperty().addListener( (o, oldVal, newVal) -> {
			okButton.setDisable( !isValid());
		});
		
		root.setResultConverter( button -> {
			if ( button == ButtonType.OK)
			{
				return new Pair<>( title.getText(), toSecs());
			}
			return null;
		});
	}

	private boolean isValid()
	{
		try
		{
			int hour = Integer.parseInt( hours.getText());
			int min = Integer.parseInt( mins.getText());
			return hour >= 0 && hour <= 10 && min >= 0 && min <= 59 && toSecs() >= MIN_SECS && toSecs() <= MAX_SECS && title.getText().trim().length() > 0;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
	}

	private int toSecs()
	{
		int hour = Integer.parseInt( hours.getText());
		int min = Integer.parseInt( mins.getText());
		return hour * 60 * 60 + min * 60;
	}
}
