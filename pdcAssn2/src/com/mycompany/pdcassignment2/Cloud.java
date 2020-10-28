package com.mycompany.pdcassignment2;

import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;

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
    
    private Random generator = new Random();
    static Image originalForm = new ImageIcon("src/cloud.png").getImage();
    static Image altForm = originalForm;

    
    
    Cloud(MoveableFigure mF){
        super(mF);    
         this.setWidth(200);
        this.setHeight(30);
        setOriginalForm(originalForm);
        setVelocityX(-GameModel.getPixelSize());
        setCoordY(GameModel.getFrameHeight() - getHeight() * 2 - generator.nextInt(3) * this.getHeight() - 1);
        setCoordX(GameModel.getFrameWidth() );
        this.setAltForm(this.getOriginalForm());
       
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


        
