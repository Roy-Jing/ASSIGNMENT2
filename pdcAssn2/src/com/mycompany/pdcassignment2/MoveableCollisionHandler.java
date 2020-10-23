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
public class MoveableCollisionHandler extends CollisionHandler<MoveableFigure>{
    
    MoveableCollisionHandler(MoveableFigure fig){
        super(fig);
        
    }
    
    public boolean checkStillWithinFrame(){
        return self.stillWithinFrame();
    }
    
    
    
    @Override
    public boolean checkForCollision(){
            
        char[][] copyVitual = dino.getVirtualGUI();
       
            
           
            if (! (self instanceof Dinosaur)){
                char[][] virtualGUI = dino.getVirtualGUI();
                
                int[][] selfCoords = self.getOriginalForm();

                for (int i = 0; i < self.getNumPixels(); i++){
                    int coordX = self.getCoordX() + selfCoords[1][i];
                    
                    int coordY = self.getCoordY() + selfCoords[0][i];
                    
                    if (GameModel.pixelInFrame(coordX + self.getVelocityX(), coordY + self.getVelocityY()))
                            copyVitual[coordY + self.getVelocityY()][coordX + self.getVelocityX()] = '.';
                        
                    if (GameModel.pixelInFrame(coordX, coordY)){
                        copyVitual[coordY][coordX] = ' ';
                        
                        
                        
                        if (virtualGUI[coordY][coordX] == '!'){
                            out.println("colliding!");
                            out.println("colliding!");
                            out.println("colliding!");
                            return true;
                        }
                    }
                }
            }  
            
            out.println("vitual");
            
            for (char[] row : copyVitual ){
                for (char c : row ){
                    out.print(c);
                }
                out.println();
            }
            return false;
            
    }
    
    
    
    @Override
    public void handleCollision() {
        
       // GameModel.setGameOver(true);
        
    }
    
}

