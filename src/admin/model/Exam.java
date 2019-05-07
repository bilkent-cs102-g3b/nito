package admin.model;

import java.time.*;
import java.time.format.*;
/**
 * This class is for Exam
 * @author Adeem Adil Khatri
 * @version 6/05/2019
 */
public class Exam extends Entry
{
	//properties
	private String examTitle;
	private Entry entry;
	private String id;
	private String content;
	private String title;
	private LocalTime now;
	private DateTimeFormatter formatter;
	
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
	
	//methods
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
	
	public void start()
	{
		now = LocalTime.now();
		DateTimeFormatter.ofPattern("HH.mm");
		String timeString = now.format(formatter);
	}
	
}
