/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.awt.event.WindowListener;
import java.io.IOException;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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


   // private JFrame frame;
    private List<MoveableFigure> tempFigs;
    
  
    
    
   
    public void addController(DinoController dinoCon){
        this.addKeyListener(dinoCon);
        
    }
    
    
    private volatile boolean busy = false;
    
    @Override
    public  void paintComponent(Graphics g){
        super.paintComponent(g);
        
        if (GameModel.isGameOver()){
            this.displayGameOver(g);
            return;
        }
        
       
        g.drawImage(img, 0, 0, GameModel.getFrameWidth(), GameModel.getFrameHeight(), null);
        g.setFont(new Font("Consolas", Font.BOLD, 16));
        g.setColor(Color.WHITE);
        
        g.drawString("Your current score: " + GameModel.getScore(), 0, 10);
        g.drawString("Number of coins collected :" + GameModel.getCoinCount(), 0, 30);
        if (tempFigs == null){
            out.println("null");
            
            busy = false;
            return;
        }
        
            ListIterator<MoveableFigure> it = tempFigs.listIterator(tempFigs.size());
            if (!it.hasPrevious()){
                out.println("has previous");
                
            }
            while (it.hasPrevious() ){
                MoveableFigure fig;
                fig = it.previous();
                if (fig.isActive()){
                    
                    out.println("drawing self");
                    
                    fig.drawSelf(g);
                    fig.doRun();
                } else
                    it.remove();
            }

        busy = false;
    }
//    
  
     public void loadPreferences(Preferences p){
        this.setImage(p.bgImage);
        this.setPreferredSize(p.screenDim);

        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        frame.pack();
      
        frame.setResizable(false);
        
        frame.setVisible(true);

        
    }
     
    public void addController(GameController control){
        frame.addWindowListener(control);
    } 
    
    public void addDinoController(Dinosaur dino){
        
        DinoController controller = new DinoController(dino);
        this.addKeyListener(controller);
        this.setFocusable(true);
        this.requestFocus();
        
    }
    
   
    @Override
    public void update(Observable o, Object arg) {
        
        //List is presumed to be the list of current in-frame figures.
        
        if (!busy && arg instanceof List){
            
            busy = true;
            tempFigs =(List)  arg;
            
        } 
        repaint();
 

    }
    
    private boolean blinked = false;
    public void displayGameOver(Graphics g){
      
        g.setFont(new Font("Consolas", Font.BOLD, 18));

        if (!blinked){
            blinked = true;
            g.drawString("Game Over (:", GameModel.getFrameWidth()/ 2, GameModel.getFrameHeight()/ 2);
        } else{
            g.drawString("", this.getWidth() / 2, this.getHeight() / 2);
            blinked = false;
        }
            
            
        
    }
   public void setImage(ImageIcon img) {
        
            this.img = img.getImage();
            
            //image = ImageIO.read(getClass().getResourceAsStream("src/img1.JPG"));
            
        
    }
    
   private JButton replay = new JButton("Replay");
   
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
        
        new ExitConfirmWindow().promptQuit();
        
    }
    
    class ExitConfirmWindow extends JFrame{
    private JButton confirmClose = new JButton("Yes");
    private JButton rejectClose = new JButton("No");
    private ClickHandler clickHandler = new ClickHandler();
    ExitConfirmWindow(){
        this.setLayout(new BorderLayout());
        this.add(new JLabel("Are you sure you want to quit?"), BorderLayout.NORTH);
        this.add(this.confirmClose, BorderLayout.WEST);
        this.add(this.rejectClose, BorderLayout.EAST);
        confirmClose.addActionListener(clickHandler);
        rejectClose.addActionListener(clickHandler);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
    }
    
    class ClickHandler implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == confirmClose){
  // Saving code here
   
                    model.setGameOver(true);
                    model.closeSession();

                } else{


                    model.setInterrupted(false);


                }
            }
            
           
        
        }
    
         public void promptQuit(){
                        this.setVisible(true);

            }
    
  
}
   
   
    
}












     