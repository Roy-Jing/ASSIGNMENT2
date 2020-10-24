/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static java.lang.System.out;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author Roy
 */
public class Comp extends JComponent{
    
    int coordX, coordY;
    
    int speedX, speedY;
    private Image img = new ImageIcon("src/dinosaur.png").getImage();
    private int width, height;
    
    Comp(String name, int width, int height){
       coordX = 500/2;
       coordY = 250;
       speedX = 1;
       this.width = width;
        this.height = height;
       
       this.setBounds(500/2, 500/2, width, height);
    }
    
    
   
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        
     
        g2.drawRect(coordX, coordY, width, height);
        g2.fillRect(coordX, coordY, width, height);
     
       
        
    }
    public void move(){
       if (coordX <= 0){
           speedX = 1;
       }  else if (coordX >= 500){
           speedX = -1;
       }
       this.setLocation(coordX + this.speedX, coordY + this.speedY);

       coordX += speedX;
       coordY += speedY;
       
       //out.println("printing");
               // super.paintComponent(g);

        
       

    }
    
    
    public boolean checkIfWillCollide(Comp another){
        if (another.speedY > 0 && another.getX() >= this.getX() && another.getX() + another.getWidth() < this.getX() + this.getWidth()){
            
            
            if (this.getBounds().intersects(another.getBounds())){
                out.println("will collide");
                return true;
            }
            
        }
        
        return false;
        
    }
    
    
    public static void main(String args[]){
        new Thread(new driver()).start();
        
        
    }
    
}

 class key extends KeyAdapter implements KeyListener{
        private Comp a;
        key(Comp a){
            this.a = a;
        }
              
               public void keyPressed(KeyEvent k){
                   out.println("key pressed");
                   
                   
                   char c= k.getKeyChar();
                   switch (c){
                       case 'a':
                           a.speedX = -3;
                           break;
                       case 'd':
                           a.speedX = 3;
                           break;
                       case 'w':
                           a.speedY = -3;
                           break;
                       case 's':
                           a.speedY = 3;
                           break;
                           
                   }
               }
               
               public void keyReleased(KeyEvent k){
                   a.speedX = a.speedY = 0;
                   
               }
    }
class driver implements Runnable{
   public void run(){
                JFrame parent = new JFrame();
     
        
        
        parent.setSize(500, 500);
        
        Comp compA = new Comp("BALL", 30, 30);
        Comp compB = new Comp("b", 50, 50);
        panel pane = new panel(new Comp[]{compA, compB});
        
        parent.addKeyListener(new key(compA));
        parent.add(pane);
       // pane.setVisibe(true);
        parent.setVisible(true);
        
        while (true){
            
            
            compA.move();
            compB.checkIfWillCollide(compA);
           
            compB.move();
            
            
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(Comp.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            pane.repaint();
        }
        
            }
        
}

class panel extends JPanel{
    Comp[] comps;
    panel(Comp[] c){
       comps = c;
        
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
       
        
        for (Comp c : comps){
           c.draw(g);
       }
    }
}