package admin.view.preparation;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

/**
 * @author Ziya Mukhtarov
 * @version 09/05/2019
 */
public class NewQuestionPartDialogController
{
	@FXML
	Dialog root;
	@FXML
	TextField titleField;
	@FXML
	TextField maxPointsField;
	
	public void initialize()
	{
		Node nextButton = root.getDialogPane().lookupButton( ButtonType.NEXT);
		nextButton.setDisable( true);
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
