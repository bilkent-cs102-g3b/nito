package admin.view.grading;

import org.controlsfx.control.BreadCrumbBar;

//import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
//import javafx.stage.Stage;

public class GradingListViewController
{
	@FXML
	private TableView<ExamineeTableItem> table;
	@FXML
	private BreadCrumbBar<String> breadcrumb;
	@FXML
	private TreeItem<String> grading, student1, student2, ques1, ques2;
	@FXML
	private TableColumn <ExamineeTableItem, CheckBox> select;
	@FXML
	private TableColumn <ExamineeTableItem, String> name;
	@FXML
	private TableColumn <ExamineeTableItem, String> idNumber;
	@FXML
	private TableColumn <ExamineeTableItem, String> group;
	@FXML
	private TableColumn <ExamineeTableItem, Button> notes;
	@FXML
	private TableColumn <ExamineeTableItem, String> q1;
	@FXML
	private TableColumn <ExamineeTableItem, String> part1;
	@FXML
	private TableColumn <ExamineeTableItem, String> part2;
	@FXML
	private TableColumn <ExamineeTableItem, String> q2;
	
	@FXML
	private TableColumn <ExamineeTableItem, String> total;
	
//	public void initialize()
//	{
//		student1 = new TreeItem<String>("Student");
//		ques1 = new TreeItem<String>("Q1");
//		ques2 = new TreeItem<String>("Q2");
//		grading = new TreeItem<String>("GradingView");
//		
//		student1.getChildren().addAll( ques1, ques2);
//		grading.getChildren().addAll(student1);
//		
//		breadcrumb.setSelectedCrumb(grading);
//	}
//	public void start()
//	{
//		table.getColumns().addAll(select, fullName, id, group, q1, q2);
//	}
//
//	@Override
//	public void start(Stage arg0) throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
	
	

}
