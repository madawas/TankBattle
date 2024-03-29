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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

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
    private ImageIcon scoreBackground;
    //Labels
    private javax.swing.JLabel healthP0;
    private javax.swing.JLabel healthP1;
    private javax.swing.JLabel healthP2;
    private javax.swing.JLabel healthP3;
    private javax.swing.JLabel healthP4;
    private javax.swing.JLabel iconP0;
    private javax.swing.JLabel iconP1;
    private javax.swing.JLabel iconP2;
    private javax.swing.JLabel iconP3;
    private javax.swing.JLabel iconP4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel scoreP0;
    private javax.swing.JLabel scoreP1;
    private javax.swing.JLabel scoreP2;
    private javax.swing.JLabel scoreP3;
    private javax.swing.JLabel scoreP4;

    public GamePanel() {
        this.transform = new AffineTransform();
        this.background = new ImageIcon("images/background.jpg");
        this.setOpaque(false);
        this.tilemap = new ImageIcon("images/tilemap.png");
        this.scoreBackground = new ImageIcon("images/scorePanelBackground.png");
        setDoubleBuffered(true);
        initializeScorePanel();
    }

    @Override
    public void paint(Graphics g) {        
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(background.getImage(), 0, 0, this);
        g2d.drawImage(scoreBackground.getImage(), 585, 80, this);
        super.paint(g);
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

    private void drawMap(Graphics g) {
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

    private void drawPlayers(Graphics g) {
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

    private void drawCoinPiles(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < coins.size(); i++) {
            x = coins.get(i).getX();
            y = coins.get(i).getY();
            g2d.drawImage(coins.get(i).getCoin().getImage(), x+Constants.PIXEL_OFFSET_X, y+Constants.PIXEL_OFFSET_Y, this);
        }
    }

    private void drawLifePacks(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < lifePacks.size(); i++) {
            x = lifePacks.get(i).getX();
            y = lifePacks.get(i).getY();
            g2d.drawImage(lifePacks.get(i).getLife().getImage(), x+Constants.PIXEL_OFFSET_X, y+Constants.PIXEL_OFFSET_Y, this);
        }
    }  
    
    private void drawBullets(Graphics g) {
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

    private void moveBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).move();
        }
    }

    private void movePlayers() {
        for (int i = 0; i < players.length; i++) {
            players[i].move();
            players[i].rotateImage();
        }
    }
    
    private void removeObjects() {
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

    private void collectObjects() {
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
    
    private void ditectCollisions() {
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
    
    private void updateBricks() {
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
    
    private void removeDeadPlayers() {
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
    
    private void updateScores() {
        int num;
        Player player;
        for(int i = 0; i < players.length; ++i){
            player = players[i];
            num = Integer.parseInt(player.getName().substring(1));
            switch(num){
                default:
                    break;
                case 0:
                    scoreP0.setText(Integer.toString(player.getScore())+" $");
                    healthP0.setText(Integer.toString(player.getDamage())+" %");
                    break;
                case 1:
                    scoreP1.setText(Integer.toString(player.getScore())+" $");
                    healthP1.setText(Integer.toString(player.getDamage())+" %");
                    break;
                case 2:
                    scoreP2.setText(Integer.toString(player.getScore())+" $");
                    healthP2.setText(Integer.toString(player.getDamage())+" %");
                    break;
                case 3:
                    scoreP3.setText(Integer.toString(player.getScore())+" $");
                    healthP3.setText(Integer.toString(player.getDamage())+" %");;
                    break;
                case 4:
                    scoreP4.setText(Integer.toString(player.getScore())+" $");
                    healthP4.setText(Integer.toString(player.getDamage())+" %");
                    break;
            }
        }
    }
    
    private void initializeScorePanel(){
        JPanel scorePanel = new JPanel();
        setLayout(null);
        scorePanel.setOpaque(false);
        scorePanel.setPreferredSize(new Dimension(300, 200));
        scorePanel.setBounds(new Rectangle(new Point(600, 100), new Dimension(350, 280)));
        scorePanel.setLayout(new GridLayout(6, 3, 10, 5));
    
        iconP0 = new javax.swing.JLabel();
        iconP1 = new javax.swing.JLabel();
        iconP2 = new javax.swing.JLabel();
        iconP3 = new javax.swing.JLabel();
        iconP4 = new javax.swing.JLabel();
        scoreP3 = new javax.swing.JLabel();
        healthP4 = new javax.swing.JLabel();
        healthP3 = new javax.swing.JLabel();
        scoreP4 = new javax.swing.JLabel();
        scoreP2 = new javax.swing.JLabel();
        healthP2 = new javax.swing.JLabel();
        scoreP1 = new javax.swing.JLabel();
        healthP1 = new javax.swing.JLabel();
        healthP0 = new javax.swing.JLabel();
        scoreP0 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        iconP0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/tank0.png"))); // NOI18N

        iconP1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/tank1.png"))); // NOI18N

        iconP2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/tank2.png"))); // NOI18N

        iconP3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/tank3.png"))); // NOI18N

        iconP4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/tank4.png"))); // NOI18N

        scoreP3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        scoreP3.setForeground(new java.awt.Color(245, 208, 0));
        scoreP3.setText("0000 $");

        healthP4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        healthP4.setForeground(new java.awt.Color(245, 208, 0));
        healthP4.setText("00 %");

        healthP3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        healthP3.setForeground(new java.awt.Color(245, 208, 0));
        healthP3.setText("00 %");

        scoreP4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        scoreP4.setForeground(new java.awt.Color(245, 208, 0));
        scoreP4.setText("0000 $");

        scoreP2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        scoreP2.setForeground(new java.awt.Color(245, 208, 0));
        scoreP2.setText("0000 $");

        healthP2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        healthP2.setForeground(new java.awt.Color(245, 208, 0));
        healthP2.setText("00 %");

        scoreP1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        scoreP1.setForeground(new java.awt.Color(245, 208, 0));
        scoreP1.setText("0000 $");

        healthP1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        healthP1.setForeground(new java.awt.Color(245, 208, 0));
        healthP1.setText("00 %");

        healthP0.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        healthP0.setForeground(new java.awt.Color(245, 208, 0));
        healthP0.setText("00 %");

        scoreP0.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        scoreP0.setForeground(new java.awt.Color(245, 208, 0));
        scoreP0.setText("0000 $");

        jLabel1.setFont(new java.awt.Font("Adobe Garamond Pro", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(161, 245, 0));
        jLabel1.setText("SCORE");

        jLabel2.setFont(new java.awt.Font("Adobe Garamond Pro", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(161, 245, 0));
        jLabel2.setText("HEALTH");

        JLabel empty = new JLabel();
        scorePanel.add(empty);        
        scorePanel.add(jLabel1);
        scorePanel.add(jLabel2);
        scorePanel.add(iconP0);scorePanel.add(scoreP0);scorePanel.add(healthP0);
        scorePanel.add(iconP1);scorePanel.add(scoreP1);scorePanel.add(healthP1);
        scorePanel.add(iconP2);scorePanel.add(scoreP2);scorePanel.add(healthP2);
        scorePanel.add(iconP3);scorePanel.add(scoreP3);scorePanel.add(healthP3);
        scorePanel.add(iconP4);scorePanel.add(scoreP4);scorePanel.add(healthP4);
        
        this.add(scorePanel);
    } 
    
}


