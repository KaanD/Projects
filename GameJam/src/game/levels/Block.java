package game.levels;

import java.awt.Color;
import java.awt.Graphics;

public class Block extends BasicObject {
	
	public int blockType, xCoord, yCoord;
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
		g.fillRect((int) getAbsX(), (int) getAbsY(),
				Level.CELL_SIZE, Level.CELL_SIZE);
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
