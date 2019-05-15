package examinee.view;

import java.awt.Color;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.DesktopManager;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.controlsfx.control.CheckTreeView;

import common.NumberedEditor;
import examinee.model.ExamContainer;
import examinee.model.ExamEntry;
import examinee.model.Instruction;
import examinee.model.Model;
import examinee.model.Question;
import examinee.model.QuestionPart;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingNode;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

/*
 * @author Javid Baghirov
 * @version 14/05/2019
 */
public class MainScreenController
{
	@FXML
	private CheckTreeView<String> checkTreeView;
	private CheckBoxTreeItem<String> rootItem;
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
	private ArrayList<ExamEntry> openSolutionEntries;
	private ArrayList<ExamEntry> openStatementEntries;
	private TreeMap<TreeItem<String>, ExamEntry> map;

	/**
	 * Sets up the view
	 */
	public void initialize()
	{
		map = new TreeMap<TreeItem<String>, ExamEntry>();
		openStatementEntries = new ArrayList<>();
		openSolutionEntries = new ArrayList<>();
		Model.getInstance().getStatus().addListener( ( o, oldVal, newVal) -> {
			if ( newVal.intValue() == Model.STATUS_START)
			{
				//Disabling the submit function and the time before the exam starts
				Model.getInstance().getTimeRemain().addListener( ( ob, oldValue, newValue) -> {
					Platform.runLater( () -> {
						setTime( newValue.intValue(), Model.getInstance().getTimeTotal().intValue());
					});
				});
				submit.setDisable( false);
			}
			else if ( newVal.intValue() == Model.STATUS_FINISHED)
			{
				//forcing to submit if the exam is over
				Platform.runLater( () -> {
					endProgram();
				});
			}
		});

		// Initializing the tree
		if ( Model.getInstance().examReadyProperty().get() == true)
			buildTree();
		Model.getInstance().examReadyProperty().addListener((o, oldVal, newVal) -> {
			buildTree();
		});

		// The listener for tree items
		checkTreeView.setOnMouseClicked( new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent)
			{
				CheckBoxTreeItem<String> intermediate = (CheckBoxTreeItem<String>) checkTreeView.getSelectionModel().getSelectedItem();
				if ( intermediate == null)
				{
					return;
				}
				if ( mouseEvent.getClickCount() == 2 && intermediate != rootItem && intermediate.isLeaf())
				{
					SwingUtilities.invokeLater( () -> {
						openTab();
					});
				}
			}
		});

		// Swing components
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run()
			{
				desktopPane.setBackground( Color.LIGHT_GRAY);
				desktopPane.setVisible( true);
				Platform.runLater( new Runnable() {
					@Override
					public void run()
					{
						splitPane.setDividerPositions( 0.2);
					}
				});
			}
		});
	}
	
	/**
	 * Sets up the tree view by using the data from administrator
	 */
	private void buildTree()
	{
		Platform.runLater( () -> {
			rootItem = new CheckBoxTreeItem<String>( Model.getInstance().getExamData().getTitle());
			checkTreeView.setRoot( rootItem);
			updateTreeView( rootItem, Model.getInstance().getExamData());
		});
	}
	
	/**
	 * Sets the time components
	 * @param timeRemain the remaining time
	 * @param timeTotal the total time
	 */
	private void setTime( int timeRemain, int timeTotal)
	{
		int remainTimeInMinutes = timeRemain / 60;
		int remainTimeInSeconds = timeRemain % 60;

		int totalTimeInMinutes = timeTotal / 60;
		int totalTimeInSeconds = timeTotal % 60;

		time.setText( remainTimeInMinutes + ":" + remainTimeInSeconds + " / " + totalTimeInMinutes + ":" + totalTimeInSeconds);
		if ( timeTotal != 0)
		{
			bar.setProgress( ( (double) timeTotal - timeRemain) / timeTotal);
		}
		else
		{
			bar.setProgress( 0);
		}
	}

	/**
	 * Updates the tree hierarchy according to the data
	 * @param item the root item to create the tree on
	 * @param container the container to get the exam entries from
	 */
	@SuppressWarnings("unchecked")
	public void updateTreeView( TreeItem<String> item, ExamContainer container)
	{
		container.getAll().forEach( e -> {
			CheckBoxTreeItem<String> nextItem = new ComparableTreeItem( e.getTitle());
			if ( e instanceof Question)
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
			else if ( e instanceof Instruction)
			{
				map.put( nextItem, e);
			}
			item.getChildren().add( nextItem);
			updateTreeView( nextItem, e);
		});
	}

	/**
	 * Opens a new tab according to the data
	 */
	public void openTab()
	{
		CheckBoxTreeItem<String> selected = (CheckBoxTreeItem<String>) checkTreeView.getSelectionModel().getSelectedItem();
		if ( selected == null)
		{
			return;
		}

		ExamEntry entry = map.get( selected);
		if ( entry == null)
		{
			return;
		}

		if ( !(entry instanceof Instruction) && Model.getInstance().getStatus().intValue() == Model.STATUS_CONNECTED)
			return;
		if ( selected.getValue().equals( "Statement") || entry instanceof Instruction)
		{
			if ( openStatementEntries.contains( entry))
			{
				return;
			}
		}
		else
		{
			if ( openSolutionEntries.contains( entry))
			{
				return;
			}
		}

		Platform.runLater( () -> {
			JFXPanel fxPanel = new JFXPanel();
			NumberedEditor editor;
			
			if ( entry instanceof QuestionPart && selected.getValue().equals( "Statement"))
				editor = new NumberedEditor( ((QuestionPart) entry).getStatement());
			else
				editor = new NumberedEditor( ((QuestionPart) entry).getContent());
			
			fxPanel.setScene( new Scene( editor));
			
			if ( Model.getInstance().getStatus().intValue() == Model.STATUS_FINISHED)
			{
				editor.setEditorNonEditable();
			}
			if ( selected.getValue().equals( "Statement") || entry instanceof Instruction)
			{
				editor.setEditorNonEditable();
				openStatementEntries.add( entry);
			}
			else
			{
				openSolutionEntries.add( entry);
			}

			SwingUtilities.invokeLater( () -> {
				JInternalFrame jif = new JInternalFrame( entry.getTitle() + " : " + entry.getClass().getName(), true, true, true, true);
				jif.add( fxPanel);
				jif.setBounds( (desktopPane.getAllFrames().length * 10) % desktopPane.getWidth(), (desktopPane.getAllFrames().length * 10) % desktopPane.getHeight(), desktopPane.getWidth() / 3, desktopPane.getHeight() / 3); 
				jif.setVisible( true);
				
				desktopPane.add( jif);
				jif.requestFocusInWindow();
				jif.moveToFront();

				jif.addInternalFrameListener( new InternalFrameAdapter() {
					@Override
					public void internalFrameClosing( InternalFrameEvent e)
					{
						if ( selected.getValue().equals( "Statement") || entry instanceof Instruction)
						{
							openStatementEntries.remove( entry);
						}
						else
						{
							openSolutionEntries.remove( entry);
						}
					}
				});
			});
			editor.addListenerToText( ( o, oldVal, newVal) -> {
				((QuestionPart) entry).updateSolution( newVal);
			});
		});
	}
	
	@FXML
	public void submit()
	{
		Model.getInstance().examEnd();
	}
	
	/*
	 * Submits all the work by sending to the administrator
	 */
	private void endProgram()
	{
		openSolutionEntries.clear();
		openStatementEntries.clear();
		submit.setDisable( true);
		
		JInternalFrame frames[] = desktopPane.getAllFrames();
		DesktopManager dm = desktopPane.getDesktopManager();
		for (int i = 0 ; i < frames.length ; i ++)
		{
			dm.closeFrame(frames[i]);
			try
			{
				frames[i].setClosed(false);
			} catch (PropertyVetoException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Platform.runLater( () -> {
			splitPane.setDividerPositions( 0.2);
		});
		
		
		Alert alert = new Alert( AlertType.INFORMATION, "The exam is finished. You can now see your solutions. Thank you for your participation");
		alert.showAndWait();
	}

	class ComparableTreeItem extends CheckBoxTreeItem<String> implements Comparable<TreeItem<String>>
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
