
public class Scene {
	//Some convoluted code I wrote for menus.
	static int gameFrame = 1;
	static int menuState = 0;
	static boolean inMenu = false;
	static boolean doAction = false;
	public final static int GAME_SCENE = 0, MENU_SCENE = 1;
	
	static Menu currentMenu = null;
	
	static void setMenu(Menu m) {
		currentMenu = m;
		menuState = m.menuRange.low;
	}
	
	//Stuff for changing the frame and if the player is in a menu
	static void changeFrame(int i) {
		menuState = i;
	}
	
	static void shiftMenuOption(boolean opposite_way) {
		if(opposite_way) {
			menuState--;
		} else {
			menuState++;
		}
	}
	
	static void menuState(boolean b) {
		inMenu = b;
	}
	
	static void enterMenu() {
		inMenu = true;
	}
	
	static void exitMenu() {
		inMenu = false;
	}
}
