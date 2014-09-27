package game;

import game.display.sprites.Sprite;
import game.levels.BasicObject;
import game.levels.Block;

import java.awt.Graphics;
import java.util.ArrayList;

public class Character extends BasicObject {
	// Movement constants, in pixels/second
	private final int INITIALSPEED = -10;
	private final int MAXFALLSPEED = 10;
	private final int ACCELERATION = 1;
	private final int MINIMUMJUMP = 10;

	private boolean standing; // true when standing
	private boolean jumping; // true when holding jump

	private int fallSpeed;
	private int jumpCountdown;

	public Character(int newX, int newY, int newWidth, int newHeight, Sprite s) {
		super(newX, newY, newWidth, newHeight, s);
		standing = true;
		jumping = false;
		jumpCountdown = 0;
	}

	public void fall(ArrayList<Block> platforms) {
		if (!standing) {
			moveVertical((int) fallSpeed, platforms);
			// if not holding jump button, start decelerating
			if (jumpCountdown <= 0 || !jumping) {
				jumpCountdown = 0;
				if (fallSpeed <= MAXFALLSPEED)
					fallSpeed += ACCELERATION;
			} else
				jumpCountdown--;

		}
	}

	public void jump() {
		if (standing == true) {
			standing = false;
			fallSpeed = INITIALSPEED;
			jumpCountdown = MINIMUMJUMP;
		}
	}

	public void setJumping(boolean j) {
		jumping = j;
	}

	public void moveLeft(int n, ArrayList<Block> platforms) {
		moveHorizontal(-n, platforms);
	}

	public void moveRight(int n, ArrayList<Block> platforms) {
		moveHorizontal(n, platforms);
	}

	private void moveVertical(int i, ArrayList<Block> platforms) {
		// down = negative, up = positive
		int direction = (int) Math.signum(i);
		int value = Math.abs(i);
		for (int a = 0; a < value; a++) {
			for (Block p : platforms) {
				if (direction < 0) {
					if (p.getCollision()
							.intersects(this.getFutureCollision(up))) {
						fallSpeed = 0;
						return;
					}
				} else {
					if (p.getCollision().intersects(
							this.getFutureCollision(down))) {
						fallSpeed = 0;
						standing = true;
						return;

					}
				}
			}
			if (direction < 0)
				this.setY(this.getY() - 1);
			else {
				this.setY(this.getY() + 1);
			}
		}
	}

	private void moveHorizontal(int i, ArrayList<Block> platforms) {
		// TODO Auto-generated method stub
		// left = negative, right = positive
		int direction = (int) Math.signum(i);
		for (int a = 0; a < Math.abs(i); a++) {
			for (Block p : platforms) {
				if (direction < 0) {
					if (p.getCollision().intersects(
							this.getFutureCollision(left))) {
						// IF STANDING TURN THE WORLD
						return;
					}
				} else {
					if (p.getCollision().intersects(
							this.getFutureCollision(right))) {
						// IF STANDING TURN THE WORLD
						return;
					}
				}
			}
			if (direction < 0)
				this.setX(this.getX() - 1);
			else
				this.setX(this.getX() + 1);
		}

	}

	@Override
	public void draw(Graphics g) {
		getSprite().draw(getX(), getY());
	}
}
