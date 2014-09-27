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
		System.out.println("test");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			game.x--;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) { 
			game.x++;
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
