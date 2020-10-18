package com.mycompany.pdcassignment2;

import java.awt.Graphics;



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
    
    public void setCollisionHandler(CollisionHandler handler){
        figure.setCollisionHandler(handler);
    }
    
    public boolean isActive(){
        return figure.isActive();
    }
  
    @Override
    public void setSymbol(char symbol) {
        figure.setSymbol(symbol);
    }
    
    public void setMoved(boolean flag){
        figure.setMoved(flag);
    }
    MoveableFigureBaseDecorator(MoveableFigure f){
        figure = f;
        this.setCollisionHandler(new MoveableCollisionHandler(figure));
    }
    
    public void doRun(){
        
        figure.doRun();
    }
    
     public void setActive(boolean flag){
         figure.setActive(flag);
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
    
   
    public void addModel(GameModel m){
        figure.addModel(m);
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
        

    public void drawSelf(Graphics g){
        figure.drawSelf(g);
    }
    
   
    
}

