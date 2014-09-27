package game.display;

import game.Placeholder;
import game.display.sprites.Sprite;
import game.levels.Block;
import game.levels.Level;
import game.states.GameState;

import java.awt.AlphaComposite;
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
	private Image win;
	
	private GameState gameState;
	private boolean paused;
	public boolean exitHovered;
	
	public int offX, offY;
	
	public GameScreen(GameState state) {
		this.gameState = state;
		try {
			pauseImage = ImageIO.read(new File("./sprites/pause.png"));
			exitToMain = ImageIO.read(new File("./sprites/exit.png"));
			exitToMainHover = ImageIO.read(new File("./sprites/exit2.png"));
			win = ImageIO.read(new File("./sprites/torch.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void center() {
		offX = (int) (Placeholder.GAME_WIDTH / 2 - gameState.me.x);
		offY = (int) (Placeholder.GAME_HEIGHT / 2 - gameState.me.y);
	}
	
	float alpha = 0;
	public void paint(Graphics g, boolean pause) {
		if (gameState.won) {
			Graphics2D gd = (Graphics2D) g;
			alpha += 0.01f;
			if (alpha > 1)
				alpha = 1;
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
			gd.setComposite(ac);
			gd.drawImage(win, 0, 0, null);
			return;
		}
		offX = (int) (Placeholder.GAME_WIDTH / 2 - gameState.me.x);
/*		for (int i = 0; i < 50; i++) {
			g.drawLine(i * Level.CELL_SIZE, 0, i * Level.CELL_SIZE, 1000);
		}
		for (int i = 0; i < 50; i++) {
			g.drawLine(0, i * Level.CELL_SIZE, 1000, i * Level.CELL_SIZE);
		}*/
		while (!drawQueue.isEmpty()) {
			drawQueue.get(drawQueue.size() - 1).draw(g);
			drawQueue.remove(drawQueue.size() - 1);
		}
		for (Block block : gameState.activeLevel.blocks) {
			Graphics2D g2 = (Graphics2D) g;
			g.setColor(block.getColor());
			g2.setColor(block.getColor());
			Rectangle2D rectangle = new Rectangle((int) block.getAbsX() + offX, (int) block.getAbsY() + offY, Level.CELL_SIZE, Level.CELL_SIZE);
			AffineTransform transform = new AffineTransform();
			if (gameState.rotation != gameState.cameraTheta)
				transform.rotate(Math.toRadians(0), Placeholder.GAME_WIDTH/2, Placeholder.GAME_HEIGHT/2);
			Shape transformed = transform.createTransformedShape(rectangle);
			g2.fill(transformed);
		}
		if (gameState.rotation == 0) 
			gameState.me.draw(g, (int) gameState.me.x + offX, (int) gameState.me.y + offY);
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
