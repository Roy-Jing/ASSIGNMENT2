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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.awt.event.WindowListener;
import java.io.IOException;
import static java.lang.System.out;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

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
    private Image img;
    private GameController controller;
    
    
    public void setModel(GameModel model) {
        this.model = model;
    }
    
    public DinoController getDinoControl() {
        return dinoControl;
    }

    private Dimension screenDim;

    public void setScreenDim(Dimension screenDim) {
        this.screenDim = screenDim;
    }
   // private JFrame frame;
    private Deque<MoveableFigure> tempFigs;
    
  
    
    
   
    public void addController(DinoController dinoCon){
        this.addKeyListener(dinoCon);
        
    }
    
    
    volatile boolean busy = false;
    
    int paintCount = 10;
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.drawImage(img, 0, 0, null);
        
        
        boolean found = false;
        MoveableFigure first = tempFigs.peekFirst();
      
      
        MoveableFigure fig;
        if (!tempFigs.isEmpty()){
            do{
               fig = tempFigs.pollLast();



                if (fig.isActive()){

                    fig.drawSelf(g);
                    fig.doRun();
                    tempFigs.addFirst(fig);
                } 

            } while (fig != first);
        }
        
        if (dino.isActive()){
            dino.drawSelf(g);
            // (gui);
            
            dino.doRun();
            
            
        }
        
        
       // if (paintCount++ % 10 == 0)
        //    printGUI();
       
        
        
    }
//    
    public void printGUI(){
        for (char [] row : dino.getVirtualGUI()){
            for (char c : row){
                out.print(c);
            }
            
             
            
        }
    }
     public void loadPreferences(Preferences p){
        this.setImage(p.bgImage);
        this.setPreferredSize(p.screenDim);
        this.setVisible(true);

    }
     
    public void addController(GameController control){
        frame.addWindowListener(control);
    } 
    
    public void initFrame(){
   
        frame.add(this);

        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    }
    
    private Dinosaur dino;
   
    @Override
    public synchronized void update(Observable o, Object arg) {
        
        if (arg instanceof Deque){
                
                if (!busy){
                    busy = true;
                    tempFigs = (Deque<MoveableFigure>) arg;
                     //gui = dino.getGUIObj();

                    repaint();
                    busy = false;
                    //revalidate();
              
                }
           
               
        } else if (arg instanceof Dinosaur){
            this.setFocusable(true);
            this.requestFocus();
            this.addKeyListener(new DinoController((Dinosaur) arg));
            dino = (Dinosaur) arg;
            this.initFrame();
        }
    }
    
    public void setImage(ImageIcon img) {
        
            this.img = img.getImage();
            
            //image = ImageIO.read(getClass().getResourceAsStream("src/img1.JPG"));
            
        
    }
    
    public void promptQuit(){
        int dialogResult;
        dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?","Warning", JOptionPane.YES_NO_OPTION);
        
        if(dialogResult == JOptionPane.YES_OPTION){
  // Saving code here
   
            model.setGameOver(true);
            model.closeSession();
            
        } else{
            
            
            model.setInterrupted(false);
            
            
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
    
    
    
  
}






     