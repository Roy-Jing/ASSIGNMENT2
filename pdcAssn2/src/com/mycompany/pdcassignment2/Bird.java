package com.mycompany.pdcassignment2;

import java.awt.Graphics;


/**
 *Class Bird is a concrete decorator for a MoveableObject.
 * It's altForm is different from originalForm
 * @author Roy
 */
public class Bird extends Animal{
    
    
    Bird(MoveableFigure f){
        super(f);
        
       setAltForm(new int[][]{
             {0,  0, 0, 0, 0, -1, -1, 1, 1, -1},
             {0,  1, 2, 3, 4,  2, 3, 2, 3, 5}
         });
         setOriginalForm(new int[][]{
           {0,  0, 0, 0, 0, -1,-2, 1, 2, -1},
           {0,  1, 2, 3, 4,  2, 3, 2, 3, 5}}
         );
      
       setCoordX(GameModel.getFrameWidth() + 5);
       setCoordY(GameModel.getFrameHeight() - 8);
       setNumPixels(this.getAltForm()[0].length);
       this.setVelocityX(-2);
     
    }
    
    //for the meaning of the method please see the enum class MoveableTypes
    @Override
     public Bird getNewFigure(){
        return new Bird(new MoveableObject());
    } 
   

    public void run(){
        while (super.getRightMostCoordX() >= 0){
            
        }
    }
 
    
    
    
}