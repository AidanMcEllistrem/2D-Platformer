import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.script.ScriptException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

/**
 * This game is the second attempt - the first was poorly written, and did not take advantage of fullscreen resources.
 * A rewrite was necessary not only for me, but for the sake of all the programmers on this team.
 * Hopefully this will get done by the end of my senior year *knock on wood*.
 * @author Aidan
 *
 */
public class Main {
	//Number of buffers, which are extra screens that the computer can display when there is rendering in one frame.
	public final static int buffNum = 2;
	public static Frame mainFrame;
	
	 //Event Handler and Mouse Handler
	public static EventHandler eh = new EventHandler();
	
	//Calculation loop for the game
	public final static int framerate = 60;
	public final static long billion = 1000000000;
	public final static long million = 1000000;
	public final static long optimal_time = billion / framerate;
	public final static AudioPlayer audio = new AudioPlayer(); 
	public static Dimension screenres = new Dimension(), realres = new Dimension(384,216);
	static long lastLoopTime = System.nanoTime();
	static long lastFpsTime;
	static long fps;
	
	//GUI variables
	public static int runtimeoptions = 0;
	
	//Filepaths and directory fun
	public static String filepath = "";
	public static String resources = "";
	public static int searches = 0;
	public static boolean foundfolder = false;
	public static boolean newGame = false;
	public static ArrayList<SignDialogue> dialogues = new ArrayList<SignDialogue>();
	
	//Single instance entities
	public static Player p = new Player(950,520);
	public static Camera cam = new Camera(0,0);
	public static Map m = new Map();
	public static FileReader lr = new FileReader();
	public static EntityHolder ent = new EntityHolder();
	public static long time = 0L;
	
	public static void main(String[] args) {
		//Call to the setup GUI
		//SetupGUI.use();
		
		//Set the graphical mode to be the default screen, but swap to the game's resolution
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice();
		
		//Set up the container, use tricks to improve performance
		GraphicsConfiguration gc = device.getDefaultConfiguration();
        mainFrame = new Frame(gc);
        mainFrame.addKeyListener(eh);
        mainFrame.setUndecorated(true);
        mainFrame.setIgnoreRepaint(true);
        //Enter fullscreen mode
        device.setFullScreenWindow(mainFrame);
        mainFrame.createBufferStrategy(buffNum);
        //BufferStrategy bufferStrategy = mainFrame.getBufferStrategy();
        
        boolean autoDisp = true;
        if(autoDisp) {
        	runtimeoptions = 5;
        }
        //Set the dimensions of the screen resolution to be the set display mode
        screenres = new Dimension(device.getDisplayMode().getWidth(), device.getDisplayMode().getHeight());
        
        DisplayMode d = device.getDisplayMode();
        
        
        //Make sure you turn this to FALSE when you release a build!!!
        boolean testDir = true;
        
        
        checkTestDirectorySet(testDir);
        //Set the current menu to the main menu
       	Scene.setMenu(Menu.mainMenu);
       	try {
			FileReader.readSave(new File(Main.resources + "Saves/" + "save1.sav"),1);
		} catch (IOException e) {}
       	ent.add(p);
        //Start the game loop
        update(d);
        
	}

	
	public static void setTimeForSaves(String time, int save) throws IOException {
        Menu.fileMenu.changeElement(save, "Save " + save + " - " + time + " 0%");
       }
	
	private static void draw(DisplayMode d) {
		//Get the buffer to draw on
		BufferStrategy bufferStrategy = mainFrame.getBufferStrategy();
		Graphics g = bufferStrategy.getDrawGraphics();
		//Setup the Graphics 2D object
		Graphics2D g2d = (Graphics2D) g;
		BufferedImage gameGraphics = new BufferedImage(10,10,BufferedImage.TYPE_INT_ARGB);
		
		//Determine which frame the game is on, and draw the according context.
		switch (Scene.gameFrame) {
			case 0:
				gameGraphics = drawGameGraphics(); break;
			case 1:
				Scene.enterMenu();
				gameGraphics = drawLevelSelect(); break;
		}
		//Resize the low-res game onto the (hopefully) high-res monitor
		drawImageOnScreen(gameGraphics,g2d);
		//System.out.println(gameGraphics.getWidth() + " " + gameGraphics.getHeight());
        
        FPSTracker.showGraph(new Point(d.getWidth() - 100, d.getHeight() - 100), 60, fpsdata, g, 60);
        
        //Flip over the new buffer
        bufferStrategy.show();
        //Wipe frame
        clearframe(g,d);
        
        //Clear up the things we dont need anymore
        g.dispose();
        g2d.dispose();
        
	}
	
	static void drawImageOnScreen(BufferedImage gameGraphics, Graphics2D g2d) {
		g2d.drawImage(gameGraphics, 0, 0, screenres.width, screenres.height, 0, 0, gameGraphics.getWidth(), gameGraphics.getHeight(), null);
	}
	
	static void showRes(Graphics2D g2d, DisplayMode d) {
		g2d.setColor(Color.RED);
        g2d.drawRect(0, 0, d.getWidth(), d.getHeight());
        g2d.setFont(new Font("Serif", Font.BOLD, 20));
        g2d.drawString(d.getWidth() + "x" + d.getHeight(), 10, 40);
	}
	
	private static BufferedImage drawLevelSelect() {
		//Create a new image for the screen display
		BufferedImage img = new BufferedImage(384, 216, BufferedImage.TYPE_INT_ARGB);
		//Make a context to draw graphics onto
		Graphics g = img.getGraphics();
		
		//Get the main menu from the current scene and draw it
		Scene.currentMenu.draw(g);
		return img;
	}
	
	private static BufferedImage drawGameGraphics() {
		
		//Create a new image for the screen display
		BufferedImage img = new BufferedImage(384, 216, BufferedImage.TYPE_INT_ARGB);
		//Make a context to draw graphics onto
		Graphics g = img.getGraphics();
		if(foundfolder) {
			try {
				m.draw(g); 
				if(!played) {
				File pathToFile = new File(resources + "Music/" + "Iridescense.wav");
				playSound(pathToFile.getPath());
				}
			} catch (Exception e) {
				System.err.println("Something went wrong with playing audio.");
			}
		}
		g.setColor(Color.BLACK);
		//g.setFont(new Font("Verdana", Font.PLAIN,8));
		//Draw pixel lines
		//bufferedResolutionTest(g,img);
		
		//Draw all entities
        ent.draw(g);
		
		g.setFont(new Font("Verdana", Font.PLAIN,12));
		return img;
	}
	
	/**
	 * Shows the width and height of a buffered image.
	 */
	private static void bufferedResolutionTest(Graphics g, BufferedImage img) {
		//Draw x marks to show every 32nd pixel
		for(int x = 0; x < img.getWidth(); x += 32) {
			g.drawLine(x, 0, x, 5);
			g.drawString(x + "px", x, 8);
		}
		//Same with y
		for(int y = 0; y < img.getHeight(); y += 32) {
			g.drawLine(0, y, 5, y);
			g.drawString(y + "px", 8, y);
		}
	}
	
	public static void clearframe(Graphics g, DisplayMode d) {
		//Draw over the old frame to make sure graphics are cleared up
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, d.getWidth(), d.getHeight());
	}
	
	//An arraylist to keep track of the frames per second
	static ArrayList<Integer> fpsdata = new ArrayList<Integer>();
	
	/**
	 * Uses a variable timestep to update the game's logic and drawing
	 * @param d
	 */
	public static void update(DisplayMode d) {
	Thread calc_loop = new Thread() {
		int frames = 0;
		boolean drawframe = true;
		public void run()
        {	
            while(true)
            {
            	//Take the current time in nanoseconds
            	long now = System.nanoTime();
            	//Find how long it took to make one update
                long updateLength = now - lastLoopTime;
                lastLoopTime = now;
                //Delta is the factor all movement related variables should be multiplied by
                double delta = (updateLength / ((double)optimal_time)) * (60.0/framerate);
                
                //Update the frames
                lastFpsTime += updateLength;
                time += updateLength;
                fps++;
                
                if(Scene.gameFrame == Scene.GAME_SCENE) {
                ent.update(delta);
                }
                //Draw the map
                draw(d);
                
                if(lastFpsTime >= billion) {
                	frames = (int) fps;
                	//Add a point to the FPS data collection
                	fpsdata.add((int) fps);
                	//Shave off the bottom data to make room for new data if needed
                	if(fpsdata.size() > 10) {
                		fpsdata.remove(0);
                	}
                	System.out.println("Frames Per Second: " + fps + " Data size: " + fpsdata.size());
                	lastFpsTime = 0;
                	fps = 0;
                	
                }
                
                //Wait here
                try {Thread.sleep(Math.abs( (lastLoopTime-System.nanoTime() + optimal_time)/million));}
                catch(InterruptedException e){
                	e.printStackTrace();
                }
            }
        }
	};
	calc_loop.start();
	}
	
	static boolean played = false;
	public static synchronized void playSound(final String path) {
		  Thread soundloop = new Thread(new Runnable() {
		  // The wrapper thread is unnecessary, unless it blocks on the
		  // Clip finishing; see comments.
		    public void run() {
		      try {
		        audio.play(path);
		    } catch(Exception e) {
		    	e.printStackTrace();
		    }
		    }
		  });
		  if(!played) {
			  soundloop.start();
			  played = true;
		  }
	}
	static void checkTestDirectorySet(boolean t) {
		//Find directory or get the file from the dev's computer for testing
		if(!t) {
			try {
				Main.filepath = new File(".").getCanonicalPath();
				Main.resources = filepath + "/Resources/";
				//Read map
				m.map = lr.readFileToLevel(new File(resources + "Levels/" + "Level2.sav"));
				foundfolder = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        } else {
	        	Main.filepath = "C:/Users/Aidan/Desktop/Nuclear Mantra/TestExecutable";
				Main.resources = filepath + "/Resources/";
				//Read map
				try {
					m.map = lr.readFileToLevel(new File(resources + "Levels/" + "Level2.sav"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				foundfolder = true;
	        }
	}
	
	
}
