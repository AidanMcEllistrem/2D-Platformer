import javax.swing.JOptionPane;

public class SetupGUI {
	
	public static void use() {
		//Setup resolution
		Object[] options = {"640x480(Bordered)","1280x720","1366x768","1600x1900","1920x1080","Use Own Resolution"};
		Main.runtimeoptions = JOptionPane.showOptionDialog(null, "Choose a setting to launch at:", "Launch Options", 
		JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		//String s = JOptionPane.showInputDialog("Specify file path here...");
	}
}
