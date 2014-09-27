package game.display;

import game.Placeholder;
import game.display.sprites.Sprite;
import game.levels.Block;

import java.awt.Graphics;
import java.util.ArrayList;

public class GameScreen {

	public ArrayList<Sprite> drawQueue = new ArrayList<Sprite>();
	
	private Placeholder main;
	private boolean paused;
	
	public GameScreen(Placeholder main) {
		this.main = main;
	}
	
	public void paint(Graphics g) {
		while (!drawQueue.isEmpty()) {
			drawQueue.get(drawQueue.size() - 1).draw(g);
			drawQueue.remove(drawQueue.size() - 1);
		}
		for (Block block : main.activeLevel.blocks) {
			block.draw(g);
		}
	}
	
	public void run() {
		if (!paused) {
			
		}
	}
}
