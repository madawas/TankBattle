/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author Madawa
 */
public class Brick {

    private int x;
    private int y;
    private int damage;
    private String brickImage = "images/brick.png";
    private ImageIcon brick;
    private Rectangle rectangle;
    private int imageX;
    private int imageY;
    private boolean visible;

    public Brick(int x, int y, int damage) {
        this.x = x;
        this.y = y;
        this.damage = damage;
        brick = new ImageIcon(brickImage);
        this.visible = true;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public ImageIcon getBrick() {
        return brick;
    }

    public void setBrick(ImageIcon brick) {
        this.brick = brick;
    }

    public String getBrickImage() {
        return brickImage;
    }

    public void setBrickImage(String brickImage) {
        this.brickImage = brickImage;
    }

    public Rectangle getRectangle() {
        imageX = this.getBrick().getImage().getWidth(null);
        imageY = this.getBrick().getImage().getHeight(null);
        rectangle = new Rectangle(x, y, imageX, imageY);
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
