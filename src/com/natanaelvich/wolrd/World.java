
package com.natanaelvich.wolrd;

import com.natanaelvich.entites.Arma;
import com.natanaelvich.entites.Entity;
import com.natanaelvich.entites.Inimigo;
import com.natanaelvich.entites.Municao;
import com.natanaelvich.entites.Vida;
import com.natanaelvich.main.Game;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class World {
    private Tile[] tiles;
    public static int width,heght;
    
    public World(String path){
        try {
            BufferedImage map  = ImageIO.read(getClass().getResource(path));
            //leitura de pixels
            int[] pixels = new  int[map.getWidth() * map.getHeight()];
            width = map.getWidth();
            heght = map.getHeight();
            tiles = new Tile[map.getWidth() * map.getHeight()];
            map.getRGB(0, 0, map.getWidth(),map.getHeight(), pixels, 0, map.getHeight());
                 ////////////////////////////////////////
             for(int xx=0;xx<map.getWidth();xx++){
             for(int yy=0;yy<map.getHeight();yy++){
                 int pixelAtual = pixels[xx+(yy*map.getWidth())];
                 tiles[xx+(yy*map.getWidth())] = new Tile_Florr(xx*16, yy*16,Tile.TILE_FLORRPRINCIPAL);
                 if (pixelAtual==0xff000000) {
                     //chao
                 tiles[xx+(yy*map.getWidth())] = new Tile_Florr(xx*16, yy*16,Tile.TILE_FLORRPRINCIPAL);
                 }
                 else if(pixelAtual==0xffffffff){
                     //parede
                 tiles[xx+(yy*map.getWidth())] = new Tile_Florr(xx*16, yy*16,Tile.TILE_WALLPRINCIPAL);
                 }
                 else if(pixelAtual==0xFF0026ff){
                     //player
                  Game.player.setX(xx*16);
                  Game.player.setY(yy*16);
                 }
                 else if(pixelAtual==0xFFFF0000) {
                  //inimigo
                  Game.entitys.add(new Inimigo(xx*16, yy*16, 16, 16,Entity.inimigo_en));
                 }
                 else if(pixelAtual==0xFFFFD800) {
                  //municao
                  Game.entitys.add(new Municao(xx*16, yy*16, 16, 16,Entity.municao_en));
                 }
                 else if(pixelAtual==0xFF7F3300) {
                  //arma
                  Game.entitys.add(new Arma(xx*16, yy*16, 16, 16,Entity.arma_en));
                 }
                 else if(pixelAtual==0xFFFF00DC) {
                  //life
                  Game.entitys.add(new Vida(xx*16, yy*16, 16, 16,Entity.life_en));
                 }}}} catch (IOException ex) {
                      Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
                      }
                      }
    public void render(Graphics g){
        int xstart  = Camera.x>>4;
        int ystart = Camera.y>>4;
        int xfinal = xstart  + (Game.w>>4);
        int yfinal = ystart  + (Game.h>>4);
        
        
        for(int xx = xstart ; xx<= xfinal ; xx++){
        for(int yy = ystart ; yy <= yfinal ; yy++){
        if(xx<0||yy<0||xx>=width||yy>=heght)
        continue;
        Tile tile = tiles[xx+(yy*width)];
        tile.render(g);
    
    }
    }
    
    }
}