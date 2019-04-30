package admin.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

/**
 * Stores the workspace folder. If any get method is called before setting the
 * workspace, it will set the workspace automatically to
 * CURRENT_FOLDER\Workspace
 * @author Ziya Mukhtarov
 * @version 01/05/2019
 */
public class Workspace
{
	private File workspace;

	/**
	 * Sets the workspace. If the workspace is already set, then it first moves the
	 * contents to the new workspace and deletes the previous folder.
	 * @param newWorkspace The new workspace folder
	 * @return true if this method succeeded, false otherwise
	 */
	public boolean set( File newWorkspace)
	{
		if ( newWorkspace.isDirectory() && newWorkspace.listFiles().length != 0)
		{
			// Non-empty folder. DANGER! Don't accept it for now.
			// TODO ask whether the user is sure about it instead of ignoring
			return false;
		}
		newWorkspace.mkdirs();
		if ( workspace != null)
		{
			// Move all files from previous workspace to the new one
			moveContents( newWorkspace);
		}
		workspace = newWorkspace;
		return true;
	}

	/**
	 * Moves all of the contents of current workspace recursively to new workspace
	 * folder while preserving the folder structure
	 * @param newWorkspace The new workspace to move current content to
	 */
	private void moveContents( File newWorkspace)
	{
		String oldWorkspace = workspace.getPath();
		try
		{
			Files.walk( Paths.get( workspace.toURI())).filter( Files::isRegularFile).forEach( new Consumer<Path>() {
				@Override
				public void accept( Path t)
				{
					String relativePath = t.toString().substring( oldWorkspace.length() + 1);
					File oldFile = t.toFile();
					File newFile = new File( newWorkspace.getPath(), relativePath);

					new File( newFile.getParent()).mkdirs();
					oldFile.renameTo( newFile);
				}
			});
			deleteDirectory( workspace);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Recursively deletes the given directory. The directory should only contain
	 * empty sub-directories in its entire hierarchy
	 * @param folder The folder to recursively erase
	 */
	private void deleteDirectory( File folder)
	{
		for ( File child : folder.listFiles())
		{
			deleteDirectory( child);
		}
		folder.delete();
	}

	/**
	 * If the workspace is not set before, this is going to set the workspace folder
	 * to CURRENT_FOLDER\Workspace
	 * @return The workspace folder
	 */
	public File get()
	{
		if ( workspace == null)
		{
			set( new File( System.getProperty( "user.dir") + File.separator + "Workpsace"));
		}
		return workspace;
	}

	/**
	 * If the workspace is not set before, this is going to set the workspace folder
	 * to CURRENT_FOLDER\Workspace
	 * @return The folder for examinees inside workspace
	 */
	public File getExamineesFolder()
	{
		File folder = new File( get().getPath() + File.separator + "Examinees");
		folder.mkdirs();
		return folder;
	}

	/**
	 * If the workspace is not set before, this is going to set the workspace folder
	 * to CURRENT_FOLDER\Workspace
	 * @param e The examinee
	 * @return The folder of the specified examinee inside the workspace
	 */
	public File getFolderOfExaminee( Examinee e)
	{
		File folder = new File( getExamineesFolder().getPath() + File.separator + e.getName() + "#" + e.getId());
		folder.mkdirs();
		return folder;
	}
}
