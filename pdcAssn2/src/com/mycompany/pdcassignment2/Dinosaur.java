package com.mycompany.pdcassignment2;

import java.awt.Graphics;
import java.awt.Image;
import static java.lang.System.out;
import javax.swing.ImageIcon;

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
    //private char[][] virtualGUI;
    
    
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
    private Image img = new ImageIcon("src/dinosaur.png").getImage();
    
    
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
        
        if (!this.isJumping){
            this.hunch(hunched);
            
        }
        if (this.shouldMovePart){
            
            super.movePart();
            
            
        } 
    }
    public void moveForward(){
        
    }
//    
    public char[][] getVirtualGUI(){
        return virtualGUI;
    }
    
    
    
    public void jump(){
        
        this.setVelocityY(this.getVelocityY() + 1 * GameModel.getPixelSize());
    }
   
    
    
    public void hunch(boolean toHunch){
       
        
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
//  
    char[][] virtualGUI;
    
            
    public void initaliseVirtualGUI(){
        virtualGUI = new char[GameModel.getFrameHeight()][GameModel.getFrameWidth()];
        
        int[][] coordMat = getOriginalForm();
        int dinoCoordX = getCoordX();
        int dinoCoordY = getCoordY();
        for (int i = 0; i < getNumPixels(); i++){
            int cX = coordMat[1][i] ;
            int cY = coordMat[0][i];
            
            virtualGUI[cY + dinoCoordY ][cX + dinoCoordX] = '!';
            
        }
    }
    public void updateVirtualGUI(){
        
        int velocityX =  getVelocityX();
        int velocityY = getVelocityY();
        int coordY = getCoordY();
        int coordX = getCoordX();
        
        int startPos = 0, shiftDir = 0, endPos = 0;
        if (velocityX != 0 || velocityY != 0){
       if (velocityX > 0 || velocityY > 0){
           startPos = getNumPixels() - 1;
           shiftDir = -1;
           endPos = -1;
       } else if (velocityX <= 0 || velocityY < 0){
           startPos = 0;
           shiftDir = 1;
           endPos = getNumPixels();
           
       } 
     
            int form[][] = getOriginalForm();
            
            for (int i = startPos; i != endPos; i+= shiftDir){
                int cY = form[0][i] + coordY;
                int cX = form[1][i] + coordX;
                if (GameModel.pixelInFrame(cX, cY))
                    virtualGUI[cY][cX] =  ' ';
                
                if (GameModel.pixelInFrame(cX + velocityX, cY + velocityY))

                    virtualGUI[cY + velocityY][cX + velocityX] = '!';
               
            }
            
        
        }
    }
    
    
    @Override
    public void doRun(){
        if (isJumping){
            
            this.jump();
            if (this.getFeetLocationY() >= GameModel.getFrameHeight()){
                
            }
            // ("velocityY" + this.getVelocityY());
        }
        
//        synchronized(guiobj){
//            
//            guiobj.updateVirtualGUI(this.getVelocityX(), this.getVelocityX(), this.getCoordX(), this.getCoordY(), this);
//            
//        }

        
        super.doRun();
        this.updateVirtualGUI();

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
        setCoordY(GameModel.getFrameHeight() - 4); //coord Y is body line
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
        initaliseVirtualGUI();

     
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
        int frameHeight = GameModel.getFrameHeight();
        int frameWidth = GameModel.getFrameWidth();
        //check left edge of frame.
        if (this.getCoordX() + this.getVelocityX() < 0 ){
             this.setVelocityX(-this.getCoordX());
             this.setAccelerated(false);
        }
        
        //check r edge of frame.
        else if (getRightMostCoordX() + this.getVelocityX() >= frameWidth){
            this.setVelocityX(frameWidth - this.getRightMostCoordX() - 1);
            this.setAccelerated(false);

        }
        
        if (getFeetLocationY() + this.getVelocityY() >= frameHeight){
            this.setVelocityY(frameHeight - this.getFeetLocationY() - 1);
            
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


