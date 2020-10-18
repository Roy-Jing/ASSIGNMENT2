package com.mycompany.pdcassignment2;

import java.util.Random;

/**
 *The Cloud class. Cloud is a hybrid of CollideableFigure and MoveableFigure
 * so, it should have functionalities derived from both. It is collideable 
 * because dinosaur can land on top of it.
 * As a result, Cloud should be composed of two wrappee objects, 
 * a MoveableFigure mF and a CollideableFigure cF.
 * 
 * @author Roy
 */


public class Cloud extends MoveableFigureBaseDecorator{
    
    Random generator = new Random();
    
    
    
    Cloud(MoveableFigure mF){
        super(mF);    
   

        setOriginalForm(new int[][]{
       
            {0,   0, 0, 0, 0, 0, 0, 0, 0,   1, 1,  1, 1, 1, 1, 1, 1, 1},
            {0,   1, 2, 3, 4, 5, 6, 7, 8,   0, 1, 2,  3, 4, 5, 6, 7, 8}
        });
        setVelocityX(-1);
        setCoordY(GameModel.getFrameHeight() - 4 - generator.nextInt(GameModel.getFrameHeight() - 4 ));
        setCoordX(GameModel.getFrameWidth() );
        this.setAltForm(this.getOriginalForm());
        this.setNumPixels(this.getOriginalForm()[0].length);
        this.setCollisionHandler(new CollideableCollisionHandler(this));
        
    }
   
    
    /**
     *
     * @param d
     * @param adjustedVelocity
     */
    
    
        @Override
        public Cloud getNewFigure(){

            return new Cloud(new MoveableObject("cloud"));

        }

    
   
         
    }
        
