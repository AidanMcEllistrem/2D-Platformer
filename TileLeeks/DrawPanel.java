import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.JPanel;

//A modified JPanel
public class DrawPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5644104981862898288L;
	
	//All the tiles in a level
	public short[][] tilelist = new short[0][0];
	public Dimension screens = new Dimension(1,1);
	
	//Undo data
	public short[][] tilelistBackup = new short[0][0];
	public Dimension screensBackup = new Dimension(1,1);
	public String alert = "";
	
	//Booleans
	public boolean displayHelp = false;
	public boolean displayAlert = false;
	public boolean displayData = false;
	
	public Camera c = new Camera();
	//Aspect ratio
	double ar = 1;

	
	
	public void readFile(File f) throws IOException {
		if(f != null) {
			//Read the file from the filepath
			FileInputStream saveFile = new FileInputStream(f.getPath());
			ObjectInputStream restore = new ObjectInputStream(saveFile);
			//Write the array back
			Dimension d = new Dimension(restore.readInt(),restore.readInt());
			Main.graphicInterface.screens = d;
			Main.graphicInterface.setDimensions(d);
			for(int x = 0; x < Main.graphicInterface.tilelist.length; x++) {
				for(int y = 0; y < Main.graphicInterface.tilelist[0].length; y++) {
					Main.graphicInterface.tilelist[x][y] = restore.readShort();
				}
			}
			int length = restore.readInt();
			System.out.println(length);
			for(int i = 0; i < length; i++) {
				System.out.println(i);
				Dialogue str = new Dialogue(restore.readUTF(),restore.readInt(),restore.readInt());
				System.out.println("Read " + str.dialogue);
				Main.dialogues.add(str);
			}
			
			//This, as I learned, is extremely important.  You will get an EOF exception if this is not there.
			restore.close();
			Main.graphicInterface.repaint();
		}
	}
	
	public void setDimensions(Dimension d) {
		//Set the tilelist in accordance to the number of screens the map contains
		
		short[][] oldtiles = tilelist;
		//Now a sane person would ask why there's a modulus there. It's so an extra layer doesn't appear beneath the map.L
		tilelist = new short[d.width * 24][(int) (d.height * 13.5) + 1 - ((d.height + 1) % 2)];
		for(int x = 0; x < oldtiles.length; x++) {
			for(int y = 0; y < oldtiles[0].length; y++) {
				try {
				if(x < tilelist.length && y < tilelist[0].length) {
				tilelist[x][y] = oldtiles[x][y];
				}
				} catch(Exception e) {
					
				}
			}
		}
	}
	
	public void tile(Point p, short n) {
		if(Main.pen.held) {
		//Change a tile relating to the mouse coordinates and catch an out of bounds exception
		try {
		//Set the tile to the number of the pen
			Main.mouseCoords = new Point(9,9);
		tilelist[(int)((p.x - c.position.x - 2) / (16 * ar))][(int)((p.y - c.position.y - 24) / (16 * ar))] = n;
		} catch(ArrayIndexOutOfBoundsException e) {
			//No printing or error messages needed, it's solely to stop the console from printing an error
		}
		}
	}
	
	ArrayList<Searcher> search_array = new ArrayList<Searcher>();
	ArrayList<Searcher> search_buffer = new ArrayList<Searcher>();
	
	public void paintbucket(Point p, short n) {
		//I'm not proud of this
		destroy = false;
		goPaint = false;
		int xtile = (int)((p.x - c.position.x - 2) / (16 * ar));
		int ytile = (int)((p.y - c.position.y - 24) / (16 * ar));
		//Add a single searcher at the mouse location
		System.out.println("Context: " + tilelist[xtile][ytile] );
		//Check if the tilelist and the pen are the same thing to make sure that redundancy is avoided
		if(tilelist[xtile][ytile] != n) {
		search_array.add(new Searcher(xtile,ytile,n,tilelist[xtile][ytile]));
		}
		//This is the loop that implements the paint fill
		while(!destroy) {
			destroy = searchArray();
			System.out.println(destroy);
			if(destroy) break;
		}
		//Cleanup
		search_array.clear();
		search_buffer.clear();
		System.out.println("Destroyed Search Array " + search_array.size() + " " + search_buffer.size());
		//Toggle paint off
		Main.pen.paintbucket = false;
	}
	
	private void appendBuffer() {
		search_array.addAll(search_buffer);
		search_buffer.clear();
	}
	
	private boolean searchArray() {
		for(Searcher s : search_array) {
			//Search each tile in each cardinal direction
			s.search();
		}
		//Destroy this paint bucket array if no new connections are found
		//Add the buffer onto the real search array
		if(search_buffer.size() < 1) return true;
		System.out.println(search_buffer.size());
		appendBuffer();
		Main.graphicInterface.repaint();
		return false;
	}
	static boolean destroy = false, goPaint = true;
	
	//A class designed for filling in arrays
	private class Searcher {
		int x,y;
		short n, ctx;
		
		private Searcher(int x, int y, short n, short ctx) {
			this.x = x;
			this.y = y;
			this.n = n;
			this.ctx = ctx;
		}
		
		private void search() {
			//Go through the array and look in four directions.  Set the tile to the context specified if there is an empty space of of the same color.
			Point[] pointarray = {new Point(1,0),new Point(-1,0),new Point(0,1),new Point(0,-1)};
			for(Point p : pointarray) {
				qualify(p.x,p.y);
			}
		}
		
		private void qualify(int a, int b) {
			try {
				if(tilelist[x + a][y + b] == ctx) {
					set(a,b);
				}
			} catch(ArrayIndexOutOfBoundsException e) {
				
			}
		}
		
		private void set(int a, int b) {
			try {
			tilelist[x + a][y + b] = this.n;
			//System.out.println("New searcher at " + "x - " + a);
			search_buffer.add(new Searcher(x + a, y + b, this.n, this.ctx));
			//If there was at least one new tile found, do not destroy the array
			} catch (ArrayIndexOutOfBoundsException e) {
				
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		//Set default font
		g2d.setFont(new Font("Verdana",12,12));
		//Black background
		g2d.setColor(new Color(0,0,0));
		g2d.fillRect(0, 0,(int) Main.d.getWidth(),(int) Main.d.getHeight());
		//Paint the guidance grid to show the screen resolution
		paintTiles(g);
		for(int x = 0; x < screens.width; x++) {
			for(int y = 0; y < screens.height; y++) {
				
				g2d.setColor(Color.WHITE);
				//Draw boxes that are the width of one screen.  One screen = 24x13.5 tiles.
				g2d.drawRect((int)((x * 384 * ar) + c.position.x), (int)((y * 216 * ar) + c.position.y),(int) (384 * ar), (int)(216 * ar));
			}
		}
		
		paintPenContext(g);
		
		//Display the help window if help is needed
			if(displayHelp) {
			displayHelp(g);
			}
			if(displayAlert) {
			g2d.drawString(alert, 40, Main.d.height - 200);
			g2d.drawString("-> Enter - OK", 40, Main.d.height - 180);
			}
			if(displayData) {
			displayData(g);
			}
		g2d.drawString("X " + Main.mouseCoords.x / (int)(16 * ar) + "  Y " + Main.mouseCoords.y / (int)(16 * ar), 40, Main.d.height - 100);
	}
	
	public void changeAlert(String alert) {
		displayAlert = true;
		this.alert = alert;
	}
	
	public void paintTiles(Graphics g) {
		//Paint the 2-Part Hexidecimal numbers into visible tiles
		Graphics2D g2d = (Graphics2D) g;
		for(int x = 0; x < tilelist.length; x++) {
			for(int y = 0; y < tilelist[0].length; y++) {
				try {
				//Draw simple colors
				if(Main.pen.tiles[tilelist[x][y]].type == "Color") {
				g2d.setColor(Main.pen.tiles[tilelist[x][y]].color);
				g2d.fillRect((int)((x * 16 * ar) + c.position.x),(int) ((y * 16 * ar) + c.position.y),(int) (16 * ar),(int) (16 * ar));
				} else if(Main.pen.tiles[tilelist[x][y]].type == "Glyph") {
					
				//Avert your eyes, children.
				GlyphCollection glyphs = new GlyphCollection();
				if(Main.pen.tiles[tilelist[x][y]].glyph == "Ladder") {
					Glyph[] ladder = {new Glyph(0,8,15,8),new Glyph(0,0,0,16),new Glyph(15,0,15,16)};
					glyphs.setGlyphs(x,y,ladder);
				}
				if(Main.pen.tiles[tilelist[x][y]].glyph == "Sign") {
					Glyph[] sign = {new Glyph(new Point(0,0),15,8),new Glyph(7,8,7,16),new Glyph(8,8,8,16)};
					glyphs.setGlyphs(x,y,sign);
				}
				if(Main.pen.tiles[tilelist[x][y]].glyph == "Rest") {
					Glyph[] rest = {new Glyph(0,0,11,7),new Glyph(11,7,4,7),new Glyph(4,7,12,15)};
					glyphs.setGlyphs(x,y,rest);
				}
				//Draw glyph
				glyphs.drawGlyph(g2d, ar);
				}
				} catch(Exception e) {
					
				}
			}
		}
	}
	
	private class Glyph {
		int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
		int w = 0,h = 0;
		Glyph(Point p, int w, int h) {
			//Create startpoint and endpoint
			this.x1 = p.x;
			this.y1 = p.y;
			this.w = w;
			this.h = h;
		}
		Glyph(int x1, int y1, int x2, int y2) {
			//Create startpoint and endpoint
			this.x1 = x1; this.y1 = y1;
			this.x2 = x2; this.y2 = y2;
		}
		
	}
	
	private class GlyphCollection {
		Color color = Color.WHITE;
		int x = 0, y = 0;
		//An array of glyphs
		Glyph[] glyphs = {new Glyph(0,0,0,0)};
		GlyphCollection() {
			
		}
		void setGlyphs(int x, int y, Glyph[] glyphs) {
			this.x = x;
			this.y = y;
			this.glyphs = glyphs;
		}
		/**
		 * Draw all glyphs on the screen
		 * @param g
		 * @param scale
		 */
		private void drawGlyph(Graphics2D g, double scale) {
			//Set color to the color of the glyph collection
			g.setColor(color);
			for(Glyph gl : glyphs) {
			if(gl.w == 0 || gl.h == 0) {
			//Draw a line glyph
			g.drawLine((int)((gl.x1 + (x * 16)) * scale) + c.position.x,
			(int)((gl.y1 + (y * 16)) * scale) + c.position.y,(int)((gl.x2 + (x * 16)) * scale) + c.position.x,
			(int)((gl.y2 + (y * 16)) * scale) + c.position.y);
			} else {
			//Draw a rectangle glyph if the width or hight is set
			g.drawRect((int)((gl.x1 + (x * 16)) * scale) + c.position.x,
		(int)((gl.y1 + (y * 16)) * scale) + c.position.y,(int) (gl.w * scale),(int) (gl.h * scale));
			}
			}
		}
	}
	
	public void paintPenContext(Graphics g) {
		//Get the spacing of the screen from main to place the bottom right text
		int spacing_x = Main.d.width - 200;
		int spacing_y = Main.d.height - 200;
		g.setColor(Color.WHITE);
		if(Main.pen.paintbucket) {
		g.drawString("PAINTBUCKET TOOL", spacing_x, spacing_y);	
		}
		if(Main.pen.erase) {
		g.drawString("ERASE TOOL", spacing_x, spacing_y);
		}
		else if(Main.pen.held && !Main.pen.paintbucket) {
		g.drawString("PEN TOOL", spacing_x, spacing_y);	
		}
		//Draw pen context
		g.drawString("Current Tile: " + Main.pen.context + " Hex (" + Integer.toHexString(Main.pen.context) + ")", spacing_x, spacing_y + 20);
		if(!displayHelp) {
		g.drawString("Press H for help.", spacing_x, spacing_y + 40);
		} else {
		g.drawString("Press H to close help.", spacing_x, spacing_y + 40);
		}
		g.drawString("Press M for the data manager.", spacing_x, spacing_y + 60);
		g.drawString("Zoom x" + ar, spacing_x, spacing_y + 80);
	}
	
	void displayHelp(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0,0,500,500);
		g.setFont(new Font("Verdana",Font.PLAIN,12));
		//All of the commands are written here.
		String[] keystrokes = {"Shift E - Erase All","Shift F - Fill All","Shift I - Invert All","C - Reset Camera",
		"P/Mouse Wheel Down - Paint Bucket Toggle On/Off",
		"LMB - Draw","RMB - Erase","Arrow Keys - Move Camera","Numpad 6 - Add Horizontal Screen","Numpad 2 - Add Vertical Screen",
		"Numpad 4 - Remove Horizontal Screen (Erases all screens removed)","Numpad 8 - Remove Vertical Screen (Erases all screens removed)",
		"Numpad 7 - Zoom In","Numpad 9 - Zoom Out","Mouse Wheel Up/Down - Change pen context","Shift S - Save Map","Shift L - Load Map",
		"Shift N - New Map (Creates new file)","F4 - Quit"};
		g.setColor(Color.BLACK);
		g.drawRect(2, 2, 496, 496);
		//g.drawString("Created by Aidan McEllistrem, 2017.",40,40);
		for(int i = 0; i < keystrokes.length; i++) {
			g.drawString(keystrokes[i],20,60 + (20 * i));
		}
	}
	
	void displayData(Graphics g) {
		//Number used for keeping control of where text is drawn
		int spacing = 20;
		int x = Main.d.width - 500 + 10;

		g.setColor(Color.WHITE);
		g.fillRect(Main.d.width - 500,0,500,1000);
		
		drawButton(new Point(Main.d.width - 100,2),new Dimension(100,40),"Edit!",g);
		g.setFont(new Font("Verdana",Font.BOLD,20));
		g.setColor(Color.BLACK);
		g.drawString("Data Manager", x, spacing);
		g.setFont(new Font("Verdana",Font.BOLD,16));
		spacing += 40;
		g.drawString("Dialogues", x, spacing);
		g.setFont(new Font("Verdana",Font.PLAIN,10));
		spacing += 12;
		//Search through the dialouges and print them off
		for(Dialogue d : Main.dialogues) {
			spacing += 12;
			g.drawString(d.x + "," + d.y + " " + d.dialogue, x, spacing);
		}
		
		//Draw a nice little border
		g.drawRect(Main.d.width - 498, 2, 496, 996);
	}
	
	void drawButton(Point p, Dimension d, String s, Graphics g) {
		g.setColor(Color.black);
		g.setFont(new Font(Font.MONOSPACED,Font.BOLD,24));
		g.drawRect(p.x, p.y, d.width, d.height);
		g.drawString(s, p.x + 10, p.y + (d.height / 2));
	}
}
