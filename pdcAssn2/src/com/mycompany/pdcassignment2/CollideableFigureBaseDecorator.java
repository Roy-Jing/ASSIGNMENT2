package com.mycompany.pdcassignment2;

/**
 *  Base decorator for CollideableObject
 *  To be extended by concrete decorator classes:
 * Cloud and Floor. 
 * @author Roy
 */
public abstract class CollideableFigureBaseDecorator implements CollideableFigure {
    private CollideableFigure figure;
    
    CollideableFigureBaseDecorator(CollideableFigure f){
        figure = f;
    }

    @Override
    public void setRightMostCoordX(int rightMostCoordX) {
        figure.setRightMostCoordX(rightMostCoordX);
    }
    
    @Override
    public void adjustVelocity(Dinosaur d, int adjustedVelocity){
        //out.println("velocity adjusting\n\n\n\n");
        
        figure.adjustVelocity(d, adjustedVelocity);
      
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

    @Override
    public int checkIfWillCollideWith(Dinosaur d){
        return figure.checkIfWillCollideWith(d);
    }
    
    @Override
     public boolean willIntersect( int feetLocationY, int dVelocityY){
         return figure.willIntersect( feetLocationY, dVelocityY);
     }
   
}
