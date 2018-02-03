import java.awt.Color;

/**
 * A class meant to manage tile creation better.
 * @author Aidan
 *
 */
public class TilePen {
	//A list of all possible tiles, with the max value of the entire scope of a short (FFFF)
	Tile[] tiles = new Tile[Short.MAX_VALUE * 2];
	
	//Some collections of tiles that help organization
	Tile[] solid_colors = {
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
	};
	
	Tile[] basic_utilities = {
		new Tile("Ladder","Ladder"),
		new Tile("Sign","Sign"),
		new Tile("Rest","Rest")
	};
	
	public void initialize() {
		//Load the tile "libraries" in order
		load(solid_colors);
		load(basic_utilities);
	}
	
	//Keeps track of where the tiles are being loaded in "memory"
	int place = 0;
	private void load(Tile[] t) {
		for(int i = 0; i < t.length; i++) {
			tiles[place] = t[i];
			place++;
		}
	}
	short context = 1;
	boolean held = false, erase = false, paintbucket = false;
	String tilename = "";
	
	public void change(short i) {
		context = i;
	}
}
