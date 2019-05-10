package admin.view.preparation;

import java.io.IOException;
import java.util.Optional;

import org.controlsfx.control.BreadCrumbBar;

import admin.model.Model;
import admin.model.exam_entries.Container;
import admin.model.exam_entries.Entry;
import admin.model.exam_entries.Exam;
import admin.model.exam_entries.Instruction;
import admin.model.exam_entries.QuestionPart;
import admin.model.exam_entries.Template;
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

	public void initialize()
	{
		updateTreeView();
	}

	private void updateTreeView()
	{
		updateTreeView( examTree, Model.getInstance().getEntries());
	}

	private void updateTreeView( TreeItem<Entry> item, Container container)
	{
		container.getAll().forEach( e -> {
			TreeItem<Entry> nextItem = new TreeItem<>( e);

			Optional<TreeItem<Entry>> optional = item.getChildren().stream().filter( ti -> ti.getValue().equals( e)).findAny();
			if ( optional.isPresent())
				nextItem = optional.get();
			else
				item.getChildren().add( nextItem);

			updateTreeView( nextItem, e);
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
		try
		{
			d = FXMLLoader.load( getClass().getResource( "/admin/view/fxml/preparation/NewExamDialog.fxml"));
			result = d.showAndWait();
			result.ifPresent( p -> {
				Exam e = Model.getInstance().createExam( p.getKey(), p.getValue());
				updateTreeView();
				openTab( e);
			});
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addInstructions()
	{
		Dialog<Exam> d;
		Optional<Exam> result;
		try
		{
			d = FXMLLoader.load( getClass().getResource( "/admin/view/fxml/preparation/NewExamInstructionsDialog.fxml"));
			result = d.showAndWait();
			result.ifPresent( e -> {
				Instruction i = Model.getInstance().createInstruction( e);
				updateTreeView();
				openTab( i);
			});
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// public void addQuestion()
	// {
	// Dialog<Exam> d;
	// Optional<Exam> test;
	// try {
	// d =
	// FXMLLoader.load(getClass().getResource("/admin/view/fxml/preparation/NewQuestionDialog.fxml"));
	// test = d.showAndWait();
	//
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	// public void addPart()
	// {
	// Dialog<Exam> d;
	// Optional<Exam> test;
	// try {
	// d =
	// FXMLLoader.load(getClass().getResource("/admin/view/fxml/preparation/NewQuestionPartDialog.fxml"));
	// test = d.showAndWait();
	//
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	public void addTemplate()
	{
		Dialog<QuestionPart> d;
		Optional<QuestionPart> result;
		try
		{
			d = FXMLLoader.load( getClass().getResource( "/admin/view/fxml/preparation/NewQuestionTemplateDialog.fxml"));
			result = d.showAndWait();
			result.ifPresent( qp -> {
				Template t = Model.getInstance().createTemplate( qp);
				updateTreeView();
				openTab(t);
			});
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void openTab( Entry e)
	{
		// TODO
		System.out.println( "Contents of " + e + " is displayed");
	}
}
