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

	// Setting up the view
	public void initialize()
	{
		map = new TreeMap<TreeItem<String>, ExamEntry>();
		openStatementEntries = new ArrayList<>();
		openSolutionEntries = new ArrayList<>();
		Model.getInstance().getTimeRemain().addListener((o, oldVal, newVal) ->
		{
			Platform.runLater(() ->
			{
				setTime(newVal.intValue(), Model.getInstance().getTimeTotal().intValue());
			});
		});

		// Initializing the tree
		rootItem = new CheckBoxTreeItem<String>(Model.getInstance().getExamData().getTitle());
		checkTreeView.setRoot(rootItem);
		updateTreeView(rootItem, Model.getInstance().getExamData());

		// The listener for tree items
		checkTreeView.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent mouseEvent)
			{
				CheckBoxTreeItem<String> intermediate = (CheckBoxTreeItem<String>) checkTreeView.getSelectionModel()
						.getSelectedItem();
				if (intermediate == null)
				{
					return;
				}
				if (mouseEvent.getClickCount() == 2 && intermediate != rootItem && intermediate.isLeaf())
				{
					SwingUtilities.invokeLater(() ->
					{
						System.out.println("Clicked on " + intermediate.getValue());
						openTab();
					});
				}
			}
		});

		// Swing components
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				desktopPane.setBackground(Color.LIGHT_GRAY);
				desktopPane.setVisible(true);
				Platform.runLater(new Runnable()
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

	/**
	 * Sets the time components
	 */
	private void setTime(int timeRemain, int timeTotal)
	{
		time.setText(timeRemain + " / " + timeTotal);
		if (timeTotal != 0)
		{
			bar.setProgress(timeRemain / timeTotal);
		} else
		{
			bar.setProgress(0);
		}
	}

	/*
	 * Updates the tree hierarchy according to the data
	 * 
	 * @param item the root item to create the tree on
	 * 
	 * @param container the container to get the exam entries from
	 */
	@SuppressWarnings("unchecked")
	public void updateTreeView(TreeItem<String> item, ExamContainer container)
	{
		container.getAll().forEach(e ->
		{
			CheckBoxTreeItem<String> nextItem = new CheckBoxTreeItem<>(e.getTitle());
			if (e instanceof Question)
			{
				ComparableTreeItem cti = new ComparableTreeItem("Statement");
				nextItem.getChildren().add(cti);
				map.put(cti, e);
			} else if (e instanceof QuestionPart)
			{
				ComparableTreeItem cti1 = new ComparableTreeItem("Statement");
				ComparableTreeItem cti2 = new ComparableTreeItem("Solution");
				nextItem.getChildren().addAll(cti1, cti2);
				map.put(cti1, e);
				map.put(cti2, e);
			}
			item.getChildren().add(nextItem);
			updateTreeView(nextItem, e);
		});
	}

	/*
	 * Opens a new tab according to the data
	 * 
	 * @param entry the exam entry to open the tab with
	 */
	public void openTab()
	{
		CheckBoxTreeItem<String> selected = (CheckBoxTreeItem<String>) checkTreeView.getSelectionModel().getSelectedItem();
		if (selected == null)
		{
			return;
		}

		ExamEntry entry = map.get(selected);
		if (entry == null)
		{
			return;
		}
		
		if (selected.getValue().equals("Statement"))
		{
			if (openStatementEntries.contains( entry))
			{
				return;
			}
		}
		else
		{
			if (openSolutionEntries.contains( entry))
			{
				return;
			}
		}

		
		Platform.runLater(() -> {
			JFXPanel fxPanel = new JFXPanel();
			NumberedEditor editor = new NumberedEditor(entry.getContent());
			fxPanel.setScene(new Scene(editor));

			if (selected.getValue().equals("Statement"))
			{
				editor.disableEditor();
				openStatementEntries.add( entry);
			}
			else
			{
				openSolutionEntries.add( entry);
			}

			SwingUtilities.invokeLater(() -> {
				JInternalFrame jif = new JInternalFrame( entry.getTitle(), true, true, true, true);
				jif.add(fxPanel);
				jif.setSize(600, 600);
				jif.setVisible(true);
				desktopPane.add(jif);
				
				jif.addInternalFrameListener(new InternalFrameAdapter()
				{
					@Override
					public void internalFrameClosing(InternalFrameEvent e)
					{
						if (selected.getValue().equals("Statement"))
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
			editor.addListenerToText((o, oldVal, newVal) -> {
				((QuestionPart) entry).updateSolution(newVal);
			});
		});
	}

	/*
	 * Submits all the work by sending to the administrator
	 */
	@FXML
	public void submit()
	{
		System.out.println("Submit Pressed");
		Model.getInstance().examEnd();
		Stage stage = (Stage) splitPane.getScene().getWindow();
		
		try
		{
			Pane submit;
			submit = FXMLLoader.load( getClass().getResource( "/examinee/view/fxml/Submit.fxml"));
			Scene scene = new Scene( submit);
			stage.setScene( scene);
			stage.setFullScreen( false);
			stage.setMaxHeight( 400);
			stage.setMaxWidth( 500);
			stage.setTitle( "Nito - Submitted");
			stage.show();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class ComparableTreeItem extends CheckBoxTreeItem<String> implements Comparable<TreeItem<String>>
	{
		public ComparableTreeItem(String s)
		{
			super(s);
		}

		@Override
		public int compareTo(TreeItem<String> o)
		{
			return ((Integer) hashCode()).compareTo(o.hashCode());
		}
	}
}
