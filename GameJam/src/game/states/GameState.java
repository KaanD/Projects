package game.states;

import game.Placeholder;
import game.display.GameScreen;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class GameState extends State {
	
	private GameScreen gameScreen;
	
	public GameState(Placeholder main) {
		this.gameScreen = new GameScreen(main);
	}
	
	public void paint(Graphics g) {
		gameScreen.paint(g);
	}
	
	public void run() {
		
	}

	@Override
	public void click(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) { 
			
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE 
				|| e.getKeyCode() == KeyEvent.VK_UP) {
			
		}
	}

}
