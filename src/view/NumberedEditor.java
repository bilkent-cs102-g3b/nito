package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * A JavaFX class for editor with line numbering
 * @author Ziya Mukhtarov
 * @version 09/04/2019
 */
public class NumberedEditor extends HBox
{
	/**
	 * Padding for line number text area
	 */
	private final int TEXT_PADDING = 20;
	
	/**
	 * The TextArea of line numbers
	 */
	private TextArea lineNums;
	
	/**
	 * The TextArea of editor itself
	 */
	private TextArea editor;
	
	private int lastNum;
	private String currentNums;
	private boolean scrollDisabled;
	private boolean rightAligned;
	
	/**
	 * Initializes the editor
	 */
	public NumberedEditor()
	{
		super();
		
		editor = new TextArea();
		setHgrow(editor, Priority.ALWAYS);
		
		lastNum = 1;
		currentNums = "1";
		scrollDisabled = false;
		rightAligned = false;

		lineNums = new TextArea();
		lineNums.setDisable(true);
		lineNums.setOpacity(1);
		sizeTextAreaToText(lineNums, currentNums);
		
		getChildren().add( lineNums);
		getChildren().add( editor);

		editor.fontProperty().addListener( o -> syncFont());
		editor.scrollTopProperty().addListener( o -> syncScroll());
		
		// For numbering
		editor.textProperty().addListener( new ChangeListener<String>() {
			@Override
			public void changed( ObservableValue<? extends String> observable, String oldValue, String newValue)
			{
				int lines = 1;
				
				for (int i = 0; i < newValue.length(); i++)
				{
					if ( newValue.charAt( i) == 10) lines++;
				}

				while (lastNum < lines)
				{
					lastNum++;
					currentNums += "\n" + lastNum;
				}
				while (lastNum > lines)
				{
					lastNum--;
					currentNums = currentNums.substring(0, currentNums.lastIndexOf("\n"));
				}

				// Try to disable ScrollPane of line numbers if not done yet
				if (!scrollDisabled)
				{
					ScrollPane scroll = (ScrollPane) lineNums.lookup(".scroll-pane");
					if (scroll != null)
					{
						scroll.setHbarPolicy( ScrollBarPolicy.NEVER);
						scroll.setVbarPolicy( ScrollBarPolicy.NEVER);
						scrollDisabled = true;
					}
				}
				// Try to align numbers to right if not done yet
				if (!rightAligned)
				{
					Text text = (Text) lineNums.lookup(".text");
					if (text != null)
					{
						text.setTextAlignment( TextAlignment.RIGHT);
						rightAligned = true;
					}
				}

				sizeTextAreaToText( lineNums, currentNums);
			}
		});
	}
	
	/**
	 * Changes width of TextArea so that its size is minimal and it can show the text without wrapping
	 * @param textArea - The TextArea to configure its width
	 * @param text - The text to be shown
	 */
	private void sizeTextAreaToText( TextArea textArea, String text)
	{
		Text t;
		StackPane pane;
		double width;

        t = new Text( text);
        t.setFont( textArea.getFont());
        
        pane = new StackPane( t);
        pane.layout();

        width = t.getLayoutBounds().getWidth();

        textArea.setMinWidth( width + TEXT_PADDING);
        textArea.setMaxWidth( width + TEXT_PADDING);
        textArea.setText( text);
        
        syncScroll();
    }
	
	/**
	 * Synchronizes the scrolling
	 */
	private void syncScroll()
	{
		lineNums.setScrollTop( editor.getScrollTop());
	}

	/**
	 * Synchronizes the font
	 */
	private void syncFont()
	{
		lineNums.setFont( editor.getFont());
		sizeTextAreaToText( lineNums, currentNums);
	}
	
	/**
	 * Returns the textual content of the editor
	 * @return The textual content of the editor
	 */
	public String getText()
	{
		return editor.getText();
	}
}
