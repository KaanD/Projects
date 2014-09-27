package game.io;

import game.Placeholder;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {
	
	private Placeholder game;
	
	public KeyboardInput(Placeholder game) {
		this.game = game;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("test");
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) { 
			
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE 
				|| e.getKeyCode() == KeyEvent.VK_UP) {
			
		}
		game.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
