package testers.admin.gradingList;

//import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
//import javafx.stage.Stage;

public class GradingListViewController
{
	@FXML
	private TableView<ExamineeTableItem> table;
	
	@FXML
	private TableColumn <ExamineeTableItem, String> select;
	@FXML
	private TableColumn <ExamineeTableItem, String> name;
	@FXML
	private TableColumn <ExamineeTableItem, String> idNumber;
	@FXML
	private TableColumn <ExamineeTableItem, String> group;
	@FXML
	private TableColumn <ExamineeTableItem, String> q1;
	@FXML
	private TableColumn <ExamineeTableItem, String> q2;
	
	
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
