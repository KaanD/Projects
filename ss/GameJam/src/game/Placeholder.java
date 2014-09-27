package game;

import game.display.GameScreen;
import game.io.KeyboardInput;
import game.levels.Level;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

public class Placeholder extends Applet {
	
	public Level activeLevel;
	
	private GameScreen gameScreen;

	@Override
	public void init() {
		activeLevel = Level.parseLevel(new File("./levels/1.lvl"));
		/* Initializing game components */
		setSize(800, 800);
		addKeyListener(new KeyboardInput(this));
		gameScreen = new GameScreen(this);
	}
	
	@Override
	public void paint(Graphics g) {
		gameScreen.paint(g);
	}
	
	private Image dbImage; 
	private Graphics dbg;
	
	@Override
	public void update (Graphics g) {
		// initialize buffer 
		if (dbImage == null) {
			dbImage = createImage (this.getSize().width, this.getSize().height); 
			dbg = dbImage.getGraphics (); 
		} 

		// clear screen in background 
		dbg.setColor (getBackground ()); 
		dbg.fillRect (0, 0, this.getSize().width, this.getSize().height); 

		// draw elements in background 
		dbg.setColor (getForeground()); 
		dbg.setColor(Color.BLACK);
		paint (dbg); 

		// draw image on the screen 
		g.drawImage (dbImage, 0, 0, this); 
	} 
}
