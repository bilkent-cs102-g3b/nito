package testers.admin;

import java.util.Arrays;

import admin.model.Examinees;

/**
 * @author Ziya Mukhtarov
 * @version 29/04/2019
 */
public class ExamineesSearchTester
{
	public static void main( String[] args)
	{
		Examinees list = new Examinees();

		list.newExaminee( "Ziya Mukhtarov", null);
		list.newExaminee( "Mannan Abdul", null);
		list.newExaminee( "Adeem Adil Khatri", null);
		list.newExaminee( "Mokhlaroyim Raupova", null);
		list.newExaminee( "Javid Baghirov", null);
		list.newExaminee( "Alper Sari", null);

		System.out.println( Arrays.toString( list.search( "ar")));
	}
}
