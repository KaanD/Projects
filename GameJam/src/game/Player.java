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
	}
	
	public void reset() {
		x = startX;
		y = startY;
		yVel = xVel = 0;
	}

	public void act(String keys){
		if(!keys.equals(""))
			System.out.println(keys);
		double newx = x;
		double newy = y;
		//jump
		
		if(keys.contains("d") && !inAir) {
			game.rotation=90;
		}
		
		if(keys.contains("u") && !keysOn.contains("u") && !inAir){
			inAir = true;
			yVel = INIT_JUMP_SPEED;
			System.out.println("yVel="+yVel);
			//release jump and in air
		} else if(!keys.contains("u") && keysOn.contains("u") && inAir){
			yVel += yVel < 0 ? GRAVITY_JUMPING : GRAVITY;
		}
			yVel += GRAVITY;
		
		//horizontal
		//accel
		if(keys.contains("l") && !keys.contains("r")){ //go left 
			xVel -= inAir ? X_ACCEL_AIR : X_ACCEL_GROUND;
			System.out.println("xVel="+xVel);
		}
		else if(!keys.contains("l") && keys.contains("r")){ //go right
			xVel += inAir ? X_ACCEL_AIR : X_ACCEL_GROUND;
			System.out.println("xVel="+xVel);
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
		if((r = collide((int) newx, (int) newy))!=null){
			
			Rectangle me = new Rectangle(newx,newy,width,height);
			newx=xVel>0?r.x-1-width:r.x+r.getWidth()+1;
			newy=xVel>0?r.y-1-height:r.y+r.getHeight()+1;
		
			Rectangle inter = r.createIntersection(me);

			if(yVel>0 ){ 
				if(xVel>0){ // collided with top left
					if(inter.getX() > inter.getY())//collided with top
						yVel=0;
					else if ( inter.getX() < inter.getY()) // collided with left
						xVel=0;
					else
						xVel=yVel=0;
				} else if(xVel<0){ //collided with top right
					if(inter.getx() > inter.getY())
						yVel=0;
					else if (inter.getX() < inter.getY())
						xVel=0;
					else
						xVel=yVel=0;
				} if(yVel>0){ // collided with top left
					if(inter.getX() > inter.getY())//collided with 
						yVel=0;
					else if ( inter.getX() < inter.getY()) // collided with left
						xVel=0;
					else
						xVel=yVel=0;
				} else if(yVel<0){ //collided with top right
					if(inter.getx() > inter.getY())
						yVel=0;
					else if (inter.getX() < inter.getY())
						xVel=0;
					else
						xVel=yVel=0;
				}
			} else if (yVel<0){
				if(xVel>0) //collided with bottom left
				if(xVel<0) // collided with bottom right
			}
				yVel=0;
			if(yVel<0 && xVel !=0 ) // collided with bottom
				yVel=0;i
			if(xVel>0 && yVel !=0 ) // collided with left
				xVel=0;
			if(yVel<0 && yVel !=0 ) // collided with right

		} else if((r = collide((int) newx, (int) y))!=null){

			//subtract width for true test case to account for width of player sprite
			//however, this only works when approaching the box from the left
			//width must be added coming from the right
			newx=xVel>0?r.x-1-width:r.x+r.getWidth()+1;
			
			xVel = 0;
			System.out.println("TOUCH SIDE");
		}else if((r = collide((int) x, (int) newy))!=null && yVel - GRAVITY != this.INIT_JUMP_SPEED) {

			//subtract height for true test case to account for height of sprite
			//however, this only works when approaching the box from above
			//hitting the block from below requires addition
			game.gameScreen.center();
			newy=yVel>0?r.y-1-height:r.y+r.getHeight()+1;

			yVel = 0;
			inAir= yVel > 0;
			System.out.println("TOUCH VERTICAL");
			//check for ont op of block
			if(yVel<=0){
				int blockType = game.activeLevel.grid[(int) (r.getX() / Level.CELL_SIZE)]
						[(int) (r.getY() / Level.CELL_SIZE)].getBlockType();
				if (blockType == 11) {
					System.out.println(" YOU WIN");
					game.won = true;
				}
			}
		}
		System.out.println("yvel="+ yVel + " inAir=" + inAir);
		//newx = newx + newxVel;
		//newy = newy + newyVel;

		//rotate check
		if(yVel > 0 && r != null){
			int dir = (int) Math.signum(xVel);
			if(newx+ width/2*dir  > (xVel>0?r.x:r.x+r.getWidth()) && collide((int) newx,(int) newy) == null)
					game.rotation=90*dir;
		}
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
		System.out.println("final x=" + x + " final y=" + y);
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
				System.out.printf("me.x=%d me.y=%d block.x=%d block.y=%d",me.x,me.y,bRect.x,bRect.y);
				return bRect;
			}
		}
		return null;
	}
	
	int drawX = 0, drawY = 0;
	public void draw(Graphics g, int x, int y) {
		drawX = x;
		drawY = y;
		draw(g);
	}

	@Override
	public void draw(Graphics g) {
		getSprite().draw(drawX, drawY);
	}
}
