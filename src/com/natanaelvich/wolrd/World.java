
package com.natanaelvich.wolrd;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class World {
    public Tile[] tiles;
    public static int width,heght;
    
    public World(String path){
        try {
            BufferedImage map  = ImageIO.read(getClass().getResource(path));
            //leitura de pixels
            int[] pixels = new  int[map.getWidth()*map.getHeight()];
            width = map.getWidth();
            heght = map.getHeight();
            tiles = new Tile[map.getWidth()*map.getHeight()];
            map.getRGB(0, 0, map.getWidth(),map.getHeight(), pixels, 0, map.getHeight());
             for(int xx=0;xx<map.getWidth();xx++){
             for(int yy=0;yy<map.getHeight();yy++){
                 int pixelAtual = pixels[xx+(yy*map.getWidth())];
                 if (pixelAtual==0xff000000) {
                     //tiles[xx+(yy*width)] = new Tile_Florr(xx, yy,);
                 }else if(pixelAtual==0xffffffff){
                 
                 
                 }else if(pixelAtual==0xff0026ff){
                 
                 
                 }else{
                 
                 }
                 
                 
             }
             
             
         }
        } catch (IOException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void render(Graphics g){
    for(int xx=0;xx<width;xx++){
    for(int yy=0;yy<heght;yy++){
    Tile tiles1 = tiles[xx+(yy*width)];
    tiles1.render(g);
    
    }
    }
    
    }
}
