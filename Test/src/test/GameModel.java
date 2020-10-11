/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

import java.awt.Dimension;
import static java.lang.System.out;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
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
    private static int numCoinsCollected = 0;
    private Preferences preferences;
    private static boolean reset = false;
    private static volatile boolean drawing = false;
    
    public static boolean isDrawing() {
        return drawing;
    }

    public static void setDrawing(boolean drawing) {
        GameModel.drawing = drawing;
    }
    
    public Preferences getPreferences() {
        return preferences;
    }

    public static void addCoins(){
        numCoinsCollected += 1;
    }
    public static boolean reset(){
        return reset;
        
    }
    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }
    private final static int pixelSize = 5;
    private static int frameWidth;
    private static Random rand = new Random();
    private volatile int numCurrentFigs;

    public int getNumCurrentFigs() {
        return numCurrentFigs;
    }

    
    public synchronized void setNumCurrentFigs(int numCurrentFigs) {
        this.numCurrentFigs = numCurrentFigs;
    }
    
  
    private volatile static int drawCount = 0;
    private volatile boolean gameOver = false;
    private int spawnPeriod = 5000;
    
    public static int getDrawCount() {
        return drawCount;
    }
    
    public static void setDrawCount(int ct) {
        drawCount = ct;
    }
    public static Random getRand() {
        return rand;
    }
    
    
    
    public synchronized void updateCount(){
        out.println("update called");
        
        drawCount++;
        
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
    
 
    
    public void init(){
        
        dbM = new DatabaseModel();
        Boolean prevExist;
        if ((preferences = dbM.getPreference()) != null){
            
            prevExist = true;
        } else
            prevExist = false;
        //preferences can be null
        this.setChanged();
        this.notifyObservers(prevExist);
           
       
        //this.setChanged();
        //this.notifyObservers(null);

    }
    
   
    
    public void useDefaultSetting(boolean useDefault){
        
        if (useDefault){
            
            preferences = new Preferences(true);
            
        } else if (preferences == null){
            //has no previous 
            //does not use default
            out.println("shouldn't be printed");
            preferences = new Preferences(true);
        }
        
        //this.refreshFrame();
        update();
        
        this.setChanged();
        //notify gamegui
        this.notifyObservers(preferences);
        
    }
    
    private LinkedList<MoveableFigure> figs = new LinkedList<MoveableFigure>();
    
    public void removeInactiveFigures(){
        synchronized (figs){
            Iterator<MoveableFigure> it = figs.iterator();

                it.next();
                while (it.hasNext()){
                    MoveableFigure f = it.next();
                    if (!f.isActive()){
                        out.println("removed");
                        this.numCurrentFigs--;
                        it.remove();
                    }
                }
            
        }
        
    }
    
    public void setDim(Dimension d){
        setFrameHeight(d.height);
        setFrameWidth(d.width);
        
    }
    
    
    public static void main(String args[]){
        LinkedList<MoveableFigure> list = new LinkedList();
        
        Collection<MoveableFigure> c = Collections.synchronizedList(list);
        
        c.add(new Dinosaur(new MoveableObject()));
        out.println(list);
        list.remove();
        out.println(c);
        
        
    }
    
    
    public void refreshFrame(){
        out.println(drawing);
        if (!isDrawing()){
            this.setChanged();
            this.notifyObservers(figs);
        }
    }
    public void update(){
        int frameCount = 0;
        setDim(preferences.getScreenDim());
        
        Dinosaur dino = new Dinosaur(new MoveableObject());
        dino.addModel(this);
        
        figs.add(dino);
        setChanged();
        
        notifyObservers(preferences);
        
        
        while(!gameOver){
            this.removeInactiveFigures();
           
                int numValues =  MoveableTypes.values().length;
                
                MoveableFigure fig = MoveableTypes.values()[rand.nextInt(numValues)].getNewFigure();
                
                fig.addModel(this);
                
                figs.add(fig);
                numCurrentFigs++;
                
                new Thread(fig).start();
                
                
            
            
//            out.println("drawCount = " + drawCount);
//            out.println("numCurrent = " + this.numCurrentFigs);
            
//            if (drawCount == numCurrentFigs){
//                
//                drawCount = 0;
//                figs.forEach(f -> {
//                    f.setMoved(false);
//                });
//                
//                setChanged();
//                this.notifyObservers(figs);
//            
//            } 

            //cannot say else draw = true
            try {
                    Thread.sleep(2000);
                    
                } catch (InterruptedException ex) {
                }
                
            
        }
    }
    
    public void shutdown(){
        
    }

    void setCustomisedPreferences() {
    }
    
    
}
