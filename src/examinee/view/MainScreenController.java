package examinee.view;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import javafx.fxml.FXML;
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
	private JDesktopPane jdp;
	private JInternalFrame jif;
	
	public void initialize()
	{
		question1 = new TreeItem<String>( "Question1");
		question2 = new TreeItem<String>( "Question2");
		rootItem = new TreeItem<String>( EXAM_NAME);
		rootItem.setExpanded(true);
		treeView.setRoot(rootItem);
		treeView.setShowRoot(true);
		addItems();
		jdp = new JDesktopPane();
		jif = new JInternalFrame( "Question1");
		jdp.add( jif);
		jif.show();
	}
	
	@SuppressWarnings("unchecked")
	public void addItems()
	{
		question1.getChildren().add( new TreeItem<String>( "Part 1"));
		question2.getChildren().add(new TreeItem<String>("Shuffle Method"));
		rootItem.getChildren().addAll( question1, question2);
	}
}
