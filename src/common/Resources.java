package common;

import java.net.URL;

import admin.Runner;
import javafx.scene.image.Image;

/**
 * @author Ziya Mukhtarov
 * @version 20/05/2019
 */
public class Resources
{
	public static final Image logo = new Image( Runner.class.getResourceAsStream( "/nito.png"));
	public static final Image bilkent = new Image( Runner.class.getResourceAsStream( "/bilkent.png"));
	public static final Image note = new Image( Runner.class.getResourceAsStream( "/note.png"));
	public static final Image home = new Image( Runner.class.getResourceAsStream( "/home.png"));
	public static final Image download = new Image( Runner.class.getResourceAsStream( "/download.png"));
	public static final Image folder = new Image( Runner.class.getResourceAsStream( "/folder.png"));
	public static final Image info = new Image( Runner.class.getResourceAsStream( "/info.png"));
	public static final Image list = new Image( Runner.class.getResourceAsStream( "/list.png"));
	public static final Image plus = new Image( Runner.class.getResourceAsStream( "/plus.png"));
	public static final Image checklist_eye = new Image( Runner.class.getResourceAsStream( "/checklist_eye.png"));
	public static final Image table_pencil = new Image( Runner.class.getResourceAsStream( "/table_pencil.png"));
	public static final Image quit = new Image( Runner.class.getResourceAsStream( "/quit.png"));
	public static final Image green_tick = new Image( Runner.class.getResourceAsStream( "/green_tick.png"));
	
	public static final URL stylesheet = Resources.class.getResource( "/common/stylesheet.css");
}
