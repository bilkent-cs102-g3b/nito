package admin.view;

import org.controlsfx.control.BreadCrumbBar;

import common.NumberedEditor;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;

public class MainController
{
	@FXML
	private BreadCrumbBar<String> breadCrumb;
	@FXML
	private NumberedEditor editor;
	@FXML
	private TreeItem<String> examTree;
	
//	private ArrayList<String> exams;
	
	public void initialize()
	{
		test();
	}
	
	/**
	 * TODO Remove after testing
	 */
	private void test()
	{
		breadCrumb.setSelectedCrumb( BreadCrumbBar.buildTreeModel( "Some", "crumb", "here", "!"));
//		breadCrumb.autosize();
		
		examTree.getChildren().add(new TreeItem<String>("Exam1"));
	}
	
	/**
	 * Adds exam to the view
	 * @param name - The name of the exam
	 */
	public void addExam( String name)
	{
	}
}
