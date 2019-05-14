package admin.view.preparation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.TreeMap;

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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

public class MainEditorController
{
	@FXML
	SplitPane root;
	private TreeItem<String> examTree;
	@FXML
	private TreeView<String> examTreeView;
	@FXML
	private TabPane tabPane;

	private MainController mainController;
	private TreeMap<TreeItem<String>, Entry> map;

	public void initialize()
	{
		map = new TreeMap<>();
		examTree = new ComparableTreeItem( "Exams");
		map.put( examTree, new Entry( "TRASH"));
		examTreeView.setRoot( examTree);

		updateTreeView();
		examTreeView.setOnMouseClicked( new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent)
			{
				Entry e = getSelectedEntry();
				if ( mouseEvent.getClickCount() == 2 && e != null && examTreeView.getSelectionModel().getSelectedItem().isLeaf())
					openTab( e);
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
		Entry selected = getSelectedEntry();
		if ( selected == null)
			return;

		Exam e = (Exam) selected.findFirstAncestor( Exam.class);
		Alert confirmation = new Alert( AlertType.CONFIRMATION, "Are you sure you want to start " + e + "? This will make this exam and its public data available for examiness.\nWARNING: It will overwrite all existing examinee solutions and grades");
		Optional<ButtonType> result = confirmation.showAndWait();
		if ( result.isPresent() && result.get() == ButtonType.OK)
		{
			Model.getInstance().startSendingExam( e);

			// TODO open tabs! disable
			mainController.changeToMonitoring();
		}
	}

	@FXML
	public void delete()
	{
		Entry e = getSelectedEntry();
		TreeItem<String> selected = examTreeView.getSelectionModel().getSelectedItem();

		if ( Model.getInstance().getLastExam() != null)
		{
			if ( selected == examTree || e.findFirstAncestor( Exam.class) == Model.getInstance().getLastExam())
			{
				Alert error = new Alert( AlertType.ERROR, "You cannot edit the content of the last started exam.");
				error.showAndWait();
				return;
			}
		}
		if ( selected == null)
			return;

		if ( selected == examTree)
		{
			Alert confirmation = new Alert( AlertType.CONFIRMATION, "Are you sure you want to delete all the data? This action can not be undone.");
			Optional<ButtonType> result = confirmation.showAndWait();
			if ( result.isPresent() && result.get() == ButtonType.OK)
			{
				Model.getInstance().getEntries().getAll().clear();
				updateTreeView();
			}
		}
		else
		{
			Alert confirmation = new Alert( AlertType.CONFIRMATION, "Are you sure you want to delete " + e.getTitle() + "? This will delete all sub-items. This action can not be undone.");
			Optional<ButtonType> result = confirmation.showAndWait();
			if ( result.isPresent() && result.get() == ButtonType.OK)
			{
				Model.getInstance().deleteEntry( e);
				updateTreeView();
			}
		}
	}

	public Entry getSelectedEntry()
	{
		TreeItem<String> selected = examTreeView.getSelectionModel().getSelectedItem();
		if ( selected == null || selected == examTree)
			return null;

		return map.get( selected);
	}

	public void updateTreeView()
	{
		updateTreeView( examTree, Model.getInstance().getEntries());
	}

	private void updateTreeView( TreeItem<String> item, Container container)
	{
		ArrayList<TreeItem<String>> listOfItems = new ArrayList<>();

		container.getAll().forEach( e -> {
			TreeItem<String> nextItem = new ComparableTreeItem( e.getTitle());
			if ( e instanceof Question || e instanceof QuestionPart)
			{
				TreeItem<String> statement = new ComparableTreeItem( "Statement");
				nextItem.getChildren().add( statement);
				map.put( statement, e);
			}

			Optional<TreeItem<String>> optional = item.getChildren().stream().filter( ti -> map.get( ti) != null && map.get( ti).equals( e)).findAny();
			if ( optional.isPresent())
				nextItem = optional.get();
			else
			{
				item.getChildren().add( nextItem);
				map.put( nextItem, e);
			}

			listOfItems.add( nextItem);
			updateTreeView( nextItem, e);
		});

		item.getChildren().removeIf( i -> !listOfItems.contains( i) && !map.get( i).equals( map.get( item)));
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
				Model.getInstance().createExam( p.getKey(), p.getValue());
				updateTreeView();
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
			d.getDialogPane().setUserData( getSelectedEntry());
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
			questionDialog.getDialogPane().setUserData( getSelectedEntry());

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
			d.getDialogPane().setUserData( getSelectedEntry());

			result = d.showAndWait();
			result.ifPresent( p -> {
				QuestionPart qp = Model.getInstance().createQuestionPart( p.getKey(), p.getValue().getKey(), p.getValue().getValue());
				updateTreeView();
				openTab( qp);
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
			d.getDialogPane().setUserData( getSelectedEntry());

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
		if ( e instanceof Exam)
			return;
		
		NumberedEditor editor = new NumberedEditor( e.getContent());
		editor.addListenerToText( new ChangeListener<String>() {
			@Override
			public void changed( ObservableValue<? extends String> observable, String oldVal, String newVal)
			{
				if ( e.findFirstAncestor( Exam.class) == Model.getInstance().getLastExam())
				{
					Alert error = new Alert( AlertType.ERROR, "You cannot edit the content of the last started exam.");
					error.showAndWait();
					observable.removeListener( this);
					editor.setText( oldVal);
					observable.addListener( this);
				}
				else
					Model.getInstance().setContentOfEntry( e, newVal);
			}
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
			tabPane.getTabs().stream().filter( t -> t.getUserData().equals( e)).findAny().ifPresent( tabPane.getSelectionModel()::select);
		}

	}

	class ComparableTreeItem extends TreeItem<String> implements Comparable<TreeItem<String>>
	{
		public ComparableTreeItem( String s)
		{
			super( s);
		}

		@Override
		public int compareTo( TreeItem<String> o)
		{
			return ((Integer) hashCode()).compareTo( o.hashCode());
		}
	}
}
