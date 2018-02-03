import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Item extends Entity {
	
	Rectangle bounds = new Rectangle(16,16);
	String name = "";
	boolean obt = false;
	
	public Item(int x, int y, int tilesize) {
		bounds = new Rectangle(x,y,tilesize,tilesize);
	}
	public Item(int x, int y) {
		bounds = new Rectangle(x,y,16,16);
	}
	public Item(int x, int y, String name) {
		bounds = new Rectangle(x,y,16,16);
		this.name = name;
	}
	
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		//System.out.println(bounds.x + " " + bounds.y);
		g.setColor(Color.RED);
		g.fillRect((int)(bounds.x + Main.cam.x),(int)(bounds.y + Main.cam.y),bounds.width,bounds.height);
	}

	@Override
	public void update(double deltaTime) {
		//Add the item to the player's inventory, and remove it from existance
		if(Main.p.hitbox.intersects(bounds) && !obt) {
			Main.p.inv.add(name);
			Main.ent.remove(this);
			System.out.println("Obtained " + name + "");
			obt = true;
		}
		
	}

}
