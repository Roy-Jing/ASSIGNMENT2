package com.mycompany.pdcassignment2;

import java.awt.Graphics;
import static java.lang.System.out;

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
    private volatile boolean isJumping = false;
    
  
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
        
    }
    
    public void movePart(){
        if (this.shouldMovePart){
            super.movePart();
        } 
    }
    public void moveForward(){
        
    }
    
    public void jump(){
        out.println("jumping");
        
        this.setVelocityY(this.getVelocityY() + 1);
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
    
    @Override
    public void doRun(){
        out.println("do Run dino");
        if (isJumping){
            
            this.jump();
            if (this.getFeetLocationY() >= GameModel.getFrameHeight()){
                out.println("exceeded frame height !");
                
            }
            //out.println("velocityY" + this.getVelocityY());
        }
        
        super.doRun();
        out.println("coordY dino: " + this.getCoordY());
        
        
        
    }
    
    
    
    /**
     * The stillWithinFrame method will adjust a MoveableFigure's
     * next horizontal velocity. The velocityX value will be used
     * by method refreshFrameMatrix to shift a MoveableFigure across
     * the screen
     * @return 
     */
    
   
   
    Dinosaur(MoveableFigure fig){
        
        
        super(fig);
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
        
        if (isJumping && getFeetLocationY() + this.getVelocityY() >= GameModel.getFrameHeight()){
            this.setVelocityY(GameModel.getFrameHeight() - this.getFeetLocationY() - 1);
            out.println("dino not jumping");
            
            this.isJumping(false);
        }
        
        return true;
        
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
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void isJumping(boolean b) {
        isJumping = b;
        
    }



}


