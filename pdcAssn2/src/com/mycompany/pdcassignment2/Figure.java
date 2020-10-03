package com.mycompany.pdcassignment2;

import java.awt.Graphics;



/**
 *Interfaces to be implemented by concrete decorators such as Dinosaur, Plant etc.
 * For the meanings of methods of CollideableFigure, examine the class CollideableObject.
 * For the meanings of methods of MoveableFigure, examine the class MoveableObject.
 * @author Roy
 */
public interface Figure {
    
 
    public int getRightMostCoordX();
  
    public int getCoordX();
    
    public void setCoordX(int c);
    
    public int getCoordY();
    
    public void setCoordY(int c);
  
}


interface CollideableFigure extends Figure{
    
    public void setRightMostCoordX(int c);
    
    public int checkIfWillCollideWith(Dinosaur d);
    
    public boolean willIntersect(int feetLocationY, int dVelocityY);
     
    public void adjustVelocity(Dinosaur d, int vY);
   
}


interface MoveableFigure extends Figure, Runnable{
 
    public int getNumPixels();

    public void setNumPixels(int numPixels);
    
    public int getVelocityX();
    
    public void setVelocityX(int v);
    
    public int getVelocityY();
    
    public void setVelocityY(int v);
    
    public boolean stillWithinFrame();

    public MoveableFigure getNewFigure();
    
    public char getSymbol();
    
    public void setSymbol(char c);
    
    public int[][] getAltForm();
    
    public void setAltForm(int[][] altForm);
    
    public int[][] getOriginalForm();
    
    public void setOriginalForm(int[][] form);
 
    public void drawSelf(Graphics g);
}






