package GUI;

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
import Utility.Constants;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;

/**
 *
 * @author Madawa
 */
public class GamePanel extends JPanel implements ActionListener {

    private int x, y;
    private ArrayList<CoinPile> coins;
    private ArrayList<LifePack> lifePacks;
    private ArrayList<Bullet> bullets;
    private Player[] players;    
    private Brick[] bricks;
    private Water[] water;
    private Stone[] stone;    
    private Timer timer;
    private AffineTransform transform;
    private ImageIcon tilemap;
    private ImageIcon background;

    public GamePanel() {
        this.transform = new AffineTransform();
        this.background = new ImageIcon("images/background.jpg");
        this.tilemap = new ImageIcon("images/tilemap.png");
        setDoubleBuffered(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(background.getImage(), 0, 0, this);
        g2d.drawImage(tilemap.getImage(), 50, 50, this);
        drawMap(g);
        drawPlayers(g);
        drawCoinPiles(g);
        drawLifePacks(g);
        drawBullets(g);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void initialize(Brick[] bricks, Water[] water, Stone[] stones, Player[] player, ArrayList<CoinPile> coins, ArrayList<LifePack> lifePacks, ArrayList<Bullet> bullets) {
        this.bricks = bricks;
        this.stone = stones;
        this.players = player;
        this.water = water;
        this.coins = coins;
        this.lifePacks = lifePacks;
        this.bullets = bullets;
        timer = new Timer(25, this);
        timer.start();
    }

    public void drawMap(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < bricks.length; i++) {
            x = bricks[i].getX();
            y = bricks[i].getY();
            if(bricks[i].isVisible())
                g2d.drawImage(bricks[i].getBrick().getImage(), x+Constants.PIXEL_OFFSET_X, y+Constants.PIXEL_OFFSET_Y, this);
        }
        for (int i = 0; i < stone.length; i++) {
            x = stone[i].getX();
            y = stone[i].getY();
            g2d.drawImage(stone[i].getStone().getImage(), x+Constants.PIXEL_OFFSET_X, y+Constants.PIXEL_OFFSET_Y, this);
        }
        for (int i = 0; i < water.length; i++) {
            x = water[i].getX();
            y = water[i].getY();
            g2d.drawImage(water[i].getWater().getImage(), x+Constants.PIXEL_OFFSET_X, y+Constants.PIXEL_OFFSET_Y, this);
        }

    }

    public void drawPlayers(Graphics g) {
        int rotation;
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < players.length; i++) {
            Player p = players[i];
            
            if(p.isVisible()){
                x = p.getNextX()+Constants.PIXEL_OFFSET_X;
                y = p.getNextY()+Constants.PIXEL_OFFSET_Y;
                //int direction = p.getDirection();

                //int dir = p.getCurrentDirn();
                rotation = p.getRotation();
                transform.setToTranslation(x, y);
                transform.rotate(Math.toRadians(rotation), 12.5, 12.5);
                g2d.drawImage(p.getNorth().getImage(), transform, this);
            }
        }
    }

    public void drawCoinPiles(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < coins.size(); i++) {
            x = coins.get(i).getX();
            y = coins.get(i).getY();
            g2d.drawImage(coins.get(i).getCoin().getImage(), x+Constants.PIXEL_OFFSET_X, y+Constants.PIXEL_OFFSET_Y, this);
        }
    }

    public void drawLifePacks(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < lifePacks.size(); i++) {
            x = lifePacks.get(i).getX();
            y = lifePacks.get(i).getY();
            g2d.drawImage(lifePacks.get(i).getLife().getImage(), x+Constants.PIXEL_OFFSET_X, y+Constants.PIXEL_OFFSET_Y, this);
        }
    }  
    
    public void drawBullets(Graphics g) {
        int bulletDirn;
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < bullets.size(); i++) {

            bulletDirn = bullets.get(i).getDirection();
            x = bullets.get(i).getX()+Constants.PIXEL_OFFSET_X;
            y = bullets.get(i).getY()+Constants.PIXEL_OFFSET_Y;

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
    
    public void removeObjects() {
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
    
    public void updateBricks() {
        int damage;
        
        for(int i = 0; i < bricks.length; i++) {
            Brick brick = bricks[i];
            damage = brick.getDamage();
            switch(damage){
                case 0:
                    break;
                case 1:
                    brick.setBrickImage("images/brick25.png");
                    break;
                case 2:
                    brick.setBrickImage("images/brick50.png");
                    break;
                case 3:
                    brick.setBrickImage("images/brick75.png");
                    break;
                case 4:
                    brick.setVisible(false);
                    break;
                default:
                    break;
            }
        }
    }
    
    public void removeDeadPlayers() {
        for(int i = 0; i < players.length; i++) {
            if(!players[i].isAlive())
                players[i].setVisible(false);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {        
        removeObjects();
        collectObjects();
        moveBullets();
        movePlayers();
        ditectCollisions();
        updateBricks();
        updateScores();
        repaint();
    }
    
    public void updateScores() {
        int[][] scores = new int[players.length][3];
        for(int i = 0; i < players.length; ++i){
            scores[i][0] = Integer.parseInt(players[i].getName().substring(1));
            scores[i][1] = players[i].getScore();
            scores[i][2] = players[i].getDamage();
            ScoreBoard.updateScores(scores, players.length);
        }
    }
}


