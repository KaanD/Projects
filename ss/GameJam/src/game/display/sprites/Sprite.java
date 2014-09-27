package game.display.sprites;

import game.display.GameScreen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {
	
	BufferedImage image;
	GameScreen canvas;
	
	int x, y;

	public Sprite(File pic, int x, int y, GameScreen canvas) {
		try {
			image = ImageIO.read(pic);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.x = x;
		this.y = y;
		this.canvas = canvas;
	}
	
	/** 
	 * Add sprite to drawing queue
	 */
	public void draw() {
		canvas.drawQueue.add(this);
	}
	
	public void draw(int x, int y) {
		this.x = x;
		this.y = y;
		draw();
	}
	
	/**
	 * Drawing in paint(g)
	 * @param g
	 */
	public void draw(Graphics g) {
		g.drawImage(image, x, y, null);
	}
	
}
