package Character;

import org.newdawn.slick.geom.Rectangle;

public interface Object {
	
	public int getX();
	public int getY();
	public void setX(int newX);
	public void setY(int newY);
	public void draw(int x, int y, float scale);
	public Rectangle getCollision();

}
