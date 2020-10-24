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
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Roy
 */
public class GameModel extends Observable implements Runnable{

    
    private int currentScore = 0;
    private static volatile int numCoinsCollected = 0;
    private String difficulty;
    
    
    //remoember to adjust game based on difficulty
    private volatile boolean interrupted  = false;

    public boolean isInterrupted() {
        return interrupted;
    }
    
 
    
    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }
    
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
    
    public void init(){
        
        new Thread(this).start();
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
    
    public static boolean pixelWithFrame(int x, int y){
        if (x > -1 && x < frameWidth){
            if (y > -1 && y < frameHeight){
                return true;
            }
        }
        
        return false;
    }
  
    private volatile static int drawCount = 0;
    private static volatile boolean gameOver = false;
    private int spawnPeriod = 5000;
    
  
    private static boolean onPause = false;
    
    public void pause(boolean flag){
        onPause = flag;
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

    public Preferences getPrevUserPrefs(){
        return preferences;
    }
 
    
    
    
   
    
  
    public void setDim(Dimension d){
        setFrameHeight(d.height);
        setFrameWidth(d.width);
        
    }
    
    
    
   
 
    private volatile int drawCountLim;

    public synchronized void addDrawCount(){
        drawCount++;
    }
    
    private final int MAX_NUM_FIGS = 5;
    
    public synchronized int getNumCurrentFig(){
        return figs.size();
    }
    
    
    public static boolean isGameOver(){
        return gameOver;
    }
    Deque<MoveableFigure> figs = new LinkedList();
    public void initDino(){
        
        Dinosaur d = new Dinosaur(new MoveableObject("Dino"));
        d.addModel(this);
        //figs.add(d);
   
        setChanged();
        
        
        notifyObservers(d);
        
        CollisionHandler.addDino(d);
    }
    
    private Data userData;
    
    public static void setDimensions(Dimension d){
        frameHeight = d.height;
        frameWidth = d.width;
      
    }
    public void generateNewFigures(){
        int numValues =  MoveableTypes.values().length;
                        
        MoveableFigure fig = MoveableTypes.values()[rand.nextInt(numValues)].getNewFigure();

        fig.addModel(this);
        //adding new figure while painting
        figs.addLast(fig);
    }
    public void printWelcome(){
        int dialogResult;
        dialogResult = JOptionPane.showConfirmDialog(null, "Welcome back" + userData.username 
                + "\n your highest current score:  " + userData.currentScore,"Info", JOptionPane.OK_OPTION);
        
       
    
    }
    
    public void run(){
        
        int frameCount = 0;
        initDino();
        while (!gameOver){
        if (!interrupted){
                
                
               
                    if (figs.size() < MAX_NUM_FIGS ){
                        this.generateNewFigures();
                    }
                    
                
                this.setChanged();
                this.notifyObservers(figs);
                
                
                
                
            
        }
        
        try {
                    Thread.sleep(200);
                    
                } catch (InterruptedException ex) {
                }
        }
        
        
    }
  
    
    public void closeSession(){
        
        //dbM.savePreferences(preferences);
        if (preferences != null){
            dbM.savePreferences(preferences);
            
        }
        dbM.closeConnections();
        System.exit(0);
    }
    
  
 
    public static void setGameOver(boolean b) {
        gameOver = b;
        
        
    }

    public void setDiffLevel(String diffLevel) {
        this.difficulty = diffLevel;
    }

    void savePreferences(Preferences customisedSettings) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 
    
}
