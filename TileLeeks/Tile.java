import java.awt.Color;
import java.awt.Image;

public class Tile {
	String name = "", glyph = "";
	Image img = null;
	Color color = Color.WHITE;
	String type = "Color";
	
	public Tile() {
		
	}
	public Tile(String name) {
		this.name = name;
		type = "No Type";
	}
	public Tile(String name, Color color) {
		this.name = name;
		this.color = color;
		type = "Color";
	}
	public Tile(String name, String glyph) {
		this.name = name;
		this.glyph = glyph;
		type = "Glyph";
	}
	public Tile(String name, Image img) {
		this.name = name;
		this.img = img;
		type = "Image";
	}
}
