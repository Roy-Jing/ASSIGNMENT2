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
public class CollideableCollisionHandler extends CollisionHandler{
   
    
    private int adjustedVelocity;
    
    public void handleCollision(){
        
        this.adjustVelocity(adjustedVelocity);
        dino.setShouldMovePart(false);

    }
    
    CollideableCollisionHandler(MoveableFigure fig){
        super(fig);
        
    }
    
    public boolean checkStillWithinFrame(){
        return self.getCoordX() >= 0;
        
        
    }
    public boolean checkForCollision(){
               ;
        
         char[][] gui = dino.getVirtualGUI();
        
        //This condition check can skip this method if Dinosaur is currently jump
        //but not down. (As obviously jumping up will not cause the Dino to land
        //on any figures.
        int dVelocityY = dino.getVelocityY();
        if (dVelocityY < 0 )
            return false;
        if (self instanceof Cloud && dino.isJumping() && dino.getVelocityY() > 0){
             out.println("cloud");
            
             out.println("dino coordX" + dino.getCoordX());
            
             out.println("dino previous coordY" + dino.getCoordY());
             out.println("dino coordY" + (dino.getCoordY() + dVelocityY));
             out.println("cloud coord y" + (self.getCoordY()));
                    
             out.println("cloud coordX" + self.getCoordX() );

        }
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
         out.println("adjusting velocity");
        
        dino.setVelocityY(adjustedVelocity);
        if (dino.isJumping()){
             //resetting dinosaur's horizontal velocity to 0
             //because when dinosaur lands, it should not appear to
             //be moving on the screen.
             dino.setVelocityX(self.getVelocityX());
             dino.isJumping(false);
             
        }
        
        
    }
    
}
