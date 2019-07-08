
package com.natanaelvich.entites;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Entity {
    private int x;
    private int y;
    private int w;
    private int h;
    private BufferedImage sprite;
    public Graphics g;
    
  

    public Entity(int x, int y, int w, int h,BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.sprite = sprite;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }
    
    public void render(Graphics g){
   g.drawImage(sprite,this.getX(), this.getY(), null);
    }
    public void tick(){
    
    }
}
