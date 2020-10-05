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
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
    private JPanel promptWindow;
    private JButton yes, no;
    
    private Color bgColor;
    private Dimension screenDim;
    private JFrame frame;
    private LinkedList<MoveableFigure> tempFigs;
    private GameController preferenceController;
    
    GameGUI(){
        frame = new JFrame();
    }
    
    
    
   
    public void addController(DinoController dinoCon){
        this.addKeyListener(dinoCon);
        
    }
    
    
    
    public void paintComponent(Graphics g){
        GameModel.setDrawing(true);
        super.paintComponent(g);
        
        out.println("drawing");
        
        if (tempFigs != null){
            out.println("drawing self");
            
            tempFigs.forEach((fig) -> {
                fig.drawSelf(g);
            });
            
        } else 
            out.println("temFigs is null");
        
        //g.drawString(currentScore.toString(), 0, 0);
        GameModel.setDrawing(false);
        
    }
    
    
    @Override
    public void update(Observable o, Object arg) {
        
        if (arg instanceof Preferences){
            
            out.println("GameGUI notified");
            
            bgColor = ((Preferences) arg).getBgColour();
            screenDim = ((Preferences) arg).getScreenDim();
            
            this.setBackground(bgColor);
            frame.setSize(500, 500);
            frame.add(this);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            out.println("set visible");
            
        } else if (arg instanceof LinkedList){
            tempFigs = (LinkedList<MoveableFigure>) arg;
            out.println("refreshing frame");
            
            repaint();
            out.println("after calling repaint");
            
        }
    }
    
    
   

   
    
}





     