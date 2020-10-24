/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

import static java.lang.System.out;
import java.util.Arrays;

/**
 *
 * @author Roy
 */
public class MoveableCollisionHandler extends CollisionHandler{
    
    MoveableCollisionHandler(MoveableFigure fig){
        super(fig);
        
    }
    
    public boolean checkStillWithinFrame(){
        return self.stillWithinFrame();
    }
    
    
    
    @Override
    public boolean checkForCollision(){
            
        
        //char[][] copyVitual = dino.getVirtualGUI();
        if (!(self instanceof Dinosaur)){
            char[][] virtualGUI = dino.getVirtualGUI();
        
            if (GameModel.pixelInFrame(self.getCoordX(), self.getCoordY()))
                 virtualGUI[self.getCoordY() ][self.getCoordX()] = '/';
                 
        int[][] selfForm = self.getOriginalForm();
        int coordX = self.getCoordX();
        int coordY = self.getCoordY();
        int velX = self.getVelocityX();
        int velY = self.getVelocityY();
        
        for (int c = 0; c < self.getNumPixels(); c++){
            
            int cX= selfForm[1][c] + coordX + velX;
            int cY = selfForm[0][c] + coordY + velY;
            
            if (GameModel.pixelInFrame(cX, cY) && virtualGUI[cY][cX] == '!'){
                 out.println("COLLISION DETECTED");
                return true;
                //System.exit(0);
            }
                 
        }   
        
        }
       return false; 
    }
    
    
    
    @Override
    public void handleCollision() {
        
       // GameModel.setGameOver(true);
        
    }
    
}

