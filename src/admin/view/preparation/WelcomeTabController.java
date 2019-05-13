package admin.view.preparation;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;

public class WelcomeTabController
{
	@FXML
	Tab tab;

	private MainEditorController mainController;

	public void setMainController( MainEditorController mainController)
	{
		this.mainController = mainController;
	}

	public void addExam()
	{
		mainController.addExam();
	}
}
