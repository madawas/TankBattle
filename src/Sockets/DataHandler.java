/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sockets;

import AI.AI;
import GUI.GameUI;
import GameObjects.Brick;
import GameObjects.Bullet;
import GameObjects.CoinPile;
import GameObjects.LifePack;
import GameObjects.Player;
import GameObjects.Stone;
import GameObjects.Water;
import Utility.Constants;

import java.util.ArrayList;

/**
 *
 * @author Madawa
 */
public class DataHandler {

    private Brick[] bricks;
    private Water[] water;
    private Stone[] stones;
    private Player[] players;
    private ArrayList coins = new ArrayList<CoinPile>();
    private ArrayList lifePacks = new ArrayList<LifePack>();
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private GameUI gameInterface;
    private Bullet bullet;
    private AI ai;
    public static int player;

    public void parseMessage(String s) {
        String[] temp;
        String[] temp2;
        int x,y,lifeTime,value,direction,coin,health,brickDamage;
        long timeInit;
        String name;
        boolean shoot;
        
        //Initializing Message parsing
        if (s.split(":")[0].equalsIgnoreCase("I")) { //If I message
            
            player = Integer.parseInt(s.split(":")[1].substring(1)); //player number
            
            temp = s.split("[:#]")[2].split(";");
            bricks = new Brick[temp.length];            
            for (int i = 0; i < bricks.length; i++) {
                bricks[i] = new Brick(Integer.parseInt(temp[i].split(",")[0]) * 25, Integer.parseInt(temp[i].split(",")[1]) * 25, 0);
            }
            
            temp = s.split("[:#]")[3].split(";");
            stones = new Stone[temp.length];
            for (int i = 0; i < getStone().length; i++) {
                stones[i] = new Stone(Integer.parseInt(temp[i].split(",")[0]) * 25, Integer.parseInt(temp[i].split(",")[1]) * 25);
            }
            
            temp = s.split("[:#]")[4].split(";");
            water = new Water[temp.length];
            for (int i = 0; i < getWter().length; i++) {
                water[i] = new Water(Integer.parseInt(temp[i].split(",")[0]) * 25, Integer.parseInt(temp[i].split(",")[1]) * 25);
            }
        }
        //Player Location Messsage Parsing
        if (s.split(":")[0].equalsIgnoreCase("S")) {//if S message            
            temp = s.split(":");
            int numPlayers = temp.length - 1;
            players = new Player[numPlayers];
            
            for (int i = 0; i < numPlayers; i++) {
                
                name = temp[i + 1].split(";")[0];
                int playerNumber = Integer.parseInt(name.substring(1));
                
                x = Integer.parseInt(temp[i + 1].split(";")[1].split(",")[0]) * 25;
                y = Integer.parseInt(temp[i + 1].split(";")[1].split(",")[1]) * 25;
                direction = Integer.parseInt(temp[i + 1].split("[;#]")[2]);
                
                players[i] = new Player(name, x, y, 0, false, direction,"images/tanks/tank"+playerNumber%Constants.MAX_PLAYERS+".png");
            }
            
            gameInterface = new GameUI();
            gameInterface.getGameInterface().initialize(bricks, water, stones, players, coins, lifePacks, bullets);
            ai = new AI(coins, lifePacks, players, bricks, water, stones, bullets);
            ai.generateMap();
           // ai.createAdjecencies();
            
            Thread t = new Thread(ai);
            t.start();
        }
        
        //Player Data Parsing
        if (s.split(":")[0].equalsIgnoreCase("G")) {
            temp = s.split(":");
            
            for (int i = 0; i < players.length; i++) {
                x = Integer.parseInt(temp[i + 1].split(";")[1].split(",")[0]) * 25;
                y = Integer.parseInt(temp[i + 1].split(";")[1].split(",")[1]) * 25;
                direction = Integer.parseInt(temp[i + 1].split(";")[2]);
                
                if (Integer.parseInt(temp[i + 1].split(";")[3]) == 0) {
                    shoot = false;
                } else {
                    shoot = true;

                    bullet = new Bullet(x, y, true, direction);
                    bullet.setShootBy(i);
                    bullets.add(bullet);

                }
                
                health = Integer.parseInt(temp[i + 1].split(";")[4]);
                coin = Integer.parseInt(temp[i + 1].split(";")[5]);
                value = Integer.parseInt(temp[i + 1].split(";")[6]);
                
                players[i].setX(x);
                players[i].setY(y);
                players[i].setDirection(direction);
                players[i].setShoot(shoot);
                players[i].setDamage(health);
                players[i].setCoins(coin);
                players[i].setScore(value);
            }
            
            temp2 = temp[players.length + 1].split("[;#]");
            for (int i = 0; i < bricks.length; i++) {
                brickDamage = Integer.parseInt(temp2[i].split(",")[2]);
                bricks[i].setDamage(brickDamage);
            }
        }
        //Coin message parsing
        if (s.split(":")[0].equalsIgnoreCase("C")) {
            
            x = Integer.parseInt(s.split(":")[1].split(",")[0]) * 25;
            y = Integer.parseInt(s.split(":")[1].split(",")[1]) * 25;
            
            value = Integer.parseInt(s.split("[:#]")[3]);
            timeInit = System.currentTimeMillis();
            lifeTime = Integer.parseInt(s.split("[:#]")[2]);
            coins.add(new CoinPile(x, y, value, lifeTime, timeInit));
        }
        //Life Pack message parsing
        if (s.split(":")[0].equalsIgnoreCase("L")) {
            
            x = Integer.parseInt(s.split(":")[1].split(",")[0]) * 25;
            y = Integer.parseInt(s.split(":")[1].split(",")[1]) * 25;
            
            lifeTime = Integer.parseInt(s.split("[:#]")[2]);
            timeInit = System.currentTimeMillis();
            lifePacks.add(new LifePack(x, y, lifeTime, timeInit));
        }
    }

    public Brick[] getBricks() {
        return bricks;
    }

    public void setBricks(Brick[] bricks) {
        this.bricks = bricks;
    }

    public Water[] getWter() {
        return water;
    }

    public void setWater(Water[] water) {
        this.water = water;
    }

    public Stone[] getStone() {
        return stones;
    }

    public void setStone(Stone[] stones) {
        this.stones = stones;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlyr(Player[] players) {
        this.players = players;
    }

    public ArrayList getCoins() {
        return coins;
    }

    public void setCoins(ArrayList coins) {
        this.coins = coins;
    }

    public ArrayList getLifePacks() {
        return lifePacks;
    }

    public void setLifePacks(ArrayList lifePacks) {
        this.lifePacks = lifePacks;
    }
}
