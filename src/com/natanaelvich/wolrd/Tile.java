
package com.natanaelvich.wolrd;

import com.natanaelvich.main.Game;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
    protected static BufferedImage TILE_FLORRPRINCIPAL = Game.spritesheet.getSprite(0, 0, 16, 16);
    protected static BufferedImage TILE_WALLPRINCIPAL = Game.spritesheet.getSprite(16, 0, 16, 16);
    
    private int x,y;
    private BufferedImage sprite;

    public Tile(int x, int y, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public void render(Graphics g){
    g.drawImage(sprite, x-Camera.x, y-Camera.y, null);
    }
    
}
