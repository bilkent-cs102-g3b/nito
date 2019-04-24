package testers.admin;

import java.io.File;

import admin.model.Workspace;

/**
 * @author Ziya Mukhtarov
 * @version 24/04/2019
 */
public class WorkspaceTester
{
	public static void main( String[] args)
	{
		System.out.println( System.getProperty( "user.dir"));
		Workspace.setWorkspace( new File( System.getProperty( "user.dir")));

		System.out.println( Workspace.getWorkspace());
		System.out.println( Workspace.getExamineesFolder());
		System.out.println( Workspace.getExamineesFolder());
	}

}
