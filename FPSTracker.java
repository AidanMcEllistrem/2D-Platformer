import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class FPSTracker {
	
	/**
	 * A helpful little tool to track the frame data.
	 * @param p
	 * @param size
	 * @param fpsdata
	 * @param g
	 */
	public static void showGraph(Point p, int size, ArrayList<Integer> fpsdata, Graphics g, int targetFPS) {
		Graphics2D g2d = (Graphics2D) g;
		//Draw bars
		g2d.setColor(Color.GREEN);
		g2d.drawLine(p.x, p.y - 4, p.x, p.y + size );
		g2d.drawLine(p.x, p.y + size, p.x  + size, p.y + size);
		
		//Draw some useful markers
		g2d.setFont(new Font("Verdana", Font.PLAIN,8));
		if(fpsdata.size() != 0) {
		showFPS(fpsdata, g, new Point(p.x - 12, p.y - 6));
		}
		
		g2d.drawString(Integer.toString(targetFPS), p.x - 12, p.y + 8);
		g2d.drawString(Integer.toString(targetFPS / 2), p.x - 12, p.y + size/2 + 4);
		g2d.drawString(Integer.toString(0), p.x - 6, p.y + size + 4);

		//Draw the first 10 data points of the graph
		g2d.setColor(Color.RED);
		//Draw the lines connecting the data points
		for(int i = 0; i < fpsdata.size() - 1; i++) {
			g2d.drawLine(p.x + (i * (size / 10)), p.y + size - fpsdata.get(i),p.x + ((i + 1) * (size / 10)),p.y + size - fpsdata.get(i + 1));
			//System.out.println((size * ((double)targetFPS / (double)fpsdata.get(i))) + " " + fpsdata.get(i) + " " + targetFPS);
		}
		
		g2d.drawString("Total Time: " + Main.time, p.x - 40, p.y + size + 12);
		
	}
	
	public static void showFPS(ArrayList<Integer> fpsdata, Graphics g, Point p) {
		Graphics2D g2d = (Graphics2D) g;
		//Set font
		g2d.setFont(new Font("Verdana", Font.PLAIN,8));
		//Average the fps
		double avg = 0;
		for(int i = 0; i < fpsdata.size(); i++) {
			avg += fpsdata.get(i);
		}
		double fpsavg = Math.floor((avg / fpsdata.size()) * 100) / 100;;
		g2d.drawString("FPS: " + fpsdata.get(fpsdata.size() - 1) + "  Avg: " + fpsavg, p.x, p.y);
		
	}
}
