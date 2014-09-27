package game.states;

import game.Placeholder;
import game.display.MenuScreen;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

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

	@Override
	public void click(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
