package admin.view.grading;


import javafx.beans.property.SimpleStringProperty;
//import javafx.scene.control.TextField;

public class GradingTableItem 
{
	private final SimpleStringProperty criteria1 = new SimpleStringProperty("");
	//private final TextField grades = new TextField("");
	
	public GradingTableItem(String string) 
	{
		setCriteria1(string);
		//setGrades(text);
	}
	
	public GradingTableItem()
	{
		this("");
		//grades.setVisible(true);
	}

	public String getCriteria1() 
	{
		return criteria1.get();
	}

	public void setCriteria1(String criteria) 
	{
		criteria1.set(criteria);
	}

//	public String getGrades() {
//		return grades.getText();
//	}
//
//	public void setGrades(String text) {
//		grades.setText(text);
//	}

}
