package game.levels;

import game.Placeholder;

import java.awt.Color;
import java.awt.Graphics;

public class Block extends BasicObject {
	
	private int blockType, xCoord, yCoord;
	private Level level;

	public Block(Level level, int blockType, int newX, int newY, int newWidth, int newHeight) {
		super(newX * Level.CELL_SIZE, newY * Level.CELL_SIZE, newWidth, newHeight);
		this.blockType = blockType;
		this.xCoord = newX;
		this.yCoord = newY;
		this.level = level;
	}

	@Override
	public void draw(Graphics g) {
		if (getBlockType() == -1)
			return;
		g.setColor(getColor());
		g.fillRect(getAbsX(), getAbsY(),
				Level.CELL_SIZE, Level.CELL_SIZE);
	}
	
	public void rotate(int dir) {
		int newX = 0;
		int newY = 0;
		if (dir < 0) {
			newX = yCoord;
			newY = Placeholder.GAME_HEIGHT / Level.CELL_SIZE - xCoord - 1;
		} else {
			newX = Placeholder.GAME_WIDTH / Level.CELL_SIZE - yCoord - 1;
			newY = xCoord;
		}
		xCoord = newX;
		yCoord = newY;
		setAbsX(xCoord * Level.CELL_SIZE);
		setAbsY(yCoord * Level.CELL_SIZE);
	}

	public int getQuadrent() {
		if (xCoord <= Placeholder.GAME_WIDTH / Level.CELL_SIZE / 2
				&& yCoord <= level.height / 2)
			return 1;
		if (xCoord > Placeholder.GAME_WIDTH / Level.CELL_SIZE / 2
				&& yCoord <= Placeholder.GAME_HEIGHT / Level.CELL_SIZE / 2)
			return 2;
		if (xCoord <= Placeholder.GAME_WIDTH / Level.CELL_SIZE / 2
				&& yCoord > Placeholder.GAME_HEIGHT / Level.CELL_SIZE / 2)
			return 3;
		return 4;
	}
	
	public Color getColor() {
		switch (blockType) {
		case 1:
			return Color.BLACK;
		case 10:
			return Color.BLUE;
		case 11:
			return Color.GREEN;
		}
		return Color.BLACK;
	}
	
	public static int getBlockType(String s) {
		switch (s) {
		case "-":
			return -1;
		case "S":
			return 10;
		case "0":
			return 0;
		case "1":
			return 1;
		case "K":
			return 11;
		}
		return 0;
	}

	public int getBlockType() {
		return blockType;
	}
}
