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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
public class GameGUI extends JPanel implements Observer, WindowListener {

    private boolean running = false;
    private DinoController dinoControl;
    private GameModel model;
    
    
    public void setModel(GameModel model) {
        this.model = model;
    }
    
    public DinoController getDinoControl() {
        return dinoControl;
    }

    public void setDinoControl(DinoController dinoControl) {
        this.dinoControl = dinoControl;
    }
    
    private Color bgColor;
    private Dimension screenDim;
   // private JFrame frame;
    private LinkedList<MoveableFigure> tempFigs;
    
  
    
    
   
    public void addController(DinoController dinoCon){
        this.addKeyListener(dinoCon);
        
    }
    
    
    
    @Override
    public void paintComponent(Graphics g){
        out.println("inside paintComp");
     
        super.paintComponent(g);
        
        out.println("drawing");

        if (tempFigs != null){
            out.println("drawing self");
            
            tempFigs.forEach((fig) -> {
                fig.drawSelf(g);
            });
            
            GameModel.setDrawing(false);
        } else 
            out.println("temFigs is null");
        
        //g.drawString(currentScore.toString(), 0, 0);
        
        
    }
    
    
    @Override
    public void update(Observable o, Object arg) {
        
        if (arg instanceof Preferences){
            
            out.println("GameGUI notified");

            bgColor = ((Preferences) arg).getBgColour();
            screenDim = ((Preferences) arg).getScreenDim();
            
            this.setBackground(bgColor);
           // frame.setPreferredSize(screenDim);
            //frame.setContentPane(this);
            //frame.setContentPane(this);
            
            //frame.setVisible(true);
            
           // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            repaint();
           
            //frame.setVisible(true);
           // frame.pack();
            
            out.println("set visible");
            
        } else if (arg instanceof LinkedList){
            tempFigs = (LinkedList<MoveableFigure>) arg;
            out.println("refreshing frame");
            
            repaint();
            
            //revalidate();
            out.println("after calling repaint");
            
        } else if (arg instanceof Dinosaur){
            this.addKeyListener(new DinoController((Dinosaur) arg));
        }
    }
    
    public void windowClosed(WindowEvent e){
        
    }

    @Override
    public void windowOpened(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   

   
    
}






     