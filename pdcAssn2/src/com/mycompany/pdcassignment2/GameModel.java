/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Roy
 */
public class GameModel extends Observable{
    private int currentScore = 0;
    private int numCoinsCollected = 0;
    private Preferences preferences;

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }
    private final static int pixelSize = 5;
    private static int frameWidth;
    private static Random rand = new Random();
    private int numCurrentFigs;
    private static int drawCount = 0;
    private volatile boolean gameOver = false;
    private int spawnPeriod = 2000;
    
    public static int getDrawCount() {
        return drawCount;
    }
    public static Random getRand() {
        return rand;
    }
    
    
    
    public void update(){
        drawCount++;
        if (drawCount == numCurrentFigs){
            
            drawCount = 0;
            setChanged();
            this.notifyObservers();
            
        }
    }
    
    public static int getFrameWidth() {
        return frameWidth;
    }
    
    
    public static void setFrameWidth(int frameWidth) {
        GameModel.frameWidth = frameWidth;
    }

    public static int getFrameHeight() {
        return frameHeight;
    }

    public static void setFrameHeight(int frameHeight) {
        GameModel.frameHeight = frameHeight;
    }
    private static int frameHeight;
    
    public static int getPixelSize() {
        return pixelSize;
    }
    private DatabaseModel dbM;
    
    public void addDatabse(DatabaseModel m){
        dbM = m;
    }
    
    
    //Test that some "pixel" of a figure is within the game frame.
    public static boolean pixelInFrame(int coordX, int coordY){
        if (coordX > -1 && coordY > -1){
            if (coordX < getFrameWidth() && coordY < getFrameHeight()) {
                return true;
            }
        }
        
        return false;
    }
    
    public static int[][] copy(int[][] from){
        int len = from[0].length;
        
        int[][] to = new int[2][len];
        System.arraycopy(from[0], 0, to[0], 0, len);
        System.arraycopy(from[1], 0, to[1], 0, len);
        return to;
    }
    
    
    public boolean init(){
        
        dbM = new DatabaseModel();
        
        if ((preferences = dbM.getPreference()) == null){
            return false;
        } else
            return true;
        
        
    }
    
    
    public void useDefaultSetting(boolean useDefault){
        if (useDefault){
            preferences = new Preferences(true);
            
        }
        
        this.setChanged();
        this.notifyObservers(preferences);
    }
    
    private LinkedList<MoveableFigure> figs = new LinkedList<MoveableFigure>();
    
    public void refreshFrame(){
        int frameCount = 0;
        figs.add(new Dinosaur(new MoveableObject()));
        
        while(!gameOver){
            if (frameCount++ % spawnPeriod == 0 ){
                int numValues =  MoveableTypes.values().length;
                
                MoveableFigure fig = MoveableTypes.values()[rand.nextInt(numValues)].getNewFigure();
                new Thread(fig).start();
                
                try {
                    Thread.sleep(2);
                } catch (InterruptedException ex) {
                }
                
            }
        }
    }
    
    public void shutdown(){
        
    }
    
    
}
