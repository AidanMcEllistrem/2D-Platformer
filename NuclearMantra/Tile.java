import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Tile {
	
	byte size = 16;
	short id = 0;
	Color c = new Color(128,64,92);
	Rectangle hitbox;
	
	public Tile(short id, int x, int y) {
		this.id = id;
		this.hitbox = new Rectangle(x * size, y * size, size, size);
	}

	public void draw(Graphics g, int x, int y) {
		int px = (int)((hitbox.x) + Main.cam.x), py = (int)((hitbox.y) + Main.cam.y);
		this.hitbox = new Rectangle(x * size, y * size, size, size);
		//System.out.println("Position at " + hitbox.x + " " + hitbox.y );
		//There has to be a better system to do this
		switch(id) {
			case 0: c = Color.black;break;
			case 1: c = Color.white;break;
			case 2: c = Color.red;break;
			case 3: c = Color.orange;break;
			case 4: c = Color.yellow;break;
			case 5: c = Color.green;break;
			case 6: c = Color.blue;break;
			case 7: c = Color.magenta;break;
			case 8: c = Color.lightGray;break;
			case 9: c = Color.darkGray;break;
		}
		/*
		 * From Tile Leeks
		new Tile("Nothing",Color.BLACK),
		new Tile("White",Color.WHITE),
		new Tile("Red",Color.RED),
		new Tile("Orange",Color.ORANGE),
		new Tile("Yellow",Color.YELLOW),
		new Tile("Green",Color.GREEN),
		new Tile("Blue",Color.BLUE),
		new Tile("Violet",Color.MAGENTA),
		new Tile("Light Gray",Color.LIGHT_GRAY),
		new Tile("Dark Gray",Color.DARK_GRAY),
		 */
		if(id >= 0 && id <= 9) {
		g.setColor(c);
		g.fillRect(px,py,(int)size,(int)size);
		}
		if(id > 9 && id <= 12) {
			g.setColor(Color.white);
		if(id == 10) {
		//Ladder glyph
		g.drawLine(px, py, px, py + 15);
		g.drawLine(px + 15, py + 4, px, py + 4);
		g.drawLine(px + 15, py + 12, px, py + 12);
		g.drawLine(px + 15, py, px + 15, py + 15);
		}
		if(id == 11) {
		//Sign glyph
		g.drawRect(px, py, 15, 7);
		g.drawLine(px + 7, py + 7, px + 7, py + 15);
		}
		if(id == 12) {
		//Ladder glyph
		g.drawLine(px + 7, py, px + 15, py + 7);
		g.drawLine(px + 15, py + 7, px + 3, py + 7);
		g.drawLine(px + 3, py + 7, px + 8, py + 15);
		}
		}
		//System.out.println("Printed tile at " + ((x * size) + Main.cam.x) + ", " + ((y * size) + Main.cam.y));
	}
	
}
