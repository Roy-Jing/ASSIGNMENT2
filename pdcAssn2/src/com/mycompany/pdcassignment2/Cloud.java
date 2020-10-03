package com.mycompany.pdcassignment2;

/**
 *The Cloud class. Cloud is a hybrid of CollideableFigure and MoveableFigure
 * so, it should have functionalities derived from both. It is collideable 
 * because dinosaur can land on top of it.
 * As a result, Cloud should be composed of two wrappee objects, 
 * a MoveableFigure mF and a CollideableFigure cF.
 * 
 * @author Roy
 */


public class Cloud extends MoveableFigureBaseDecorator implements CollideableFigure{
    
    private CollideableFigure collideableFig;
    
    
    
    
    Cloud(MoveableFigure mF, CollideableFigure cF){
        super(mF);    
        collideableFig = cF;
        setOriginalForm(new int[][]{
       
            {0,   0, 0, 0, 0, 0, 0, 0, 0,   1, 1,  1, 1, 1, 1, 1, 1, 1},
            {0,   1, 2, 3, 4, 5, 6, 7, 8,   0, 1, 2,  3, 4, 5, 6, 7, 8}
        });
        setVelocityX(-1);
        setCoordY(GameModel.getFrameHeight() - 4 -GameModel.getRand().nextInt(GameModel.getFrameHeight() - 4 ));
        setCoordX(GameModel.getFrameWidth() );

        this.setAltForm(this.getOriginalForm());
        collideableFig.setCoordY(getCoordY());
        collideableFig.setCoordX(getCoordX());
        this.setNumPixels(18);
        collideableFig.setRightMostCoordX(this.getRightMostCoordX());
        
    }
   
    /**
     *
     * @param d
     * @param adjustedVelocity
     */
    @Override
    public void adjustVelocity(Dinosaur d, int adjustedVelocity){
        
        d.setVelocityY(adjustedVelocity);
        d.setVelocityX(getVelocityX());        
      
    }
    
    @Override
    public Cloud getNewFigure(){
   
        return new Cloud(new MoveableObject(), new CollideableObject());
    }
   
    /**
     *
     * @param d
     * @return
     */
    @Override
    public int checkIfWillCollideWith(Dinosaur d) {
         int adjustedVelocityY;
         
         //For Cloud, we will also need to check that the dinosaur's feet horizontal coordinates are
         //such that 
         //dinosaur_left_foot_coord >= cloud_leftmost_coord  AND
         //
         //dinosaur_right_foot_coord <= cloud_rightmost_coord
         //
         //why we do this is because obviously the the (downward) travelling path of the dinosaur
         //will only intersect the horizontal travelling path of the cloud,
         //if the above logical conjunction is satisfied.
         
         if ( d.getRightFootCoordX() <= this.getRightMostCoordX() &&
                 
                d.getLeftFootCoordX() >= this.getCoordX()      &&
                 
                 (adjustedVelocityY = collideableFig.checkIfWillCollideWith(d)) != -1      ){
            return adjustedVelocityY;
         } 
         
         return -1;
         
    }
    
    @Override
    public void setRightMostCoordX(int rightMostCoordX) {
        collideableFig.setRightMostCoordX(rightMostCoordX);
    }
    
    
    @Override
    public boolean willIntersect(int feetLocationY, int dVelocityY) {
        
        return collideableFig.willIntersect(feetLocationY, dVelocityY);
        
    }

    

   
    
    
   
 
    
    
}

