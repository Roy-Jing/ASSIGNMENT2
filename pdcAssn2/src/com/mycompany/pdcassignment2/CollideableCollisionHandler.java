/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

import java.awt.Image;
import java.awt.Shape;
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
        return self.getCoordX() + self.getWidth() >= 0;
        
        
    }
    
    public boolean willIntersect(){
        
        
        int dinoBottomCoordY = dino.getCoordY() + dino.getHeight();
        int dinoRightEdge = dino.getCoordX() + dino.getWidth();
//        out.println(dinoRightEdge <= self.getCoordX() + self.getVelocityX());
//        
//        //self's coord y will always be negative.
//        //with this in mind, this difference plus self's coordY is < self's coordY
//        out.println("dinorightedge = " + dinoRightEdge);
//        out.println("dinoleftedge = " + dino.getCoordX());
//        
//        out.println("this.rightCoordX" + self.getCoordX() + self.getWidth());
//        out.println("this.leftCoordX" + self.getCoordX());

        if (dinoRightEdge <= self.getCoordX() + self.getWidth()&&
                dino.getCoordX() >= self.getCoordX()){
           
        }
        //else (if difference is 0 or +ve, then it won't
        return ( dinoBottomCoordY  - self.getCoordY() < 0 &&
             (dino.getCoordX() >= self.getCoordX() &&
                dinoRightEdge <= self.getRightMostCoordX() )
                && (dinoBottomCoordY + dino.getVelocityY() - self.getCoordY()) >= 0);

    }
    
    public void handleFall(){
        dino.isJumping(true);
        dino.setAccelerated(false);
     
        dino.setVelocityX(-1);
    }
    public boolean checkIfWillFallOff(){
        int dinoBottomCoordY = dino.getFeetLocationY();
      
        if (dinoBottomCoordY == self.getCoordY() - 1){
            out.println("yes");
            if (dino.getCoordX() >= self.getCoordX() && 
                    dino.getCoordX() + dino.getVelocityX() < self.getCoordX() + self.getVelocityX()||
                        (dino.getRightFootCoordX()  <= self.getRightMostCoordX() &&
                            dino.getRightFootCoordX() + dino.getVelocityX() > self.getRightMostCoordX() + self.getVelocityX()))
                return true;
        }
        
        return false;
    }
   public boolean checkForCollision(){
       
       if (dino.getVelocityY() < 0 ){
           return false;
       }
        //use the utility method willIntersect(int) to determine when the Dinosaur will
        //dino's feet y coordinate plus its velocity will intersect the collider's
        //"boundary coord", namely the collider's y coordinate closest to top of
        //the frame.

       //Suppose the dino's "feetLocationY" is at 20 currently and that the
       //boundary coord is at 21. Now, isAboveFigure(int) will return true
       //for isAboveFigure(
       if (this.checkIfWillFallOff()){
           
           this.handleFall();
           return false;
       }
       if (willIntersect()){
           
           out.println("colliding");
           
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
