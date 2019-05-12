package admin.view.monitoring;

import java.io.IOException;
import java.util.ArrayList;

import admin.model.Examinee;
import admin.model.Model;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
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
	private ListView<String> logActionList;
	@FXML
	private ListView<String> logTimeList;
	@FXML
	private Pane screenPane;
	@FXML
	private Slider zoomSlider;

	private ArrayList<ExamineeScreenController> controllers;
	
	public MonitoringViewController()
	{
		controllers = new ArrayList<ExamineeScreenController>();
	}
	
	public void initialize() {
		ObservableList<Pair<String, Integer>> list = Model.getInstance().getLogs();
		list.addListener((ListChangeListener.Change<? extends Pair<String, Integer>> c) -> {
			while (c.next()) {
				c.getAddedSubList().forEach(a -> {
					// TODO check whether run later is needed
					logActionList.getItems().add(a.getKey());
					logTimeList.getItems().add((a.getValue() / 60) + ":" + (a.getValue() % 60));
				});
			}
		});

		root.widthProperty().addListener( (o, oldVal, newVal) -> {
			zoomSlider.setMax( (int) Math.max( newVal.doubleValue() - 100, 300));
			zoomSlider.setMajorTickUnit( (int) (zoomSlider.getMax() - zoomSlider.getMin()) / 10);
		});

//		root.heightProperty().addListener( (o, oldVal, newVal) -> {
//			splitPane.setMaxHeight( newVal.doubleValue() - zoomSlider.getHeight());
//		});
		
		zoomSlider.valueProperty().addListener((o, oldVal, newVal) -> {
			Model.getInstance().setScreenshotWidth( newVal.intValue());
			controllers.forEach( controller -> {
				controller.setWidth( newVal.intValue());
			});
		});
	}

	public void addExaminee(Examinee e) {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/admin/view/fxml/monitoring/ExamineeScreen.fxml"));
			Pane p = loader.load();
			((ExamineeScreenController) loader.getController()).setExaminee(e);
			controllers.add( loader.getController());
			screenPane.getChildren().add(p);
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
}
