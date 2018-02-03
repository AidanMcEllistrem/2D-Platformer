import java.awt.Point;

/**
 * A class that shifts the screen display
 * @author Aidan
 *
 */
public class Camera {
	Point position = new Point(0,0);
	public void set(int x, int y) {
		position.x = x;
		position.y = y;
	}
	public void set(Point p) {
		position = p;
	}
	
	public void shift(int x, int y) {
		position.x += x;
		position.y += y;
	}
}
