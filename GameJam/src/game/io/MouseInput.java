package game.io;

import game.Placeholder;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {
	
	Placeholder main;

	public MouseInput(Placeholder placeholder) {
		main = placeholder;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		main.currentState.click(e.getX(), e.getY());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

}
