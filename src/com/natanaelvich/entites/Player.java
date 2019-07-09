
package com.natanaelvich.entites;

import com.natanaelvich.main.Game;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player  extends Entity{
    public boolean right,left,up,down;
    public double speed = 0.8;
    public int dir_right = 0,dir_left = 1;
    public int dir= dir_right;
    private int frames = 0,maxFrame = 5,index = 0, maxIndex = 3;
    private boolean moved = false;
    private BufferedImage[] playerRight;
    private BufferedImage[] playerLeft;

    
    public Player(int x, int y, int w, int h, BufferedImage sprite) {
        super(x, y, w, h, sprite);
        playerRight = new BufferedImage[4];
        playerLeft = new  BufferedImage[4];
        //
        for(int i=0;i<4;i++){
        playerRight[i] = Game.spritesheet.getSprite(32+(i*16), 0, 16, 16);
        }
         for(int i=0; i<4 ;i++){
        playerLeft[i] = Game.spritesheet.getSprite(32+(i*16), 16, 16, 16);
        }
    }
    public void tick(){
        moved = false;
        if (right) {
            moved = true;
            dir= dir_right;
            x+=speed;
            
        }else if (left) {
            moved = true;
            dir = dir_left;
            x-=speed;
        }if (up) {
            moved = true;
            y-=speed;
        }else if (down) {
            moved = true;
            y+=speed;
        }
        if(moved){
        frames ++;
        if(frames==maxFrame){
        frames = 0;
        index++;
        if(index>maxIndex){
        index = 0;
        }
        }
        }
    
    }

    @Override
    public void render(Graphics g){
        if(dir == dir_right){
        g.drawImage(playerRight[index], this.getX(), this.getY(),null);
        }
        else if(dir == dir_left){
        g.drawImage(playerLeft[index], this.getX(), this.getY(),null);
        } 
        
    }

    
}
