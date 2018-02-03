import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Display is a branch of Entity that is meant to show displays, like menus and dialogues.
 * @author Aidan
 *
 */
public class Display extends Entity {
	Dimension d = new Dimension(0,0);
	Point position;
	int padHeight, padWidth;

	@Override
	public void draw(Graphics g) {}

	@Override
	public void update(double deltaTime) {}

}
