package admin.view;

import java.io.IOException;

import admin.model.Model;
import admin.view.grading.GradingViewController;
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
	Pane toolBar;
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
	private GradingViewController gradingController;

	private Node preparation;
	private Node monitoring;
	private Node grading;

	public static final int STAGE_PREPARATION = 1;
	public static final int STAGE_MONITORING = 2;
	public static final int STAGE_GRADING = 3;
	private int currentStage;

	public void initialize() throws IOException
	{
		preparationLoader = new FXMLLoader( getClass().getResource( "/admin/view/fxml/preparation/MainEditor.fxml"));
		monitoringLoader = new FXMLLoader( getClass().getResource( "/admin/view/fxml/monitoring/MonitoringView.fxml"));
		gradingLoader = new FXMLLoader( getClass().getResource( "/admin/view/fxml/grading/GradingView.fxml"));

		preparation = preparationLoader.load();
		preparationController = preparationLoader.getController();

		monitoring = monitoringLoader.load();
		monitoringController = monitoringLoader.getController();

		grading = gradingLoader.load();
		gradingController = gradingLoader.getController();

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

			toolBar.getChildren().clear();
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

			/***** TOOLBAR *****/
			Button examControl = new Button();
			toolBar.getChildren().add( examControl);
			
			Button blockConnection = new Button("Block Connections");
			blockConnection.setDisable(true);
			blockConnection.setOnAction(b -> {
				Model.getInstance().blockConnection();
				blockConnection.setDisable(true);
			});
			toolBar.getChildren().add( blockConnection);
			
			if ( Model.getInstance().getLastExam().isRunning())
			{
				examControl.setText( "End this exam");
				examControl.setOnAction( e -> {
					Model.getInstance().endCurrentExam();
					examControl.setDisable(true);
					blockConnection.setDisable(true);
				});
				
				if (!Model.getInstance().isConnectionBlocked())
					blockConnection.setDisable(false);
			}
			else
			{
				examControl.setText( "Start exam");
				examControl.setOnAction( e -> {
					Model.getInstance().startExam();
					
					blockConnection.setDisable(false);
				});
			}
		}
	}

	private 
	
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

			toolBar.getChildren().clear();
			gradingController.init();
		}
	}
}
