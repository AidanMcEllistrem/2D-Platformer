import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

/**
 * Allows keyboard handling. Using keyPressed and keyReleased are important for smooth movement.
 *
 */
public class EventHandler implements KeyListener{
	static boolean shift = false;
    @Override
    public void keyPressed(KeyEvent e) {
    	Player p = Main.p;
        int key = e.getKeyCode();
        
        
        if(key == KeyEvent.VK_Q && shift) {
        	if(Scene.gameFrame == Scene.GAME_SCENE) {
        	try {
				FileReader.save(p,new File(Main.resources + "/Saves/save1.sav"),false);
				System.out.println("SAVING GAME...");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
        	System.exit(0);
        	}
        }
        
        if(key == KeyEvent.VK_W) {
        	if(Scene.inMenu) {
        		Scene.shiftMenuOption(true);
        	}
        }
        
        if(key == KeyEvent.VK_S) {
        	if(Scene.inMenu) {
        		Scene.shiftMenuOption(false);
        	}
        	
        }
        
        if(key == KeyEvent.VK_E) {
        	
        	String action = Scene.currentMenu.getAction();
        	if(action.contains("Save 1")) {
        		try {
        			if(Scene.inMenu) {
        			//Set our player to the file save
        			Main.ent.remove(p);
					Player pl = FileReader.readSave(new File(Main.resources + "Saves/" + "save1.sav"),1);
					pl.readout();
					//Now, you might be thinking to yourself, "This doesn't make any sense!"
					//Well, you'd be completely correct.  Just accept it and move on.  I spent hours trying to fix it and this was the best I could do.
					//You know, in hindsight, I made a stupid mistake, and this is ridiculous.  Whatever, I'm moving on.
					Main.ent.add(pl = new Player(pl.x,pl.y,pl.life,pl.maxLife,pl.energy,pl.maxEnergy,pl.jumping,pl.holdingJump,pl.canJump,pl.movingLeft,pl.movingRight,pl.hitCeiling,pl.onGround));
					Main.p = pl;
					Scene.gameFrame = Scene.GAME_SCENE;
					Scene.inMenu = false;
        			}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
        	}
        	//Only Save 1 is allowed to read/write now.
        	if(action.contains("Save 2")) {
        		System.out.println("This does nothing.");
        	}
        	if(action.contains("Save 3")) {
        		System.out.println("This still does nothing.");
        	}
        	if(Scene.inMenu) {
	        	switch(Scene.currentMenu.getAction()) {
	        	
		        	case "New Game": Scene.gameFrame = Scene.GAME_SCENE; try {
						FileReader.save(p,new File(Main.resources + "/Saves/save1.sav"),true);
					} catch (IOException e1) {
					}
		        	Scene.inMenu = false; Main.newGame = true; Main.ent.add(new Dialogue()); break;
		        	case "Load Game": Scene.setMenu(Menu.fileMenu); Scene.inMenu = false; break;
		        	//Straight-up quits the application.  Shift-Q does the same thing.
		        	case "Quit": System.exit(0); break;
		        	case "Back to Main Menu": Scene.setMenu(Menu.mainMenu); break;
		        	//Toggles the non-functional delete menu.
		        	case "Delete Save": Menu.delete = !Menu.delete; Scene.currentMenu.changeForeground(Menu.delete ? Color.RED : Color.WHITE); break;
		        	
	        	}
        	}
        		
        	
        }
        
        if(key == KeyEvent.VK_E) {
        	Point point = Main.m.collidesWithSign(Main.p.hitbox);
    		//Match up the dialogues and their location
    		for(int i = 0; i < Main.dialogues.size(); i++) {
    			System.out.println("Looking at " + Main.dialogues.get(i).x + " " + Main.dialogues.get(i).y);
    			if(Main.dialogues.get(i).x == point.x && Main.dialogues.get(i).y == point.y) {
    				System.out.println("Getting a new dialogue...");
    				Main.ent.add(new Dialogue(Main.dialogues.get(i).dialogue));
    			}
    		}
        }
        
        if(key == KeyEvent.VK_A) {
        	p.movingLeft = true;
        }
        
        if(key == KeyEvent.VK_D) {
        	p.movingRight = true;
        }
        
        if(key == KeyEvent.VK_W) {
        	p.movingUp = true;
        }
        
        if(key == KeyEvent.VK_SHIFT) {
        	shift = true;
        }
        
        if(key == KeyEvent.VK_SPACE || key == KeyEvent.VK_L) {
        	p.holdingJump = true;
        }
    }
 
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_A) {
        	Main.p.movingLeft = false;
        }
        
        if(key == KeyEvent.VK_D) {
        	Main.p.movingRight = false;
        }
        
        if(key == KeyEvent.VK_W) {
        	Main.p.movingUp = false;
        }
        
        if(key == KeyEvent.VK_SPACE || key == KeyEvent.VK_L) {
        	Main.p.holdingJump = false;
        	Main.p.canJump = true;
        }
        if(key == KeyEvent.VK_SHIFT) {
        	shift = false;
        }
        
       
    }
    @Override
    public void keyTyped(KeyEvent e) {
        int key = e.getKeyCode();
 
    }
}    
