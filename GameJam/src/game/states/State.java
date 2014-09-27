package game.states;

import java.awt.Graphics;

public abstract class State extends Thread {
	
	public abstract void paint(Graphics g);

	public abstract void run();
	
}
