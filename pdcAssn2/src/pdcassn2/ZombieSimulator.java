/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcassn2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Roy
 */

class ZombieSimulator extends JPanel{
    
    JButton btn = new JButton("add");
    static Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    static final int width = d.width;
    static final int height = d.height;
    Random gen = new Random();
    static LinkedList<Human> hums = new LinkedList();
    

    ZombieSimulator(){
        add(btn);
        this.setSize(d);
        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == btn){
            Human h = new Human(gen.nextInt(width), gen.nextInt(height), 10, true, width, height);
            h.setGUI(this);
            new Thread(h).start();
            
        } else{
            repaint();
            
        }
    }
    
    public void paintComponent(Graphics g){
        Collection<Human> c = Collections.synchronizedList(hums);
        for (Human h : c){
            h.drawSelf(g);
            
            h.move();
           
        }
    }
    public static void main(String args[]){
        ZombieSimulator z = new ZombieSimulator();
        
        z.setVisible(true);
    }
}

class Human implements Runnable{
    private Color clr = Color.BLACK;
    
    double x;
    double y;
    double size;
    boolean isAlive;
    int world_width;
    int world_height;
    double velX, velY;
    
    ZombieSimulator gui;
    public void setGUI(ZombieSimulator gui){
        this.gui = gui;
        velX = (gui.gen.nextBoolean() ? -3 : 3);
        velY = (gui.gen.nextBoolean() ? -3 : 3);
    }
    Human(double x, double y, double size, boolean isAlive, int world_width, int world_height)
    {
        
        
        this.x = x;
        this.y = y;
        this.size = size;
        this.isAlive = isAlive;
        this.world_height = world_height;
        this.world_width = world_width;

    }
    
    
    public void move(){
        boolean bOrT = gui.gen.nextBoolean();
        boolean lOrR = gui.gen.nextBoolean();
        
        
        if (x - gui.getWidth() == 0){
            velX = -3;
        } else if (x == 0){
            velX = 3;
        } else{
            velX = velX + x % (lOrR ? x + 1 : (gui.getWidth() - x + 1));
            
        }
        
        if (y - gui.getWidth() == 0){
            velY = -3;
        } else if (x == 0){
            velY = 3;
        } else{
            velY = velX + y % (bOrT ? y + 1 : (gui.getWidth() - y + 1));
            
        }
        
        x += velX;
        y += velY;
        
    }
    public void run(){
        while(true){
            move();
        }
    }
    public void drawSelf(Graphics g){
        g.setColor(clr);
        g.drawOval((int) x, (int) y, (int)size,(int) size);
        gui.repaint();
    }
}
