/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowListener;
import static java.lang.System.out;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Roy
 */
public class GameGUI extends JPanel implements Observer {

    private boolean running = false;
    private DinoController dinoControl;
    private GameModel model;
    private JFrame frame = new JFrame("Main game");
    
    
    public void setModel(GameModel model) {
        this.model = model;
    }
    
    public DinoController getDinoControl() {
        return dinoControl;
    }

    private Color bgColor;
    private Dimension screenDim;
   // private JFrame frame;
    private Deque<MoveableFigure> tempFigs;
    
  
    
    
   
    public void addController(DinoController dinoCon){
        this.addKeyListener(dinoCon);
        
    }
    
    
    volatile boolean busy = false;
    @Override
    public synchronized void paintComponent(Graphics g){
        out.println("inside paintComp");
        
        super.paintComponent(g);
        boolean found = false;
        Figure first = tempFigs.peekFirst();
        int ct = 0;
            for (MoveableFigure f : tempFigs){
                out.println(f.getid());
                
            }
       
        out.println("num dino" + ct);
        
        MoveableFigure fig;
        
        do{
            fig = tempFigs.pollLast();
            if (fig instanceof Dinosaur){
                if (!found){
                    found = true;
                } else{
                    out.println("error: duplicate dinosaur");
                    System.exit(0);
                }
                
            }
            if (fig.stillWithinFrame())
                tempFigs.addFirst(fig);
            
            fig.drawSelf(g);
            fig.doRun();
            
        } while (fig != first);
        
       
        
        //g.drawString(currentScore.toString(), 0, 0);
        
        
    }
    
    
    public void initFrame(){
        frame.setPreferredSize(screenDim);
            frame.add(this);
           
            frame.setVisible(true);
           
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        
        if (arg instanceof Preferences){
            
            out.println("GameGUI notified");

            bgColor = ((Preferences) arg).getBgColour();
            screenDim = ((Preferences) arg).getScreenDim();
            
            this.setBackground(bgColor);
            initFrame();
            repaint();
            out.println("set visible");
            
        } else if (arg instanceof LinkedList){
            if (!busy){
                busy = true;
                tempFigs = (LinkedList<MoveableFigure>) arg;
                out.println("refreshing frame");

                repaint();
                busy = false;
                //revalidate();
                out.println("after calling repaint");
            }
            
        } 
    }
    
   
   
    
}


class ExitConfirmWindow extends JFrame{
    private JButton confirmClose = new JButton("Yes");
    private JButton rejectClose = new JButton("No");
    private GameModel m;
   
    public void setModel(GameModel m){
        this.m = m;
    }
    
    
    public void promptQuit(){
        m.setInterrupted(true);
        int dialogResult;
        dialogResult = JOptionPane.showConfirmDialog(this, "Would You Like to Save your Previous Note First?","Warning", JOptionPane.YES_NO_OPTION);
        
        if(dialogResult == JOptionPane.YES_OPTION){
  // Saving code here
            m.setGameOver(true);
           
        } else{
            m.setInterrupted(false);
            this.dispose();
        }
        
        
    }
  
}




     