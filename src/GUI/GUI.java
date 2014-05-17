/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;
import javax.swing.JPanel;
import GraphicOb.Brick;
import GraphicOb.Bullet;
import GraphicOb.CoinPile;
import GraphicOb.LifePack;
import GraphicOb.Player;
import GraphicOb.Stone;
import GraphicOb.Water;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;

/**
 *
 * @author Madawa
 */
public class GUI extends JPanel implements ActionListener {

    private ArrayList<CoinPile> coins;
    private ArrayList<LifePack> lifePacks;
    private int x, y;
    private Player[] players;
    private int direction;
    private Timer timer;
    private Brick[] bricks;
    private Water[] water;
    private Stone[] stone;
    private int dir;
    private ArrayList<Bullet> bullet;
    private boolean status = false;
    private AffineTransform transform;
    private ImageIcon background = new ImageIcon("game.jpg");

    public GUI() {
        transform = new AffineTransform();
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
    }

    public void paint(Graphics graphic) {
        super.paint(graphic);
        Graphics2D g2d = (Graphics2D) graphic;
        g2d.drawImage(background.getImage(), 0, 0, this);
        drawMap(graphic);
        drawPlayers(graphic);
        drawCoinPiles(graphic);
        drawLifePacks(graphic);
        drawBullets(graphic);
        Toolkit.getDefaultToolkit().sync();
        graphic.dispose();
    }

    public void initialize(Brick[] bricks, Water[] water, Stone[] stones, Player[] player, ArrayList<CoinPile> coins, ArrayList<LifePack> lifePacks, ArrayList<Bullet> bullet) {
        this.bricks = bricks;
        this.stone = stones;
        this.players = player;
        this.water = water;
        this.coins = coins;
        this.lifePacks = lifePacks;
        this.bullet = bullet;
        status = true;
        timer = new Timer(25, this);
        timer.start();
    }

    public void drawMap(Graphics graphic) {
        Graphics2D g = (Graphics2D) graphic;
        for (int i = 0; i < bricks.length; i++) {
            x = bricks[i].getX();
            y = bricks[i].getY();
            g.drawImage(bricks[i].getBrick().getImage(), x, y, this);
        }
        for (int i = 0; i < stone.length; i++) {
            x = stone[i].getX();
            y = stone[i].getY();
            g.drawImage(stone[i].getStone().getImage(), x, y, this);
        }
        for (int i = 0; i < water.length; i++) {
            x = water[i].getX();
            y = water[i].getY();
            g.drawImage(water[i].getWater().getImage(), x, y, this);
        }

    }

    public void drawPlayers(Graphics g) {
        int rotation;
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < players.length; i++) {
            x = players[i].getNextX();
            y = players[i].getNextY();
            direction = players[i].getDirection();

            dir = players[i].getPresentD();
            rotation = players[i].getRotation();
            transform.setToTranslation(x, y);
            transform.rotate(Math.toRadians(rotation), 12.5, 12.5);
            g2d.drawImage(players[i].getNorth().getImage(), transform, this);
        }
    }

    public void drawCoinPiles(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < coins.size(); i++) {
            x = coins.get(i).getX();
            y = coins.get(i).getY();
            g2d.drawImage(coins.get(i).getCoin().getImage(), x, y, this);
        }
    }

    public void drawLifePacks(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < lifePacks.size(); i++) {
            x = lifePacks.get(i).getX();
            y = lifePacks.get(i).getY();
            g2d.drawImage(lifePacks.get(i).getLife().getImage(), x, y, this);
        }
    }

    public void actionPerformed(ActionEvent e) {
        checkForTimeExpired();
        checkForGrab();
        moveBullets();
        movePlayers();
        //ditectCollisions();
        repaint();
    }

    public void checkForTimeExpired() {
        long timeNow = System.currentTimeMillis();
        long dif;
        long pileTime;
        long coinTime;
        if (lifePacks != null) {
            for (int i = 0; i < lifePacks.size(); i++) {
                dif = timeNow - lifePacks.get(i).getInitiateTime();
                pileTime = lifePacks.get(i).getLifeTime();
                if (pileTime <= dif) {
                    System.out.println("called");
                    lifePacks.remove(i);
                    i = i - 1;
                }

            }
        }
        if (coins != null) {
            for (int i = 0; i < coins.size(); i++) {
                dif = timeNow - coins.get(i).getInitiateTime();
                coinTime = coins.get(i).getLifeTime();
                if (coinTime <= dif) {
                    coins.remove(i);
                    i = i - 1;
                }
            }
        }
    }

    public void checkForGrab() {
        int xx;
        int yy;
        int px;
        int py;
        if (coins != null) {
            for (int i = 0; i < coins.size(); i++) {
                xx = coins.get(i).getX();
                yy = coins.get(i).getY();
                for (int j = 0; j < players.length; j++) {
                    px = players[j].getX();
                    py = players[j].getY();
                    if (px == xx && py == yy) {
                        coins.remove(i);
                        if (coins.size() > 0) {
                            i = i - 1;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < lifePacks.size(); i++) {
            xx = lifePacks.get(i).getX();
            yy = lifePacks.get(i).getY();

            for (int j = 0; j < players.length; j++) {
                px = players[j].getX();
                py = players[j].getY();
                if (px == xx && py == yy) {
                    lifePacks.remove(i);
                    if (lifePacks.size() > 0) {
                        i = i - 1;
                    }
                }
            }
        }
    }

    public void ditectCollisions() {
        Rectangle one, two;
        for (int i = 0; i < bullet.size(); i++) {
            one = bullet.get(i).getRec();
            for (int j = 0; j < stone.length; j++) {
                two = stone[j].getRec();
                //    System.out.println(two.width+"and"+two.OUT_BOTTOM+"and"+two.OUT_LEFT+"and"+two.OUT_RIGHT);
                if (one.intersects(two)) {

                    if (bullet.size() > 0) {
                        bullet.remove(i);
                        i = i - 1;
                        System.out.println("Bullet Removed1");
                    }
                }
            }
            for (int j = 0; j < players.length; j++) {
                two = players[j].getRec();
                if (one.intersects(two) && bullet.get(i).getShootBy() != j) {

                    if (bullet.size() > 0) {
                        bullet.remove(i);
                        i = i - 1;
                        System.out.println("Bullet Removed2");
                    }
                }
            }
            for (int j = 0; j < bricks.length; j++) {
                two = bricks[j].getRec();
                if (one.intersects(two)) {

                    if (bullet.size() > 0) {
                        bullet.remove(i);
                        i = i - 1;
                        System.out.println("Bullet Removed3");
                    }
                }
            }
            if (bullet.size() > 0) {
                if (!bullet.get(i).isVisible()) {

                    if (bullet.size() > 0) {
                        bullet.remove(i);
                        i = i - 1;
                        System.out.println("Bullet Removed4");
                    }
                }
            }
        }

    }

    public void drawBullets(Graphics g) {
        int bDirection;
        Graphics2D g2d = (Graphics2D) g;
        //  System.out.println("Bullet Size"+bullet.size());
        for (int i = 0; i < bullet.size(); i++) {
            bDirection = bullet.get(i).getDirection();
            x = bullet.get(i).getX();
            y = bullet.get(i).getY();
            if (bDirection == 0 && bullet.get(i).isVisible()) {

                g2d.drawImage(bullet.get(i).getBullet().getImage(), x, y, this);
            } else if (bDirection == 1 && bullet.get(i).isVisible()) {

                transform.setToTranslation(x, y);
                transform.rotate(Math.toRadians(90), 12.5, 12.5);
                g2d.drawImage(bullet.get(i).getBullet().getImage(), transform, this);
            } else if (bDirection == 2 && bullet.get(i).isVisible()) {
                transform.setToTranslation(x, y);
                transform.rotate(Math.toRadians(180), 12.5, 12.5);
                g2d.drawImage(bullet.get(i).getBullet().getImage(), transform, this);
            } else if (bDirection == 3 && bullet.get(i).isVisible()) {
                transform.setToTranslation(x, y);
                transform.rotate(Math.toRadians(270), 12.5, 12.5);
                g2d.drawImage(bullet.get(i).getBullet().getImage(), transform, this);
            }
            System.out.println("Bullet Moving");
        }
    }

    public void moveBullets() {
        for (int i = 0; i < bullet.size(); i++) {
            bullet.get(i).move();
        }
    }

    public void movePlayers() {
        for (int i = 0; i < players.length; i++) {
            players[i].move();
            players[i].rotateImage();
        }
    }
}
