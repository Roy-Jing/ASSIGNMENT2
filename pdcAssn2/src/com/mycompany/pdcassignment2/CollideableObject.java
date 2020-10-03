package com.mycompany.pdcassignment2;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A CollideableObject is one which can collide with the dinosaur and not cause the
 * game to exit. Essentially it means Dinosaur can land (or jump) on top of the  object.
 * @author Roy
 */
public class CollideableObject implements CollideableFigure{
    
    private int coordX, rightMostCoordX;
    private int coordY;
    private boolean active = true;
    
    @Override
    public void setRightMostCoordX(int rightMostCoordX) {
        this.rightMostCoordX = rightMostCoordX;
    }
    
    @Override
    public int getCoordY() {
        return coordY;
    }
    
      
  

    @Override
    public int getRightMostCoordX() {
        return rightMostCoordX;
    }

    @Override
    public void setCoordY(int c) {
        coordY = c;
    }

    @Override
    public int getCoordX() {
        return coordX;
    }

    @Override
    public void setCoordX(int c) {
        coordX = c;
    }
    
    
    /**
     * This method checks whether the Dinosaur will collide with it, in the 
     * NEXT frame.
     * @param dino
     * @return 
     */
    @Override
    public int checkIfWillCollideWith(Dinosaur dino){
        
        //This condition check can skip this method if Dinosaur is currently jump
        //but not down. (As obviously jumping up will not cause the Dino to land
        //on any figures.
        int dVelocityY = dino.getVelocityY();
        if (dVelocityY < 0 )
            return -1;
        
        int feetLocationY = dino.getFeetLocationY();
        //use the utility method willIntersect(int) to determine when the Dinosaur will
        //dino's feet y coordinate plus its velocity will intersect the collider's
        //"boundary coord", namely the collider's y coordinate closest to top of
        //the frame.

       //Suppose the dino's "feetLocationY" is at 20 currently and that the
       //boundary coord is at 21. Now, isAboveFigure(int) will return true
       //for isAboveFigure(
       if (willIntersect(feetLocationY, dVelocityY))

            return this.getCoordY() - feetLocationY - 1;
       
       //return -1, signalling that the velocity needs not be adjusted.
       return -1;
               
    }
   
    /**
     * adjusts dinosaur's velocities, on the basis that a collision with this
     * object is imminent. (This method shall only be called if the 
     * method checkIfWillCollideWith returns true).
     * @param d
     * @param adjustedVelocity 
     */
    @Override
    public void adjustVelocity(Dinosaur d, int adjustedVelocity){
        
        d.setVelocityY(adjustedVelocity);
        if (d.isJumping()){
             //resetting dinosaur's horizontal velocity to 0
             //because when dinosaur lands, it should not appear to
             //be moving on the screen.
             d.setVelocityX(0);
             
        }
        
        d.setShouldMovePart(true);
        
    }
    
    /*
    * check if dinosaur's feet will "collide" with this object.
    */
    @Override
    public boolean willIntersect(int feetLocationY, int dinoVelocityY){
        
        //dinotIsAboveThisObjectOnNextFrame is literally what it means: it checks whether the
        //dinosaur's (bottom-most) next y coordinate in the next game frame will cross
        //this object's top most y coordinate. If such is the case, the variable is set to false.
        
        //Likewise dinotIsAboveThisObjectOnCurrentFrame checks whether the
        //dinosaur's (bottom-most) current y coordinate will cross
        //this object's top most y coordinate. If such is the case, variable is set to false.
        
        boolean dinoIsAboveThisObjectOnNextFrame = dinoVelocityY  + feetLocationY - this.getCoordY() <0;
        boolean dinotIsAboveThisObjectOnCurrentFrame = feetLocationY - this.getCoordY() < 0;
        
        //If the two boolean values are the same, then we know that dinosaur will collide
        //with this object.
        return dinoIsAboveThisObjectOnNextFrame ^ dinotIsAboveThisObjectOnCurrentFrame;
    }
    
}
