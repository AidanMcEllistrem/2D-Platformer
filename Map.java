import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Map {
	int tilesize = 16;
	Range range_x, range_y;
	Tile[][] map;
	
	public Map(Tile[][] map) {
		this.map = map;
	}
	public Map() {
		this.map = new Tile[10][10];
	}
	
	//Set boundaries for bounds checking
	
	//Draw each tile onto the screen
	public void draw(Graphics g) {
		for(int x = 0; x < map.length; x++) {
			for(int y = 0; y < map[0].length; y++) {
				map[x][y].draw(g, x, y);
			}
		}
	}
	
	ArrayList<Rectangle> tree = new ArrayList<Rectangle>();
	ArrayList<Tile> tiletree = new ArrayList<Tile>();
	int boundy, boundx;
	
	private void setMapBounds() {
		boundx = (map.length) * tilesize;
		boundy = (map[0].length) * tilesize;
		
		if(range_y.high > boundy) {
			range_y.high = (boundy);
		}
		if(range_y.low < 0) {
			range_y.low = 0;
		}
		if(range_x.high > boundx) {
			range_x.high = (boundx);
		}
		if(range_x.low < 0) {
			range_x.low = 0;
		}
	}
	/**
	 * Check for dialogues
	 * @param hitbox
	 * @return
	 */
	public Point collidesWithSign(Rectangle hitbox) {
		range_x = new Range(hitbox.getCenterX() - tilesize - hitbox.width, hitbox.getCenterX() + tilesize + hitbox.width);
		range_y = new Range(hitbox.getCenterY() - tilesize - hitbox.height, hitbox.getCenterY() + tilesize + hitbox.height);
		setMapBounds();
		
		for(int x = range_x.low / 16; x < range_x.high / 16; x++) {
			for(int y = range_y.low / 16; y < range_y.high / 16; y++) {
				if(range_x.contains(map[x][y].hitbox.x) && range_y.contains(map[x][y].hitbox.y) && map[x][y].id == 11) {
					System.out.println("Found Sign!!!");
					tree.add(map[x][y].hitbox); 
				}
				
			}
		}
		for(Rectangle h : tree) {
			if(h.intersects(hitbox)) {
				tree.clear();
				System.out.println("Place " + h.x / 16 + " " + h.y / 16);
				return new Point(h.x / 16,h.y / 16);
			}
		}
		tree.clear();
		return new Point(-1,-1);
		
	}
	
	/**
	 * Collision ID, tests if a certain tile overlaps a hitbox
	 * @param hitbox
	 * @return
	 */
	public boolean collidesWithID(Rectangle hitbox, short id) {
		range_x = new Range(hitbox.getCenterX() - tilesize - hitbox.width, hitbox.getCenterX() + tilesize + hitbox.width);
		range_y = new Range(hitbox.getCenterY() - tilesize - hitbox.height, hitbox.getCenterY() + tilesize + hitbox.height);
		setMapBounds();
		
		for(int x = range_x.low / 16; x < range_x.high / 16; x++) {
			for(int y = range_y.low / 16; y < range_y.high / 16; y++) {
				if(range_x.contains(map[x][y].hitbox.x) && range_y.contains(map[x][y].hitbox.y) && map[x][y].id == id) {
					tree.add(map[x][y].hitbox); 
				}
				
			}
		}
		for(Rectangle h : tree) {
			if(h.intersects(hitbox)) {
				tree.clear();
				return true;
			}
		}
		tree.clear();
		return false;
		
	}
	
	/**
	 * A method that checks the tiles surrounding a certain hitbox and checks to see if there is a collision.
	 * @param hitbox
	 * @return
	 */
	public boolean collides(Rectangle hitbox) {
		range_x = new Range(hitbox.getCenterX() - tilesize - hitbox.width, hitbox.getCenterX() + tilesize + hitbox.width);
		range_y = new Range(hitbox.getCenterY() - tilesize - hitbox.height, hitbox.getCenterY() + tilesize + hitbox.height);
		setMapBounds();

		
		//System.out.println(range_y.high +  "-" + Main.realHeight);
		//System.out.println("Ranges " + range_x.low / 16 + "," + range_x.high / 16 + "," + range_y.low / 16 + "," + range_y.high / 16);
		//int tiles_searched = 0;
		for(int x = range_x.low / 16; x < range_x.high / 16; x++) {
			for(int y = range_y.low / 16; y < range_y.high / 16; y++) {
				if(range_x.contains(map[x][y].hitbox.x) && range_y.contains(map[x][y].hitbox.y) && map[x][y].id > 0 && map[x][y].id < 10) {
					tree.add(map[x][y].hitbox); 
				}
				
				//tiles_searched++;
			}
		}
		//System.out.println("Tiles Searched: " + tiles_searched);
		
		//System.out.println("Tree Size: " + tree.size());
		for(Rectangle h : tree) {
			if(h.intersects(hitbox)) {
				tree.clear();
				return true;
			}
		}
		tree.clear();
		return false;
	}
}
