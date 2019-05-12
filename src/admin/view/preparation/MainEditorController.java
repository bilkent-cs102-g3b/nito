package admin.view.preparation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import admin.model.Model;
import admin.model.exam_entries.Container;
import admin.model.exam_entries.Entry;
import admin.model.exam_entries.Exam;
import admin.model.exam_entries.Instruction;
import admin.model.exam_entries.Question;
import admin.model.exam_entries.QuestionPart;
import admin.model.exam_entries.Template;
import admin.view.MainController;
import common.NumberedEditor;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

public class MainEditorController
{
	@FXML
	SplitPane root;
	@FXML
	private TreeItem<Entry> examTree;
	@FXML
	private TreeView<Entry> examTreeView;
	@FXML
	private TabPane tabPane;

	private MainController mainController;
	private ArrayList<TreeItem<Entry>> disabledItems;

	public void initialize()
	{
//		examTreeView.setCellFactory( ( TreeView<Entry> item) -> {
//			final TreeCell<Entry> cell = new TreeCell<Entry>();
//
//			cell.itemProperty().addListener( ( o, oldVal, newVal) -> {
//				if ( newVal != null)
//					cell.setText( newVal.getTitle());
//
//				if ( newVal == Model.getInstance().getLastExam())
//					cell.setDisable( true);
//			});
//
//			return cell;
//		});
		examTree.setValue( new Entry( "Exams"));
		examTreeView.setRoot( examTree);

		updateTreeView();
		examTreeView.setOnMouseClicked( new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent)
			{
				TreeItem<Entry> intermediate = getSelectedItem();
				if ( mouseEvent.getClickCount() == 2 && intermediate != null && intermediate.isLeaf() && intermediate != examTree)
					openTab( intermediate.getValue());
			}
		});

		try
		{
			FXMLLoader loader = new FXMLLoader( getClass().getResource( "/admin/view/fxml/preparation/WelcomeTab.fxml"));
			Tab welcomeTab = loader.load();
			welcomeTab.setUserData( new Entry( "TRASH"));
			((WelcomeTabController) loader.getController()).setMainController( this);
			tabPane.getTabs().add( welcomeTab);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setMainController( MainController controller)
	{
		mainController = controller;
	}

	@FXML
	public void startExam()
	{
		TreeItem<Entry> selected = getSelectedItem();
		if ( selected == null || selected == examTree)
		{
			return;
		}

		Exam e = (Exam) selected.getValue().findFirstAncestor( Exam.class);
		Alert confirmation = new Alert( AlertType.CONFIRMATION, "Are you sure you want to start " + e + "? This will make this exam and its public data available for examiness.");
		Optional<ButtonType> result = confirmation.showAndWait();
		if ( result.isPresent() && result.get() == ButtonType.OK)
		{
			Model.getInstance().startExam( e);
			mainController.changeToMonitoring();
		}
	}

	@FXML
	public void delete()
	{
		TreeItem<Entry> selected = getSelectedItem();
		if ( selected == null)
			return;

		Alert confirmation = new Alert( AlertType.CONFIRMATION, "Are you sure you want to delete " + selected.getValue() + "? This will delete all sub-items. This action can not be undone.");
		Optional<ButtonType> result = confirmation.showAndWait();
		if ( result.isPresent() && result.get() == ButtonType.OK)
		{
			if ( selected != examTree)
				Model.getInstance().deleteEntry( selected.getValue());
			else
				Model.getInstance().getEntries().getAll().clear();
			updateTreeView();
		}
	}

	public TreeItem<Entry> getSelectedItem()
	{
		return examTreeView.getSelectionModel().getSelectedItem();
	}

	public void updateTreeView()
	{
		updateTreeView( examTree, Model.getInstance().getEntries());
	}

	private void updateTreeView( TreeItem<Entry> item, Container container)
	{
		ArrayList<TreeItem<Entry>> listOfItems = new ArrayList<>();

		container.getAll().forEach( e -> {
			TreeItem<Entry> nextItem = new TreeItem<>( e);

			Optional<TreeItem<Entry>> optional = item.getChildren().stream().filter( ti -> ti.getValue().equals( e)).findAny();
			if ( optional.isPresent())
				nextItem = optional.get();
			else
				item.getChildren().add( nextItem);

			if ( Model.getInstance().getLastExam() == e)
			{
				// examTreeView.cell
			}
			listOfItems.add( nextItem);
			updateTreeView( nextItem, e);
		});

		item.getChildren().retainAll( listOfItems);
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
			d.initOwner( root.getScene().getWindow());
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
			d.initOwner( root.getScene().getWindow());

			TreeItem<Entry> selection = getSelectedItem();
			if ( selection != null)
			{
				d.getDialogPane().setUserData( selection.getValue());
			}

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

	public void addQuestion()
	{
		Dialog<Pair<Entry, Pair<String, Integer>>> questionDialog;
		Optional<Pair<Entry, Pair<String, Integer>>> result;
		try
		{
			questionDialog = FXMLLoader.load( getClass().getResource( "/admin/view/fxml/preparation/NewQuestionDialog.fxml"));
			questionDialog.initOwner( root.getScene().getWindow());

			TreeItem<Entry> selection = getSelectedItem();
			if ( selection != null)
			{
				questionDialog.getDialogPane().setUserData( selection.getValue());
			}

			result = questionDialog.showAndWait();
			result.ifPresent( e -> {
				Question q = Model.getInstance().createQuestion( e.getKey(), e.getValue().getKey());
				for ( int i = 1; i <= e.getValue().getValue(); i++)
				{
					try
					{
						Dialog<Pair<String, Integer>> partDialog = FXMLLoader.load( getClass().getResource( "/admin/view/fxml/preparation/NewQuestionPartDialog.fxml"));
						partDialog.getDialogPane().setUserData( new Pair<Integer, Boolean>( i, i == e.getValue().getValue()));
						partDialog.initOwner( root.getScene().getWindow());
						Optional<Pair<String, Integer>> partResult = partDialog.showAndWait();
						if ( partResult.isPresent())
						{
							Model.getInstance().createQuestionPart( q, partResult.get().getKey(), partResult.get().getValue());
						}
						else
						{
							Model.getInstance().deleteEntry( q);
						}
					}
					catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				updateTreeView();
			});
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addPart()
	{
		Dialog<Pair<Entry, Pair<String, Integer>>> d;
		Optional<Pair<Entry, Pair<String, Integer>>> result;
		try
		{
			d = FXMLLoader.load( getClass().getResource( "/admin/view/fxml/preparation/NewQuestionPartOnlyDialog.fxml"));
			d.initOwner( root.getScene().getWindow());

			TreeItem<Entry> selection = getSelectedItem();
			if ( selection != null)
			{
				d.getDialogPane().setUserData( selection.getValue());
			}

			result = d.showAndWait();
			result.ifPresent( p -> {
				Model.getInstance().createQuestionPart( p.getKey(), p.getValue().getKey(), p.getValue().getValue());
				updateTreeView();
			});
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addTemplate()
	{
		Dialog<QuestionPart> d;
		Optional<QuestionPart> result;
		try
		{
			d = FXMLLoader.load( getClass().getResource( "/admin/view/fxml/preparation/NewQuestionTemplateDialog.fxml"));
			d.initOwner( root.getScene().getWindow());

			TreeItem<Entry> selection = getSelectedItem();
			if ( selection != null)
			{
				d.getDialogPane().setUserData( selection.getValue());
			}

			result = d.showAndWait();
			result.ifPresent( qp -> {
				Template t = Model.getInstance().createTemplate( qp);
				updateTreeView();
				openTab( t);
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
		NumberedEditor editor = new NumberedEditor( e.getContent());
		editor.addListenerToText( ( o, oldVal, newVal) -> {
			Model.getInstance().setContentOfEntry( e, newVal);
		});

		Tab tabData = new Tab( e.toString(), editor);
		tabData.setUserData( e);

		if ( !tabPane.getTabs().stream().anyMatch( t -> e.equals( t.getUserData())))
		{
			tabPane.getTabs().add( tabData);
			tabPane.getSelectionModel().select( tabData);
		}
		else
		{
			// TODO NullPointerException here! Fix it
			tabPane.getTabs().stream().filter( t -> t.getUserData().equals( e)).findAny().ifPresent( tabPane.getSelectionModel()::select);
		}

	}
}
