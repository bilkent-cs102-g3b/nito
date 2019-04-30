package testers.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import admin.model.Workspace;

/**
 * @author Ziya Mukhtarov
 * @version 01/05/2019
 */
public class WorkspaceTester
{
	public static void main( String[] args) throws IOException
	{
		Workspace workspace = new Workspace();
		workspace.get();

		System.out.println( workspace.get());
		System.out.println( workspace.getExamineesFolder() + "\n\n");

		Files.createFile( Paths.get( workspace.get().getPath(), "tmp1.a"));
		Files.createFile( Paths.get( workspace.get().getPath(), "tmp2.b"));
		Files.createFile( Paths.get( workspace.get().getPath(), "tmp3.c"));
		Files.createFile( Paths.get( workspace.get().getPath(), "tmp4"));
		Files.createFile( Paths.get( workspace.getExamineesFolder().getPath(), "tmp5.d"));
		Files.createFile( Paths.get( workspace.getExamineesFolder().getPath(), "tmp6.z"));

		workspace.set( new File( System.getProperty( "user.dir") + File.separator + "Workspace2"));
	}

}
