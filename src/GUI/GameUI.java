/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Madawa
 */
public class GameUI extends JFrame {

    private GamePanel gameInterface;
    private ScoreBoard scoreBoard;
    private ImageIcon background;

    public GameUI() {
        this.gameInterface = new GamePanel();        
        //setupScoreBoard();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 700);
        setLocationRelativeTo(null);
        setTitle("Tank Battle");
        setResizable(true);
        setVisible(true);
        this.background = new ImageIcon("images/background.jpg");
        add(gameInterface);
        //add(scoreBoard, BorderLayout.EAST);
        
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(new ImageIcon("images/background.jpg").getImage(), 0, 0, this);
    }   
    
    private void setupScoreBoard(){
        this.scoreBoard = new ScoreBoard();
        this.scoreBoard.setMaximumSize(new Dimension(424, 302));
        this.scoreBoard.setPreferredSize(new Dimension(424, 302));
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
