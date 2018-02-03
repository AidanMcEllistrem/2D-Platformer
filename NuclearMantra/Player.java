import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * The player character
 * @author Aidan
 *
 */
public class Player extends SmartEntity {
	HUD hud = new HUD();
	ArrayList<String> inv = new ArrayList<String>();
	
	public Player(double x, double y) {
		this.x = x;
		this.y = y;
		this.vx = 0;
		this.vy = 0;
		this.hitbox = new Rectangle((int)x,(int)y,14,30);
		life = 50;
		energy = 500;
		maxLife = 500;
		maxEnergy = 10000;
		//give("doublejump");
		
	}
	
	public Player() {
		this.x = 100;
		this.y = 60;
		this.hitbox = new Rectangle((int)x,(int)y,14,30);
		life = 50;
		energy = 500;
		maxLife = 500;
		maxEnergy = 10000;
		jumping = false;
	}
	
	public Player(double x, double y, double life, double maxLife, double energy, double maxEnergy, boolean jumping,
	boolean holdingJump, boolean canJump, boolean movingLeft, boolean movingRight, boolean hitCeiling, boolean onGround) {
		this.x = x;
		this.y = y;
		this.vx = 0;
		this.vy = 0;
		this.hitbox = new Rectangle((int)x,(int)y,14,30);
		this.life = life;
		this.energy = energy;
		this.maxLife = maxLife;
		this.maxEnergy = maxEnergy;
		this.jumping = jumping;
		this.holdingJump = holdingJump;
		this.canJump = canJump;
		this.movingLeft = movingLeft;
		this.movingRight = movingRight;
		this.hitCeiling = hitCeiling;
		this.onGround = onGround;
		//give("doublejump");

	}
	
	@Override
	void checkCollision(double deltaTime) {

		applyVelocity();
		
		int intvx = (int) (vx * deltaTime);
		//Compact so I don't get eye strain just from looking at it
		for(int i = 0; i < Math.abs(intvx); i++) {
			if(intvx > 0) {x++; hitbox.x++;}
			else if(intvx < 0) {x--; hitbox.x--;}	
				if(Main.m.collides(hitbox)) {
				//If there's a collision, move back
				if(intvx > 0) {x--; hitbox.x--;}
				else if(intvx < 0) {x++; hitbox.x++;}
				vx = 0;
			}
		}
		//Jump if the spacebar is held down and the player is on the ground.
		//This also resets the y-velocity.  Important!
		if(holdingJump && onGround && canJump) {vy = -6; jumping = true; canJump = false;}
		if(holdingJump && !onGround && jumping && jumpBoost > 0) {
			jumpBoost--;
			vy -= 0.2;
		}
		else if(!holdingJump && !onGround) {
			jumping = false;
		}

		int intvy = (int) ((vy) * deltaTime);
		for(int i = 0; i < Math.abs(intvy); i++) {
			if(intvy > 0) {y++; hitbox.y++;}
			else if(intvy < 0) {y--; hitbox.y--;}			
				if(Main.m.collides(hitbox)) {
				//If there's a collision, move back
				if(intvy >= 0) {
					y--; hitbox.y--;
					jumping = false;
					onGround = true;
					jumpBoost = boosts;
					vy = 0;
				}
				else if(intvy < 0) {y++; hitbox.y++; vy = 0;}
				}
			else if(!Main.m.collides(hitbox)) {
				onGround = false;
			}
		}
		if(Math.abs(vx) < 0.01) {
			vx = 0;
		}
		if(Math.abs(vy) < 0.01) {
			vy = 0;
		}
		
		vy += g * deltaTime;
		
		shiftHitbox();
		
		//System.out.println(deltaTime + " --- " + vx * deltaTime + " " + vy * deltaTime);
	}
	@Override
	public void draw(Graphics g) {
		//g.setColor(Color.RED);
		//g.fillRect((int) (x + Main.cam.x),(int) (y + Main.cam.y), hitbox.width, hitbox.height);
		g.setColor(new Color(0,192,40));
		g.fillRect((int) (hitbox.x + Main.cam.x),(int) (hitbox.y + Main.cam.y), hitbox.width, hitbox.height);
		hud.draw(g);
	}
	
	@Override
	public void update(double deltaTime) {
		hud.refreshInfo(Math.hypot(vx, vy));
		//Move the player
		checkCollision(deltaTime);
		
		//Reset jumping ability if on ground
		if(onGround) {
			setJumps();
		}
		
		//shiftHitbox();
		checkTerminalVelocity();
		checkResetPosition();
		
		//Change camera position to the player's position
		Main.cam.shift(this);
	}
	
	public boolean has(String s) {
		return (inv.contains(s));
	}
	
	public void give(String s) {
		inv.add(s);
	}
	
	public void readout() {
		System.out.println("------------------------------------");
		System.out.println(x + " " + y + " " + vx + " " + vy + " " + jumping);
	}
	void setJumps() {
        //Set the max amount of jumps the player can make
        jumps = 1;
        //If the player has the double jump powerup, give them two max jumps;
        if(has("doublejump")) jumps = 2;
        if(has("triplejump")) jumps = 3;
	}
	
	void checkResetPosition() {
		if(hitbox.y > 216 * 6) {hitbox.x=950;hitbox.y=520;}
	}
	
	
	
}
