package game.levels;
 
import game.display.sprites.Sprite;

import java.awt.Graphics;
import java.awt.Rectangle;
 
 
public abstract class BasicObject {
     
    private Sprite sprite;
    public double x;
    public double y;
    public int width;
    public int height;
    
   public BasicObject(int newX, int newY, int newWidth, int newHeight) {
       x = newX;
       y = newY;
       width = newWidth;
       height = newHeight;
   }
   
  public BasicObject(int newX, int newY, int newWidth, int newHeight, Sprite s) {
      x = newX;
      y = newY;
      width = newWidth;
      height = newHeight;
      this.sprite = s;
  }
     
    public double getAbsX() {
        return this.x;
    }
    public double getAbsY() {
        return this.y;
    }
 
    public void setAbsX(double newX) {
        this.x = newX;
    }
    public void setAbsY(double newY) {
       this.y = newY;
    }
    public void setLocation(int newX, int newY){
        this.setAbsX(newX);
        this.setAbsY(newY);
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
 
    public Sprite getSprite(){
        return sprite;
    }
    
    public abstract void draw(Graphics g);
    
}