//package com.mycompany.pdcassignment2;
//
//
//
//
//import static java.lang.System.out;
//
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
///**
// *
// * @author Roy
// */
//
///**
// * 
// * The class MoveableObject provides implementations for MoveableFigure.
// * Any concrete decorators that implement MoveableFigure should compose of 
// * a MoveableObject, conforming to the decorator design pattern.
// * @author Roy
// */
//public class MoveableObject implements MoveableFigure{
//    
//    private boolean changeToAltForm = true;
//    private int velocityX;
//    private int velocityY = 0;
//    
//    private int numPixels;
//    private int coordX;
//    private int coordY;
//    
//    //Definition of a symbol:
//    /*A group of them together represents the on-screen appearance of a 
//    particular MoveableFigure. How the group is arranged is defined by the figure's altForm
//    and originalForm. */
//    
//    private char symbol = '~';
//    
//    /*
//    Alternative form of a MoveableObject on screen.
//    */
//    private int[][] altForm;
//    
//    /*
//    Original form of a MoveableObject on screen.
//    */
//    private int[][] originalForm;
//    @Override
//    public int[][] getOriginalForm() {
//        return originalForm;
//    }
//
//    @Override
//    public void setOriginalForm(int[][] originalForm) {
//        this.originalForm = originalForm;
//    }
//    
//    
//    @Override
//    public char getSymbol() {
//        return symbol;
//    }
//
//    @Override
//    public void setSymbol(char symbol) {
//        this.symbol = symbol;
//    }
//    
//    @Override
//    public int getCoordY() {
//        return coordY;
//    }
//
//    @Override
//    public void setCoordY(int coordY) {
//        this.coordY = coordY;
//    }
//   
//   
//    @Override
//    public int getNumPixels(){
//        return numPixels;
//    }
//    
//   
//    @Override
//    public void setNumPixels(int n){
//        this.numPixels = n;
//    }
//   
// 
//    @Override
//    public int getCoordX(){ 
//        return coordX;
//    }
//    
//    @Override
//    public void setCoordX(int x){
//        coordX = x;
//    }
//    
//    @Override
//    public void setVelocityY(int v){
//        velocityY = v;
//    }
//    
//    @Override
//    public int getVelocityY(){
//        return velocityY;
//    }
//    
//    //Check that tis object is still visible within the frame.
//    @Override
//    public boolean stillWithinFrame(){
//        return this.getRightMostCoordX() >= 0;
//    }
//   
//
//    
//    @Override
//    public int getVelocityX(){
//        return velocityX;
//    }
//    @Override
//    public void setVelocityX(int v){
//        this.velocityX = v;
//    }
//
//   
//    @Override
//    public int getRightMostCoordX() {
//        //the coord furthest to the right in the matrix will be the right most coord.
//        return this.getOriginalForm()[1][this.getNumPixels() - 1] + getCoordX();
//    }
//    
//    //This method should only be overriden. What it does is that it instantiates
//    //a class that overrides this method. For example,
//    //the concrete decorator class Cloud overrides this method, and calling it
//    //will instantiate a new Cloud. (This method is used for the Game.generateMoveableFigs
//    //method).
//    @Override
//    public MoveableFigure getNewFigure() {
//        throw new UnsupportedOperationException("Not supported yet."); 
//    }
//    
//    
//    @Override
//    public int[][] getAltForm() {
//        return altForm;
//    }
//
//    @Override
//    public void setAltForm(int[][] altForm) {
//        this.altForm = altForm;
//    }
//
//   
//}
//
//
//
//
//
//
//
//
//
