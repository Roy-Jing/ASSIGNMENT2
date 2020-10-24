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
public abstract class CollisionHandler {
    public abstract void handleCollision();
    
    protected static Dinosaur dino;
    protected MoveableFigure self;
    
    CollisionHandler(MoveableFigure fig){
        self = fig;
        
    }
    
    public static void addDino(Dinosaur d){
        dino = d;
    }
    
    public abstract boolean checkForCollision();
      
               
   
    public boolean willIntersect(){
        
        //if dino is above self on the current frame, then dino's feet location minus
        //self's coord y will always be negative.
        //with this in mind, this difference plus self's coordY is < self's coordY
        //indicating dino will intersect with self.
        out.println("dino below the cloud on next frame " + (dino.getFeetLocationY()+ dino.getVelocityY() - self.getCoordY() >= 0));
        out.println("dino above the cloud on this frame" + (dino.getFeetLocationY() - self.getCoordY() < 0));
        
        //else (if difference is 0 or +ve, then it won't
        return (dino.getLeftFootCoordX() >= self.getCoordX() &&
                dino.getRightFootCoordX() <= self.getRightMostCoordX() 
                && (dino.getFeetLocationY() - self.getCoordY()) < 0)
                && (dino.getFeetLocationY()+ dino.getVelocityY() - self.getCoordY()) >= 0;
        
    }

    public abstract boolean checkStillWithinFrame();
   
}
