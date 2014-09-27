package game.io;

import game.Placeholder;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {
	
	private Placeholder main;
	
	public KeyboardInput(Placeholder game) {
		this.main = game;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		main.currentState.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		main.currentState.keyReleased(e);
	}

}
