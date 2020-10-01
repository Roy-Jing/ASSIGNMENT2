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
    private int radius;
    private int width;
    private Color color;
    private boolean isCollected = false;
    Coin(CollideableFigure fig){
        super(fig);
        color = Color.YELLOW;
        
    }
    public void drawSelf(Graphics g){
        g.setColor(color);
        
        g.fillOval(super.getCoordX(), super.getCoordY(), width, radius);
        
    }
    
    public void run(){
        while (!isCollected){
            int direction = 1;
            if (width == radius){
                direction *= -1;
            } else if (width == 0)
                direction *= -1;
            
            width += direction;
        }
    }
   
}
