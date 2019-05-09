package admin.view.grading;

// import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class ExamineeTableItem
{
	private SimpleStringProperty name;
	private SimpleStringProperty idNumber;
	private SimpleStringProperty grade;
	private CheckBox select;

	public ExamineeTableItem()
	{
		name = new SimpleStringProperty( "");
		group = new SimpleStringProperty( "");
		idNumber = new SimpleStringProperty( "");
		grade = new SimpleStringProperty( "");
		select = new CheckBox();
		notes = new Button( "BUTTON");
		notes.setMinWidth( 100);
	}

	public String getIdNumber()
	{
		return idNumber.get();
	}

	public void setIdNumber( String idNumber2)
	{
		this.idNumber.set( idNumber2);
	}

	public String getGrade()
	{
		return grade.get();
	}

	public void setGrade( String grade)
	{
		this.grade.set( grade);
	}

	public String getName()
	{
		return name.get();
	}

	public void setName( String name)
	{
		this.name.set( name);
		;
	}

	public String getGroup()
	{
		return group.get();
	}

	public void setGroup( String group)
	{
		this.group.set( group);
		;
	}

	public CheckBox getSelect()
	{
		return select;
	}

	public void setSelected( CheckBox select)
	{
		this.select = select;

	}

	public void setSelected( Button button)
	{
		this.notes = button;
		notes.setText( "notes");
	}
	// public void addQuestionAnswer(String qAnswer)
	// {
	// questionAnswers.add(qAnswer);
	// }
	//
	// public ArrayList<String> getAllAnswers()
	// {
	// return questionAnswers;
	// }
	//
	// public String getQuestionAnswers(int index)
	// {
	// return questionAnswers.get(index);
	// }
	//
	// public void setQuestionAnswers(ArrayList<String> questionAnswers)
	// {
	// this.questionAnswers = questionAnswers;
	// }

}
