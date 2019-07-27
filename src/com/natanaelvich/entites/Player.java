package com.natanaelvich.entites;

import com.natanaelvich.main.Game;
import com.natanaelvich.wolrd.Camera;
import com.natanaelvich.wolrd.World;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    public boolean right, left, up, down;
    public double speed = 1.4;
    public int dir_right = 0, dir_left = 1;
    public int dir = dir_right;
    private int frames = 0, maxFrame = 5, index = 0, maxIndex = 3;
    private boolean moved = false;
    private BufferedImage[] playerRight;
    private BufferedImage[] playerLeft;
    public double life = 10, maxlife = 100;
    public int ammo = 0;
    private BufferedImage playerdamage;
    public boolean isdamage = false;
    private int damageFrames = 0;
    private boolean arma = false;
    public boolean atirar = false, mouseTiro = false;
    public int mx, my;

    public Player(int x, int y, int w, int h, BufferedImage sprite) {
        super(x, y, w, h, sprite);
        playerRight = new BufferedImage[4];
        playerLeft = new BufferedImage[4];
        playerdamage = Game.spritesheet.getSprite(0, 16, 16, 16);
        //
        for (int i = 0; i < 4; i++) {
            playerRight[i] = Game.spritesheet.getSprite(32 + (i * 16), 0, 16, 16);
        }
        for (int i = 0; i < 4; i++) {
            playerLeft[i] = Game.spritesheet.getSprite(32 + (i * 16), 16, 16, 16);
        }
    }

    @Override
    public void tick() {
        moved = false;
        if (right && World.isFree((int) (x + speed), this.getY())) {
            moved = true;
            dir = dir_right;
            x += speed;

        } else if (left && World.isFree((int) (x - speed), this.getY())) {
            moved = true;
            dir = dir_left;
            x -= speed;
        }

        if (up && World.isFree(this.getX(), (int) (y - speed))) {
            moved = true;
            y -= speed;
        } else if (down && World.isFree(this.getX(), (int) (y + speed))) {
            moved = true;
            y += speed;
        }

        if (moved) {
            frames++;
            if (frames == maxFrame) {
                frames = 0;
                index++;
                if (index > maxIndex) {
                    index = 0;
                }

            }
        }
        //checar colsisao com intens
        checkVida();
        checkAmmo();
        checkArma();
        //animação de dano
        if (isdamage) {
            this.damageFrames++;
            if (this.damageFrames == 30) {
                this.damageFrames = 0;
                isdamage = false;
            }
        }
        //Atirando com o player
        if (atirar) {
            atirar = false;
            if (arma && ammo > 0) {
                ammo--;
                int px = 0;
                int py = 5;
                int dx = 0;
                if (dir == dir_right) {
                    px = 18;
                    dx = 1;
                } else {
                    px = -8;
                    dx = -1;
                }
                Atirar atirarbalas = new Atirar(this.getX() + px, this.getY() + py, 3, 3, null, dx, 0);
                Game.atirar.add(atirarbalas);
            }
        }
        if (mouseTiro) {
            mouseTiro = false;

            if (arma && ammo > 0) {
                ammo--;

                int px = 0, py = 8;

                double angulo = 0;
                if (dir == dir_right) {
                    px = 18;
                    angulo = Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + 8 - Camera.x));
                } else {
                    px = -8;
                    angulo = Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + 8 - Camera.x));
                }
                double dx = Math.cos(angulo);
                double dy = Math.sin(angulo);
                Atirar atirarbalas = new Atirar(this.getX() + px, this.getY() + py, 3, 3, null, dx, dy);
                Game.atirar.add(atirarbalas);
            }
        }
        //Game Over
        if (life <= 0) {
            Game.gameStat  = "GameOver";
        }
        //camera acompanhar o jogador
        Camera.x = Camera.clamp(this.getX() - (Game.w / 2), 0, World.width * 16 - Game.w);
        Camera.y = Camera.clamp(this.getY() - (Game.h / 2), 0, World.heght * 16 - Game.h);

    }

    public void checkVida() {
        for (int i = 0; i < Game.entitys.size(); i++) {
            Entity atual = Game.entitys.get(i);
            if (atual instanceof Vida) {
                if (Entity.isColidding(this, atual)) {
                    life += 10;
                    if (life > 100) {
                        life = 100;
                        Game.entitys.remove(atual);
                    }
                }
            }

        }
    }

    public void checkAmmo() {
        for (int i = 0; i < Game.entitys.size(); i++) {
            Entity atual = Game.entitys.get(i);
            if (atual instanceof Municao) {
                if (Entity.isColidding(this, atual)) {
                    ammo += 100;
                    System.out.println("munição atual : " + ammo);
                    Game.entitys.remove(atual);
                }
            }

        }
    }

    public void checkArma() {
        for (int i = 0; i < Game.entitys.size(); i++) {
            Entity atual = Game.entitys.get(i);
            if (atual instanceof Arma) {
                if (Entity.isColidding(this, atual)) {
                    arma = true;
                    System.out.println("munição atual : " + ammo);
                    Game.entitys.remove(atual);
                }
            }

        }
    }

    @Override
    public void render(Graphics g) {
        if (!isdamage) {
            if (dir == dir_right) {
                g.drawImage(playerRight[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                //desenhar arma para direita
                if (arma) {
                    g.drawImage(Entity.arma_right, this.getX() - Camera.x + 8, this.getY() - Camera.y, null);
                }
            } else if (dir == dir_left) {
                g.drawImage(playerLeft[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                //desenhar arma para esquerda
                if (arma) {
                    g.drawImage(Entity.arma_left, this.getX() - Camera.x - 8, this.getY() - Camera.y, null);

                }
            }

        } else {
            g.drawImage(playerdamage, this.getX() - Camera.x, this.getY() - Camera.y, null);
        }
    }

}
