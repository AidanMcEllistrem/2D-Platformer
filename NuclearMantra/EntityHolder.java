import java.awt.Graphics;
import java.util.ArrayList;

public class EntityHolder {
	//Holds all the entities
	ArrayList<Entity> entities = new ArrayList<Entity>();
	//Delta Time is used for implementing changes in movement-based velocities for smooth moving.
	boolean pause = false;
	
	public void update(double deltaTime) {
		//Update all entities in the array
		for(int i = 0; i < entities.size(); i++) {
			if(pause == false || entities.get(i) instanceof Dialogue)
			entities.get(i).update(deltaTime);
			//System.out.println(entities.get(i).x + " " + entities.get(i).y);
			//System.out.println(":" + Main.p.x + " " + Main.p.y);
		}
	}
	
	public void draw(Graphics g) {
		//Draw all entities in the array
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).draw(g);
		}
	}
	
	public void add(Entity e) {
		entities.add(e);
		System.out.println("Added an entity with " + e.x + " " + e.y);
	}
	
	public void remove(Entity e) {
		entities.remove(e);
		System.out.println("Removed an entity with " + e.x + " " + e.y);
	}
}
