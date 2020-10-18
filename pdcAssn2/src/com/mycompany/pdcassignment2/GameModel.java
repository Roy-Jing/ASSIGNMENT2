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

/**
 *
 * @author Roy
 */
public class GameModel extends Observable implements Runnable{

    
    private int currentScore = 0;
    private static volatile int numCoinsCollected = 0;
    private String difficulty;
    private boolean interrupted  = false;

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
            userData = dbM.getData();
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
    
   
    public synchronized void addDrawCount(){
        drawCount++;
    }
    
    private final int MAX_NUM_FIGS = 5;
    
    public synchronized int getNumCurrentFig(){
        return figs.size();
    }
    
    private Dinosaur d;
    
    public static char[][] getVirtualGUI(){
        return virtualGUI;
    }
    
    public void initaliseVirtualGUI(){
        virtualGUI = new char[frameHeight][frameWidth];
        
        int[][] coordMat = d.getOriginalForm();
        int dinoCoordX = d.getCoordX();
        int dinoCoordY = d.getCoordY();
        for (int i = 0; i < d.getNumPixels(); i++){
            int cX = coordMat[1][i] ;
            int cY = coordMat[0][i];
            
            virtualGUI[cY + dinoCoordY][cX + dinoCoordX] = '!';
            
        }
    }
    public void updateVirtualGUI(){
        int velocityX =  d.getVelocityX();
        int velocityY = d.getVelocityY();
        int coordY = d.getCoordY();
        int coordX = d.getCoordX();
        
        int startPos = 0, shiftDir = 0, endPos = 0;
        if (velocityX != 0 || velocityY != 0){
       if (velocityX > 0 || velocityY > 0){
           startPos = d.getNumPixels() - 1;
           shiftDir = -1;
           endPos = -1;
       } else if (velocityX <= 0 || velocityY < 0){
           startPos = 0;
           shiftDir = 1;
           endPos = d.getNumPixels();
           
       } 
     
            int form[][] = d.getOriginalForm();
            
            for (int i = startPos; i != endPos; i+= shiftDir){
                int cY = form[0][i] + coordY;
                int cX = form[1][i] + coordX;
                
                virtualGUI[cY][cX] =  ' ';
                virtualGUI[cY + velocityY][cX + velocityX] = '!';
               
            }
            
        
        }
    }
    
    public static boolean isGameOver(){
        return gameOver;
    }
    Deque<MoveableFigure> figs = new LinkedList();
    public void initDino(){
        
        d = new Dinosaur(new MoveableObject("Dino"));
        d.addModel(this);
        figs.add(d);
   
        setChanged();
        
        
        notifyObservers(d);
        initaliseVirtualGUI();
        CollisionHandler.addDino(d);
    }
    
    private Data userData;
    
    public void generateNewFigures(){
        int numValues =  MoveableTypes.values().length;
                        
        MoveableFigure fig = MoveableTypes.values()[rand.nextInt(numValues)].getNewFigure();

        fig.addModel(this);
        //adding new figure while painting
        figs.addFirst(fig);
    }
    public void printWelcome(){
        int dialogResult;
        dialogResult = JOptionPane.showConfirmDialog(null, "Welcome back" + userData.username 
                + "\n your highest current score:  " + userData.currentScore,"Info", JOptionPane.OK_OPTION);
        
       
    
    }
    public void run(){
        
        int frameCount = 0;
        setDim(preferences.getScreenDim());
        initDino();
        setChanged();
        notifyObservers(preferences);
        
        
        
        while(true){
                
                if (!this.isInterrupted()){
                    if (figs.size() < MAX_NUM_FIGS ){
                        this.generateNewFigures();
                    }
                    
                }
                
                this.setChanged();
                this.notifyObservers(figs);
                try {
                    Thread.sleep(200);
                    
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


    public static void setGameOver(boolean b) {
        gameOver = b;
        
        
    }
    
    
}
