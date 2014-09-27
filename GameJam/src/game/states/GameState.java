package game.states;

import game.Placeholder;
import game.Player;
import game.display.GameScreen;
import game.display.sprites.Sprite;
import game.levels.Block;
import game.levels.Level;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;

public class GameState extends State {
	
	public Placeholder main;
	public GameScreen gameScreen;
	public Level activeLevel;
	public int rotation, cameraTheta;
	
	private int screenWidth, screenHeight;
	
	public Player me;
	private boolean pause = false;
	private boolean alive = true;
	private boolean rotating = false;
	
	public GameState(Placeholder main) {
		this.main = main;
		activeLevel = Level.parseLevel(new File("./levels/1.lvl"));
		this.gameScreen = new GameScreen(this);
		me = new Player(this, (int) (activeLevel.start.getAbsX() + Level.CELL_SIZE / 2), 
				(activeLevel.start.yCoord - 1) * Level.CELL_SIZE, 48, 48, new Sprite("./sprites/char.png", 0, 0, gameScreen));
	}
	
	public void reset() {
		activeLevel = Level.parseLevel(new File("./levels/1.lvl"));
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
						rotating = false;
						Point m = rotate((int) me.x / Level.CELL_SIZE, (int) me.y / Level.CELL_SIZE, rotation);
						me.x = (m.x + 1) * Level.CELL_SIZE;
						me.y = (m.y - 1) * Level.CELL_SIZE;
						for (int i = 0; i < activeLevel.blocks.size(); i++) {
							Block b = activeLevel.blocks.get(i);
							Point p = rotate(b.xCoord, b.yCoord, rotation);
							b.setAbsX(p.x * Level.CELL_SIZE);
							b.setAbsY(p.y * Level.CELL_SIZE);
							b.xCoord = p.x;
							b.yCoord = p.y;
						}
						activeLevel.mapToArray();
						rotation = 0;
						cameraTheta = 0;
						main.repaint();
					}
					if (rotation > cameraTheta) {
						cameraTheta += 5;
						main.repaint();
					} else if (rotation < cameraTheta) {
						cameraTheta -= 5;
						main.repaint();
					}
				} else {
					me.act(s);
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
		if (pause && x > 145 && x < 660 && y > 310 && y < 350) {
			main.changeState(new MenuState(main));
			alive = false;
		}
	}
		String s="";

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("s");
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			s = s.concat("l");
		}  if (e.getKeyCode() == KeyEvent.VK_RIGHT) { 
			s = s.concat("r");
		}  if (e.getKeyCode() == KeyEvent.VK_DOWN) { 
			s = s.concat("d");
		}  if (e.getKeyCode() == KeyEvent.VK_SPACE 
				|| e.getKeyCode() == KeyEvent.VK_UP) {
			s = s.concat("u");
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
			s = s.replaceAll("l", "");
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) { 
			s = s.replaceAll("r", "");
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) { 
			s = s.replaceAll("d", "");
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE 
				|| e.getKeyCode() == KeyEvent.VK_UP) {
			s = s.replaceAll("u", "");
		} else if (e.getKeyCode() == KeyEvent.VK_P) {
			
		}
	}
	
	public void rotateGame(int angle) {
		
	}
	
	/* angle > 0 is clockwise, angle < 0 is counter-clockwise */
	public Point rotate(int xCoord, int yCoord, double angle) {
		int rotatedX = 0, rotatedY = 0;
		if (angle < 0) {
			rotatedX = yCoord;
			rotatedY = activeLevel.width - xCoord - 1;
		} else if (angle > 0) {
			rotatedX = activeLevel.height - yCoord - 1;
			rotatedY = xCoord;
		}
		return new Point(rotatedX, rotatedY);
	}

}
