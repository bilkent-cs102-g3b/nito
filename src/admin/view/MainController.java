package admin.view;

import java.io.IOException;

import admin.view.monitoring.MonitoringViewController;
import admin.view.preparation.MainEditorController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * @author Ziya Mukhtarov
 * @version 12/05/2019
 */
public class MainController
{
	@FXML
	VBox root;
	@FXML
	Button preparationButton;
	@FXML
	Button monitoringButton;
	@FXML
	Button gradingButton;

	private FXMLLoader preparationLoader;
	private FXMLLoader monitoringLoader;
	private FXMLLoader gradingLoader;

	private MainEditorController preparationController;
	private MonitoringViewController monitoringController;
	// TODO gradingControl

	private Node preparation;
	private Node monitoring;
	private Pane grading;

	public static final int STAGE_PREPARATION = 1;
	public static final int STAGE_MONITORING = 2;
	public static final int STAGE_GRADING = 3;
	private int currentStage;

	public void initialize() throws IOException
	{
		preparationLoader = new FXMLLoader( getClass().getResource( "/admin/view/fxml/preparation/MainEditor.fxml"));
		monitoringLoader = new FXMLLoader( getClass().getResource( "/admin/view/fxml/monitoring/MonitoringView.fxml"));
		// TODO gradingLoader = new FXMLLoader( getClass().getResource(
		// "/admin/view/fxml/grading/MainEditor.fxml"));

		preparation = preparationLoader.load();
		preparationController = preparationLoader.getController();

		monitoring = monitoringLoader.load();
		monitoringController = monitoringLoader.getController();

		// TODO load grading

		((MainEditorController) preparationLoader.getController()).setMainController( this);
		
		currentStage = STAGE_PREPARATION;
		root.getChildren().add( preparation);
	}

	@FXML
	public void addExam()
	{
		preparationController.addExam();
	}

	@FXML
	public void addInstructions()
	{
		preparationController.addInstructions();
	}

	@FXML
	public void addQuestion()
	{
		preparationController.addQuestion();
	}

	@FXML
	public void addPart()
	{
		preparationController.addPart();
	}

	@FXML
	public void addTemplate()
	{
		preparationController.addTemplate();
	}

	@FXML
	public void changeToPreparation()
	{
		if ( currentStage != STAGE_PREPARATION)
		{
			currentStage = STAGE_PREPARATION;
			root.getChildren().remove( root.getChildren().size() - 1);
			root.getChildren().add( preparation);
			VBox.setVgrow( preparation, Priority.ALWAYS);
			preparationController.updateTreeView();

			preparationButton.getStyleClass().add( "selectedNavButton");
			monitoringButton.getStyleClass().add( "navButton");
			gradingButton.getStyleClass().add( "navButton");
			preparationButton.getStyleClass().remove( "navButton");
			monitoringButton.getStyleClass().remove( "selectedNavButton");
			gradingButton.getStyleClass().remove( "selectedNavButton");
		}
	}

	@FXML
	public void changeToMonitoring()
	{
		if ( currentStage != STAGE_MONITORING)
		{
			currentStage = STAGE_MONITORING;
			root.getChildren().remove( root.getChildren().size() - 1);
			root.getChildren().add( monitoring);
			VBox.setVgrow( monitoring, Priority.ALWAYS);

			preparationButton.getStyleClass().add( "navButton");
			monitoringButton.getStyleClass().add( "selectedNavButton");
			gradingButton.getStyleClass().add( "navButton");
			preparationButton.getStyleClass().remove( "selectedNavButton");
			monitoringButton.getStyleClass().remove( "navButton");
			gradingButton.getStyleClass().remove( "selectedNavButton");
		}
	}

	@FXML
	public void changeToGrading()
	{
		if ( currentStage != STAGE_GRADING)
		{
			currentStage = STAGE_GRADING;
			root.getChildren().remove( root.getChildren().size() - 1);
			root.getChildren().add( grading);
			VBox.setVgrow( grading, Priority.ALWAYS);

			preparationButton.getStyleClass().add( "navButton");
			monitoringButton.getStyleClass().add( "navButton");
			gradingButton.getStyleClass().add( "selectedNavButton");
			preparationButton.getStyleClass().remove( "selectedNavButton");
			monitoringButton.getStyleClass().remove( "selectedNavButton");
			gradingButton.getStyleClass().remove( "navButton");
		}
	}
}
