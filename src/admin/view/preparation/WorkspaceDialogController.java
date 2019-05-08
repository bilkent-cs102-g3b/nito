package admin.view.preparation;

import java.io.File;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.util.Pair;

public class WorkspaceDialogController {
	
	@FXML
	Dialog< Pair<File, Boolean>> root;
	@FXML
	TextField field;
	@FXML
	CheckBox checkBox;
	
	File folder;
	
	public WorkspaceDialogController()
	{
		folder = new File(System.getProperty( "user.dir"));
	}
	
	public void initialize()
	{
		field.setText( folder.getAbsolutePath());
		
		root.setResultConverter( button -> {
			if (button == ButtonType.OK)
			{
				return new Pair<>(folder, checkBox.isSelected());
			}
			return null;
		});
	}
	
	public void browse() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(folder);
		File selectedDirectory = directoryChooser.showDialog(root.getOwner());

		if(selectedDirectory == null){
		     //No Directory selected
		}else{
			folder = selectedDirectory;
			field.setText( folder.getAbsolutePath());
		}
	}
	
}
