
package com.natanaelvich.entites;
import com.natanaelvich.main.Game;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Entity {
    public static BufferedImage life_en = Game.spritesheet.getSprite(6*16, 0, 16, 16);
    public static BufferedImage arma_en = Game.spritesheet.getSprite(7*16, 0, 16, 16);
    public static BufferedImage municao_en = Game.spritesheet.getSprite(6*16, 16, 16, 16);
    public static BufferedImage inimigo_en = Game.spritesheet.getSprite(7*16, 16, 16, 16);
    protected double x;
    protected double y;
    protected int w;
    protected int h;
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
        return (int)x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return (int)y;
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
