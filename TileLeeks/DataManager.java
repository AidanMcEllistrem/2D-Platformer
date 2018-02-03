import java.awt.Dimension;

import javax.swing.JFrame;

public class DataManager extends JFrame {

	private static final long serialVersionUID = -4027067081761732123L;
	
	public DataManager() {
		
		//Dispose only of this window on close, this is only a side tool
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(600,400));
		setResizable(false);
		setTitle("Data Editor");
		pack();
		setVisible(true);
	}
	
	
	
}
