import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * A class meant for objects that inhabit the world.
 * @author Aidan
 *
 */
public class SmartEntity extends Entity {
	double vx = 0.0, vy = 0.0, g = 0.4, traction = 0.95, maxvx = 3.5;
	double terminalVelocity = 20;
	double life, energy, maxLife, maxEnergy;
	int jumps = 0, maxjumps = 0, jumpBoost = 5, boosts = 15;
	//States
	boolean jumping = false, holdingJump = false, canJump = true;
	boolean onGround = false, hitCeiling = false;
	boolean movingLeft = false, movingRight = false, movingUp = false;
	ArrayList<Rectangle> hitboxes = new ArrayList<Rectangle>();
	Rectangle hitbox;

	@Override
	public void draw(Graphics g) {
	}

	@Override
	public void update(double deltaTime) {
	}
	
	void checkTerminalVelocity() {
		if(vy > terminalVelocity) {
			vy = terminalVelocity;
		}
	}
	
	/**
	 * Rectangle based collision detection
	 * @param deltaTime 
	 */
	void checkCollision(double deltaTime) {
		
	}
	
	//Update positions of x and y based on velocity
	void applyVelocity() {
		//Apply smooth delta
		if(movingLeft && vx > -maxvx) {
		vx -= 0.5;
		if(vx < -maxvx) vx = -maxvx;
		} else if(movingRight && vx < maxvx) {
		vx += 0.5;
		}
		//Check velocity
		velocityCheck();
		if(movingUp && Main.m.collidesWithID(this.hitbox, (short) 10)) {
			vy = -3;
		}
		if(!movingLeft && !movingRight) {
			if(onGround) {
			vx *= 0.7;
			} else {
			vx *= 0.8;
			}
		}
		
	}
	
	void velocityCheck() {
		//Keeps the velocity safe
		if(vx > maxvx)  vx = maxvx;
		if(vx < -maxvx) vx = -maxvx;
	}
		
	//Shift the hitbox to the position of the player
	void shiftHitbox() {
		x = hitbox.x;
		y = hitbox.y;
	}

}
