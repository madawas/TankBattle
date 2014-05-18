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
import GameObjects.Brick;
import GameObjects.Bullet;
import GameObjects.CoinPile;
import GameObjects.LifePack;
import GameObjects.Player;
import GameObjects.Stone;
import GameObjects.Water;
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
    private ArrayList<Bullet> bullets;
    private boolean status = false;
    private AffineTransform transform;
    private ImageIcon background = new ImageIcon("images/game.jpg");

    public GUI() {
        transform = new AffineTransform();
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
    }

    @Override
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

    public void initialize(Brick[] bricks, Water[] water, Stone[] stones, Player[] player, ArrayList<CoinPile> coins, ArrayList<LifePack> lifePacks, ArrayList<Bullet> bullets) {
        this.bricks = bricks;
        this.stone = stones;
        this.players = player;
        this.water = water;
        this.coins = coins;
        this.lifePacks = lifePacks;
        this.bullets = bullets;
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

    public void drawPlayers(Graphics graphic) {
        int rotation;
        Graphics2D g2d = (Graphics2D) graphic;
        for (int i = 0; i < players.length; i++) {
            x = players[i].getNextX();
            y = players[i].getNextY();
            direction = players[i].getDirection();

            dir = players[i].getCurrentDirn();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        timeExpireObject();
        collectObjects();
        moveBullets();
        movePlayers();
        ditectCollisions();
        repaint();
    }

    public void timeExpireObject() {
        long currentTime = System.currentTimeMillis();
        long timeToExpire;
        long lifePackTime;
        long coinPileTime;
        
        if (lifePacks != null) {
            for (int i = 0; i < lifePacks.size(); i++) {
                timeToExpire = currentTime - lifePacks.get(i).getInitiateTime();
                lifePackTime = lifePacks.get(i).getLifeTime();
                if (lifePackTime <= timeToExpire) {
                    lifePacks.remove(i);
                    i = i - 1;
                }

            }
        }
        if (coins != null) {
            for (int i = 0; i < coins.size(); i++) {
                timeToExpire = currentTime - coins.get(i).getInitiateTime();
                coinPileTime = coins.get(i).getLifeTime();
                if (coinPileTime <= timeToExpire) {
                    coins.remove(i);
                    i = i - 1;
                }
            }
        }
    }

    public void collectObjects() {
        int objectX;
        int objectY;
        int playerX;
        int playerY;
        
        //coin collect
        if (coins != null) {
            for (int i = 0; i < coins.size(); i++) {
                objectX = coins.get(i).getX();
                objectY = coins.get(i).getY();
                for (int j = 0; j < players.length; j++) {
                    playerX = players[j].getX();
                    playerY = players[j].getY();
                    if (playerX == objectX && playerY == objectY) {
                        coins.remove(i);
                        if (coins.size() > 0) {
                            i = i - 1;
                        }
                    }
                }
            }
        }
        
        //lifepack collect
        for (int i = 0; i < lifePacks.size(); i++) {
            objectX = lifePacks.get(i).getX();
            objectY = lifePacks.get(i).getY();

            for (int j = 0; j < players.length; j++) {
                playerX = players[j].getX();
                playerY = players[j].getY();
                if (playerX == objectX && playerY == objectY) {
                    lifePacks.remove(i);
                    if (lifePacks.size() > 0) {
                        i = i - 1;
                    }
                }
            }
        }
    }

    public void ditectCollisions() {
        Rectangle bullet, object;
        //bullet hit stone
        for (int i = 0; i < bullets.size(); i++) {
            bullet = bullets.get(i).getRec();
            for (int j = 0; j < stone.length; j++) {
                object = stone[j].getRec();
                if (bullet.intersects(object)) {

                    if (bullets.size() > 0) {
                        bullets.remove(i);
                        i = i - 1;
                    }
                }
            }
            //bullet hit player
            /*for (int j = 0; j < players.length; j++) {
                object = players[j].getRectangle();
                if (bullet.intersects(object) && bullets.get(i).getShootBy() != j) {

                    if (bullets.size() > 0) {
                        bullets.remove(i);
                        i = i - 1;
                    }
                }
            }*/
            //bullet hit brick
            for (int j = 0; j < bricks.length; j++) {
                object = bricks[j].getRectangle();
                if (bullet.intersects(object)) {

                    if (bullets.size() > 0) {
                        bullets.remove(i);
                        i = i - 1;
                    }
                }
            }
            if (bullets.size() > 0) {
                if (!bullets.get(i).isVisible()) {

                    if (bullets.size() > 0) {
                        bullets.remove(i);
                        i = i - 1;
                    }
                }
            }
        }

    }

    public void drawBullets(Graphics graphic) {
        int bulletDirn;
        Graphics2D g2d = (Graphics2D) graphic;
        
        for (int i = 0; i < bullets.size(); i++) {
            
            bulletDirn = bullets.get(i).getDirection();
            x = bullets.get(i).getX();
            y = bullets.get(i).getY();
            
            if (bulletDirn == 0 && bullets.get(i).isVisible()) {
                g2d.drawImage(bullets.get(i).getBullet().getImage(), x, y, this);                
            } else if (bulletDirn == 1 && bullets.get(i).isVisible()) {
                transform.setToTranslation(x, y);
                transform.rotate(Math.toRadians(90), 12.5, 12.5);
                g2d.drawImage(bullets.get(i).getBullet().getImage(), transform, this);
            } else if (bulletDirn == 2 && bullets.get(i).isVisible()) {
                transform.setToTranslation(x, y);
                transform.rotate(Math.toRadians(180), 12.5, 12.5);
                g2d.drawImage(bullets.get(i).getBullet().getImage(), transform, this);
            } else if (bulletDirn == 3 && bullets.get(i).isVisible()) {
                transform.setToTranslation(x, y);
                transform.rotate(Math.toRadians(270), 12.5, 12.5);
                g2d.drawImage(bullets.get(i).getBullet().getImage(), transform, this);
            }
        }
    }
    
    public void moveBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).move();
        }
    }

    public void movePlayers() {
        for (int i = 0; i < players.length; i++) {
            players[i].move();
            players[i].rotateImage();
        }
    }
}
