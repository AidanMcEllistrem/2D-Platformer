import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class EventHandler implements KeyListener {
	boolean shift = false;
	@Override
    public void keyPressed(KeyEvent e) {
		DrawPanel p = Main.graphicInterface;
        int key = e.getKeyCode();
        
        
        if(key == KeyEvent.VK_F4) {
        	//Quit the program
        	System.exit(0);
        }
        
        if(key == KeyEvent.VK_INSERT && shift) {
        }
        
        if(key == KeyEvent.VK_DELETE && shift) {
        	//Make the entire grid erased
        	for(int x = 0; x < p.tilelist.length; x++) {
    			for(int y = 0; y < p.tilelist[0].length; y++) {
    				p.tilelist[x][y] = 0;
    			}
    		}
        	//Show changes
        	
        }
        
        //Methods to shift the camera
        //Note: These will shift the camera one tile length RELATIVE to the camera's zoom.
        if(key == KeyEvent.VK_RIGHT) {
        	p.c.shift(-16, 0);
        }
        if(key == KeyEvent.VK_LEFT) {
        	p.c.shift(16, 0);
        }
        if(key == KeyEvent.VK_UP) {
        	p.c.shift(0, 16);
        }
        if(key == KeyEvent.VK_DOWN) {
        	p.c.shift(0, -16);
        }
        
        //Methods to add more screens
        if(key == KeyEvent.VK_NUMPAD6) {
        	p.screens.width++;
        	p.setDimensions(p.screens);
        }
        if(key == KeyEvent.VK_NUMPAD4) {
        	if(p.screens.width > 0) {
        	p.screens.width--;
        	p.setDimensions(p.screens);
        	}
        }
        if(key == KeyEvent.VK_NUMPAD2) {
        	p.screens.height++;
        	p.setDimensions(p.screens);
        }
        if(key == KeyEvent.VK_NUMPAD8) {
        	if(p.screens.height > 0) {
        	p.screens.height--;
        	p.setDimensions(p.screens);
        	}
        }
        
        //Methods to change the screen width and height
        if(key == KeyEvent.VK_NUMPAD7) {
        	if(p.ar < 16) {
        	p.ar *= 2;
        	}
        }
        if(key == KeyEvent.VK_NUMPAD9) { 
        	if(p.ar > 0.0625) {
        	p.ar /= 2;
        	}
        }
        
        //Display help
        if(key == KeyEvent.VK_H) {
        	p.displayHelp = !p.displayHelp;
        }
        
        //Resets camera
        if(key == KeyEvent.VK_C) {
        	p.c.set(0,0);
        	p.ar = 1;
        }
        
        //Pulls up the data viewer
        if(key == KeyEvent.VK_M) {
        	p.displayData = !p.displayData;
        }
        
        //Pulls up a new GUI for the data
        if(key == KeyEvent.VK_E && p.displayData) {
        	//This is stuff for next version
        	//DataManager d = new DataManager();
        }
        
        //Sets paintbucket
        if(key == KeyEvent.VK_P) {
        	Main.pen.paintbucket = !Main.pen.paintbucket;
        }
        
        if(key == KeyEvent.VK_SHIFT) {
        	//Turn shift on
        	shift = true;
        }
        
        //New File + Load + Save
        if(key == KeyEvent.VK_N && shift) {
        	p.changeAlert("New file is a mess right now, create a blank save file from the template if you would like a new level to work on.");
        	/*
        	try {

      	      File file = new File(new File(".").getCanonicalPath() + "/Resources/Levels/Level.sav");

      	      if (file.createNewFile()){
      	        System.out.println(new File(".").getCanonicalPath() + "/Resources/Levels/Level.sav" + " has been created.");
      	        p.changeAlert("File created.");
      	      }else{
      	        System.out.println(new File(".").getCanonicalPath() + "/Resources/Levels/Level.sav" + " already exists.");
      	        p.changeAlert("File was already created.");
      	      }

          	} catch (IOException ex) {
      	      p.changeAlert("Couldn't create file.");
          	} finally {
          	
          	}
          	*/
        }
        
        if(key == KeyEvent.VK_L && shift) {
        	p.changeAlert("Attempting to load file...");
        	Main.load();
        }
        if(key == KeyEvent.VK_S && shift) {
        	p.changeAlert("Attempting to save file...");
        	Main.saveFile();
        }
        
        if(key == KeyEvent.VK_ENTER) {
        	p.displayAlert = false;
        }
        //This really should have just been stuck at the end
        p.repaint();
        
    }
 
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_SHIFT) {
        	shift = false;
        }
       
    }
    @Override
    public void keyTyped(KeyEvent e) {
        int key = e.getKeyCode();
 
    }
}
