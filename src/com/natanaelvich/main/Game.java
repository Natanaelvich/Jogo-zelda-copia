package com.natanaelvich.main;

import com.natanaelvich.entites.Atirar;
import com.natanaelvich.entites.Entity;
import com.natanaelvich.entites.Inimigo;
import com.natanaelvich.entites.Player;
import com.natanaelvich.graficos.Spritesheet;
import com.natanaelvich.graficos.UI;
import com.natanaelvich.wolrd.World;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener {

    private static final long serialVersionUID = 1l;
    public static final int w = 240;
    public static final int h = 120;
    public static final int scale = 3;
    public BufferedImage layer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    private Thread thread;
    private boolean isRunning = true;
    public static List<Entity> entitys;
    public static List<Inimigo> inimigos;
    public static List<Atirar> atirar;
    private BufferedImage image;
    public static Spritesheet spritesheet;
    public static Player player;
    public static World world;
    public static Random rand;
    public static UI ui;
    private int cur_level = 1, max_level = 2;
    public static String gameStat = "Menu";
    private boolean showMensageGameOver = true;
    private int framesGameOver = 0;
    private boolean restartGame = false;
    public Menu menu;
    public boolean saveGame = false;
    public int[] pixels;

    public Game() {
        Sound.musicBack.loop();
        rand = new Random();
        addKeyListener(this);
        addMouseListener(this);
        this.setPreferredSize(new Dimension(w * scale, h * scale));
        initFrame();
        //inicializando objetos
        ui = new UI();
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        entitys = new ArrayList<>();
        inimigos = new ArrayList<>();
        atirar = new ArrayList<>();

        spritesheet = new Spritesheet("/spritesheet.png");
        player = new Player(0, 0, 16, 16, spritesheet.getSprite(32, 0, 16, 16));
        entitys.add(player);
        world = new World("/level1.png");
        menu = new Menu();
        /* try {
            newFont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(80f);
        } catch (FontFormatException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }*/
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
        if (gameStat == "Normal") {
            if (this.saveGame) {
                this.saveGame = false;
                String[] opt1 = {"level", "vida"};
                int[] opt2 = {this.cur_level, (int) player.life};
                Menu.save(opt1, opt2, 10);
                System.out.println("Salvo");
            }
            this.restartGame = false;
            for (int i = 0; i < entitys.size(); i++) {
                Entity e = entitys.get(i);
                e.tick();
            }
            for (int i = 0; i < atirar.size(); i++) {
                atirar.get(i).tick();
            }
            if (inimigos.size() == 0) {
                //avançãr de level
                cur_level++;
                if (cur_level > max_level) {
                    cur_level = 1;
                }
                //reiniciando o game
                String newWorld = "level" + cur_level + ".png";
                World.restartGame(newWorld);
            }
        } else if (gameStat == "GameOver") {
            this.framesGameOver++;
            if (framesGameOver == 15) {
                this.framesGameOver = 0;
                if (this.showMensageGameOver) {
                    this.showMensageGameOver = false;
                } else {
                    this.showMensageGameOver = true;
                }
            }

        }
        if (restartGame) {
            this.restartGame = false;
            this.gameStat = "Normal";
            cur_level = 1;
            //reiniciando o game
            String newWorld = "level" + cur_level + ".png";
            World.restartGame(newWorld);
        } else if (gameStat == "Menu") {
            menu.tick();
        }
    }
    //rederizar
/*
    public void drawRectangaleExemple(int xoof, int yoof) {
        for (int xx = 0; xx < 32; xx++) {
            for (int yy = 0; yy < 32; yy++) {
                int xOof = xx + xoof;
                int yOof = yy + yoof;
                if (xOof < 0 || yOof < 0 || xOof >= w || yOof >= h) {
                    continue;
                }
                pixels[xOof + (yOof * w)] = 0xff0000;
            }
        }
    }*/

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
        for (int i = 0; i < atirar.size(); i++) {
            atirar.get(i).render(g);
        }
        ui.render(g);
        /////////
        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, w * scale, h * scale, null);
        g.setFont(new Font("arial", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        g.drawString("Munição: " + player.ammo, 580, 18);

        if (gameStat == "GameOver") {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(0, 0, 0, 100));
            g2.fillRect(0, 0, w * scale, h * scale);
            g.setFont(new Font("arial", Font.BOLD, 40));
            g.setColor(Color.WHITE);
            g.drawString("GAME OVER", 240, 170);
            g.setFont(new Font("arial", Font.BOLD, 20));
            g.setColor(Color.WHITE);
            if (showMensageGameOver) {
                g.drawString(">pressione ENTER para reiniciar", 220, 210);
            }
        } else if (gameStat == "Menu") {
            menu.render(g);
        }
        /*  g.setFont(newFont);
        g.setColor(Color.red);
        g.drawString("Teste", 90, 90);
         */
        bs.show();
    }

    @Override
    public void run() {
        requestFocus();
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
            if (gameStat == "Menu") {
                menu.up = true;
            }
        } else if (ke.getKeyCode() == KeyEvent.VK_DOWN || ke.getKeyCode() == KeyEvent.VK_S) {
            player.down = true;
            if (gameStat == "Menu") {
                menu.down = true;
            }
        }
        if (ke.getKeyCode() == KeyEvent.VK_E) {
            player.atirar = true;

        }
        if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
            if (gameStat == "Menu") {
                menu.enter = true;
            }
            if (gameStat == "GameOver") {
                this.restartGame = true;
            }
        }
        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameStat = "Menu";
            menu.pause = true;
        }
        if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
            if (gameStat == "Normal") {
                this.saveGame = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke
    ) {

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
        if (ke.getKeyCode() == KeyEvent.VK_E) {
            player.atirar = true;
        }
    }

    @Override
    public void mouseClicked(MouseEvent me
    ) {
    }

    @Override
    public void mousePressed(MouseEvent me
    ) {
        player.mouseTiro = true;
        player.mx = (me.getX() / 3);
        player.my = (me.getY() / 3);
    }

    @Override
    public void mouseReleased(MouseEvent me
    ) {
    }

    @Override
    public void mouseEntered(MouseEvent me
    ) {
    }

    @Override
    public void mouseExited(MouseEvent me
    ) {
    }

}
