package admin.view.preparation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.controlsfx.control.BreadCrumbBar;

import admin.model.Model;
import admin.model.exam_entries.Entry;
import admin.model.exam_entries.Exam;
import admin.model.exam_entries.Question;
import admin.model.exam_entries.QuestionPart;
import common.NumberedEditor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.TreeItem;
import javafx.util.Pair;

public class MainController
{
	@FXML
	private BreadCrumbBar<String> breadCrumb;
	@FXML
	private NumberedEditor editor;
	@FXML
	private TreeItem<Entry> examTree;

	
	private TreeItem<Entry> intermediate;

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
		//breadCrumb.setSelectedCrumb( BreadCrumbBar.buildTreeModel( "Some", "crumb", "here", "!"));
		// breadCrumb.autosize();

		fillTreeView(examTree);
	}
	
	private void fillTreeView(TreeItem<Entry> item) 
	{
		if(item == examTree)
		{
			Model.getInstance().getEntries().getAll().forEach(d -> item.getChildren().add(new TreeItem<Entry>(d)));
		}
		item.getChildren().forEach(e -> 
		{
			e.getValue().getAll().forEach(d -> 
			{
				e.getChildren().add(new TreeItem<Entry>(d));
			});
			fillTreeView(e);
		});
	}

	/**
	 * Adds exam to the view
	 * @param name - The name of the exam
	 */
	public void addExam()
	{
		Dialog<Pair<String, Integer>> d;
		Optional<Pair<String, Integer>> result;
		try {
			d = FXMLLoader.load(getClass().getResource("/admin/view/fxml/preparation/NewExamDialog.fxml"));
			result = d.showAndWait();
			if(result.isPresent())
			{
				Model.getInstance().createExam(result.get().getKey(), result.get().getValue());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addInstructions()
	{
		Dialog<Exam> d;
		Optional<Exam> result;
		try {
			d = FXMLLoader.load(getClass().getResource("/admin/view/fxml/preparation/NewExamInstructionsDialog.fxml"));
			result = d.showAndWait();
			if(result.isPresent())
			{
				Model.getInstance().createInstruction(result.get());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public void addQuestion()
//	{
//		Dialog<Exam> d;
//		Optional<Exam> test;
//		try {
//			d = FXMLLoader.load(getClass().getResource("/admin/view/fxml/preparation/NewQuestionDialog.fxml"));
//			test = d.showAndWait();
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
//	public void addPart()
//	{
//		Dialog<Exam> d;
//		Optional<Exam> test;
//		try {
//			d = FXMLLoader.load(getClass().getResource("/admin/view/fxml/preparation/NewQuestionPartDialog.fxml"));
//			test = d.showAndWait();
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public void addTemplate()
	{
		Dialog<QuestionPart> d;
		Optional<QuestionPart> result;
		try {
			d = FXMLLoader.load(getClass().getResource("/admin/view/fxml/preparation/NewQuestionTemplateDialog.fxml"));
			result = d.showAndWait();
			if(result.isPresent())
			{
				Model.getInstance().createTemplate(result.get());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
