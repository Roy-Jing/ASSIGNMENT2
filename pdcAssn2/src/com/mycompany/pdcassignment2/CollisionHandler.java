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

    public abstract boolean checkStillWithinFrame();
    
   
}
