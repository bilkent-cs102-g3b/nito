package admin.view;

import java.io.File;
import java.net.MalformedURLException;

import org.controlsfx.control.BreadCrumbBar;

import default_package.NumberedEditor;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WelcomeViewController
{
	@FXML
	private BreadCrumbBar<String> breadCrumb;
	@FXML
	private NumberedEditor editor;
	@FXML
	private TreeItem<String> examTree;
	@FXML
	private ImageView logoBilkent;
	@FXML
	private ImageView logoNito;
	@FXML
	private ImageView logoPlus;
	@FXML
	private ImageView instructions;
	@FXML
	private ImageView importExam;
	@FXML
	private ImageView about;
	
//	private ArrayList<String> exams;
	
	public void initialize() throws MalformedURLException
	{
		test();
		logoNito.setImage( new Image( new File("Resources/logo.png").toURI().toURL().toString()));
		logoBilkent.setImage( new Image( new File("Resources/bilLogo.png").toURI().toURL().toString()));
		logoPlus.setImage( new Image( new File("Resources/logoPlus.PNG").toURI().toURL().toString()));
		instructions.setImage( new Image( new File("Resources/instructions.PNG").toURI().toURL().toString()));
		importExam.setImage( new Image( new File("Resources/importExam.PNG").toURI().toURL().toString()));
		about.setImage( new Image( new File("Resources/about.PNG").toURI().toURL().toString()));
	}
	
	/**
	 * TODO Remove after testing
	 */
	private void test()
	{
		breadCrumb.setSelectedCrumb( BreadCrumbBar.buildTreeModel( "Some", "crumb", "here", "!"));
	}
}
