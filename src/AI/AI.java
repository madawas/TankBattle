/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import AI.heuristics.AStarHeuristic;
import AI.heuristics.ClosestHeuristic;
import GameObjects.Brick;
import GameObjects.Bullet;
import GameObjects.CoinPile;
import GameObjects.LifePack;
import GameObjects.Player;
import GameObjects.Stone;
import GameObjects.Water;
import Sockets.DataHandler;
import Sockets.WriteToServer;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Madawa
 */
public class AI extends Thread {

    private int coinX;
    private int coinY;
    private ArrayList<CoinPile> coins;
    private ArrayList<LifePack> packs;
    private int x, y;
    private Player[] players;
    private int direction;
    private Brick[] bricks;
    private Water[] water;
    private Stone[] stone;
    private ArrayList<Bullet> bullet;
    private int playerX;
    private int playerY;
    private int dir;
    private Player temp;
    private String status;
   // private List<Square> bestListCoins = new ArrayList<Square>();
    private WriteToServer write;
    private AreaMap map;

    public AI(ArrayList<CoinPile> coins, ArrayList<LifePack> packs, Player[] players, Brick[] bricks, Water[] water, Stone[] stone, ArrayList<Bullet> bullet) {
        this.coins = coins;
        this.packs = packs;
        this.players = players;
        this.bricks = bricks;
        this.water = water;
        this.stone = stone;
        this.bullet = bullet;
  
       // square = new Square[20][20];
        write = new WriteToServer();
    }

    public void generateMap() {
        int x, y;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                int[][] obstacleMap = new int[20][20];
                this.map = new AreaMap(20, 20, obstacleMap);
            }
        }
        for (int i = 0; i < bricks.length; i++) {
            x = bricks[i].getX() / 25;
            y = bricks[i].getY() / 25;
            map.obstacleMap[x][y] = 1;
        }
        for (int i = 0; i < water.length; i++) {
            x = water[i].getX() / 25;
            y = water[i].getY() / 25;
            map.obstacleMap[x][y] = 1;
        }
        for (int i = 0; i < stone.length; i++) {
            x = stone[i].getX() / 25;
            y = stone[i].getY() / 25;
            map.obstacleMap[x][y] = 1;
        }
        for (int i = 0; i < 20; i++) {
                    for (int j = 0; j < 20; j++) {
                        System.out.print(map.obstacleMap[j][i]);
                    }
                    System.out.println("");
            }
    }


    public void followCoinPile() throws IOException, UnknownHostException, InterruptedException{
        int startX;
        int startY;
        int goalX;
        int goalY;
        AStarHeuristic heuristic = new ClosestHeuristic();
        AStar pathFinder = new AStar(map, heuristic);
        startX = getPlayerLocationX(DataHandler.player)/25;
        startY = getPlayerLocationY(DataHandler.player)/25;
        ArrayList<Path> pathsToCoins = new ArrayList<>();
        int closestCoinDistance=1000;
        int closestCoin=0;
        
        for (int i = 0; i < coins.size(); i++) {
            goalX = coins.get(i).getX()/25;
            goalY = coins.get(i).getY()/25;
            Path shortestPath = pathFinder.calcShortestPath(startX, startY, goalX, goalY);
            if(shortestPath.getLength()<closestCoinDistance){
                closestCoinDistance = shortestPath.getLength();
                closestCoin = i;
            }
            pathsToCoins.add(i, shortestPath);
            
        }
        travel(pathsToCoins.get(closestCoin));
        
    }
    
    
    private void travel(Path path) throws IOException, UnknownHostException, InterruptedException {
       int k=2;
        if(path.getLength()<2) k = path.getLength();
        for (int i = 0; i < k; i++) {
            int dx = path.getX(i);
            int dy = path.getY(i);
            int px = getPlayerLocationX(DataHandler.player)/25;
            int py = getPlayerLocationY(DataHandler.player)/25;
            dir = getPlayerDirection(DataHandler.player);
            System.out.println(px+" "+dx+" , "+py+" "+dy+" "+dir);
            if(dx-px>0) right(dir);else if(dx-px<0) left(dir);
            dir = getPlayerDirection(DataHandler.player);
            if(dy-py>0) down(dir);else if(dy-py<0) up(dir);
                    
            
        }
    }
    
    

//    public void follow() throws UnknownHostException, IOException, InterruptedException {
//        while (bestListCoins != null && bestListCoins.size() > 0 && coinPileExist(coinX, coinY)) {
//
//
//            playerX = temp.getX();
//            playerY = temp.getY();
//            // System.out.println(bestListCoins.get(bestListCoins.size()-1).getX()+"ane manda"+bestListCoins.get(bestListCoins.size()-1).getY());
//            // System.out.println(playerX+"aneeee"+playerY);
//            direction = getPlayerDirection(DataHandler.player);
//            if (playerX == bestListCoins.get(bestListCoins.size() - 1).getX() && (playerY + 1) == bestListCoins.get(bestListCoins.size() - 1).getY()) {
//                if (!detectCollision(playerX, playerY + 1)) {
//                    //System.out.println(bestListCoins.size() + "checkD");
//                    down(direction);
//                    temp.setX(playerX);
//                    temp.setY(playerY + 1);
//                    temp.setDirection(2);
//                    bestListCoins.remove(bestListCoins.size() - 1);
//                }
//            } else if (playerX == bestListCoins.get(bestListCoins.size() - 1).getX() && (playerY - 1) == bestListCoins.get(bestListCoins.size() - 1).getY()) {
//                if (!detectCollision(playerX, playerY - 1)) {
//                    //System.out.println(bestListCoins.size() + "checkU");
//                    up(direction);
//                    temp.setX(playerX);
//                    temp.setY(playerY - 1);
//                    temp.setDirection(0);
//                    bestListCoins.remove(bestListCoins.size() - 1);
//                }
//            } else if ((playerX + 1) == bestListCoins.get(bestListCoins.size() - 1).getX() && playerY == bestListCoins.get(bestListCoins.size() - 1).getY()) {
//                if (!detectCollision(playerX + 1, playerY)) {
//                    //System.out.println(bestListCoins.size() + "checkR");
//                    right(direction);
//                    temp.setX(playerX + 1);
//                    temp.setY(playerY);
//                    temp.setDirection(1);
//                    bestListCoins.remove(bestListCoins.size() - 1);
//                }
//            } else if ((playerX - 1) == bestListCoins.get(bestListCoins.size() - 1).getX() && playerY == bestListCoins.get(bestListCoins.size() - 1).getY()) {
//                if (!detectCollision(playerX - 1, playerY)) {
//                    //System.out.println(bestListCoins.size() + "checkL");
//                    left(direction);
//                    temp.setX(playerX - 1);
//                    temp.setY(playerY);
//                    temp.setDirection(3);
//                    bestListCoins.remove(bestListCoins.size() - 1);
//                }
//            } else {
//                return;
//            }
//            //System.out.println("sizeOfit=" + bestListCoins.size());
//        }
//        write.writeCommand("SHOOT#");
//        bestListCoins = null;
//
//    }

    public void up(int dir) throws IOException, UnknownHostException, InterruptedException {
        if (dir == 0) {
            status = "UP#";
            write.writeCommand(status);
            // out.outToServer("SHOOT#");
        }
        if (dir == 1 || dir == 3 || dir == 2) {
            status = "UP#";
            write.writeCommand(status);
            // out.outToServer("SHOOT#");
            status = "UP#";
            write.writeCommand(status);
            write.writeCommand("SHOOT#");
        }
    }

    public void down(int dir) throws UnknownHostException, IOException, InterruptedException {
        if (dir == 2) {
            status = "DOWN#";
            write.writeCommand(status);
            //  out.outToServer("SHOOT#");

        }
        if (dir == 1 || dir == 3 || dir == 0) {
            status = "DOWN#";
            write.writeCommand(status);
            // out.outToServer("SHOOT#");
            status = "DOWN#";
            write.writeCommand(status);
            write.writeCommand("SHOOT#");
        }
    }

    public boolean coinPileExist(int x, int y) {
        for (int i = 0; i < coins.size(); i++) {
            if (x == coins.get(i).getX() / 25 && y == coins.get(i).getY() / 25) {
                return true;
            }
        }
        return false;
    }

    public void left(int dir) throws UnknownHostException, IOException, InterruptedException {
        if (dir == 3) {
            status = "LEFT#";
            write.writeCommand(status);
            // out.outToServer("SHOOT#");
        }
        if (dir == 1 || dir == 2 || dir == 0) {
            status = "LEFT#";
            write.writeCommand(status);
            // out.outToServer("SHOOT#");
            status = "LEFT#";
            write.writeCommand(status);
            write.writeCommand("SHOOT#");
        }
    }

    public void right(int dir) throws UnknownHostException, IOException, InterruptedException {
        if (dir == 1) {
            status = "RIGHT#";
            write.writeCommand(status);
        }
        if (dir == 3 || dir == 2 || dir == 0) {
            status = "RIGHT#";
            write.writeCommand(status);
            status = "RIGHT#";
            write.writeCommand(status);
        }
    }

    public boolean detectCollision(int x, int y) {
        boolean status1 = false;
        for (int i = 0; i < players.length - 1; i++) {
            if ((x == players[i].getNextX() / 25 && y == players[i].getNextY() / 25) || (x == players[i].getX() / 25 && y == players[i].getY() / 25)) {
                status1 = true;
                //System.out.println("COLLISION DETECTED");
            }
        }
        return status1;
    }

    public void coinFollow() throws UnknownHostException, IOException, InterruptedException {

        followCoinPile();
    }

    public int getPlayerLocationX(int player) {
        return players[player].getX();
    }

    public int getPlayerLocationY(int player) {
        return players[player].getY();
    }

    public int getPlayerDirection(int player) {
        return players[player].getDirection();
    }

    public void run() {
        while (true) {
            try {
                while (coins.size() <= 0) {
                    Thread.sleep(50);
                }
                generateMap();
                coinFollow();
            } catch (UnknownHostException ex) {
                Logger.getLogger(AI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(AI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
}
