/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

/**
 *
 * @author Roy
 */
public class MoveableCollisionHandler extends CollisionHandler<MoveableFigure>{
    
    MoveableCollisionHandler(MoveableFigure fig){
        super(fig);
        
    }
    
    public boolean checkForCollision(){
            char[][] virtualGUI = GameModel.getVirtualGUI();
            
            int[][] selfCoords = self.getOriginalForm();
          
            for (int i = 0; i < self.getNumPixels(); i++){
                int coordX = self.getCoordX() + selfCoords[1][i];
                int coordY = self.getCoordY() + selfCoords[0][i];
                
                if (virtualGUI[coordY][coordX] == '!'){
                    return true;
                }
            }
            
            return false;
    }
    @Override
    public void handleCollision() {
        
        GameModel.setGameOver(true);
        
    }
    
}
