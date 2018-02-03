import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * A class that handles the mousewheel
 * @author Aidan
 *
 */
public class MouseWheelHandler implements MouseWheelListener {

	public void mouseWheelMoved(MouseWheelEvent e) {
		Main.pen.context += e.getWheelRotation();
		Main.graphicInterface.repaint();
	}

}
