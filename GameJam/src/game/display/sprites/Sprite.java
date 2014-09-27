package game.display.sprites;

import game.display.GameScreen;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {
	
	BufferedImage image;
	GameScreen canvas;
	
	int x, y;

	public Sprite(String s, int x, int y, GameScreen canvas) {
		try {
			image = ImageIO.read(new File(s));
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
	
	public void draw(int x, int y, boolean reverse) {
		this.x = x;
		this.y = y;
		this.reverse = reverse;
		draw();
	}
	
	boolean reverse = false;
	
	/**
	 * Drawing in paint(g)
	 * @param g
	 */
	public void draw(Graphics g) {
		if (reverse)
			((Graphics2D) g).drawImage(image, x+48, y, -48, 48, null);
		else
			g.drawImage(image, x, y, null);
	}
	
}
