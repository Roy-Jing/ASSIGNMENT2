/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import static java.lang.System.out;
import java.util.Observable;
import java.util.Observer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Roy
 */
public class GameGUI extends JPanel implements Observer {
    private JLabel currentScore;
    private JDialog userInfoPane;
    private Graphics graphics;
    private boolean running = false;
    private MoveableFigure figures[];
    private Color bgColor;
    private Dimension screenDim;
    private JFrame frame;
    
    
    GameGUI(){
        frame = new JFrame();
    }
    
    
    public void addController(DinoController dinoCon){
        this.addKeyListener(dinoCon);
        
    }
    
    
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        for (MoveableFigure fig : figures){
            fig.drawSelf(g);
        }
        g.drawString(currentScore.toString(), 0, 0);
        
        
    }
    
    
    @Override
    public void update(Observable o, Object arg) {
        
        if (arg instanceof Preferences){
            out.println("GameGUI notified");
            
            bgColor = ((Preferences) arg).getBgColour();
            screenDim = ((Preferences) arg).getScreenDim();
            
            this.setBackground(bgColor);
            frame.setSize(screenDim);
            frame.add(this);
            
            frame.setVisible(true);
        } else
            repaint();
    }
    
    
   

   
    
}





     