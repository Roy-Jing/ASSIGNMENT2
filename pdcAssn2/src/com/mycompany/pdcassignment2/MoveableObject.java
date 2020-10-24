package com.mycompany.pdcassignment2;




import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import static java.lang.System.out;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Roy
 */

/**
 * 
 * The class MoveableObject provides implementations for MoveableFigure.
 * Any concrete decorators that implement MoveableFigure should compose of 
 * a MoveableObject, conforming to the decorator design pattern.
 * @author Roy
 */
public class MoveableObject implements MoveableFigure{
    
    private volatile boolean active = true;
    private boolean changeToAltForm = true;
    private int velocityX;
    private int velocityY = 0;
    private Color selfColour;

    public Color getColour() {
        return selfColour;
    }

    public void setSelfColour(Color selfColour) {
        this.selfColour = selfColour;
    }
     
    protected GameModel model;
    private int numPixels;
    private volatile int coordX;
    private volatile int coordY;
 
    MoveableObject(String id){
        this.setName(id);
    }
    //Definition of a symbol:
    /*A group of them together represents the on-screen appearance of a 
    particular MoveableFigure. How the group is arranged is defined by the figure's altForm
    and originalForm. */
    
    

    /*
    Alternative form of a MoveableObject on screen.
    */
    private volatile int[][] altForm;
    
    /*
    Original form of a MoveableObject on screen.
    */
    private volatile int[][] originalForm;
    @Override
    public int[][] getOriginalForm() {
        return originalForm;
    }

    @Override
    public void setOriginalForm(int[][] originalForm) {
        this.originalForm = originalForm;
    }
    
    
    @Override
    public int getCoordY() {
        return coordY;
    }

    @Override
    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }
   
   
    @Override
    public int getNumPixels(){
        return numPixels;
    }
    
   
    @Override
    public void setNumPixels(int n){
        this.numPixels = n;
    }
   
 
    @Override
    public int getCoordX(){ 
        return coordX;
    }
    
    @Override
    public void setCoordX(int x){
        coordX = x;
    }
    
    @Override
    public void setVelocityY(int v){
        velocityY = v;
    }
    
    @Override
    public int getVelocityY(){
        return velocityY;
    }
    
    //Check that tis object is still visible within the frame.
    @Override
    public boolean stillWithinFrame(){
        return this.getRightMostCoordX() >= 0;
    }
   

    
    @Override
    public int getVelocityX(){
        return velocityX;
    }
    @Override
    public void setVelocityX(int v){
        this.velocityX = v;
    }
    private int pixelSize = 5;
    private Image img;

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }
    
    
    public void move(){
    
        coordX += getVelocityX();
        coordY += this.getVelocityY();
        
        //moved = true;
    }
    public void addModel(GameModel mod){
        model = mod;
    }
    @Override
    public int getRightMostCoordX() {
        //the coord furthest to the right in the matrix will be the right most coord.
        return this.getOriginalForm()[1][this.getNumPixels() - 1] + getCoordX();
    }
  
    //This method should only be overriden. What it does is that it instantiates
    //a class that overrides this method. For example,
    //the concrete decorator class Cloud overrides this method, and calling it
    //will instantiate a new Cloud. (This method is used for the Game.generateMoveableFigs
    //method).
    @Override
    public MoveableFigure getNewFigure() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    
    @Override
    public int[][] getAltForm() {
        return altForm;
    }

    @Override
    public void setAltForm(int[][] altForm) {
        this.altForm = altForm;
    }
    
    public int[][] getCoordMatrix(){
        return null;
    }
    
    private String id;
    
    public String getid(){
        return id;
    }
    
    
    public void setName(String name){
        id = name;
        Rectangle rect = new Rectangle(100, 100);
       
    }
    
    
    
   
    @Override
    public void drawSelf(Graphics g) {
        
        g.drawImage(img, coordX, coordY, null);
        

    }
    public void setColour(Color clr){
        this.selfColour = clr;
    }
   
    public boolean isActive(){
        
        return active;
    }
    
    private CollisionHandler handler;
    
    private volatile boolean moved = false;
    

    
   
    private boolean canMovebody = false;

    public void setCanMovebody(boolean canMovebody) {
        this.canMovebody = canMovebody;
    }
    
    
    public void setCollisionHandler(CollisionHandler handler){
        this.handler = handler;
        
    }
    
    
    public void doRun(){
        
       if (!model.isInterrupted()){
           
            if (handler.checkStillWithinFrame()){
                if (handler.checkForCollision()){
                    handler.handleCollision();
                    
                } 
                
                
                
                move();
                
            } else{
                
                
                active = false;
            }
            
           
        } else{

            //model.setNumCurrentFigs(model.getNumCurrentFigs() - 1);
            active = false;

        }
       
    }

    

       
       
    
 
   
    @Override
    public char getSymbol() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSymbol(char c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setActive(boolean flag) {
        active = flag;
    }

    @Override
    public void setMoved(boolean flag) {
        moved = flag;
    }
    
    
    

   
}

//class CollisionHandler








