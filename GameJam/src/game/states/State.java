package game.states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public abstract class State extends Thread {
	
	public abstract void paint(Graphics g);

	public abstract void run();
	
	public abstract void click(int x, int y);
	
	public abstract void keyPressed(KeyEvent e);
	
}
