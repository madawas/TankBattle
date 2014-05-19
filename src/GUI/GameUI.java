/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.JFrame;

/**
 *
 * @author Madawa
 */
public class GameUI extends JFrame {

    private GamePanel gameInterface;

    public GameUI() {
        this.gameInterface = new GamePanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 700);
        setLocationRelativeTo(null);
        setTitle("Tank Battle");
        setResizable(true);
        setVisible(true);
        add(gameInterface);
        
    }

    public GamePanel getGameInterface() {
        return this.gameInterface;
    }    
}
