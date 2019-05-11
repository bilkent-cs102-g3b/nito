package admin.view.grading;

import common.NumberedEditor;
import javafx.fxml.FXML;
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

	// private ArrayList<String> exams;

	public void initialize()
	{
		test();
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
