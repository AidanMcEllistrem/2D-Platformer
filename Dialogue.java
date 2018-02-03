import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Dialogue extends Display {
	
	ArrayList<String> texts = new ArrayList<String>();
	String text = "Welcome to the tutorial level!  I hope you enjoy your stay.",
			//The speaker's name shows up below the text.  
			speaker = "Bulk Bogan";
	int place = 0, line = 0, letter = 0, 
			//How large the portraits are.  Some NPCs have portraits, some don't.
			div = 60;
	
	int margins = 30;
	//The box is where all the text is drawn.
	//Rather than attempt to draw it all at once, it saves it to an image.  
	//I thought it was clever, but whatever.
	BufferedImage box, portrait;
	//Decides when to add a new letter to the message
	double messageSpeed, time = -9, messageLength = 120;
	
	public Dialogue() {
		messageSpeed = 2;
		position = new Point(20,140);
		d = new Dimension(344,60);
		box = new BufferedImage(d.width,d.height,BufferedImage.TYPE_INT_RGB);
		portrait = new BufferedImage(60,60,BufferedImage.TYPE_INT_ARGB);
	}
	
	public Dialogue(String s) {
		text = s;
		messageSpeed = 2;
		position = new Point(20,140);
		d = new Dimension(344,60);
		box = new BufferedImage(d.width,d.height,BufferedImage.TYPE_INT_RGB);
		portrait = new BufferedImage(60,60,BufferedImage.TYPE_INT_ARGB);
	}
	
	@Override
	public void update(double deltaTime) {
		time += deltaTime;
		//Increment one if the time is greater than or equal to the message speed
		if(time >= messageSpeed) {
			if(place < text.length()) time = 0;
			place++;
			letter++;
		}
		if(place > margins + (line * margins)) {
			line++;
			letter = 0;
		}
	}
	
	@Override
	public void draw(Graphics g) {
		if(time < messageLength) {
		g.setColor(Color.black);
		g.fillRect(position.x - 2,position.y - 2,d.width + 4,d.height + 4);
		g.setColor(Color.white);
		g.drawRect(position.x - 1,position.y - 1,d.width + 1, d.height + 1);
		//Draw the drawing methods
		drawText();
		drawImage();
		drawSpeaker();
		g.drawImage(box, position.x, position.y, null);
		} else {
		//Remove it from the Entity Holder
		Main.ent.remove(this);
		}
	}
	
	private void drawImage() {
		Graphics bg = box.getGraphics();
		try {
			//Get the portrait from the images folder
		    portrait = ImageIO.read(new File(Main.resources + "Images/" + "nicememe2.png"));
		} catch (IOException e) {
		}
		bg.drawImage(portrait, 0, 0, 60, 60, 0, 0, portrait.getWidth(), portrait.getHeight(), null);
	}
	
	private void drawSpeaker() {
		Graphics bg = box.getGraphics();
		bg.setFont(new Font("Verdana", Font.BOLD, 8));
		bg.drawString(speaker, 2 + div, div - 4);
	}
	
	private void drawText() {
		Graphics bg = box.getGraphics();
		//Set the font to an aesthetic, monospace font
		//Be sure to use Courier instead of Courier New, the latter is owned by a company, while the former is not
		bg.setFont(new Font("Courier", Font.PLAIN, 12));
		bg.setColor(Color.WHITE);
		if(text.length() > place)
		bg.drawString(Character.toString(text.charAt(place)), (letter * 8) + 2 + div,14 + (line * 16));
	}
	
}
