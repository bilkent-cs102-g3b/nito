package examinee.view;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.controlsfx.control.CheckTreeView;

import common.NumberedEditor;
import examinee.model.ExamContainer;
import examinee.model.ExamEntry;
import examinee.model.Model;
import examinee.model.Question;
import examinee.model.QuestionPart;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingNode;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/*
 * @author Javid Baghirov
 * @version 12/05/2019
 */
public class MainScreenController
{
	@FXML
	private CheckTreeView<String> checkTreeView;
	private TreeItem<String> rootItem;
	@FXML
	private JDesktopPane desktopPane;
	@FXML
	private SwingNode swingNode;
	@FXML
	private SplitPane splitPane;
	@FXML
	private Button submit;
	@FXML
	private ProgressBar bar;
	@FXML
	private Label time;
	private int timeRemain;
	private int timeTotal;
	private ArrayList<ExamEntry> openEntries;
	private TreeMap<TreeItem<String>, ExamEntry> map;
	
	//Setting up the view
	public void initialize()
	{
		map = new TreeMap<TreeItem<String>, ExamEntry>();
		openEntries = new ArrayList<>();
		//Setting up the time
		timeRemain = Model.getInstance().getTimeRemain();
		timeTotal = Model.getInstance().getTimeTotal();
		setTime();
		
		//Setting the bar progress
		setProgress();
		
		//Initializing the tree
//		testTree();
		rootItem = new TreeItem<String>( Model.getInstance().getExamData().getTitle());
		checkTreeView.setRoot( rootItem);
		updateTreeView( rootItem, Model.getInstance().getExamData());

		//The listener for tree items
		checkTreeView.setOnMouseClicked( new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent)
			{
				TreeItem<String> intermediate = checkTreeView.getSelectionModel().getSelectedItem();
				if ( intermediate == null)
				{
					return;
				}
				if ( mouseEvent.getClickCount() == 2 && intermediate != rootItem && intermediate.isLeaf())
				{
					SwingUtilities.invokeLater(() -> {
						System.out.println( "Clicked on " + intermediate.getValue());
						openTab();
					});
				}
			}
		});
		
		//Swing components
		SwingUtilities.invokeLater( new Runnable() 
		{
			@Override
			public void run()
			{
				desktopPane.setBackground( Color.LIGHT_GRAY);
				desktopPane.setVisible( true);
				Platform.runLater( new Runnable() 
				{
					@Override
					public void run()
					{
						splitPane.setDividerPositions(0.2);
					}
				});
			}
		});
	}
	
	/*
	 * Sets the progress of the progress bar
	 */
	private void setProgress()
	{
		if ( timeTotal != 0)
		{
			bar.setProgress( timeRemain / timeTotal);
		}
		else
		{
			bar.setProgress( 0);
		}	
	}
	
	/*
	 * Sets the progress of the progress bar
	 */
	private void setTime()
	{
		time.setText( timeRemain + " / " + timeTotal);
	}
	
	/*
	 * Updates the tree hierarchy according to the data
	 * @param item the root item to create the tree on
	 * @param container the container to get the exam entries from
	 */
	@SuppressWarnings("unchecked")
	public void updateTreeView( TreeItem<String> item, ExamContainer container)
	{
		container.getAll().forEach( e -> {
			TreeItem<String> nextItem = new TreeItem<>( e.getTitle());
			if( e instanceof Question)
			{
				ComparableTreeItem cti = new ComparableTreeItem( "Statement");
				nextItem.getChildren().add( cti);
				map.put( cti, e);
			}
			else if ( e instanceof QuestionPart)
			{
				ComparableTreeItem cti1 = new ComparableTreeItem( "Statement");
				ComparableTreeItem cti2 = new ComparableTreeItem( "Solution");
				nextItem.getChildren().addAll( cti1, cti2);
				map.put( cti1, e);
				map.put( cti2, e);
			}
			item.getChildren().add( nextItem);
			updateTreeView( nextItem, e);
		});
	}
	
	@SuppressWarnings("unchecked")
	public void testTree()
	{
		CheckBoxTreeItem<String> root = new CheckBoxTreeItem<String>( "CS101 - MT1");
		checkTreeView.setRoot( root);
		CheckBoxTreeItem<String> question1 = new CheckBoxTreeItem<String>( "Question 1");
		CheckBoxTreeItem<String> question2 = new CheckBoxTreeItem<String>( "Question 2");
		root.setExpanded( true);
		question1.getChildren().addAll( new CheckBoxTreeItem<String>( "Part 1"), new CheckBoxTreeItem<String>( "Part 2"));
		question2.getChildren().addAll( new CheckBoxTreeItem<String>( "Part 1"), new CheckBoxTreeItem<String>( "Part 2"));
		root.getChildren().addAll( question1, question2);
//		checkTreeView.getCheckModel().getCheckedItems().addListener( new ListChangeListener<TreeItem<String>>() {
//			public void onChanged( ListChangeListener.Change<? extends TreeItem<String>> c) {
//				System.out.println( checkTreeView.getCheckModel().getCheckedItems());
//			}
//		});
	}
	
	/*
	 * Opens a new tab according to the data
	 * @param entry the exam entry to open the tab with
	 */
	public void openTab()
	{
		TreeItem<String> selected = checkTreeView.getSelectionModel().getSelectedItem();
		
		if ( selected == null)
		{
			return;
		}
		
		ExamEntry entry = map.get( selected);
		if ( entry == null)
		{
			return;
		}
		
		Platform.runLater( () -> {
			JFXPanel fxPanel = new JFXPanel();
			NumberedEditor editor = new NumberedEditor( entry.getContent());
			fxPanel.setScene( new Scene( editor));
			
			if ( !(entry instanceof QuestionPart))
			{
				editor.disableEditor();
			}
			else if ( selected.getValue().equals( "Statement"))
			{
				editor.disableEditor();
			}
			
			SwingUtilities.invokeLater(() -> {
				JInternalFrame jif = null;
				if ( entry instanceof Question)
				{
					jif = new JInternalFrame( selected.getValue(), true, true, true, true);
				}
				else if ( entry instanceof QuestionPart)
				{
					if ( selected.getValue().equals( "Statement"))
					{
						jif = new JInternalFrame( selected.getValue(), true, true, true, true);
					}
					else if ( selected.getValue().contentEquals( "Solution"))
					{
						jif = new JInternalFrame( selected.getValue(), true, true, true, true);
					}
				}
				if ( jif != null)
				{
					jif.add( fxPanel);
					jif.setSize( 600, 600);
					jif.setVisible( true);
					desktopPane.add( jif);
				}
				jif.addInternalFrameListener( new InternalFrameAdapter() {
					@Override
					public void internalFrameClosing( InternalFrameEvent e)
					{
						openEntries.remove( entry);
					}
				});
			});
			
			if (entry instanceof QuestionPart)
			{
				editor.addListenerToText( (o, oldVal, newVal) -> {
					((QuestionPart) entry).updateSolution( newVal);
				});
			}
		});
	}
	
	/*
	 * Submits all the work by sending to the administrator
	 */
	@FXML
	public void submit() throws IOException
	{
		System.out.println( "Submit Pressed");
		//Model.getInstance().examEnd();
		Platform.exit();
		Pane submit = FXMLLoader.load( getClass().getResource( "/examinee/view/fxml/Submit.fxml"));
		Scene scene = new Scene( submit);
		Stage stage = new Stage();
		stage.setScene( scene);
		stage.setTitle( "Nito - Submitted!");
		stage.show();
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
