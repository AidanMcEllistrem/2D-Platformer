/**
 * A very important tool, the Camera class will draw entities in relation to it.  This allows for scrolling.
 * @author Aidan
 *
 */
public class Camera {
	//Using doubles for true position, but almost everything is drawn in integers
	double x, y, midpointx, midpointy, scroll, accept;
	boolean smooth_scrolling = false, flip_scrolling = false;
	boolean free_x = false, free_y = false;
	int width = Main.realres.width, height = Main.realres.height;
	
	//Constructors
	public Camera(double x, double y) {
		this.x = x;
		this.y = y;
		this.midpointx = Main.realres.width / 2;
		this.midpointy = Main.realres.height / 2;
		this.scroll = 0.5;
		this.accept = 20;
	}
	public Camera(double x, double y, double midpointx) {
		this.x = x;
		this.y = y;
		this.midpointx = midpointx;
	}
	int pxscroll = 10;
	int cpx = 0;
	
	/**
	 * Change the position of the camera to the position to the desired entity.
	 * @param e - A smart entity.  Anything that is supposed to move can be fixed on the camera.
	 */
	public void shift(SmartEntity e) {
			if(y + e.hitbox.y + e.hitbox.getHeight() != midpointy) {
				y = midpointy - e.hitbox.y - e.hitbox.getHeight() + 16;
			}
			if(x + e.hitbox.x + e.hitbox.getWidth() != midpointx) {
				x = midpointx - e.hitbox.x - e.hitbox.getWidth();
			}
			
	}
}
