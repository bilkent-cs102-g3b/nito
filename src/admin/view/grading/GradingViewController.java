package admin.view.grading;

import java.io.IOException;

import admin.model.Examinee;
import admin.model.Model;
import common.NumberedEditor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;

public class GradingViewController
{
	@FXML
	private NumberedEditor editor;
	@FXML
	private TreeItem<String> questionTree;
	@FXML
	private TableView<GradingTableItem> gradingTable;
	@FXML
	private TableColumn<GradingTableItem, String> criteria;
	@FXML
	private TableColumn<GradingTableItem, TextField> points;
	@FXML
	private ListView<Examinee> examineeList;
	@FXML
	private TabPane questionTabs;

	public void initialize()
	{
		init();
		examineeList.getSelectionModel().selectedItemProperty().addListener( ( o, oldVal, newVal) -> {
			openTabs( newVal);
		});
	}

	public void init()
	{
		examineeList.getItems().clear();
		examineeList.getItems().addAll( Model.getInstance().getExaminees().getAll());
	}

	private void openTabs( Examinee e)
	{
		if (e == null)
			return;
		questionTabs.getTabs().clear();
		e.getSolutions().forEach( ( part, solution) -> {
			try
			{
				FXMLLoader loader = new FXMLLoader( getClass().getResource( "/admin/view/fxml/grading/QuestionPartTab.fxml"));
				Tab tab = loader.load();
				((QuestionPartTabController) loader.getController()).setExamineeAndPart( e, part);
				questionTabs.getTabs().add( tab);
			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
}
