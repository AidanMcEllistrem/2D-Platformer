import java.awt.Graphics;

/**
 * Everything that moves in the game is an entity
 * @author Aidan
 *
 */
public abstract class Entity {
	int layer;
	double x,y;
	
	public abstract void draw(Graphics g);
	public abstract void update(double deltaTime);
}
