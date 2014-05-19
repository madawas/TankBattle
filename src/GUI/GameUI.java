/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Madawa
 */
public class GameUI extends JFrame {

    private GamePanel gameInterface;
    private ScoreBoard scoreBoard;

    public GameUI() {
        this.gameInterface = new GamePanel();        
        setupScoreBoard();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 700);
        setLocationRelativeTo(null);
        setTitle("Tank Battle");
        setResizable(false);
        setVisible(true);
        
        add(gameInterface);
        add(scoreBoard, BorderLayout.EAST);

    }
    
    private void setupScoreBoard(){
        this.scoreBoard = new ScoreBoard();
        this.scoreBoard.setMaximumSize(new Dimension(424, 302));
        this.scoreBoard.setSize(424, 302);
        this.scoreBoard.setLocation(600, 50);
    }

    public GamePanel getGameInterface() {
        return this.gameInterface;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }    
}
