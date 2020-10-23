/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 *
 * @author Roy
 */
public class Coin extends MoveableFigureBaseDecorator{
    private int value;
   
    private Color color;
    private boolean isCollected = false;
    private int ovalWidth = 10;
    private int coinSize = 10;
    private Random generator = new Random();
    
    public int getCoinSize() {
        return coinSize;
    }
    private int spinDir = -1;
    Coin(MoveableFigure fig){
        super(fig);
        figure.setCollisionHandler(new CoinCollisionHandler(this));
        color = Color.YELLOW;
        
        setCoordY(GameModel.getFrameHeight() - generator.nextInt(10));
        setCoordX(GameModel.getFrameWidth()  - ovalWidth);
        
        
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
        
        g.fillOval(super.getCoordX(), super.getCoordY(), ovalWidth , coinSize);
        
        spin();
        
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
    
    
    public boolean checkForCollision() {
        int[][] dinoMatrix = dino.getOriginalForm();
        
        for (int i = 0; i < dino.getNumPixels(); i++){
            int cx = dinoMatrix[1][i];
            
            int cy = dinoMatrix[0][i];
            
            if (Math.sqrt((Math.pow((cx - self.getCoordX()), 2.0) +
                    (Math.pow((cy - ((Coin)self).getCoordY()), 2.0)))) < ((Coin)self).getCoinSize() / 2){
                return true;
            }
        }
        
        return false;
    }
}