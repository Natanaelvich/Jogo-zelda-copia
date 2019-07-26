package com.natanaelvich.entites;

import com.natanaelvich.main.Game;
import com.natanaelvich.wolrd.Camera;
import com.natanaelvich.wolrd.World;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Inimigo extends Entity {

    private double speed = 0.4;
    private int maskx = 8, masky = 8, maskw = 5, maskh = 5;
    private int frames = 0, maxFrame = 20, index = 0, maxIndex = 1;
    private BufferedImage[] sprites;
    private int life = 20;

    public Inimigo(int x, int y, int w, int h, BufferedImage sprite) {
        super(x, y, w, h, null);
        sprites = new BufferedImage[2];
        sprites[0] = Game.spritesheet.getSprite(112, 16, 16, 16);
        sprites[1] = Game.spritesheet.getSprite(112 + 16, 16, 16, 16);
    }

    @Override
    public void tick() {
        if (isColiddingPlayer() == false) {
            if ((int) x < Game.player.getX() && World.isFree((int) (x + speed), this.getY()) && !isColidding((int) (x + speed), this.getY())) {
                x += speed;
            } else if ((int) x > Game.player.getX() && World.isFree((int) (x - speed), this.getY()) && !isColidding((int) (x - speed), this.getY())) {
                x -= speed;
            }
            if ((int) y < Game.player.getY() && World.isFree(this.getX(), (int) (y + speed)) && !isColidding(this.getX(), (int) (y + speed))) {
                y += speed;
            } else if ((int) y > Game.player.getY() && World.isFree(this.getX(), (int) (y - speed)) && !isColidding(this.getX(), (int) (y - speed))) {
                y -= speed;
            }
        } else {
            //colidindo com player
            if (Game.rand.nextInt(100) < 10) {
                Game.player.life--;
                Game.player.isdamage = true;
                System.out.println("vida: " + Game.player.life);
            }
        }
        frames++;
        if (frames == maxFrame) {
            frames = 0;
            index++;
            if (index > maxIndex) {
                index = 0;

            }
        }
        //destruindo inimigo
        coliggingBullet();
        if (life <= 0) {
            destroySelf();
        }

    }

    public void destroySelf() {
        Game.entitys.remove(this);
    }

    public void coliggingBullet() {
        for (int i = 0; i < Game.atirar.size(); i++) {
            Entity e = Game.atirar.get(i);
            if (e instanceof Atirar) {
                if (Entity.isColidding(this, e)) {
                    life--;
                    Game.atirar.remove(i);
                    return;
                }
            }
        }

    }

    public boolean isColiddingPlayer() {
        Rectangle eneRectangle = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
        Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);
        return eneRectangle.intersects(player);
    }

    public boolean isColidding(int xnext, int ynext) {
        Rectangle inimigorRectangle = new Rectangle(xnext + maskx, ynext + masky, maskw, maskh);
        for (int i = 0; i < Game.inimigos.size(); i++) {
            Inimigo e = Game.inimigos.get(i);
            if (e == this) {
                continue;
            }
            Rectangle targetInimigo = new Rectangle(e.getX() + maskx, e.getY() + masky, maskw, maskh);
            if (inimigorRectangle.intersects(targetInimigo)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
        //super.render(g);
        // g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskw, maskh);
    }
}
