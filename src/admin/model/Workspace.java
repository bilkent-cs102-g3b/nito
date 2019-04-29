package admin.model;

import java.io.File;

/**
 * @author Ziya Mukhtarov
 * @version 24/04/2019
 */
public class Workspace
{
	private static File workspace;

	/**
	 * @param workspace - The new workspace folder
	 */
	public static void setWorkspace( File workspace)
	{
		Workspace.workspace = workspace;
	}

	/**
	 * @return The workspace folder
	 */
	public static File getWorkspace()
	{
		if ( workspace == null)
		{
			setWorkspace( new File( System.getProperty( "user.dir") + File.separator + "Workpsace"));
		}
		workspace.mkdirs();
		return workspace;
	}

	/**
	 * @return The folder for examinees inside workspace
	 */
	public static File getExamineesFolder()
	{
		File folder = new File( getWorkspace().getPath() + File.separator + "Examinees");
		folder.mkdirs();
		return folder;
	}

	/**
	 * @param e - The examinee
	 * @return The folder of the specified examinee inside the workspace
	 */
	public static File getFolderOfExaminee( Examinee e)
	{
		File folder = new File( Workspace.getExamineesFolder().getPath() + File.separator + e.getName() + "#" + e.getId());
		folder.mkdirs();
		return folder;
	}
}
