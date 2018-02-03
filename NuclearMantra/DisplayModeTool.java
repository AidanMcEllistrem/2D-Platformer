import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/**
 * Just in case you wanted to know what kind of displays your monitor has
 * @author Aidan
 *
 */
public class DisplayModeTool {
	public static DisplayMode[] getMode() {
		//Get all of the display modes of this computer
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] devices = env.getScreenDevices();
		//Search through all display devices
		for(GraphicsDevice d : devices) {
			DisplayMode[] display = d.getDisplayModes();
			//Search through all display modes
			System.out.println("Resolution-Hz-Color");
			for(DisplayMode disp : display) {
			
			System.out.println(disp.getWidth() + "x" + disp.getHeight() + " " + disp.getRefreshRate() + "Hz " + disp.getBitDepth() + "-bit color");
			}
			return display;
		}
		//If the display mode was not found, return nothing
		return null;
	}
}

