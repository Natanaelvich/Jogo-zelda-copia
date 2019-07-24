package com.natanaelvich.entites;

import com.natanaelvich.main.Game;
import com.natanaelvich.wolrd.Camera;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import jdk.nashorn.internal.codegen.CompilerConstants;

public class Entity {

    public static BufferedImage life_en = Game.spritesheet.getSprite(6 * 16, 0, 16, 16);
    public static BufferedImage arma_en = Game.spritesheet.getSprite(7 * 16, 0, 16, 16);
    public static BufferedImage municao_en = Game.spritesheet.getSprite(6 * 16, 16, 16, 16);
    public static BufferedImage inimigo_en = Game.spritesheet.getSprite(7 * 16, 16, 16, 16);
    public static BufferedImage arma_right = Game.spritesheet.getSprite(128,0, 16, 16);
    public static BufferedImage arma_left = Game.spritesheet.getSprite(128+16,0,16, 16);
    protected double x;
    protected double y;
    protected int w;
    protected int h;
    private BufferedImage sprite;
    public Graphics g;
    private int maskx, masky, mwidth, mheght;

    public Entity(int x, int y, int w, int h, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.sprite = sprite;

        this.maskx = 0;
        this.masky = 0;
        this.mwidth = w;
        this.mheght = h;
    }

    public void setMask(int maskx, int masky, int mwidth, int mheight) {
        this.maskx = maskx;
        this.masky = masky;
        this.mwidth = mwidth;
        this.mheght = mheight;
    }

    public int getX() {
        return (int) x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return (int) y;
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

    public void tick() {

    }

    public static boolean isColidding(Entity e1, Entity e2) {
        Rectangle e1mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheght);
        Rectangle e2mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.mwidth, e2.mheght);
        return e1mask.intersects(e2mask);
    }

    public void render(Graphics g) {
        g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
        //g.setColor(Color.red);
        //g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheght);
    }

}
