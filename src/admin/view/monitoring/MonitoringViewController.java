package admin.view.monitoring;

import java.io.IOException;

import admin.model.Examinee;
import admin.model.Model;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

public class MonitoringViewController
{
	@FXML
	private ListView<String> logActionList;
	@FXML
	private ListView<String> logTimeList;
	@FXML
	private Pane screenPane;

	public MonitoringViewController()
	{
		ObservableList<Pair<String, Integer>> list = Model.getInstance().getLogs();
		list.addListener( ( ListChangeListener.Change<? extends Pair<String, Integer>> c) -> {
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
	}

	public void addExaminee( Examinee e)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader( getClass().getResource( "/admin/view/fxml/monitoring/ExamineeScreen.fxml"));
			Pane p = loader.load();
			((ExamineeScreenController) loader.getController()).setExaminee( e);
			screenPane.getChildren().add( p);
		}
		catch (IOException ex)
		{
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
}
