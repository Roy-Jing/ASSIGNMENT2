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
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Roy
 */
public class GameModel extends Observable implements Runnable{

    static char[][] getVirtualGUI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private int currentScore = 0;
    private static volatile int numCoinsCollected = 0;
    private String difficulty;
    
    public static void addCoin(){
        numCoinsCollected++;
    }
    public static int getNumCoinsCollected() {
        return numCoinsCollected;
    }

    public static void setNumCoinsCollected(int numCoinsCollected) {
        GameModel.numCoinsCollected = numCoinsCollected;
    }
    private Preferences preferences;
    private static boolean reset = false;
    private static volatile boolean drawing = false;
    private static char[][] virtualGUI;
    
    public static boolean isDrawing() {
        return drawing;
    }

    public static void setDrawing(boolean flag) {
        drawing = flag;
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
    private static volatile boolean gameOver = false;
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
        new Thread(this).start();
        //this.setChanged();
        //notify gamegui
        //this.notifyObservers(preferences);
        
    }
    
    private LinkedList<MoveableFigure> figs = new LinkedList<MoveableFigure>();
    
    public void removeInactiveFigures(){
        
        synchronized (figs){
            Iterator<MoveableFigure> it = figs.iterator();

                //it.next();
                while (it.hasNext()){
                    MoveableFigure f = it.next();
                    if (!f.isActive()){
                        out.println("removed");
                        //this.numCurrentFigs--;
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
        
        out.println(list);
        list.remove();
        out.println(c);
        
        
    }
 
    private volatile int drawCountLim;
    
    public synchronized void printInfo(){
        out.println("dCtLim" + drawCountLim);
            out.println("dCt" + drawCount);
            out.println("is drawing: " + drawing);
    }
    
    
    public void refreshFrame(){
        this.printInfo();
        
        if (!drawing){
            
            drawing = true;
            
            out.println("dCtLim" + drawCountLim);
            out.println("dCt" + drawCount);
            out.println("is drawing: " + drawing);
            
            //drawCount = 0;
           //drawCountLim = this.getNumCurrentFig();
            
            
            this.setChanged();
            //figs.clone()
            this.notifyObservers(figs);
            
            //drawing = false;
             for (MoveableFigure fig : figs){
                fig.setMoved(false);
                //affecting those that just had their moved flag set to true
            }
            
            //setting drawing = false here is too late as !drawing means the if condition 
            //won't be true by the time.
           
           
        }
       
    }
    
   
 
    public synchronized void addDrawCount(){
        drawCount++;
    }
    
    private final int MAX_NUM_FIGS = 5;
    
    public synchronized int getNumCurrentFig(){
        return figs.size();
    }
    
    private Dinosaur d;
    
    public Dinosaur getDinosaur(){
        return d;
        
    }
    public void run(){
        int frameCount = 0;
        setDim(preferences.getScreenDim());
        //drawCountLim = 1;
        
        Dinosaur dino = d = new Dinosaur(new MoveableObject("Dinosaur"));
        dino.addModel(this);
        
        figs.add(dino);
        setChanged();
        notifyObservers(preferences);
        CollisionHandler.addDino(dino);
        
        
        while(!gameOver){
            
                this.removeInactiveFigures();
                if (getNumCurrentFig() < MAX_NUM_FIGS ){
                    int numValues =  MoveableTypes.values().length;

                    MoveableFigure fig = MoveableTypes.values()[rand.nextInt(numValues)].getNewFigure();

                    fig.addModel(this);
                    //adding new figure while painting
                    figs.add(fig);
                    

                    new Thread(fig).start();
                }
                
                
            
            
//            out.println("drawCount = " + drawCount);
//            out.println("numCurrent = " + this.numCurrentFigs);
            
            

            //cannot say else draw = true
            try {
                    Thread.sleep(2000);
                    
                } catch (InterruptedException ex) {
                }
                
            
        }
    }
    
    public void shutdown(){
        Data info = new Data();
        info.currentScore = currentScore;
        info.difficulty = difficulty;
        info.numCoinsCollected = numCoinsCollected;
        
        dbM.closeConnections();
    }

    void setCustomisedPreferences() {
    }

    public static void setGameOver(boolean b) {
        gameOver = b;
    }
    
    
}
