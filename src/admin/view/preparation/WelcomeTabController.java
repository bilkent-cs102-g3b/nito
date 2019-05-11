package admin.view.preparation;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;

public class WelcomeTabController
{
	@FXML
	Tab tab;
	
	private MainController mainController;
	
	public void initialize()
	{
//		tab.getTabPane().getParent().
	}

	public void controllerInstance(MainController mainController) {
		   this.mainController = mainController;
	}
	
	public void addExam()
	{
		mainController.addExam();
	}
}
