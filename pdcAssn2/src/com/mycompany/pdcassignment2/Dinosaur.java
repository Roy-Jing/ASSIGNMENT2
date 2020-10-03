package com.mycompany.pdcassignment2;

import java.awt.Graphics;

/*
 * The Dinosaur class that is to be controlled by the player.
 */

/**
 *
 * @author Roy
 */



class Dinosaur extends Animal{
  
    private boolean previouslyHunched = false;
    private boolean hunched = false;
    private boolean isJumping = false;

    public boolean isJumping() {
        return isJumping;
    }
    //the indices in the coordinate matrix (altForm and 
    //originalForm) of Dinosaur, where the feet pixels can be found.
    private final int leftFootCoordIndexX = 4;
    private final int rightFootCoordIndexX = 9 ;
    
    //the indices in the coordinate matrix (altForm and 
    //originalForm) of Dinosaur, where the neck pixels
    //can be found
    private final int neckIndexX1 = 8;
    private final int neckIndexX2 = 10;
    private boolean accelerated = false;
    private boolean landed = true;
    private boolean shouldMovePart = true;
    private GameGUI observerView;
    
    public boolean isLanded() {
        return landed;
    }

    public void setLanded(boolean landed) {
        this.landed = landed;
    }
   
    public boolean isAccelerated() {
        return accelerated;
    }

    public void setAccelerated(boolean accelerated) {
        this.accelerated = accelerated;
    }
    
    
    public boolean isPreviouslyHunched() {
        return previouslyHunched;
    }

    public void setPreviouslyHunched(boolean previouslyHunched) {
        this.previouslyHunched = previouslyHunched;
    }
    
    public boolean isHunched() {
        return hunched;
    }
    
    public void setHunched(boolean hunched) {
        this.hunched = hunched;
    }
    
    /**
     * getting the Y coord of Dinosaur's feet.
     * @return 
     */
    public int getFeetLocationY() {
        return this.getOriginalForm()[0][leftFootCoordIndexX] + getCoordY();
    }
    
   
    public void moveBackward(){
        if (!this.isJumping){
            if (this.stillWithinFrame()){
                this.setCoordX(this.getCoordX() + this.getVelocityX());
                
            }
            
        }
    }
    
    public void moveForward(){
        
    }
    
    public void jump(){
        isJumping = true;
        
    }
    
    public void hunch(){
        
    }
    
    
    
    /**
     * The stillWithinFrame method will adjust a MoveableFigure's
     * next horizontal velocity. The velocityX value will be used
     * by method refreshFrameMatrix to shift a MoveableFigure across
     * the screen
     * @return 
     */
    @Override
    public boolean stillWithinFrame(){
        
        //we don't want the dinosaur to shift outside the frame,
        //so the wise thing to do is to readjust its velocityX 
        //so that it doesn't
        
        //check left edge of frame.
        if (this.getCoordX() + this.getVelocityX() < 0){
             this.setVelocityX(-this.getCoordX());
             this.setAccelerated(false);
        }
        
        //check right edge of frame.
        else if (getRightMostCoordX() + this.getVelocityX() >= GameModel.getFrameWidth()){
            this.setVelocityX(GameModel.getFrameWidth() - this.getRightMostCoordX() - 1);
            this.setAccelerated(false);

        }
        
        return true;
        
    }
   
   
    Dinosaur(MoveableFigure figure){
        super(figure);
        setCoordY(GameModel.getFrameHeight() - 5); //coord Y is body line
        setCoordX(5);
        
        setAltForm(new int[][]{
            {0, 1,  2,  1, 3, 1,  1, 2, 0, 3, -1},//first pair is tail
            //last is feet
            //x
            {0, 1,   1, 2, 0, 3,  4, 4, 5,3,  6}
           
        });
        setOriginalForm(new int[][]{
        //y
            {0, 1,  2,  1, 3, 1,  1, 2, 0, 3, -1},//first pair is tail
            //last is feet
            //x
            {0, 1,   1, 2, 2, 3,  4, 4, 5,5,  6}
    });
       
        this.setSymbol('*');
        this.setNumPixels(11);
        
     
    }
    
    //Get the x coordinate of Dinosaur's foot, on the Game frame.
    public int getLeftFootCoordX(){
        
        return this.getOriginalForm()[1][leftFootCoordIndexX] + this.getCoordX();
    
    }
    
    public int getRightFootCoordX(){
        
         return this.getOriginalForm()[1][rightFootCoordIndexX] + this.getCoordX();

    }
    
    public int[][] getAltForm(){
        
        this.hunch(hunched);
        
        if (shouldMovePart)
            
            return super.getAltForm();
            
        else
            
            return this.getOriginalForm();
        
    }

    
    

    
    
    //need to override the refreshrameMatrix method to allow for moevements
    //like hunching.
    
  
    @Override
    public void movePart(){
        
        //The Dinosaur shall only move if it's feet are directly above something and
        //that its velocity in the Y direction is 0. Note the former condition must be checked
        //against because we can run into situations where dinosaur is at the top most
        //of its parabolic trajectory (so is still jumping) and yet has its velocityY set to
        //0, in the case, we don't want the dinosaur to movePart().
        if (shouldMovePart){
            super.movePart();
        } 
   
        
    }

    public boolean isShouldMovePart() {
        return shouldMovePart;
    }

    public void setShouldMovePart(boolean shouldMovePart) {
        this.shouldMovePart = shouldMovePart;
    }
    
   
   
   
    //The hunch method. Hunches the dinosaur by adjusting some
    //numbers in its coordinate matrix.
    //So next time Game.printScr() is called, the dinosaur figure will look
    //hunched
    public void hunch(boolean toHunch){
       //if un-hunching is desired and the dinosaur is not previously hucnched, then
       //get the dinosaur to hunch. 
        if (!toHunch && previouslyHunched){
       
                //modifying the dinosaur's originalForm and altForm to
                //update its appearance.
                //Note the use of super. Because in this class, the getAltForm 
                //method is overriden (due to the need from logic), to actually get the
                //altForm, we shall call the super.getAltForm method to obtain the altForm.
                
                super.getAltForm()[0][neckIndexX1]      =     
                super.getOriginalForm()[0][neckIndexX1] = 0;
                super.getAltForm()[0][neckIndexX2]      =     -1;  
                super.getOriginalForm()[0][neckIndexX2] =     -1; 
                
                previouslyHunched = false;
                
        //if hunching is desired, and the dinosaur is previously hunched, then get the
        //dinosaur to unhunch.
        } else if (toHunch && !previouslyHunched){
           
                super.getAltForm()[0][neckIndexX1]      =        
                super.getAltForm()[0][neckIndexX2]      =  
                super.getOriginalForm()[0][neckIndexX1] =        
                super.getOriginalForm()[0][neckIndexX2] =  1;
                
                previouslyHunched = true;
              
        }

       
    }
    
   
    
    public void sprintForward(){
        setVelocityX(this.getVelocityX() + 1);
    }
    
    public void sprintBackward(){
        setVelocityX(this.getVelocityX() - 1);
    }
    
    public void drawSelf(Graphics g){
        if (isJumping){
            
        }
    }


        

   
}
