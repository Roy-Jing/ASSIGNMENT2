//package com.mycompany.pdcassignment2;
//
//import java.awt.Graphics;
//
//
///**
// *Class Bird is a concrete decorator for a MoveableObject.
// * It's altForm is different from originalForm
// * @author Roy
// */
//public class Bird extends Animal{
//    
//    
//    Bird(MoveableFigure f){
//        super(f);
//        
//       
//       setCoordX(Game.getFrameWidth() + 5);
//       setCoordY(Game.getFrameHeight() - 8);
//       setNumPixels(getOriginalForm()[0].length);
//       this.setVelocityX(-2);
//     
//    }
//    
//    //for the meaning of the method please see the enum class MoveableTypes
//    @Override
//     public Bird getNewFigure(){
//        return new Bird(new MoveableObject());
//    } 
//   
//    public void drawSelf(Graphics g){
//        
//    }
//    public void run(){
//        while (super.getRightMostCoordX() >= 0){
//            
//        }
//    }
// 
//    
//    
//    
//}