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
    
    
    @Override
    public synchronized void paintComponent(Graphics g){
        out.println("inside paintComp");
        
        super.paintComponent(g);
        g.drawImage(image, 0, 0, GameModel.getFrameWidth(), GameModel.getFrameHeight(), null);
        
        //img.paintIcon(this, g, 0, 0);
        g.drawRect(GameModel.getFrameWidth() / 2, GameModel.getFrameHeight() / 2, 10, 10);
        boolean found = false;
        MoveableFigure first = tempFigs.peekFirst();
      
      
        MoveableFigure fig;
        
        do{
           fig = tempFigs.pollLast();

            
           
            if (fig.isActive()){
               
                fig.drawSelf(g);
                fig.doRun();
                tempFigs.addFirst(fig);
            }
            
        } while (fig != first);
        
        if (dino.isActive()){
            dino.drawSelf(g);
            dino.doRun();
            
            
        }
        printGUI();
       
        
        
    }
    
    public void printGUI(){
        for (char [] row : dino.getVirtualGUI()){
            for (char c : row){
                out.print(c);
            }
            
            out.println();
            
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
    public void update(Observable o, Object arg) {
        
        if (arg instanceof LinkedList){
            
              
                tempFigs = (LinkedList<MoveableFigure>) arg;
                out.println("refreshing frame");

                repaint();
                busy = false;
                //revalidate();
                out.println("after calling repaint");
           
               
        } else if (arg instanceof Dinosaur){
            this.setFocusable(true);
            this.requestFocus();
            this.addKeyListener(new DinoController((Dinosaur) arg));
            dino = (Dinosaur) arg;
            this.initFrame();
        }
    }
    private Image image;
    
    public void setImage(ImageIcon img) {
        
            this.img = img.getImage();
            
            //image = ImageIO.read(getClass().getResourceAsStream("src/img1.JPG"));
            out.println("set image :" + image);
            
        
    }
    
    public void promptQuit(){
        int dialogResult;
        dialogResult = JOptionPane.showConfirmDialog(this, "Would You Like to Save your Previous Note First?","Warning", JOptionPane.YES_NO_OPTION);
        
        if(dialogResult == JOptionPane.YES_OPTION){
  // Saving code here
            model.setGameOver(true);
           
        } else{
            model.setInterrupted(false);
            frame.dispose();
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






     