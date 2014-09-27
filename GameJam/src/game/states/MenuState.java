package game.states;

import game.Placeholder;
import game.display.MenuScreen;

import java.awt.Graphics;

public class MenuState extends State {
	
	MenuScreen menuScreen;
	
	public MenuState(Placeholder main) {
		this.menuScreen = new MenuScreen(main);
	}

	@Override
	public void run() {
		
	}

	@Override
	public void paint(Graphics g) {
		
	}

}
