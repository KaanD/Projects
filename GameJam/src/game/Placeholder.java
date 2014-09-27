package game;

import game.io.KeyboardInput;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

public class Placeholder extends Applet {

	@Override
	public void init() {
		/* Initializing game components */
		setSize(800, 800);
		addKeyListener(new KeyboardInput(this));
	}
	
	public int x = 0;
	public int y = 0;
	@Override
	public void paint(Graphics g) {
		g.drawRect(x, y, 20, 20);
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
		paint (dbg); 

		// draw image on the screen 
		g.drawImage (dbImage, 0, 0, this); 
	} 
}
