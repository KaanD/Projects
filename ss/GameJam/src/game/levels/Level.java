package game.levels;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Level {
	
	public final static int GRID_CELL_COUNT = 40;
	public final static int CELL_WIDTH = 50;
	
	public Block[][] grid = new Block[GRID_CELL_COUNT][GRID_CELL_COUNT];
	
	public String name;
	
	public Level(String name) {
		this.name = name;
	}
	
	public static Level parseLevel(File input) {
		try (Scanner s = new Scanner(input)) {
			Level level = new Level(s.nextLine());
			String[] xy = s.nextLine().split("x");
			int width = Integer.parseInt(xy[0]);
			int height = Integer.parseInt(xy[1]);
			level.grid = new Block[width][height];
			int gridX = 0, gridY = 0;
			while (s.hasNextLine()) {
				String line = s.nextLine();
				String[] elements = line.split(" ");
				for (String element : elements) {
					level.grid[gridX++][gridY] = 
							new Block(Block.getBlockType(element), gridX, gridY, CELL_WIDTH, CELL_WIDTH);
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
	
	public static void printLevel(Level level) {
		for (int y = 0; y < level.grid.length; y++) {
			for (int x = 0; x < level.grid[y].length; x++) {
				System.out.print(level.grid[x][y].getBlockType() + " ");
			}
			System.out.println();
		}
	}
	
}
