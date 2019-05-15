package admin.view.monitoring;

import java.io.IOException;
import java.util.ArrayList;

import admin.model.Examinee;
import admin.model.Model;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class MonitoringViewController
{
	@FXML
	VBox root;
	@FXML
	SplitPane splitPane;
	@FXML
	ScrollPane logScroller;
	@FXML
	private ListView<String> logActionList;
	@FXML
	private ListView<String> logTimeList;
	@FXML
	private Label placeHolder;
	@FXML
	ScrollPane scrollPane;
	@FXML
	private FlowPane screenPane;
	@FXML
	private Slider zoomSlider;
	@FXML
	private Label timeRemaining;

	private ArrayList<ExamineeScreenController> controllers;

	private ObjectProperty<ScrollBar> logActionListScroll;
	private ObjectProperty<ScrollBar> logTimeListScroll;

	public MonitoringViewController()
	{
		logActionListScroll = new SimpleObjectProperty<>();
		logTimeListScroll = new SimpleObjectProperty<>();

		logActionListScroll.addListener( ( o, oldVal, newVal) -> {
			if ( logTimeListScroll.get() != null)
			{
				logActionListScroll.get().valueProperty().bindBidirectional( logTimeListScroll.get().valueProperty());
			}
		});
		logTimeListScroll.addListener( ( o, oldVal, newVal) -> {
			if ( logActionListScroll.get() != null)
			{
				logActionListScroll.get().valueProperty().bindBidirectional( logTimeListScroll.get().valueProperty());
			}
		});

		controllers = new ArrayList<ExamineeScreenController>();
	}

	public void initialize()
	{
		scrollPane.viewportBoundsProperty().addListener( ( o, oldVal, newVal) -> {
			screenPane.setPrefWidth( newVal.getWidth());
		});

		Model.getInstance().getLogs().addListener( ( ListChangeListener.Change<? extends Pair<String, Integer>> c) -> {
			while ( c.next())
			{
				c.getAddedSubList().forEach( a -> {
					Platform.runLater( () -> {
						logActionList.getItems().add( a.getKey());
						logTimeList.getItems().add( (a.getValue() / 60) + ":" + (a.getValue() % 60));
					});
				});
			}
		});

		Model.getInstance().getExaminees().getAllAsObservable().addListener( ( ListChangeListener.Change<? extends Examinee> c) -> {
			while ( c.next())
			{
				c.getAddedSubList().forEach( e -> {
					Platform.runLater( () -> {
						addExaminee( e);
					});
				});
			}
		});

		setTimeRemaining();
		Model.getInstance().getLastExam().timeLeftProperty().addListener( ( o, oldVal, newVal) -> setTimeRemaining());

		root.widthProperty().addListener( ( o, oldVal, newVal) -> {
			zoomSlider.setMax( (int) Math.max( newVal.doubleValue() - 100, 300));
			zoomSlider.setMajorTickUnit( (int) (zoomSlider.getMax() - zoomSlider.getMin()) / 10);
		});

		zoomSlider.valueProperty().addListener( ( o, oldVal, newVal) -> {
			Model.getInstance().setScreenshotWidth( newVal.intValue());
			controllers.forEach( controller -> {
				controller.setWidth( newVal.intValue());
			});
		});

		new Thread( () -> {
			while ( true)
			{
				ScrollBar sb = (ScrollBar) logActionList.lookup( ".scroll-bar:vertical");
				if ( sb != null)
				{
					sb.setStyle( "-fx-opacity: 0");
					logActionListScroll.set( sb);
					break;
				}
			}
		}).start();
		new Thread( () -> {
			while ( true)
			{
				ScrollBar sb = (ScrollBar) logTimeList.lookup( ".scroll-bar:vertical");
				if ( sb != null)
				{
					logTimeListScroll.set( sb);
					break;
				}
			}
		}).start();
	}

	public void setTimeRemaining()
	{
		Platform.runLater( () -> {
			int seconds = Model.getInstance().getLastExam().getTimeLeft();
			timeRemaining.setText( "Time Remaining: " + (seconds / 3600) + ":" + (seconds % 3600 / 60) + ":" + (seconds % 60));
		});
	}

	public void addExaminee( Examinee e)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader( getClass().getResource( "/admin/view/fxml/monitoring/ExamineeScreen.fxml"));
			Pane p = loader.load();
			((ExamineeScreenController) loader.getController()).setExaminee( e);
			controllers.add( loader.getController());
			screenPane.getChildren().remove( placeHolder);
			screenPane.getChildren().add( p);

			e.statusProperty().addListener( ( o, oldVal, newVal) -> {
				if ( newVal.intValue() == Examinee.STATUS_DISCONNECTED)
				{
					Platform.runLater( () -> {
						controllers.remove( loader.getController());
						screenPane.getChildren().remove( p);
					});
				}
			});
		}
		catch (IOException ex)
		{
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
}
