package examinee.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;

import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class MainScreenController
{
	public static final String EXAM_NAME = "CS101 - MT1";

	@FXML
	private TreeView<String> treeView;
	@FXML
	private TreeItem<String> rootItem;
	@FXML
	private TreeItem<String> question1;
	@FXML
	private TreeItem<String> question2;
	@FXML
	private JDesktopPane jdp;
	@FXML
	private SwingNode swingNode;
	@FXML
	private SplitPane sp;
	private JInternalFrame jif1;
	private JInternalFrame jif2;
	public void initialize()
	{
		//Tree view
		question1 = new TreeItem<String>( "Question1");
		question2 = new TreeItem<String>( "Question2");
		rootItem = new TreeItem<String>( EXAM_NAME);
		rootItem.setExpanded( true);
		treeView.setRoot( rootItem);
		treeView.setShowRoot( true);
		addTreeItems();
		
		//Swing components
		SwingUtilities.invokeLater( new Runnable() 
		{
			@Override
			public void run()
			{
				jdp.setSize(200, 200);
				Platform.runLater( new Runnable() 
				{
					@Override
					public void run()
					{
						sp.setDividerPositions(0.2);
					}
				});
				System.out.println( jdp);
				jdp.setBackground( Color.LIGHT_GRAY);
				treeViewClick();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public void addTreeItems()
	{
		//To be combined with model
		question1.getChildren().add( new TreeItem<String>( "Part 1"));
		question2.getChildren().add( new TreeItem<String>( "Shuffle Method"));
		rootItem.getChildren().addAll( question1, question2);
	}
	
	public void treeViewClick()
	{
		//To be combined with model
		jif1 = new JInternalFrame( "Question1", true, true, true, true);
		jif2 = new JInternalFrame( "Question2", true, true, true, true);
		jdp.add( jif1);
		jdp.add( jif2);
		jif1.setVisible( true);
		jif1.setSize( new Dimension( 400, 300));
		jif1.setLocation( 400, 250);
		jif2.setVisible( true);
		jif2.setSize( new Dimension( 400, 300));
		jif2.setLocation( 600, 400);
	}
}
