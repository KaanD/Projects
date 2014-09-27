package game;

import game.io.KeyboardInput;
import game.io.MouseInput;
import game.levels.Level;
import game.states.MenuState;
import game.states.State;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Placeholder extends Applet {
	
	
	public State currentState;
	
	public final static int GAME_WIDTH = 800;
	public final static int GAME_HEIGHT = 800;

	@Override
	public void init() {
		/* Initializing game components */
		setSize(GAME_WIDTH, GAME_HEIGHT);
		addKeyListener(new KeyboardInput(this));
		addMouseListener(new MouseInput(this));
		
		Level.levels = Level.loadLevels();
		//add(canvas);
		currentState = new MenuState(this);
		/* Starts game process, needs to be done from menu */
		currentState.start();
	}
	
	@Override
	public void paint(Graphics g) {
		currentState.paint(g);
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
	
	public void changeState(State newState) {
		currentState = newState;
		currentState.start();
		repaint();
	}
}
