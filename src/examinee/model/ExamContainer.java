package examinee.model;

import java.util.ArrayList;

/**
 * A container class for ExamEntry and its subclasses
 * @author Alper Sari
 * @version 09/05/2019
 */
public class ExamContainer
{
	ArrayList<ExamEntry> children;
	
	public ExamContainer()
	{
		children = new ArrayList<ExamEntry>();
	}
	
	/**
	 * Add ExamEntry object to container
	 * @param e Entry to add
	 */
	public void add( ExamEntry e)
	{
		children.add( e);
		e.setParent( this);
	}

	/**
	 * Return container
	 * @return children
	 */
	public ArrayList<ExamEntry> getAll()
	{
		return children;
	}
}
