package game.states;

import game.Placeholder;
import game.display.MenuScreen;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class MenuState extends State {
	
	Placeholder main;
	MenuScreen menuScreen;
	boolean alive = true;
	public MenuState(Placeholder main) {
		this.main = main;
		this.menuScreen = new MenuScreen(main);
	}

	@Override
	public void run() {
		while(alive){
			
			//buttons and other stuff
			
			
		}
	}

	@Override
	public void paint(Graphics g) {
		menuScreen.paint(g);
	}

	@Override
	public void click(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			System.out.println("Changed State");
			main.changeState(new GameState(main));
			alive = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
