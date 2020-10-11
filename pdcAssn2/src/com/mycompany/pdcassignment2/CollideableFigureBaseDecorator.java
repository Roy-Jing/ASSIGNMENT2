package com.mycompany.pdcassignment2;

import java.awt.Graphics;

/**
 *  Base decorator for CollideableObject
 *  To be extended by concrete decorator classes:
 * Cloud and Floor. 
 * @author Roy
 */
public abstract class CollideableFigureBaseDecorator implements CollideableFigure {
    private CollideableFigure figure;
    private Dinosaur d;
    
    
    CollideableFigureBaseDecorator(CollideableFigure f){
        figure = f;
        this.setCollisionHandler(new CollideableCollisionHandler(figure));
    }

    public void setCollisionHandler(CollisionHandler handler){
        figure.setCollisionHandler(handler);
    }
    
    
    @Override
    public void setRightMostCoordX(int rightMostCoordX) {
        figure.setRightMostCoordX(rightMostCoordX);
    }
   
    @Override
    public void setCoordY(int c) {
        figure.setCoordY(c);
    }
    @Override
    public int getCoordY() {
        return figure.getCoordY();
    }

    @Override
    public int getCoordX(){
        return figure.getCoordX();
    }

    @Override
    public int getRightMostCoordX() {
        return figure.getRightMostCoordX();
    }

    @Override
    public void setCoordX(int c) {
        figure.setCoordX(c);
    }

}
