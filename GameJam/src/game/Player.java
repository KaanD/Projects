/* BUGS
 * 
 * -Player sprite does not stop moving left/right after the left/right arrow keys are released
 * -Can't jump
 */

package game;

import game.display.sprites.Sprite;
import game.levels.BasicObject;
import game.levels.Block;
import game.levels.Level;
import game.states.GameState;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Player extends BasicObject {
	//Movement constants, in pixels/tick
	//+Y direction is down and vice versa
	private final double INIT_JUMP_SPEED = -20;
	private final double TERMINAL_VELOCITY = 10;

	//+X direction is right and vice versa
	private final double MAX_HORIZONTAL_SPEED = 5;

	//Acceleration
	//+Y direction is down and vice versa
	private final double GRAVITY = 2;
	private final double GRAVITY_JUMPING = 1;

	//+X direction is right and vice versa
	private final double X_ACCEL_GROUND = 1.0;
	private final double X_ACCEL_AIR = 1.3;


	private boolean inAir=false;

	//tracks when up, left, and right arrow keys are pressed
	
	private String keysOn;

	private double yVel;
	private double xVel;

	private GameState game;
	
	private int startX, startY;
	
	public Player(GameState game, int x, int y, int width, int height, Sprite s) {
		super(x, y, width, height, s);
		this.game = game;
		inAir = false;
		keysOn = "";
		yVel = xVel = 0;
		startX = x;
		startY = y;
		loadSprites();
	}
	
	public void reset() {
		x = startX;
		y = startY;
		yVel = xVel = 0;
	}

	boolean jumpDelay = false;
	public void act(String keys){
		double newx = x;
		double newy = y;
		//jump
		
		if(keys.contains("d") && yVel == 0 && !game.rotating) {
			game.rotation = -90;
			game.cameraTheta = 0;
			game.rotating = true;
			game.gameScreen.fade = true;
		}
		
		if(keys.contains("u") && !keysOn.contains("u") && !inAir){
			animation = 2;
			animationState = 0;
			animationDelay = System.currentTimeMillis();
			jumpDelay = false;
		}
		if (animation == 2 && animationState == 2 && !jumpDelay) {
			inAir = true;
			yVel = INIT_JUMP_SPEED;
			jumpDelay = true;
			//release jump and in air
			AudioInputStream stream;
			try {
				stream = AudioSystem.getAudioInputStream(new File("./wav/jumpSound.wav"));
				Clip music = null;
				try {
					music = AudioSystem.getClip();
				} catch (LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					music.open(stream);
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
				music.start();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if(!keys.contains("u") && keysOn.contains("u") && inAir){
			yVel += yVel < 0 ? GRAVITY_JUMPING : GRAVITY;
		}
		if (yVel >= 0 && animation == 2 && animationState == 2) {
			animationState = 3;
			jumpDelay = false;
		}
		if (yVel > 0 && animation !=2) {
			animation = 2;
			animationState = 3;
		}
		if (yVel == 0 && !inAir && animation != 2) {
			if (xVel != 0) {
				animation = 1;
				if (animationState == 0) {
					animationState = 1;
					animationDelay = System.currentTimeMillis();
				}
			} else {
				animation = 0;
				animationState = 0;
			}
		}
		yVel += GRAVITY;
		
		//horizontal
		//accel
		if(keys.contains("l") && !keys.contains("r")){ //go left 
			xVel -= inAir ? X_ACCEL_AIR : X_ACCEL_GROUND;
			reverse = true;
		}
		else if(!keys.contains("l") && keys.contains("r")){ //go right
			xVel += inAir ? X_ACCEL_AIR : X_ACCEL_GROUND;
			reverse = false;
		} else {
			xVel -= (int) Math.signum(xVel);
		}
		//clamp velocities
		xVel = Math.abs(xVel) > MAX_HORIZONTAL_SPEED ? (int) Math.signum(xVel) * MAX_HORIZONTAL_SPEED : xVel;
		yVel = yVel > TERMINAL_VELOCITY ? TERMINAL_VELOCITY : yVel;
		
		///xVel += (int) -Math.signum(xVel);

		newx = x + xVel;
		newy = y + yVel;

		//check collisions and clamp position
		Rectangle r;
		if((r = collide((int) newx, (int) y))!=null){

			//subtract width for true test case to account for width of player sprite
			//however, this only works when approaching the box from the left
			//width must be added coming from the right
			newx=xVel>0?r.x-1-width:r.x+r.getWidth()+1;

			xVel = 0;
		}
		if((r = collide((int) x, (int) newy))!=null && yVel - GRAVITY != this.INIT_JUMP_SPEED) {

			//subtract height for true test case to account for height of sprite
			//however, this only works when approaching the box from above
			//hitting the block from below requires addition
			game.gameScreen.center();
			newy=yVel>0?r.y-1-height:r.y+getHeight()+1;

			yVel = 0;
			inAir= yVel > 0;
			//check for ont op of block
			if(yVel<=0){
				if (animation == 2 && animationState == 3) {
					animationState = 4;
					animationDelay = 100;
				}
				int blockType = game.activeLevel.grid[(int) (r.getX() / Level.CELL_SIZE)]
						[(int) (r.getY() / Level.CELL_SIZE)].getBlockType();
				if (blockType == 11) {
					game.won = true;
				}
				
				//rotate check
			//	if(yVel > 0 && r != null){
/*					int dir = (int) Math.signum(xVel);
					//if(newx+ width/2*dir  > (xVel>0?r.x:r.x+r.getWidth()) && collide((int) newx,(int) newy) == null)
					Rectangle me = new Rectangle( (int)newx, (int) newy, width+5, height );
					Rectangle testleft = new Rectangle ((int) r.getX(), (int) r.getY()+2, 2,2);
					Rectangle testright = new Rectangle ((int)(r.getX() + r.getWidth()-2), (int)r.getY() + 2, 2,2);
					if(me.intersects(testleft)){
						System.out.println("this is never called");
						
					} else if(me.intersects(testright)){
						System.out.println("this is never called");
						
					}
							
				//}
*/			}
		}
		//newx = newx + newxVel;
		//newy = newy + newyVel;

		
		keysOn=keys;
		x = newx;
		y = newy;
		if (x > Level.CELL_SIZE * (game.activeLevel.width + 2)
				|| x < - 3 * Level.CELL_SIZE
				|| y > Level.CELL_SIZE * (game.activeLevel.height + 2)
				|| y < - 3 * Level.CELL_SIZE) {
			reset();
			game.reset();
		}
		game.main.repaint();
	}

	/**
	* checks for collision and returns rectangle u collided with, null otherwise
	**/
	private Rectangle collide(int newx, int newy){
		Rectangle me = new Rectangle(newx,newy,width,height);
		for(Block b: game.activeLevel.blocks){
			Rectangle bRect = new Rectangle((int) b.getAbsX(),(int) b.getAbsY(),
					game.activeLevel.CELL_SIZE, game.activeLevel.CELL_SIZE);
			if(me.intersects(bRect)){
				//System.out.printf("me.x=%d me.y=%d block.x=%d block.y=%d",me.x,me.y,bRect.x,bRect.y);
				return bRect;
			}
		}
		return null;
	}
	
	public int animation = 0; // 0 = idle, 1 = move, 2 = jump
	public int animationState = 0;
	
	public Sprite idle;
	public Sprite[] move = new Sprite[4];
	public Sprite[] jump = new Sprite[7];
	
	public void loadSprites() {
		idle = new Sprite("./sprites/rIdle0.png", 1, 1, game.gameScreen);
		
		move[0] = new Sprite("./sprites/rTransition0.png", 0, 0, game.gameScreen);
		move[1] = new Sprite("./sprites/rMove0.png", 0, 0, game.gameScreen);
		move[2] = new Sprite("./sprites/rMove1.png", 0, 0, game.gameScreen);
		move[3] = new Sprite("./sprites/rMove2.png", 0, 0, game.gameScreen);

		jump[0] = new Sprite("./sprites/rJump0.png", 0, 0, game.gameScreen);
		jump[1] = new Sprite("./sprites/rJump1.png", 0, 0, game.gameScreen);
		jump[2] = new Sprite("./sprites/rJump2.png", 0, 0, game.gameScreen);
		jump[3] = new Sprite("./sprites/rJump3.png", 0, 0, game.gameScreen);
		jump[4] = new Sprite("./sprites/rJump4.png", 0, 0, game.gameScreen);
		jump[5] = new Sprite("./sprites/rJump1.png", 0, 0, game.gameScreen);
		jump[6] = new Sprite("./sprites/rJump0.png", 0, 0, game.gameScreen);
	}
	
	int nextWalk;
	
	public Sprite getAnimationState() {
		if (animation == 2) {
			return jump[animationState];
		}
		if (animation == 1) {
			return move[animationState];
		}
		return getSprite();
	}
	
	int drawX = 0, drawY = 0;
	public void draw(Graphics g, int x, int y) {
		drawX = x;
		drawY = y;
		draw(g);
	}
	
	boolean reverse = false;
	
	private long animationDelay = 0;

	@Override
	public void draw(Graphics g) {
		getAnimationState().draw(drawX, drawY, reverse);
		if (animation == 1) {
			if (animationState == 1 && System.currentTimeMillis() - animationDelay >= 150) {
				animationState = 2 + (nextWalk++) % 2;
				animationDelay = System.currentTimeMillis();
			} else if (animationState > 1 && System.currentTimeMillis() - animationDelay >= 150) {
				animationState = 1;
				animationDelay = System.currentTimeMillis();
			}
		} else if (animation == 2) {
			if (animationState == 0 && System.currentTimeMillis() - animationDelay >= 70) {
				animationState = 1;
				animationDelay = System.currentTimeMillis();
			} else if (animationState == 1 && System.currentTimeMillis() - animationDelay >= 70) {
				animationState = 2;
				animationDelay = System.currentTimeMillis();
			} else if (animationState == 4 && System.currentTimeMillis() - animationDelay >= 40) {
				animationState = 5;
				animationDelay = System.currentTimeMillis();
			} else if (animationState == 5 && System.currentTimeMillis() - animationDelay >= 40) {
				animationState = 0;
				animation = 0;
				animationDelay = System.currentTimeMillis();
			}
		}
	}
}
