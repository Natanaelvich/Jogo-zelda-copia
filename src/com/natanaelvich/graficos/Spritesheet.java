/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.natanaelvich.graficos;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Spritesheet {
    private BufferedImage spritesheet;
    public Spritesheet(String path){
        try {
            spritesheet = ImageIO.read(getClass().getResource(path));
        } catch (IOException ex) {
            Logger.getLogger(Spritesheet.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    } public BufferedImage getSprite(int x,int y, int w, int h){
        return spritesheet.getSubimage(x, y, w, h);
        }
}
