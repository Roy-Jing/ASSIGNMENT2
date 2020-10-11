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
public class CollideableCollisionHandler extends CollisionHandler{
   
    
    private int adjustedVelocity;
    
    public void handleCollision(){
       
        this.adjustVelocity(adjustedVelocity);
    }
    
    CollideableCollisionHandler(Figure fig){
        super(fig);
        
    }
    
    public boolean checkForCollision(){
        
        
        //This condition check can skip this method if Dinosaur is currently jump
        //but not down. (As obviously jumping up will not cause the Dino to land
        //on any figures.
        int dVelocityY = dino.getVelocityY();
        if (dVelocityY < 0 )
            return false;
        
        int feetLocationY = dino.getFeetLocationY();
        //use the utility method willIntersect(int) to determine when the Dinosaur will
        //dino's feet y coordinate plus its velocity will intersect the collider's
        //"boundary coord", namely the collider's y coordinate closest to top of
        //the frame.

       //Suppose the dino's "feetLocationY" is at 20 currently and that the
       //boundary coord is at 21. Now, isAboveFigure(int) will return true
       //for isAboveFigure(
       if (willIntersect()){
           adjustedVelocity = self.getCoordY() - dino.getFeetLocationY() - 1;
           return true;

       }
       
       //return -1, signalling that the velocity needs not be adjusted.
       return false;
   
     
    }
    public void adjustVelocity(int adjustedVelocity){
        
        dino.setVelocityY(adjustedVelocity);
        if (DinoController.isJumping()){
             //resetting dinosaur's horizontal velocity to 0
             //because when dinosaur lands, it should not appear to
             //be moving on the screen.
             dino.setVelocityX(0);
             
        }
        
        dino.setShouldMovePart(true);
        
    }
    
}