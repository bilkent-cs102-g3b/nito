package testers.admin.model;

import admin.model.Examinee;
import admin.model.Group;
import admin.model.Model;

/**
 * @author Ziya Mukhtarov
 * @version 18/04/2019
 */
public class GroupExaminee
{
	public static void main( String[] args)
	{
		Model m = new Model();

		Examinee ziya = m.createExaminee( "Ziya Mukhtarov");
		Group s03 = m.createGroup( "CS102-03");
		System.out.println( m + "\n");

		Examinee javid = m.createExaminee( "Javid Baghirov");
		Group s02 = m.createGroup( "CS102-02");
		Examinee batur = m.createExaminee( "Osman Batur Ince");
		System.out.println( m + "\n");

		m.changeExamineeGroup( ziya, s03);
		m.changeExamineeGroup( javid, s03);
		m.changeExamineeGroup( batur, s02);
		System.out.println( m + "\n");
	}
}
