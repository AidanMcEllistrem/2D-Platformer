import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * Also a second attempt at a tile editor, this one is less hex-editory and more graphical.
 * Since I dislike how verbose and needlessly complicated layout are in Swing, I avoided them as much as I could.
 * However, if and when I need one, no big deal, I'll just restrict them to pop-up editors.
 * @author Aidan
 *
 */

public class Main extends JFrame{
	//The containers where the content goes
	Container contentPane;
	static Container filePane;
	static Point mouseCoords = new Point(0,0);
	static DrawPanel graphicInterface = new DrawPanel();
	public static TilePen pen = new TilePen();
	static Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	static JFileChooser fc = new JFileChooser();
	static int returnval;
	static ArrayList<Dialogue> dialogues = new ArrayList<Dialogue>();
	
	public static void main(String[] args) {
		Main m = new Main();
	}
	
	public Main() {

		//Add tiles to pen
		pen.initialize();
		//Set the size of the GUI
		createLayout(new Dimension(d.width,d.height));
		setInfo();
		addGraphics();
		setIcon();
		finishAndSetVisible();
		add(graphicInterface);
		setDirectory();
		addDialogues();
		//Add keyboard support
		EventHandler eh = new EventHandler();
		addKeyListener(eh);
		
		//Add mousewheel support
		MouseWheelHandler mh = new MouseWheelHandler();
		addMouseWheelListener(mh);
		holdDownMouse();
		
	}
	
	void addDialogues() {
		//These are just for testing purposes
		dialogues.add(new Dialogue("It's lonely all the way out    here...", 14, 12));
		dialogues.add(new Dialogue("That wasn't too hard!", 40, 13));
		dialogues.add(new Dialogue("Enjoy the view!",97,17));
		dialogues.add(new Dialogue("Down this hole is a little     passageway.  To the right are some jumping courses.",69,34));
		dialogues.add(new Dialogue("What could be to the left?",40,61));
		dialogues.add(new Dialogue("You should be impressed with   yourself!  I didn't expect anyone to get here!",15,65));
	}
	
	private void setIcon() {
		String filepath;
		try {
			filepath = new File(".").getCanonicalPath();
			ImageIcon img = new ImageIcon(filepath + "/Resources/Images/tlicon.png" );
			setIconImage(img.getImage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	static void load() {
		try {
			graphicInterface.readFile(getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setDirectory() {
		String filepath = "";
		try {
			filepath = new File(".").getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fc = new JFileChooser(filepath);
	}
	
	static File getFile() {
		
		returnval = fc.showOpenDialog(filePane);
		if (returnval == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            
            //This is where a real application would open the file.
            System.out.println("Opening: " + file.getName() + ".");
            return file;
        } else {
            System.out.println("Open command cancelled by user.");
        }
			return null;
	}
	
	static File saveFile() {
		returnval = fc.showSaveDialog(filePane);
		if (returnval == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            
            //This method does overwrite files.  Be careful!
            try {
				writeToFile(file);
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            System.out.println("Opening: " + file.getName() + ".");
            return file;
        } else {
            System.out.println("Open command cancelled by user.");
        }
			return null;
	}
	
	static void writeToFile(File f) throws IOException, ClassNotFoundException {
		if(f != null) {
			//Read the file from the filepath
			FileOutputStream saveFile = new FileOutputStream(f.getPath());
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			//Write dimensions
			save.writeInt(graphicInterface.screens.width);
			save.writeInt(graphicInterface.screens.height);
			short[][] tiles = graphicInterface.tilelist;
			for(int x = 0; x < tiles.length; x++) {
				for(int y = 0; y < tiles[0].length; y++) {
					save.writeShort(tiles[x][y]);
				}
			}
			save.writeInt(6);
			System.out.println(dialogues.size());
			for(int i = 0; i < 6; i++) {
				save.writeUTF(dialogues.get(i).dialogue);
				System.out.println("Wrote " + dialogues.get(i).dialogue);
				save.writeInt(dialogues.get(i).x);
				save.writeInt(dialogues.get(i).y);
			}
			save.close();
		}
		System.out.println("File written.");
		Main.graphicInterface.repaint();
	}
	
	//Size the JFrame
	void createLayout(Dimension d) {
		contentPane = getContentPane();
      	contentPane.setPreferredSize(new Dimension(d.width, d.height));
	}
	
	void holdDownMouse() {
		while(true) {
		//Get mouse position
		Point p = MouseInfo.getPointerInfo().getLocation();
		
		p.x -= contentPane.getLocationOnScreen().x - 2;
		p.y -= contentPane.getLocationOnScreen().y - 24;
		//System.out.println(pen.context);
		requestDraw(p,pen.context);
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			}
		
	}
	
	//Set name, icon, etc.
	void setInfo() {
		setTitle("Tile Leeks");
		getContentPane().setBackground(Color.BLACK);
	}
	
	//Make the frame visible
	void finishAndSetVisible() {
		//Some helpful settings that make sure the program exits when you press the X and prevents resizing.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setResizable(false);
		pack();
		//Center frame
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
      	requestFocus();
      	setVisible(true);
	}
	
	void addGraphics() {
		//Setup the mouse handler
		MouseHandler mh = new MouseHandler();
		addMouseListener(mh);
		//mh.updateMouse();
		graphicInterface.setDimensions(new Dimension(1,1));
		
	}
	
	public static void requestDraw(Point p, short n) {
		//These conditions control what tool is used
		//The paintbucket is incredibly finnicky, so edit these with care!
		if(pen.paintbucket && pen.held && !pen.erase && DrawPanel.goPaint) {
		graphicInterface.paintbucket(p, n);
		}
		if(pen.held) {
		graphicInterface.tile(p, n);
		}
		if(pen.erase) {
		graphicInterface.tile(p,(short)0);	
		}
		graphicInterface.repaint();
	}
}
