package examinee.view;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import common.NumberedEditor;
import examinee.model.ExamContainer;
import examinee.model.ExamEntry;
import examinee.model.Model;
import examinee.model.QuestionPart;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingNode;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

public class MainScreenController
{
	@FXML
	private TreeView<ExamEntry> treeView;
	private TreeItem<ExamEntry> rootItem;
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
	
	public void initialize()
	{
		openEntries = new ArrayList<>();
		//Setting up the time
		timeRemain = Model.getInstance().getTimeRemain();
		timeTotal = Model.getInstance().getTimeTotal();
		setTime();
		
		//Setting the bar progress
		setProgress();
		rootItem = new TreeItem<ExamEntry>( Model.getInstance().getExamData());
		treeView.setRoot( rootItem);
		updateTreeView( rootItem, Model.getInstance().getExamData());

		treeView.setOnMouseClicked( new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent)
			{
				TreeItem<ExamEntry> intermediate = treeView.getSelectionModel().getSelectedItem();
				if ( intermediate == null)
				{
					return;
				}
				ExamEntry e = intermediate.getValue();
				if ( mouseEvent.getClickCount() == 2 && intermediate != rootItem && intermediate.isLeaf())
				{
					SwingUtilities.invokeLater(() -> {
						System.out.println( "Clicked on " + intermediate.getValue());
						openTab( e);
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
//				testFrames();
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

	public void updateTreeView( TreeItem<ExamEntry> item, ExamContainer container)
	{
		container.getAll().forEach( e -> {
			TreeItem<ExamEntry> nextItem = new TreeItem<>( e);
			item.getChildren().add( nextItem);
			updateTreeView( nextItem, e);
		});
	}
	
	public void testFrames()
	{
		Platform.runLater( () -> {
			JFXPanel fxPanel = new JFXPanel();
			fxPanel.setScene( new Scene( new NumberedEditor()));
			SwingUtilities.invokeLater(() -> {
				JInternalFrame jif = new JInternalFrame( "Question 1", true, true, true, true);
				jif.add( fxPanel);
				jif.setSize( 600, 600);
				jif.setVisible( true);
				desktopPane.add( jif);
			});
		});
	}
	
	public void openTab( ExamEntry entry)
	{
		if ( openEntries.contains( entry))
		{
			return;
		}
		openEntries.add( entry);
		Platform.runLater( () -> {
			JFXPanel fxPanel = new JFXPanel();
			NumberedEditor editor = new NumberedEditor( entry.getContent());
			fxPanel.setScene( new Scene( editor));
			
			if ( !(entry instanceof QuestionPart))
			{
				editor.disableEditor();
			}
			
			SwingUtilities.invokeLater(() -> {
				JInternalFrame jif = new JInternalFrame( entry.getTitle(), true, true, true, true);
				jif.add( fxPanel);
				jif.setSize( 600, 600);
				jif.setVisible( true);
				
				jif.addInternalFrameListener( new InternalFrameAdapter() {
					@Override
					public void internalFrameClosing( InternalFrameEvent e)
					{
						openEntries.remove( entry);
					}
				});
				
				desktopPane.add( jif);
			});
			
			if (entry instanceof QuestionPart)
			{
				editor.addListenerToText( (o, oldVal, newVal) -> {
					((QuestionPart) entry).updateSolution( newVal);
				});
			}
		});
	}
	
	@FXML
	public void submit()
	{
		System.out.println( "Submit Pressed");
		Model.getInstance().submitAll();
		Platform.exit();
	}
}
