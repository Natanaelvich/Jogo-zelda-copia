package com.natanaelvich.main;

import com.natanaelvich.wolrd.World;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Menu {

    public String[] options = {"novo jogo", "carregar jogo", "sair"};
    public int currentOptions = 0;
    public int maxCurrentOpt = options.length - 1;
    public boolean up, down, enter;
    public static boolean pause = false;
    public static boolean saveExists = false;
    public static boolean saveGame = false;

    public static String loadGame(int encode) {
        String line = "";
        File file = new File("save.txt");
        if (file.exists()) {
            try {
                String singleLine = null;
                BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
                try {
                    while ((singleLine = reader.readLine()) != null) {
                        String[] transicao = singleLine.split(":");
                        char[] val = transicao[1].toCharArray();
                        transicao[1] = "";
                        for (int i = 0; i < val.length; i++) {
                            val[i] -= encode;
                            transicao[1] += val[i];
                        }
                        line += transicao[0];
                        line += ":";
                        line += transicao[1];
                        line += "/";

                    }
                } catch (IOException e) {
                }

            } catch (FileNotFoundException e) {
            }
        }
        return line;
    }

    public static void applySave(String str) {
        String[] spl = str.split("/");
        for (int i = 0; i < spl.length; i++) {
            String[] spl2 = spl[i].split(":");
            switch (spl2[0]) {
                case "level":
                    World.restartGame("level" + spl2[1] + ".png");
                    Game.gameStat = "Normal";
                    pause = false;
                    break;
                case "vida":
                    Game.player.life = Double.parseDouble(spl2[1]);
                    break;
            }
        }
    }

    //metodo para salvar o game
    public static void save(String[] val1, int[] val2, int encode) {
        BufferedWriter write = null;
        try {
            write = new BufferedWriter(new FileWriter("save.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < val1.length; i++) {
            String current = val1[i];
            current += ":";
            char[] value = Integer.toString(val2[i]).toCharArray();
            for (int j = 0; j < value.length; j++) {
                value[j] += encode;
                current += value[j];
            }
            try {
                write.write(current);
                if (i < val1.length - 1) {
                    write.newLine();
                }
            } catch (IOException e) {
            }
        }
        try {
            write.flush();
            write.close();
        } catch (IOException e) {
        }
    }

    public void tick() {
        File file = new File("save.txt");
        if (file.exists()) {
            saveExists = true;
        } else {
            saveExists = false;
        }
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
            if (options[currentOptions] == "novo jogo" || options[currentOptions] == "continuar") {
                System.out.println("enter");
                Game.gameStat = "Normal";
                pause = false;
                file = new File("save.txt");
                file.delete();
            } else if (options[currentOptions] == "carregar jogo") {
                System.out.println("carregar");
                file = new File("save.txt");
                if (file.exists()) {
                    String saver = loadGame(10);
                    applySave(saver);
                }
            } else if (options[currentOptions] == "sair") {
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
        if (pause == false) {
            g.drawString("Novo Jogo", Game.w * Game.scale / 2 - 90, Game.h * Game.scale / 2 - 60);
        } else {
            g.drawString("Continuar", Game.w * Game.scale / 2 - 90, Game.h * Game.scale / 2 - 60);
        }
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
