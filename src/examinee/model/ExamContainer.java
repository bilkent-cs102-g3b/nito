package examinee.model;

import java.util.ArrayList;

public class ExamContainer
{
	ArrayList<ExamEntry> children;
	
	public ExamContainer()
	{
		children = new ArrayList<ExamEntry>();
	}
	
	public void add( ExamEntry e)
	{
		children.add(e);
		e.setParent(this);
	}
	
	public ArrayList<ExamEntry> getAll()
	{
		return children;
	}
}
