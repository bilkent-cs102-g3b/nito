package admin.view.preparation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.controlsfx.control.BreadCrumbBar;

import admin.model.Model;
import admin.model.exam_entries.Container;
import admin.model.exam_entries.Entry;
import admin.model.exam_entries.Exam;
import admin.model.exam_entries.Instruction;
import admin.model.exam_entries.Question;
import admin.model.exam_entries.QuestionPart;
import admin.model.exam_entries.Template;
import common.NumberedEditor;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

public class MainController {
	@FXML
	private BreadCrumbBar<String> breadCrumb;
	@FXML
	private TreeItem<Entry> examTree;
	@FXML
	private TreeView<Entry> examTreeView;
	@FXML
	private TabPane tabPane;

	public void initialize() {

		updateTreeView();
		examTreeView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
		    	TreeItem<Entry> intermediate = examTreeView.getSelectionModel().getSelectedItem();
		        if(mouseEvent.getClickCount() == 2 && intermediate.isLeaf())
		        	openTab( intermediate.getValue());
			}
		});
		
		MenuItem item = new MenuItem( "Delete");
		item.setOnAction( e -> {
			TreeItem<Entry> selected = examTreeView.getSelectionModel().getSelectedItem();
			if (selected != examTree)
				Model.getInstance().deleteEntry(selected.getValue());
			else
				Model.getInstance().getEntries().getAll().clear();
			updateTreeView();
		});
		ContextMenu menu = new ContextMenu(item);
		
		examTreeView.setContextMenu( menu);
	}

	private void updateTreeView() {
		updateTreeView(examTree, Model.getInstance().getEntries());
	}

	private void updateTreeView(TreeItem<Entry> item, Container container) {
		ArrayList<TreeItem<Entry>> listOfItems = new ArrayList<>();
		
		container.getAll().forEach(e -> {
			TreeItem<Entry> nextItem = new TreeItem<>(e);

			Optional<TreeItem<Entry>> optional = item.getChildren().stream().filter(ti -> ti.getValue().equals(e)).findAny();
			if (optional.isPresent())
				nextItem = optional.get();
			else
				item.getChildren().add(nextItem);
			
			listOfItems.add(nextItem);
			updateTreeView(nextItem, e);
		});
		
		item.getChildren().retainAll( listOfItems);
	}

	/**
	 * Adds exam to the view
	 * 
	 * @param name - The name of the exam
	 */
	public void addExam() {
		Dialog<Pair<String, Integer>> d;
		Optional<Pair<String, Integer>> result;
		try {
			d = FXMLLoader.load(getClass().getResource("/admin/view/fxml/preparation/NewExamDialog.fxml"));
			result = d.showAndWait();
			result.ifPresent(p -> {
				Exam e = Model.getInstance().createExam(p.getKey(), p.getValue());
				updateTreeView();
				openTab(e);
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addInstructions() {
		Dialog<Exam> d;
		Optional<Exam> result;
		try {
			d = FXMLLoader.load(getClass().getResource("/admin/view/fxml/preparation/NewExamInstructionsDialog.fxml"));
			result = d.showAndWait();
			result.ifPresent(e -> {
				Instruction i = Model.getInstance().createInstruction(e);
				updateTreeView();
				openTab(i);
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addQuestion() {
		Dialog<Pair<Entry, Pair<String, Integer>>> questionDialog;
		Optional<Pair<Entry, Pair<String, Integer>>> result;
		try {
			questionDialog = FXMLLoader.load(getClass().getResource("/admin/view/fxml/preparation/NewQuestionDialog.fxml"));
			result = questionDialog.showAndWait();
			result.ifPresent(e -> {
				Question q = Model.getInstance().createQuestion(e.getKey(), e.getValue().getKey());
				for(int i = 1; i <= e.getValue().getValue(); i++)
				{
					try {
						Dialog<Pair<String, Integer>> partDialog = FXMLLoader.load(getClass().getResource("/admin/view/fxml/preparation/NewQuestionPartDialog.fxml"));
						partDialog.getDialogPane().setUserData(new Pair<Integer, Boolean>(i, i==e.getValue().getValue()));
						Optional<Pair<String, Integer>> partResult = partDialog.showAndWait();
						if(partResult.isPresent())
						{
							QuestionPart qp = Model.getInstance().createQuestionPart(q, partResult.get().getKey(), partResult.get().getValue());
						}
						else
						{
							Model.getInstance().deleteEntry(q);
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				updateTreeView();
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addTemplate() {
		Dialog<QuestionPart> d;
		Optional<QuestionPart> result;
		try {
			d = FXMLLoader.load(getClass().getResource("/admin/view/fxml/preparation/NewQuestionTemplateDialog.fxml"));
			result = d.showAndWait();
			result.ifPresent(qp -> {
				Template t = Model.getInstance().createTemplate(qp);
				updateTreeView();
				openTab(t);
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void openTab(Entry e) {
		NumberedEditor editor = new NumberedEditor( e.getContent());
		editor.addListenerToText((o, oldVal, newVal) -> {
			Model.getInstance().setContentOfEntry(e, newVal);
		});
		Tab tabData = new Tab( e.toString(), editor);
		tabData.setUserData(e);
		if (!tabPane.getTabs().stream().anyMatch(t -> t.getUserData().equals(e)))
		{
			tabPane.getTabs().add(tabData);
			tabPane.getSelectionModel().select(tabData);
		}
		else
		{
			tabPane.getTabs().stream().filter(t -> t.getUserData().equals(e)).findAny().ifPresent(tabPane.getSelectionModel()::select);
		}
		
	}
}
