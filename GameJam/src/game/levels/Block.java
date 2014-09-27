package game.levels;

import java.awt.Color;
import java.awt.Graphics;

public class Block extends BasicObject {
	
	private int blockType, xCoord, yCoord;

	public Block(int blockType, int newX, int newY, int newWidth, int newHeight) {
		super(newX * Level.CELL_WIDTH, newY * Level.CELL_WIDTH, newWidth, newHeight);
		this.blockType = blockType;
	}

	@Override
	public void draw(Graphics g) {
		if (getBlockType() == -1)
			return;
		g.setColor(getColor());
		g.fillRect(getX(), getY(),
				Level.CELL_WIDTH, Level.CELL_WIDTH);
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
