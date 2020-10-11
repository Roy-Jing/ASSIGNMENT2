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
public class Coin extends MoveableFigureBaseDecorator implements CollideableFigure{
    private int value;
   
    private Color color;
    private boolean isCollected = false;
    private int ovalWidth = 10;
    private int coinSize = 10;

    public int getCoinSize() {
        return coinSize;
    }
    private int spinDir = -1;
    Coin(MoveableFigure fig){
        super(fig);
        figure.setCollisionHandler(new CoinCollisionHandler(figure));
        color = Color.YELLOW;
        
    }
    
    public void handleCollision(){
        this.setActive(false);
        GameModel.addCoin();
        this.setActive(false);
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

    @Override
    public void setRightMostCoordX(int c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

   
    
}

class CoinCollisionHandler extends CollideableCollisionHandler{
    
    CoinCollisionHandler(Figure f){
        super(f);
    }
    public void handleCollision(){
        
    }
    public boolean checkForCollision() {
        int[][] dinoMatrix = dino.getOriginalForm();
        
        for (int i = 0; i < dino.getNumPixels(); i++){
            int cx = dinoMatrix[1][i];
            
            int cy = dinoMatrix[0][i];
            
            if (Math.sqrt((Math.pow((cx - self.getCoordX()), 2.0) +
                    (Math.pow((cy - self.getCoordY()), 2.0)))) < (((Coin) self).getCoinSize() / 2)){
                return true;
            }
        }
        
        return false;
    }
}