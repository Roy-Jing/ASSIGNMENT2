/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
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
public class GameGUI extends JPanel implements Observer{
    private JLabel currentScore;
    private JDialog userInfoPane;
    private Image dinoImg;
    private Graphics graphics;
    private boolean running = false;
    private Figure figures[];
    
    GameGUI(){
         
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawString(currentScore.toString(), 0, 0);
        
        for (Figure f : figures){
            f.drawSelf(g);
        }
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(GameGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Preferences){
            
        }
    }
    
    
}





     