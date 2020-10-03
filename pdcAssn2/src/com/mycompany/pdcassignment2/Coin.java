/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Roy
 */
public class Coin extends CollideableFigureBaseDecorator{
    private int value;
   
    private Color color;
    private boolean isCollected = false;
    private int ovalWidth = 10;
    private int coinSize = 10;
    private int spinDir = -1;
    Coin(CollideableFigure fig){
        super(fig);
        color = Color.YELLOW;
        
    }
    
    public void spin(){
        if (ovalWidth == 1){
            spinDir = 1;
        } else if (ovalWidth == coinSize){
            spinDir = -1;
        }
        
        ovalWidth += spinDir;
        
    }
    
    
    public void drawSelf(Graphics g){
        g.setColor(color);
        
        g.fillOval(super.getCoordX(), super.getCoordY(), ovalWidth , coinSize--);
       
        spin();
        
    }
    
    
}
