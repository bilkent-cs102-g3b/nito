package admin.model;

//import java.time.LocalDateTime;  
//import java.time.format.DateTimeFormatter;

/**
 * This class is for Exam
 * @author Adeem Adil Khatri
 * @version 5/05/2019
 */
public class Exam extends Entry
{
	//properties
//	private LocalDateTime startTime;
//	private int length;
	private String examTitle;
	private Entry entry;
	private String id;
	private String content;
	private String title;
	private Template template;
	
	private static final String EXAM = "Exam";
	private static final String INSTRUCTION = "Instruction";
	private static final String QUESTION = "Question";
	private static final String PART = "Part";
	
	//constructors
	/**
	 * Constructs a basic new Exam with default time value
	 */
	public Exam()
	{
		examTitle = "";
		title = entry.getTitle();
		id = entry.getId();
		content = entry.getContent();
	}
//	public Exam( int year, int month, int dayOfMonth,int hour, int minute, int second)
//	{
//		length = 0;
//		startTime = LocalDateTime.of( year, month, dayOfMonth, hour, minute, second);
//		   
//        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");  
//        String formatDateTime = startTime.format(format);
//        
//        length = startTime.getHour();
//        formatDateTime = length + ":" + startTime.getMinute() + ":" + startTime.getSecond();
//        
//        LocalDateTime endTime;
//        endTime = LocalDateTime.
//        
//	}
//	
//	//methods
	/**
	 * This method sends data in the given form to Examinee of that Model
	 * @param e The Examinee to which the data is to be send
	 * @param m The model to which the data is to be send
	 */
	public void send( Examinee e, Model m)
	{
		m.sendMessage( examTitle, EXAM, e);
		m.sendMessage( id +  ":::" + title + ":::" + content, INSTRUCTION, e);
		m.sendMessage( id +  ":::" + title, QUESTION, e);
		m.sendMessage( id +  ":::" + title + ":::" + template, PART, e);
	}
	
//	/**
//	 * This method checks if the Exam's time has started or not
//	 * @return true when Exam is started
//	 */
//	public boolean isStarted()
//	{
//		
//	}
//	
//	/**
//	 * This method finds the remaining time left for the Exam
//	 * @return Obtains the remaining time in minutes
//	 */
//	public int timeLeft()
//	{
//		length = (startTime.getHour() * 60) + (startTime.getMinute());
//		return length;
//	}
//	
//	/**
//	 * This method checks if the Exam's time has started or not
//	 * @return true when Exam has ended
//	 */
//	public boolean isEnded()
//	{
//		
//		if( startTime.getSecond() == )
//	}
}
