import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

public class HUD extends Display {
	double velocity;
	
	public HUD(Dimension d) {
		this.d = d;
	}
	
	public HUD() {
		this.d = new Dimension(384,216);
		this.padHeight = 10;
	}
	
	public void refreshInfo(double v) {
		velocity = v;
	}
	
	@Override
	public void draw(Graphics g) {
		/*
		Player p = Main.p;
		g.setFont(new Font("Verdana", Font.PLAIN, 12));
		g.setColor(Color.RED);
		g.drawString("J " +  p.jumping + " OG " +  p.onGround + " CJ " + p.canJump + " #ENT " + Main.ent.entities.size(), 30, d.height - padHeight - 80);
		
		g.drawString("LIFE " + p.life, 30, d.height - padHeight);
		g.drawString("ENERGY " +  p.energy, 90, d.height - padHeight);
		g.drawString("VELOCITY " +  Math.round(velocity * 100), 180, d.height - padHeight);
		g.drawString("VX/VY " +  Math.round(p.vx * 10) + "/" +  Math.round(p.vy), 180, d.height - padHeight - 20);
		g.drawString("J " +  p.jumping + " OG " +  p.onGround + " #jmps " + p.jumps, 30, d.height - padHeight - 20);
		//Draw a vector component triangle
		g.drawLine(320, d.height - padHeight - 10, (int) (320 + Main.p.vx), d.height - padHeight - 10);
		g.drawLine((int) (320 + Main.p.vx), d.height - padHeight - 10, (int) (320 + Main.p.vx), (int) (d.height - padHeight + Main.p.vy - 10));
		g.drawLine((int) (320 + Main.p.vx), (int) (d.height - padHeight + Main.p.vy - 10), 320, d.height - padHeight - 10);
		*/
	}
}
