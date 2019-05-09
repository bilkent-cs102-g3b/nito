package admin.view.grading;

import common.NumberedEditor;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;

public class GradingViewController
{
	@FXML
	private NumberedEditor editor;
	@FXML
	private TreeItem<String> questionTree;

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
		examTree.getChildren().add( new TreeItem<String>( "Exam1"));
	}

	/**
	 * Adds exam to the view
	 * @param name - The name of the exam
	 */
	public void addExam( String name)
	{
	}

}
