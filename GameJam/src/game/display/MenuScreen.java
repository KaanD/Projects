package game.display;

import game.Placeholder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MenuScreen {

	BufferedImage title;
	
	public MenuScreen(Placeholder main) {
		try {
			title = ImageIO.read(new File("./sprites/title.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void paint(Graphics g) {
		g.setColor(new Color(80,80,80));
		g.fillRect(0,0,5000,6000);
		g.drawImage(title, 70, 200, null);
	}

}
