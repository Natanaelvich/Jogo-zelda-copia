package com.natanaelvich.main;

import com.natanaelvich.entites.Entity;
import com.natanaelvich.entites.Inimigo;
import com.natanaelvich.entites.Player;
import com.natanaelvich.graficos.Spritesheet;
import com.natanaelvich.graficos.UI;
import com.natanaelvich.wolrd.World;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {

    private static final long serialVersionUID = 1l;
    public static final int w = 240;
    public static final int h = 120;
    public static final int scale = 3;
    public BufferedImage layer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    private Thread thread;
    private boolean isRunning = true;
    public static List<Entity> entitys;
    public static List<Inimigo> inimigos;
    private BufferedImage image;
    public static Spritesheet spritesheet;
    public static Player player;
    public static World world;
    public static Random rand;
    public static UI ui;

    public Game() {
        rand = new Random();
        addKeyListener(this);
        this.setPreferredSize(new Dimension(w * scale, h * scale));
        initFrame();
        //inicializando objetos
        ui = new UI();
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        entitys = new ArrayList<Entity>();
        inimigos = new ArrayList<Inimigo>();
        spritesheet = new Spritesheet("/spritesheet.png");
        player = new Player(0, 0, 16, 16, spritesheet.getSprite(32, 0, 16, 16));
        entitys.add(player);
        world = new World("/map.png");  
    }

    //janela grfica
    public void initFrame() {
        JFrame frame = new JFrame("jogoZelda");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    //start
    public void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void tick() {
        for (int i = 0; i < entitys.size(); i++) {
            Entity e = entitys.get(i);
            e.tick();
        }

    }

    //rederizar
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, w, h);

        //rederização do jogo
        world.render(g);
        for (int i = 0; i < entitys.size(); i++) {
            Entity e = entitys.get(i);
            e.render(g);
        }
        ui.render(g);
        /////////
        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, w * scale, h * scale, null);
        bs.show();
    }

    @Override
    public void run() {
     
        long lasttime = System.nanoTime();
        double amountofticks = 60.0;
        double ns = 1000000000 / amountofticks;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lasttime) / ns;
            lasttime = now;
            if (delta >= 1) {
                tick();
                render();
                frames++;
                delta--;
            }
            if (System.currentTimeMillis() - timer >= 1000) {
                System.out.println("FPS: " + frames);
                frames = 0;
                timer += 1000;
            }
        }
        stop();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();

    }
    //eventos do teclado    

    @Override
    public void keyTyped(KeyEvent ke) {
        //movendo para os ladoas
        if (ke.getKeyCode() == KeyEvent.VK_RIGHT || ke.getKeyCode() == KeyEvent.VK_D) {
            player.right = true;

        } else if (ke.getKeyCode() == KeyEvent.VK_LEFT || ke.getKeyCode() == KeyEvent.VK_A) {
            player.left = true;
        }
        //movendo para cima e para baixo
        if (ke.getKeyCode() == KeyEvent.VK_UP || ke.getKeyCode() == KeyEvent.VK_W) {
            player.up = true;

        } else if (ke.getKeyCode() == KeyEvent.VK_DOWN || ke.getKeyCode() == KeyEvent.VK_S) {
            player.down = true;

        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        //movendo para os ladoas
        if (ke.getKeyCode() == KeyEvent.VK_RIGHT || ke.getKeyCode() == KeyEvent.VK_D) {
            player.right = true;

        } else if (ke.getKeyCode() == KeyEvent.VK_LEFT || ke.getKeyCode() == KeyEvent.VK_A) {
            player.left = true;
        }
        //movendo para cima e para baixo
        if (ke.getKeyCode() == KeyEvent.VK_UP || ke.getKeyCode() == KeyEvent.VK_W) {
            player.up = true;

        } else if (ke.getKeyCode() == KeyEvent.VK_DOWN || ke.getKeyCode() == KeyEvent.VK_S) {
            player.down = true;

        }

    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //movendo para os ladoas
        if (ke.getKeyCode() == KeyEvent.VK_RIGHT || ke.getKeyCode() == KeyEvent.VK_D) {
            player.right = false;

        } else if (ke.getKeyCode() == KeyEvent.VK_LEFT || ke.getKeyCode() == KeyEvent.VK_A) {
            player.left = false;
        }
        //movendo para cima e para baixo
        if (ke.getKeyCode() == KeyEvent.VK_UP || ke.getKeyCode() == KeyEvent.VK_W) {
            player.up = false;

        } else if (ke.getKeyCode() == KeyEvent.VK_DOWN || ke.getKeyCode() == KeyEvent.VK_S) {
            player.down = false;

        }
    }

}
