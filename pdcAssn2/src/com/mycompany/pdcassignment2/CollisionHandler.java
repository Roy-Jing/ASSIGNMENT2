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
public abstract class CollisionHandler<F extends Figure> {
    public abstract void handleCollision();
    
    protected static Dinosaur dino;
    protected F self;
    
    CollisionHandler(F fig){
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
        
        //else (if difference is 0 or +ve, then it won't
        return (dino.getFeetLocationY() - self.getCoordY()) < 0
                && (dino.getFeetLocationY()+ dino.getVelocityY() - self.getCoordY()) >= 0;
        
    }

    public abstract boolean checkStillWithinFrame();
   
}
