package admin.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

/**
 * Stores the workspace folder. If any get method is called before setting the
 * workspace, it will set the workspace automatically to
 * CURRENT_FOLDER\Workspace<br>
 * <br>
 * There are three directories inside the workspace directory:<br>
 * 1) {@code exam_entries} contains all the data created by admin for exams<br>
 * 2) {@code solutions} contains all the submitted solutions from examinees for
 * the previously started exam<br>
 * 3) {@code grades} contains all the data about the current grades given by
 * admin
 * @author Ziya Mukhtarov
 * @version 08/05/2019
 */
public class Workspace
{
	private File workspace;
	private static Workspace instance;

	private Workspace()
	{
	}

	/**
	 * @return The instance of Workspace
	 */
	public static Workspace getInstance()
	{
		if ( instance == null)
		{
			instance = new Workspace();
		}
		return instance;
	}

	/**
	 * Sets the workspace. If the workspace is already set, then it first moves the
	 * contents to the new workspace and deletes the previous folder.
	 * @param newWorkspace The new workspace folder
	 * @return true if this method succeeded, false otherwise
	 */
	public boolean set( File newWorkspace)
	{
		if ( newWorkspace.isDirectory() && getByRelativePath( ".nito") == null)
		{
			// Non-empty folder. DANGER! Don't accept it for beta version.
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

		try
		{
			new File( workspace + File.separator + ".nito").createNewFile();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		load();
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
	 * @param path The relative path to the needed folder or file, e.g. .nito;
	 *             exam_entries\some_question
	 * @return The file object referring to the file that was found, or null if no
	 *         such file was found
	 */
	private File getByRelativePath( String path)
	{
		File[] files = get().listFiles();
		for ( File file : files)
		{
			String pathToFile = file.getPath();
			if ( pathToFile.lastIndexOf( path) == pathToFile.length() - path.length())
			{
				return file;
			}
		}
		return null;
	}

	/**
	 * Saves the data into workspace
	 */
	public void save()
	{
		try
		{
			FileOutputStream fos = new FileOutputStream( getModelFile());
			ObjectOutputStream oos = new ObjectOutputStream( fos);
			oos.writeObject( Model.getInstance());
			oos.close();
			fos.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO save other data
	}

	/**
	 * Loads the data from workspace
	 */
	public void load()
	{
		try
		{
			FileInputStream fis = new FileInputStream( getModelFile());
			ObjectInputStream ois = new ObjectInputStream( fis);
			ois.readObject();
			ois.close();
			fis.close();
		}
		catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO save other data
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
	public File getExamEntriesFolder()
	{
		File folder = new File( get().getPath() + File.separator + "exam_entries");
		folder.mkdirs();
		return folder;
	}

	public File getModelFile()
	{
		return new File( getExamEntriesFolder() + File.separator + "model");
	}
}
