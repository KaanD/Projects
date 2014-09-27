package game.levels;
 
import game.display.sprites.Sprite;

import java.awt.Graphics;
import java.awt.Rectangle;
 
 
public abstract class BasicObject {
     
    private Sprite sprite;
    private int x,y,width,height;
    private Rectangle collision;
    
   public BasicObject(int newX, int newY, int newWidth, int newHeight) {
       x = newX;
       y = newY;
       width = newWidth;
       height = newHeight;
       collision = new Rectangle(newX, newY, newWidth, newHeight);
   }
   
  public BasicObject(int newX, int newY, int newWidth, int newHeight, Sprite s) {
      x = newX;
      y = newY;
      width = newWidth;
      height = newHeight;
      collision = new Rectangle(newX, newY, newWidth, newHeight);
      this.sprite = s;
  }
     
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
 
    public void setX(int newX) {
        this.x = newX;
        this.collision.setLocation(newX, y);
    }
    public void setY(int newY) {
        this.y = newY;
        this.collision.setLocation(x, newY);
    }
    public void setLocation(int newX, int newY){
        this.setX(newX);
        this.setY(newY);
    }
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }
    public void setSprite(Sprite s){
        sprite = s;
    }
 
    public Rectangle getCollision() {
        return this.collision;
    }
 
    public void setCollision(Rectangle newCollision){
        collision = newCollision;
    }
    public Sprite getSprite(){
        return sprite;
    }
     
    public boolean collidesWith(BasicObject x)
    {
        return getCollision().intersects(x.getCollision());
    }
    
    public Rectangle getFutureCollision(int direction) {
        Rectangle rect = new Rectangle((int)collision.getX(),(int)collision.getY(),
                (int)collision.getWidth(), (int)collision.getHeight());
        switch (direction) {
        //up
        case 0: 
            rect.setLocation((int)rect.getX(), (int)(rect.getY()-1));
            break;
        //right
        case 1:
            rect.setLocation((int)(rect.getX()+1), (int)(rect.getY()));
            break;
        //down
        case 2:
            rect.setLocation((int)rect.getX(), (int)(rect.getY()+1));
            break;
        //left
        case 3:
            rect.setLocation((int)(rect.getX()-1), (int)(rect.getY()));
            break;  
        }
        return rect;
    }
    
    public abstract void draw(Graphics g);
    
}