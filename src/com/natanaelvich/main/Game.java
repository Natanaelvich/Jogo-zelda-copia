/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.natanaelvich.main;

import com.natanaelvich.entites.Entity;
import com.natanaelvich.entites.Player;
import com.natanaelvich.graficos.Spritesheet;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1l;
    public final int w = 240;
    public final int h = 120;
    public final int scale = 3;
    public BufferedImage layer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    private Thread thread;
    private boolean isRunning = true;
    public List<Entity> entitys;
    private BufferedImage image;
    public Spritesheet spritesheet;
   
    public Game(){        
    this.setPreferredSize(new Dimension(w*scale, h*scale));
    initFrame();
    //inicializando objetos
    image  = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
    entitys = new ArrayList<Entity>();
    spritesheet = new Spritesheet("/spritesheet.png");
    entitys.add( new Player(0,0,16,16,spritesheet.getSprite(32, 0, 16, 16)));
    
   
    }
    //janela grfica
    public void initFrame(){
    JFrame frame = new JFrame("jogoZelda");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);   
    
    }
    //start
    public void start(){
    thread  = new Thread(this);
    isRunning  = true;
    thread.start();  
    }
    public synchronized void stop(){
    isRunning  = false;
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void tick(){
    for(int i=0;i<entitys.size();i++){
    Entity e  = entitys.get(i);
    e.tick();
    }
    
    
    }
    //rederizar
    public void render(){
        BufferStrategy bs  = this.getBufferStrategy();
        if(bs == null){
        this.createBufferStrategy(3);
        return;
        }
        Graphics g  = image.getGraphics();
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, w, h);
        
       //rederização do jogo
       // Graphics2D g2  = (Graphics2D) g;
        for(int i=0;i<entitys.size();i++){
        Entity e  = entitys.get(i);
         e.render(g);
    }
        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image,0,0, w*scale, h*scale, null);
        bs.show();
    }

    @Override
    public void run() {
        while (true) {            
          tick();
          render();
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
        public static void main(String[] args) {
    Game game  = new Game();
    game.start();     
        
    }
    
}

