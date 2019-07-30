package com.natanaelvich.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu {

    public String[] options = {"novo jogo", "carregar jogo", "sair"};
    public int currentOptions = 0, maxCurrentOpt = options.length - 1;
    public boolean up, down, enter ;
    public boolean  pause = false;

    public void tick() {
        if (up) {
            up = false;
            currentOptions--;
            if (currentOptions < 0) {
                currentOptions = maxCurrentOpt;
            }
        }
        if (down) {
            down = false;
            currentOptions++;
            if (currentOptions > maxCurrentOpt) {
                currentOptions = 0;
            }
        }
        if (enter) {
            enter = false;
            if (options[currentOptions] == "novo jogo" || options[currentOptions]=="continuar") {
                System.out.println("enter");
                Game.gameStat = "Normal";
                pause = false;
            }else if (options[currentOptions] == "sair" ) {
               System.exit(1);
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, Game.w * Game.scale, Game.h * Game.scale);
        g.setColor(Color.red);
        g.setFont(new Font("arial", Font.BOLD, 40));
        g.drawString("Bem Facil", Game.w * Game.scale / 2 - 110, Game.h * Game.scale / 2 - 100);
        //opçoes menu
        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.BOLD, 30));
        if(pause == false)
        g.drawString("Novo Jogo", Game.w * Game.scale / 2 - 90, Game.h * Game.scale / 2 - 60);
        else
        g.drawString("Continuar", Game.w * Game.scale / 2 - 90, Game.h * Game.scale / 2 - 60);
        g.drawString("Carregar Jogo", Game.w * Game.scale / 2 - 90, Game.h * Game.scale / 2 - 30);
        g.drawString("Sair", Game.w * Game.scale / 2 - 90, Game.h * Game.scale / 2);

        if (options[currentOptions] == "novo jogo") {
            g.drawString(">", Game.w * Game.scale / 2 - 110, Game.h * Game.scale / 2 - 60);
        } else if (options[currentOptions] == "carregar jogo") {
            g.drawString(">", Game.w * Game.scale / 2 - 110, Game.h * Game.scale / 2 - 30);
        } else if (options[currentOptions] == "sair") {
            g.drawString(">", Game.w * Game.scale / 2 - 110, Game.h * Game.scale / 2);
        }
    }
}
