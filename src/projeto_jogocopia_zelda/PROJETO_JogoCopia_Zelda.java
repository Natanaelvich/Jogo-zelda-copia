
package projeto_jogocopia_zelda;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

public class PROJETO_JogoCopia_Zelda extends Canvas implements Runnable {
    private static final long serialVersionUID = 1l;
    public static  int w = 240;
    public static int h = 120;
    public static int scale = 3;
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
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
