package GameObjects;

import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author Madawa
 */
public class Player {

    private String name;
    private int x;
    private int y;
    private int damage;
    private int direction;
    private int score;
    private int coins;
    private ImageIcon north;
    private boolean alive;
    private boolean shoot;
    private int rotation;
    private String tankImage = "images/tank.png";
    private int imageX;
    private int imageY;
    private Rectangle rec;
    private int currentX;
    private int currentY;
    private int currentDirn;
    private int rotation90;
    private int rotation180;
    private int rotation270;
    private int rotation0;

    public Player(String name, int x, int y, int damage, boolean shoot, int direction) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.currentX = x;
        this.currentY = y;
        this.damage = damage;
        this.shoot = shoot;
        currentDirn = direction;
        north = new ImageIcon(tankImage);
        this.direction = direction;
    }

    public void setRDirection() {
        rotation90 = 90;
        rotation180 = 180;
        rotation270 = 270;
        rotation0 = 0;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.currentX = this.x;

        this.x = x;
    }

    public int getY() {

        return y;
    }

    public void setY(int y) {
        this.currentY = this.y;
        this.y = y;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public ImageIcon getNorth() {
        return north;
    }

    public void setNorth(ImageIcon north) {
        this.north = north;
    }

    public boolean isShoot() {
        return shoot;
    }

    public void setShoot(boolean shoot) {
        this.shoot = shoot;
    }

    public String getTankImage() {
        return tankImage;
    }

    public void setTankImage(String location1) {
        this.tankImage = location1;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        setRDirection();
        setCurrentDirn(this.direction);
        this.direction = direction;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Rectangle getRectangle() {
        imageX = this.getNorth().getImage().getWidth(null);
        imageY = this.getNorth().getImage().getHeight(null);
        rec = new Rectangle(x, y, imageX, imageY);
        return rec;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getNextX() {
        return currentX;
    }

    public void setNextX(int nextX) {
        this.currentX = nextX;
    }

    public int getNextY() {
        return currentY;
    }

    public void setNextY(int nextY) {
        this.currentY = nextY;
    }

    public void move() {
        if (x != currentX || y != currentY) {
            if (x > currentX && y == currentY) {
                currentX = currentX + 2;
            }
            if (x < currentX && y == currentY) {
                currentX = currentX - 2;
            }
            if (x == currentX && y < currentY) {
                currentY = currentY - 2;
            }
            if (x == currentX && y > currentY) {
                currentY = currentY + 2;
            }
        }
    }

    public void rotateImage() {
        int dif = direction - currentDirn;
        if (direction == currentDirn && direction == 0) {
            rotation = 0;
        }
        if (direction == currentDirn && direction == 1) {
            rotation = 90;
        }
        if (direction == currentDirn && direction == 2) {
            rotation = 180;
        }
        if (direction == currentDirn && direction == 3) {
            rotation = 270;
        }
        if (dif == 1 || dif == -1) {
            diffOne(dif);
        }
        if (dif == 3 || dif == -3) {
            diffThree(dif);
        }
        if (dif == 2 || dif == -2) {
            diffTwo(dif);
        }
    }

    public int getCurrentDirn() {
        return currentDirn;
    }

    public void setCurrentDirn(int currentDirn) {
        this.currentDirn = currentDirn;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public void diffOne(int dif) {
        if (dif == 1 && currentDirn == 0 && rotation0 <= 90) {
            rotation = rotation0;
            rotation0 = rotation0 + 5;
        }
        if (dif == -1 && currentDirn == 3 && rotation270 >= 180) {
            rotation = rotation270;
            rotation270 = rotation270 - 5;
        }
        if (dif == -1 && currentDirn == 1 && rotation90 >= 0) {
            rotation = rotation90;
            rotation90 = rotation90 - 5;
        }
        if (dif == -1 && currentDirn == 2 && rotation180 >= 90) {
            rotation = rotation180;
            rotation180 = rotation180 - 5;
        }
        if (dif == 1 && currentDirn == 1 && rotation90 <= 180) {
            rotation = rotation90;
            rotation90 = rotation90 + 5;
        }
        if (dif == 1 && currentDirn == 2 && rotation180 <= 270) {
            rotation = rotation180;
            rotation180 = rotation180 + 5;
        }
    }

    public void diffThree(int dif) {
        if (dif == 3 && rotation0 <= 90) {
            rotation = -rotation0;
            rotation0 = rotation0 + 5;
        }
        if (dif == -3 && rotation90 >= 0) {
            rotation = -rotation90;
            rotation90 = rotation90 - 5;
        }
    }

    public void diffTwo(int dif) {
        if (dif == 2 && currentDirn == 1 && rotation90 <= 270) {
            rotation = rotation90;
            rotation90 = rotation90 + 5;
        }
        if (dif == 2 && currentDirn == 0 && rotation0 <= 180) {
            rotation = rotation0;
            rotation0 = rotation0 + 5;
        }
        if (dif == -2 && currentDirn == 3 && rotation270 >= 90) {
            rotation = rotation270;
            rotation270 = rotation270 - 5;
        }
        if (dif == -2 && currentDirn == 2 && rotation180 >= 0) {
            rotation = rotation180;
            rotation180 = rotation180 - 5;
        }
    }
}
