package admin.model;
/**
 * This class is for Question
 * @author Adeem Adil Khatri
 * @version 5/05/2019
 */
public class Question extends Entry 
{
	//Properties
	private int maxPoints;
	
	//Constructors
	public Question()
	{
		maxPoints = 0;
	}
	
	//Methods
	/**
	 * @param points
	 */
	public void setMaxPoints( int points)
	{
		maxPoints = points;
	}
	
	/**
	 * @return maxPoints
	 */
	public int maxPoints()
	{
		return maxPoints;
	}
}
