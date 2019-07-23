package com.natanaelvich.entites;

import com.natanaelvich.main.Game;
import com.natanaelvich.wolrd.Camera;
import com.natanaelvich.wolrd.World;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    public boolean right, left, up, down;
    public double speed = 1.4;
    public int dir_right = 0, dir_left = 1;
    public int dir = dir_right;
    private int frames = 0, maxFrame = 5, index = 0, maxIndex = 3;
    private boolean moved = false;
    private BufferedImage[] playerRight;
    private BufferedImage[] playerLeft;
    public static double life  = 100, maxlife = 100;

    public Player(int x, int y, int w, int h, BufferedImage sprite) {
        super(x, y, w, h, sprite);
        playerRight = new BufferedImage[4];
        playerLeft = new BufferedImage[4];
        //
        for (int i = 0; i < 4; i++) {
            playerRight[i] = Game.spritesheet.getSprite(32 + (i * 16), 0, 16, 16);
        }
        for (int i = 0; i < 4; i++) {
            playerLeft[i] = Game.spritesheet.getSprite(32 + (i * 16), 16, 16, 16);
        }
    }

    @Override
    public void tick() {
        moved = false;
        if (right && World.isFree((int) (x + speed), this.getY())) {
            moved = true;
            dir = dir_right;
            x += speed;

        } else if (left && World.isFree((int) (x - speed), this.getY())) {
            moved = true;
            dir = dir_left;
            x -= speed;
        }
        
        if (up && World.isFree(this.getX(), (int) (y - speed))) {
            moved = true;
            y -= speed;
        } else if (down && World.isFree(this.getX(), (int) (y + speed))) {
            moved = true;
            y += speed;
        }
        
        if (moved) {
            frames++;
            if (frames == maxFrame) {
                frames = 0;
                index++;
                if (index > maxIndex) 
                    index = 0;
                
            }
        }
        //camera acompanhar o jogador
        Camera.x = Camera.clamp(this.getX() - (Game.w / 2), 0, World.width * 16 - Game.w);
        Camera.y = Camera.clamp(this.getY() - (Game.h / 2), 0, World.heght * 16 - Game.h);

    }

    @Override
    public void render(Graphics g) {
        if (dir == dir_right) {
            g.drawImage(playerRight[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
        } else if (dir == dir_left) {
            g.drawImage(playerLeft[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
        }

    }

}
