
package projeto_jogocopia_zelda;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class PROJETO_JogoCopia_Zelda extends Canvas implements Runnable {

    private static final long serialVersionUID = 1l;
    public static  int w = 240;
    public static int h = 120;
    public static int scale = 3;
    public BufferedImage layer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    public Player player  = new Player();
    public PROJETO_JogoCopia_Zelda(){        
    this.setPreferredSize(new Dimension(w*scale, h*scale));
    
    }
    

    public static void main(String[] args) {
        
        //impede de fechar a janela do jogo de fecha o processo ao encerrar o jogo
        PROJETO_JogoCopia_Zelda zelda = new PROJETO_JogoCopia_Zelda();
        JFrame frame = new JFrame("jogoZelda");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(zelda);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);        
        //////////////////////////////////////////////////////////////////////////
        new Thread(zelda).start();
    }
    public void tick(){}
    public void render(){
        BufferStrategy bs  = this.getBufferStrategy();
        if(bs == null){
        this.createBufferStrategy(3);
        return;
        }
        Graphics g = layer.getGraphics();
        player.render(g);
        g = bs.getDrawGraphics();
        g.drawImage(layer,0,0, w*scale, h*scale, null);
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
                Logger.getLogger(PROJETO_JogoCopia_Zelda.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
}
