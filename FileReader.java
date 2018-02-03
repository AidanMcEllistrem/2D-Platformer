import java.awt.Dimension;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

public class FileReader {
	
	public Tile[][] readFileToLevel(File f) throws IOException {
		if(f != null) {
			//Read the file from the filepath
			FileInputStream saveFile = new FileInputStream(f.getPath());
			ObjectInputStream restore = new ObjectInputStream(saveFile);
			//Write the array back
			Dimension d = new Dimension(restore.readInt(),restore.readInt());
			//Prep the tile array for reading
			Tile[][] tile = new Tile[d.width * 24][(int) (d.height * 13.5) + 1 - ((d.height + 1) % 2)];
			for(int x = 0; x < tile.length; x++) {
				for(int y = 0; y < tile[0].length; y++) {
					tile[x][y] = new Tile(restore.readShort(),x,y);
				}
			}
			int length = restore.readInt();
			//Pretty much copied from the TileLeeks code
			for(int i = 0; i < length; i++) {
				System.out.println(i);
				SignDialogue str = new SignDialogue(restore.readUTF(),restore.readInt(),restore.readInt());
				System.out.println("Read " + str.dialogue);
				Main.dialogues.add(str);
			}
			//This, as I learned, is extremely important.  You will get an EOF exception if this is not there.
			restore.close();
			return tile;
		}
		return new Tile[0][0];
	}
	
	public static void save(Player p, File f, boolean time) throws IOException {
		if(f != null) {
			FileOutputStream saveFile = new FileOutputStream(f.getPath());
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			//This is going to be ugly
			save.writeDouble(p.x);
			save.writeDouble(p.y);
			System.out.println("Saved " + p.x + " " + p.y);
			save.writeBoolean(p.jumping);
			save.writeBoolean(p.holdingJump);
			save.writeBoolean(p.canJump);
			save.writeBoolean(p.movingLeft);
			save.writeBoolean(p.movingRight);
			save.writeBoolean(p.hitCeiling);
			save.writeBoolean(p.onGround);
			save.writeDouble(p.life);
			save.writeDouble(p.maxLife);
			save.writeDouble(p.energy);
			save.writeDouble(p.maxEnergy);
			//Write elapsed time, overwrite with 0 if boolean is true
			if(!time)
			save.writeLong(Main.time);
			else
			save.writeLong(0L);
			System.out.println("Wrote " + Main.time / Main.million + " ms.");
			save.close();
		}
	}
	
	public static Player readSave(File f, int save) throws IOException {
		Player p = new Player();
		if(f != null) {
			
			//Read the file from the filepath
			FileInputStream restoreFile = new FileInputStream(f.getPath());
			ObjectInputStream restore = new ObjectInputStream(restoreFile);
			p.x = restore.readDouble();
			p.y = restore.readDouble();
			
			p.jumping = restore.readBoolean();
			p.holdingJump = restore.readBoolean();
			p.canJump = restore.readBoolean();
			p.movingLeft = restore.readBoolean();
			p.movingRight = restore.readBoolean();
			p.hitCeiling = restore.readBoolean();
			p.onGround = restore.readBoolean();
			p.life = restore.readDouble();
			p.maxLife = restore.readDouble();
			p.energy = restore.readDouble();
			p.maxEnergy = restore.readDouble();
			//Set the time
			Main.time = restore.readLong();
			Main.setTimeForSaves(getTime(Main.time), save);
			System.out.println("Got " + Main.time / Main.million + " ms.");
			restore.close();
			System.out.println("This is  " + p.x + " " + p.y);
		} else {
		System.err.println("File was null."); }
		return p;
	}
	
	public static String getTime(long l) {
		System.out.println("Setting" + l / (Main.million * 1000));
		long totalTime = l / (Main.million * 1000);
		long hours = totalTime / 3600;
		long minutes = (totalTime % 3600) / 60;
		long seconds = totalTime % 60;
		return "Continue Game (" + hours + ":" + minutes + ":" + seconds + ")";

	}
}
