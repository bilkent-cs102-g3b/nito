package admin.view.grading;

import admin.model.Examinee;
import common.NumberedEditor;
import javafx.fxml.FXML;
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
	private ListView <Examinee>examineeList;
	@FXML
	private TabPane questionTabs;

	public void initialize()
	{
		//examineeList.getItems().addAll( Model.getInstance().getExaminees().getAll());
		examineeList.getSelectionModel().selectedItemProperty().addListener( (o, oldVal, newVal) -> {
			
		});
//		test();
	}
	private void openTabs( Examinee e)
	{
		questionTabs.getTabs().clear();
		e.getSolutions().forEach((part, solution) -> {
			Tab tab = new Tab( part.getTitle());
//			tab.setContent( );
		});
	}

	/**
	 * TODO Remove after testing
	 */
	private void test()
	{
		questionTree.getChildren().add( new TreeItem<String>( "Ziya Mukhtarov"));
		questionTree.getChildren().add( new TreeItem<String>( "Mokhlaroyim Raupova"));
	}

	/**
	 * Adds student to the view
	 * @param name - The name of the student
	 */
	public void addStudent( String name)
	{
	}

}
