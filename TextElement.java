import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;

public class TextElement {
	String text = "Blank";
	Point position = new Point(0,0);
	Font font = new Font("Verdana", Font.PLAIN, 16);
	ArrayList<String> list = new ArrayList<String>();
	
	/**
	 * Creates a new element with a fixed font.
	 * @param text
	 * @param action
	 * @param position
	 */
	TextElement(String text, Point position) {
		this.text = text;
		this.position = position;
	}
	
	/**
	 * Creates a new element with font size.
	 * @param text
	 * @param action
	 * @param position
	 * @param size
	 */
	TextElement(String text, Point position, int size) {
		this.text = text;
		this.position = position;
		this.font = new Font("Verdana", Font.PLAIN, size);
	}
	
	/**
	 * Creates a new element with font size and a list.
	 * @param text
	 * @param action
	 * @param position
	 * @param size
	 */
	TextElement(ArrayList list,Point position, int size) {
		this.text = text;
		this.position = position;
		this.font = new Font("Verdana", Font.PLAIN, size);
		this.list = list;
	}
}