package game.states;

import game.Placeholder;
import game.display.GameScreen;

import java.awt.Graphics;

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

}
