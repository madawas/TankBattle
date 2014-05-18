/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import javax.swing.JFrame;

/**
 *
 * @author Harsha
 */
public class Game extends JFrame {
private GamePanel gui;
    public Game()
    {
        gui=new GamePanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(510, 535);
        setLocationRelativeTo(null);
        setTitle("TankBusters");
        setResizable(false);
        setVisible(true);
        add(gui);
       
    }
    public GamePanel getGuiInstance()
    {
        return this.gui;
    }
}
