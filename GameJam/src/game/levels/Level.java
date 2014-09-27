package game.levels;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Level {
	
	public final static int CELL_SIZE = 60;
	
	public ArrayList<Block> blocks = new ArrayList<Block>();
	public Block[][] grid;
	
	public Block goal;	
	public Block start;
	
	public String name;
	public int width;
	public int height;
	
	public static Level[] levels;
	
	public static int currentLevel = 6;
	
	public Level(String name) {
		this.name = name;
	}
	
	public void mapToArray() {
		grid = new Block[width][height];
		for (Block block : blocks) {
			grid[block.xCoord][block.yCoord] = block;
		}
	}
	
	public static Level[] loadLevels() {
		File[] fs = new File("./levels/").listFiles();
		Level[] levels = new Level[fs.length];
		int i = 0;
		for (File f : fs) {
			System.out.println(f.getName());
			levels[i++] = parseLevel(f);
		}
		return levels;
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
						if (block.getBlockType() == 10)
							level.start = block;
						if (block.getBlockType() == 11)
							level.goal = block;
					}
				}
				gridX = 0;
				gridY++;
			}
			level.mapToArray();
			return level;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
