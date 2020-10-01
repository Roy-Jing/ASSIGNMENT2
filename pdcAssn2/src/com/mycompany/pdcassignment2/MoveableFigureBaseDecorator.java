package com.mycompany.pdcassignment2;



/*
 * The MoveableFigureBaseDecorator serves as a abstract class that is to 
 * extended by concrete decorators that add extra functionalities to a MoveableObject.
 * 
 */

/**
 *
 * @author Roy
 */
public abstract class MoveableFigureBaseDecorator implements MoveableFigure{
    
    protected MoveableFigure figure;

    @Override
    public char getSymbol() {
        return figure.getSymbol();
    }

    @Override
    public void setSymbol(char symbol) {
        figure.setSymbol(symbol);
    }
    
    MoveableFigureBaseDecorator(MoveableFigure f){
        figure = f;
    }
    
    @Override
    public MoveableFigure getNewFigure(){
        throw new UnsupportedOperationException();
    }
   
    @Override
    public boolean stillWithinFrame(){
        return figure.stillWithinFrame();
    }
    
    @Override
    public int getCoordY() {
        return figure.getCoordY();
    }

    @Override
    public void setCoordY(int coordY) {
        figure.setCoordY(coordY);
    }
   
    @Override
    public int getCoordX(){
        return figure.getCoordX();
    }
    @Override
    public void setCoordX(int coordX){
        figure.setCoordX(coordX);
    }
    
    @Override
    public int getVelocityY() {
        return figure.getVelocityY();
    }

    @Override
    public void setVelocityY(int v) {
        figure.setVelocityY(v);
    }

    
    @Override
    public int getVelocityX() {
        return figure.getVelocityX();
    }

    @Override
    public void setVelocityX(int velocityX) {
        figure.setVelocityX(velocityX);
    }


    @Override
    public int getNumPixels(){
        return figure.getNumPixels();
    }
  
    public void setNumPixels(int numPixels){
        figure.setNumPixels(numPixels);
    }

    @Override
    public int getRightMostCoordX() {
        return figure.getRightMostCoordX();
    }
    
    @Override
   public int[][] getAltForm() {
        return figure.getAltForm();
    }
    
   
    @Override
    public void setAltForm(int[][] altForm) {
        figure.setAltForm(altForm);
    }
    
    @Override
    public int[][] getOriginalForm() {
        return figure.getOriginalForm();
    }
    
   
    @Override
    public void setOriginalForm(int[][] form) {
       figure.setOriginalForm(form);
    }
        

   
    
    
}

