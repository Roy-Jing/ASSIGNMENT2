package com.mycompany.pdcassignment2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;



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
    
    public void setCollisionHandler(CollisionHandler handler);
    
    public void doRun();
    
    
}


interface CollideableFigure extends Figure{
    
    public void setRightMostCoordX(int c);
    
   
        
}


interface MoveableFigure extends Figure{
    
    
    public boolean stillWithinFrame();
    
    public int getNumPixels();

    public void setNumPixels(int numPixels);
    
    public int getVelocityX();
    
    public void setVelocityX(int v);
    
    public int getVelocityY();
    
    public void setActive(boolean active);
    
    public void setVelocityY(int v);
    
    public MoveableFigure getNewFigure();
    
    public char getSymbol();
    
    public void setSymbol(char c);
    
    public int[][] getAltForm();
    
    public void setAltForm(int[][] altForm);
    
    public int[][] getOriginalForm();
    
    public void setOriginalForm(int[][] form);
 
    public void drawSelf(Graphics g);
    
    public Image getImg();
    
    public void setImg(Image img);
    
    
    public boolean isActive();
    
    public void addModel(GameModel m);
    
    public void setMoved(boolean flag);
    
    public Color getColour();
    
    public String getid();

    public void setColour(Color clr);

    
}






