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
    
    public void movePart(){
        if (this.shouldMovePart){
            super.movePart();
        } 
    }
    public void moveForward(){
        
    }
    
    public void jump(){
        
        this.setVelocityY(this.getVelocityY() + 1);
    }
    
    
    public void updateVirtualGUI(){
        int velocityX = this.getVelocityX();
        int velocityY = this.getVelocityY();
        int startPos = 0, shiftDir = 0, endPos = 0;
        
       if (velocityX > 0 || velocityY > 0){
           startPos = getNumPixels() - 1;
           shiftDir = -1;
           endPos = -1;
       } else if (velocityX <= 0 || velocityY < 0){
           startPos = 0;
           shiftDir = 1;
           endPos = getNumPixels();
           
       } 
        if (this.getVelocityX() != 0 || this.getVelocityY() !=0){
            char[][] virtualGUI = GameModel.getVirtualGUI();
            
            for (int i = startPos; i != endPos; i+= shiftDir){
                int coordY = this.getOriginalForm()[0][i];
                int coordX = this.getOriginalForm()[1][i];
                
                virtualGUI[coordY][coordX] =  ' ';
                virtualGUI[coordY + velocityY][coordX + velocityX] = '!';
               
            }
            
        }
        
    }
    public void run(){
       doRun();
        
    }
    
    public void hunch(boolean toHunch){
        int[][] original = this.getOriginalForm();
        int[][] alt = this.getAltForm();
        
        if (toHunch && !previouslyHunched){
            super.getAltForm()[0][neckIndexX1]      =        
                super.getAltForm()[0][neckIndexX2]      =  
                super.getOriginalForm()[0][neckIndexX1] =        
                super.getOriginalForm()[0][neckIndexX2] =  1;
                
                previouslyHunched = true;
        } else if (!toHunch && previouslyHunched){
            
                super.getAltForm()[0][neckIndexX1]      =     
                super.getOriginalForm()[0][neckIndexX1] = 0;
                super.getAltForm()[0][neckIndexX2]      =     -1;  
                super.getOriginalForm()[0][neckIndexX2] =     -1; 
                
                previouslyHunched = false;
        }
    }
    
    public void doRun(){
        super.doRun();
        if (isJumping){
            this.setVelocityY(getVelocityY() + 1);
            
        }
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

    
    public void sprintForward(){
        setVelocityX(this.getVelocityX() + 1);
    }
    
    public void sprintBackward(){
        setVelocityX(this.getVelocityX() - 1);
    }

    void setShouldMovePart(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void isJumping(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   


        

   
}
