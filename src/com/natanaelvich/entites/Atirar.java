package com.natanaelvich.entites;

import com.natanaelvich.main.Game;
import com.natanaelvich.wolrd.Camera;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Atirar extends Entity {

    private final double dir_x;
    private final double dir_y;
    private final double spd = 4;
    private final int life = 30;
    private int currLife = 0;

    public Atirar(int x, int y, int w, int h, BufferedImage sprite, double dir_x, double dir_y) {
        super(x, y, w, h, sprite);
        this.dir_x = dir_x;
        this.dir_y = dir_y;
    }

    @Override
    public void tick() {
        x += dir_x * spd;
        y += dir_y *spd;
        currLife++;
        if (currLife == life) {
            Game.atirar.remove(this);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, w, h);
    }

}
