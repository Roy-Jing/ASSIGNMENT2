package com.mycompany.pdcassignment2;



/**
 * The Plant class represents a Plant (or a Cactus) if you will.
 * Its form is displayed onto the screen. Composed of a MoveableFigure
 * Object f.
 * @author Roy
 */
public class Plant extends MoveableFigureBaseDecorator{
  
    Plant(MoveableFigure f){
        super(f);
        setNumPixels(3);
        setVelocityX(-1);
       this.setOriginalForm(new int[][]{
        {1, 0, -1},
        { 0, 0, 0}
       });
       
       this.setAltForm(this.getOriginalForm());
       
       setCoordX(GameModel.getFrameWidth() + 5);
       setCoordY(GameModel.getFrameHeight() - 3); //centre is the mid point
       
    }
    
    @Override
    public MoveableFigure getNewFigure(){
        return new Plant(new MoveableObject());
    }
   
  
}