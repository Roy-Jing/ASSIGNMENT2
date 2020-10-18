/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

import static java.lang.System.out;

/**
 *
 * @author Roy
 */
public class MoveableCollisionHandler extends CollisionHandler<MoveableFigure>{
    
    MoveableCollisionHandler(MoveableFigure fig){
        super(fig);
        
    }
    
    
    @Override
    public boolean checkForCollision(){
            if (! (self instanceof Dinosaur)){
            char[][] virtualGUI = GameModel.getVirtualGUI();
            
            int[][] selfCoords = self.getOriginalForm();
          
            for (int i = 0; i < self.getNumPixels(); i++){
                int coordX = self.getCoordX() + selfCoords[1][i];
                
                int coordY = self.getCoordY() + selfCoords[0][i];
                if (GameModel.pixelInFrame(coordX, coordY)){
                    if (virtualGUI[coordY][coordX] == '!'){
                        return true;
                    }
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

