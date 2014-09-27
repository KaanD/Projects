package game.states;

import game.Placeholder;
import game.display.GameScreen;
import game.levels.Level;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;

public class GameState extends State {
	
	private Placeholder main;
	private GameScreen gameScreen;
	public Level activeLevel;
	public int rotation, cameraTheta;
	
	private int screenWidth, screenHeight;
	
	private Character me;
	private boolean pause = false;
	private boolean alive = true;
	private boolean rotating = false;
	
	public GameState(Placeholder main) {
		this.main = main;
		activeLevel = Level.parseLevel(new File("./levels/1.lvl"));
		this.gameScreen = new GameScreen(this);
	}
	
	public void paint(Graphics g) {
		screenWidth = main.getWidth();
		screenHeight = main.getHeight();
		gameScreen.paint(g, pause);
	}
	
	public void run() {
		while(alive){
			if(pause) {
				boolean oldVal = gameScreen.exitHovered;
				Point mouse = main.getMousePosition();
				if (mouse != null 
						&& mouse.getX() > 145 && mouse.getX() < 660
						&& mouse.getY() > 310 && mouse.getY() < 350) {
					gameScreen.exitHovered = true;
				} else {
					gameScreen.exitHovered = false;
				}
				if (oldVal != gameScreen.exitHovered)
					main.repaint();
			} else {
				if (rotating) {
					if (rotation == cameraTheta) {
						cameraTheta = 0;
						for (int i = 0; i < activeLevel.blocks.size(); i++)
							activeLevel.blocks.get(i).rotate(rotation);
						rotation = 0;
						rotating = false;
						main.repaint();
					}
					if (rotation > cameraTheta) {
						cameraTheta += 5;
						main.repaint();
					} else if (rotation < cameraTheta) {
						cameraTheta -= 5;
						main.repaint();
					}
				}
			}
			try {
				sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getWidth() {
		return screenWidth;
	}
	
	public int getHeight() {
		return screenHeight;
	}

	@Override
	public void click(int x, int y) {
		if (x > 145 && x < 660 && y > 310 && y < 350) {
			main.changeState(new MenuState(main));
			alive = false;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			//me.moveLeft();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) { 
			
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE 
				|| e.getKeyCode() == KeyEvent.VK_UP) {
			//jump
		} else if (e.getKeyCode() == KeyEvent.VK_P) {
			pause = !pause;
			main.repaint();
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			rotation -= 90;
			rotating = true;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			rotation += 90;
			rotating = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			//me.moveLeft();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) { 
			
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE 
				|| e.getKeyCode() == KeyEvent.VK_UP) {
			//jump
		} else if (e.getKeyCode() == KeyEvent.VK_P) {
			
		}
	}

}
