package game.display;

import game.Placeholder;
import game.display.sprites.Sprite;
import game.levels.Block;
import game.levels.Level;
import game.states.GameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class GameScreen {

	public ArrayList<Sprite> drawQueue = new ArrayList<Sprite>();
	private Image pauseImage;
	private Image exitToMain;
	private Image exitToMainHover;
	
	private GameState gameState;
	private boolean paused;
	public boolean exitHovered;
	
	public int cameraX, cameraY;
	
	public GameScreen(GameState state) {
		this.gameState = state;
		try {
			pauseImage = ImageIO.read(new File("./sprites/pause.png"));
			exitToMain = ImageIO.read(new File("./sprites/exit.png"));
			exitToMainHover = ImageIO.read(new File("./sprites/exit2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paint(Graphics g, boolean pause) {
		//cameraX = (int) (Placeholder.GAME_WIDTH / 2 - gameState.me.x);
		//cameraY = (int) (Placeholder.GAME_HEIGHT / Level.CELL_SIZE / 2 - gameState.me.y / Level.CELL_SIZE);
		for (int i = 0; i < 50; i++) {
			g.drawLine(i * Level.CELL_SIZE, 0, i * Level.CELL_SIZE, 1000);
		}
		for (int i = 0; i < 50; i++) {
			g.drawLine(0, i * Level.CELL_SIZE, 1000, i * Level.CELL_SIZE);
		}
		while (!drawQueue.isEmpty()) {
			drawQueue.get(drawQueue.size() - 1).draw(g);
			drawQueue.remove(drawQueue.size() - 1);
		}
		for (Block block : gameState.activeLevel.blocks) {
			Graphics2D g2 = (Graphics2D) g;
			g.setColor(block.getColor());
			g2.setColor(block.getColor());
			Rectangle2D rectangle = new Rectangle((int) block.getAbsX() - cameraX, (int) block.getAbsY() - cameraY, Level.CELL_SIZE, Level.CELL_SIZE);
			AffineTransform transform = new AffineTransform();
			transform.rotate(Math.toRadians(gameState.cameraTheta), Placeholder.GAME_WIDTH/2, Placeholder.GAME_HEIGHT/2);
			Shape transformed = transform.createTransformedShape(rectangle);
			g2.fill(transformed);
		}
		gameState.me.draw(g, (int) gameState.me.x + cameraX, (int) gameState.me.y + cameraX);
		Block goal = gameState.activeLevel.goal;
		if (goal.getAbsX() - cameraX > Placeholder.GAME_WIDTH
				|| goal.getAbsX() + goal.getWidth() - cameraX < 0
				|| goal.getAbsY() - cameraY > Placeholder.GAME_HEIGHT
				|| goal.getAbsY() + goal.getHeight() - cameraY < 0) {
			
		}
		if (pause) {
			g.setColor(new Color(0, 0 ,0, 128));
			g.fillRect(0, 0, gameState.getWidth(), gameState.getHeight());
			g.drawImage(pauseImage, 80, 100, null);
			g.drawImage(exitHovered ? exitToMainHover : exitToMain, 115, 255, null);
		}
	}
	
	public void run() {
		if (!paused) {
			
		}
	}
}
