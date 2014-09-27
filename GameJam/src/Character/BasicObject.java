package Character;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public class BasicObject implements Object{

	
	private Image image;
	private int x,y,width,length;
	private Rectangle collision;
	
	public BasicObject(int newX, int newY, int newWidth, int newLength, Image newImage){
		x = newX;
		y = newY;
		width = newWidth;
		length = newLength;
		image = newImage;
		collision = new Rectangle(x,y,width,length);
	}
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}

	public void setX(int newX) {
		this.x = newX;
		this.collision.setX(newX);
	}
	public void setY(int newY) {
		this.y = newY;
		this.collision.setY(newY);
	}
	public void setXY(int newX, int newY){
		this.setX(newX);
		this.setY(newY);
	}
	public int getWidth(){
		return this.width;
	}
	public int getLength(){
		return this.length;
	}
	public void setImage(Image newImage){
		image = newImage;
	}
	public void draw(int x, int y, float scale){
		if(image != null)
			image.draw(x,y,scale);	
		else System.out.println("Error: No sprite to draw");
	}
	public Rectangle getCollision() {
		return this.collision;
	}

	public void setCollision(Rectangle newCollision){
		collision = newCollision;
	}
	public Image getImage(){
		return image;
	}
	public boolean collidesWith(Object x)
	{
		return getCollision().intersects(x.getCollision());
	}
	
	public boolean collisionCheck(Object x, Object y){
		return x.getCollision().intersects(y.getCollision())||x.getCollision().contains(y.getCollision());
	}
	public Rectangle getFutureCollision(int direction) {
		Rectangle rect = new Rectangle(this.getCollision().getX(),this.getCollision().getY(),
				this.getCollision().getWidth(), this.getCollision().getHeight());
		switch (direction)
		{
		//up
		case 0: 
			rect.setY(rect.getY()-1);
			break;
		//right
		case 1:
			rect.setX(rect.getX()+1);
			break;
		//down
		case 2:
			rect.setY(rect.getY()+1);
			break;
		//left
		case 3:
			rect.setX(rect.getX()-1);
			break;	
		}
		return rect;
	}
}
