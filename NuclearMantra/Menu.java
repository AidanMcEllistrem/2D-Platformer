import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Menu extends Display {
	
	ArrayList<TextElement> dialogues = new ArrayList<TextElement>();
	Color bg = Color.BLACK, fg = Color.WHITE;
	Range menuRange;
	String text = "";
	static boolean delete = false;
	
	public Menu(TextElement[] elements, int x, int y) {
		position = new Point(x,y);
		//Push the elements onto the list
		for(TextElement e : elements) {
			dialogues.add(e);
		}
		//Set bounds for the text selector
		menuRange = new Range(0,dialogues.size());
	}
	
	public Menu(TextElement[] elements, int x, int y, int low, int high, int current) {
		position = new Point(x,y);
		//Push the elements onto the list
		for(TextElement e : elements) {
			dialogues.add(e);
		}
		//Set bounds for the text selector
		menuRange = new Range(low,high);
		Scene.menuState = current;
	}
	
	public void changeElement(int i, String s) {
		dialogues.get(i).text = s;
	}
	
	public void changeForeground(Color c) {
		fg = c;
	}
	
	
	@Override
	public void draw(Graphics g) {
		int state = Scene.menuState;
		//Make sure the user cannot select an option outside of the scope of the menu
		checkBounds(state);
		//Draw background
		g.setColor(bg);
		g.fillRect(position.x,position.y,d.width,d.height);
		//Draw foreground
		g.setColor(fg);
		//Poor man's for loop
		int i = 0;
		for(TextElement e : dialogues) {
			//Draw the text elements onto the screen
			g.setFont(e.font);
				if(e.list.size() == 0) {
				g.drawString(e.text,e.position.x + position.x,e.position.y + position.y);
				} else {
				for(int x = 0; x < e.list.size(); x++) {
					g.drawString(e.list.get(x),e.position.x + position.x,e.position.y + position.y + (e.font.getSize() * x));
				}
			}
			if(i == state) {
			//Draw a selector
			g.drawRect(e.position.x + position.x - 8, e.position.y + position.y - e.font.getSize() / 2, 4, 4);
			}
				
			i++;
		}
	}
	
	public void checkBounds(int state) {
		if(menuRange.outLeft(state)) {
			Scene.menuState = menuRange.low;
		}
		if(menuRange.outRight(state + 1)) {
			Scene.menuState = menuRange.high;
		}
	}

	public String getAction() {
		return dialogues.get(Scene.menuState).text;
	}
	
	//Stuff for easily accessible menus
	static ArrayList<String> devs = new ArrayList<String>(){{
		add("Aidan McEllistrem");
		add("Ben Fagre");
		add("Nic Draves");
		add("Danny Molina");
		add("Graham Stetzenbach");
		add("Isaac Buxton");
		add("v. Pre-Alpha");
	}};
	static TextElement[] fileOptions = {new TextElement("Nuclear Mantra",new Point(60, 40),28),
	new TextElement("New Game",new Point(40, 100),14),
	new TextElement("Load Game",new Point(40, 120),14),
	new TextElement("Quit",new Point(40, 140),14),
	new TextElement("A Game By",new Point(180, 100),10),
	new TextElement(devs,new Point(180, 120),10)
	};
	static Menu mainMenu = new Menu(fileOptions,0,0,1,3,1);
	
	static TextElement[] files = {
	new TextElement("Load a save... ",new Point(40, 60),24),		
	new TextElement("Save 1 ",new Point(40, 100),12),
	new TextElement("Save 2 ",new Point(40, 120),12),
	new TextElement("Save 3 ",new Point(40, 140),12),
	new TextElement("Delete Save",new Point(40, 160),12),
	new TextElement("Back to Main Menu",new Point(40,180),12)
	};
	
	static Menu fileMenu = new Menu(files,0,0,1,5,1);
	
}


