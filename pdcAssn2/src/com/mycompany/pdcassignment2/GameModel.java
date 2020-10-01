/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

import java.util.Observable;

/**
 *
 * @author Roy
 */
public class GameModel extends Observable{
    private int currentScore = 0;
    private int numCoinsCollected = 0;
    private Preferences preferences;
    
    private DatabaseModel dbM;
    
    public void addDatabse(DatabaseModel m){
        dbM = m;
    }
    
    
    public void init(){
        
        preferences = dbM.getPreference();
        this.setChanged();
        this.notifyObservers(preferences);
        
    }
    
    public void shutdown(){
        
    }
    
    
}
