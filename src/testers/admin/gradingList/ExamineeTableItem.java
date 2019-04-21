package testers.admin.gradingList;

//import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

public class ExamineeTableItem 
{	
	private final SimpleStringProperty name = new SimpleStringProperty("");
	private final SimpleStringProperty group = new SimpleStringProperty("");
	private final SimpleStringProperty idNumber = new SimpleStringProperty("");
	
	
	public ExamineeTableItem() 
	{
        this("", "", "");
    }

	public ExamineeTableItem(String name, String group, String idNumber) 
	{
        setName(name);
        setGroup(group);
        setIdNumber(idNumber);
    }
	
	public String getIdNumber() {
		return idNumber.get();
	}

	public void setIdNumber(String idNumber2) {
		this.idNumber.set(idNumber2);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);;
	}

	public String getGroup() {
		return group.get();
	}

	public void setGroup(String group) {
		this.group.set(group);;
	}
	
//	public void addQuestionAnswer(String qAnswer)
//	{
//		questionAnswers.add(qAnswer);
//	}
//	
//	public ArrayList<String> getAllAnswers()
//	{
//		return questionAnswers;
//	}
//
//	public String getQuestionAnswers(int index) 
//	{
//		return questionAnswers.get(index);
//	}
//
//	public void setQuestionAnswers(ArrayList<String> questionAnswers) 
//	{
//		this.questionAnswers = questionAnswers;
//	}
	

}
