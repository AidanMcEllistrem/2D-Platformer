import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class MouseHandler extends MouseAdapter {
	
	public void mousePressed(MouseEvent e) {
		//Main.cursorPosition = new Point(Main.raspx(e.getX() + Main.aspx(3)),Main.raspy(e.getY() + Main.aspx(3)));
		
	    if(e.getButton() == MouseEvent.BUTTON1) {
	    	Main.pen.held = true;
	    	Main.graphicInterface.repaint();
	    	}
	    
	    else if(e.getButton() == MouseEvent.BUTTON2) {
	    	Main.pen.paintbucket = !Main.pen.paintbucket;
	    	DrawPanel.goPaint = true;
	    	Main.graphicInterface.repaint();
	    }
	    
	    else if(e.getButton() == MouseEvent.BUTTON3) {
	    	Main.pen.held = true;
	    	Main.pen.erase = true;
	    	Main.graphicInterface.repaint();
			}
		}
		
	
	public void mouseReleased(MouseEvent e) {
		//Main.cursorPosition = new Point(Main.raspx(e.getX() + Main.aspx(3)),Main.raspy(e.getY() + Main.aspx(3)));
		if(e.getButton() == MouseEvent.BUTTON1) {
			Main.pen.held = false;
			Main.graphicInterface.repaint();
		}
		else if(e.getButton() == MouseEvent.BUTTON3) {
			Main.pen.held = false;
			Main.pen.erase = false;
			Main.graphicInterface.repaint();
		}
	}

}
