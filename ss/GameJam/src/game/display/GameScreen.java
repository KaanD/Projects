package game.display;

import game.Placeholder;
import game.display.sprites.Sprite;
import game.levels.Block;
import game.levels.Level;

import java.awt.Canvas;
import java.awt.Graphics;
import java.util.ArrayList;

public class GameScreen extends Canvas {

	public ArrayList<Sprite> drawQueue = new ArrayList<Sprite>();
	
	private Placeholder main;
	
	public GameScreen(Placeholder main) {
		this.main = main;
	}
	
	public void paint(Graphics g) {
		while (!drawQueue.isEmpty()) {
			drawQueue.get(drawQueue.size() - 1).draw(g);
			drawQueue.remove(drawQueue.size() - 1);
		}
		for (int x = 0; x < main.activeLevel.grid.length * Level.CELL_WIDTH + Level.CELL_WIDTH; x+=Level.CELL_WIDTH) {
			g.drawLine(0, x, main.activeLevel.grid.length * Level.CELL_WIDTH, x);
	}
		for (int x = 0; x < main.activeLevel.grid[0].length * Level.CELL_WIDTH + Level.CELL_WIDTH; x+=Level.CELL_WIDTH) {
			g.drawLine(x, 0, x, main.activeLevel.grid[0].length * Level.CELL_WIDTH);
		}
		for (int y = 0; y < main.activeLevel.grid.length; y++) {
			for (int x = 0; x < main.activeLevel.grid[y].length; x++) {
				Block block = main.activeLevel.grid[x][y];
				if (block.getBlockType() != -1) {
					g.setColor(block.getColor());
					g.fillRect(x * Level.CELL_WIDTH, y * Level.CELL_WIDTH, Level.CELL_WIDTH, Level.CELL_WIDTH);
				}
			}
		}
	}
}
