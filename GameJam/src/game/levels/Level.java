package game.levels;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Level {
	
	public final static int GRID_CELL_COUNT = 40;
	public final static int CELL_SIZE = 50;
	
	public ArrayList<Block> blocks = new ArrayList<Block>();
	public Block goal;
	
	public String name;
	public int width;
	public int height;
	
	public Level(String name) {
		this.name = name;
	}
	
	public static Level parseLevel(File input) {
		try (Scanner s = new Scanner(input)) {
			Level level = new Level(s.nextLine());
			String[] xy = s.nextLine().split("x");
			level.width = Integer.parseInt(xy[0]);
			level.height = Integer.parseInt(xy[1]);
			int gridX = 0, gridY = 0;
			while (s.hasNextLine()) {
				String line = s.nextLine();
				String[] elements = line.split(" ");
				for (String element : elements) {
					Block block = new Block(level, Block.getBlockType(element), gridX++, gridY, CELL_SIZE, CELL_SIZE);
					if (block.getBlockType() != -1) {
						level.blocks.add(block);
						if (block.getBlockType() == 11)
							level.goal = block;
					}
				}
				gridX = 0;
				gridY++;
			}
			return level;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
