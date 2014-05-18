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

    private GUI gameInterface;

    public GameUI() {
        gameInterface = new GUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Tank Battle");
        setResizable(false);
        setVisible(true);
        add(gameInterface);

    }

    public GUI getGameInterface() {
        return this.gameInterface;
    }
}
