/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

import java.awt.Color;
import java.awt.Graphics;
import static java.lang.System.out;
import java.util.Random;

/**
 *
 * @author Roy
 */
public class Coin extends MoveableFigureBaseDecorator{
    private int value;
   
    private boolean isCollected = false;
    private int ovalWidth = 50;
    private int coinSize = 50;
    private Random generator = new Random();
    
    public int getCoinSize() {
        return coinSize;
    }
    
    
    private int spinDir = -1;
    Coin(MoveableFigure fig){
        super(fig);
        setCollisionHandler(new CoinCollisionHandler(this));
        
        setColour(Color.YELLOW);
        setCoordY(GameModel.getFrameHeight() - (generator.nextInt(10) + ovalWidth));
        setCoordX(GameModel.getFrameWidth()  - ovalWidth);
        this.setVelocityX(-1 * GameModel.getPixelSize());
      
        
    }
    
   
    
    
    public boolean stillWithinFrame(){
        return ovalWidth / 2 + this.getCoordX() >= 0;
    }
    
    public void spin(){
        if (ovalWidth == 3){
            spinDir = 1;
        } else if (ovalWidth == coinSize){
            spinDir = -1;
        }
        
        ovalWidth += spinDir;
        
    }
    
    
    public void drawSelf(Graphics g){
        g.setColor(this.getColour());
        
        g.fillOval(super.getCoordX(), super.getCoordY(), ovalWidth , coinSize);
        
        spin();
        g.setColor(Color.BLACK);
        
    }
    
    public MoveableFigure getNewFigure(){
        return new Coin(new MoveableObject("Coin"));
        
        
    }

   

    

   
    
}

class CoinCollisionHandler extends MoveableCollisionHandler{
    
    CoinCollisionHandler(Coin c){
        super(c);
       
        
    }
    public void handleCollision(){
        GameModel.addCoin();
        
        ((Coin)self).setActive(false);
        
    }
   
}