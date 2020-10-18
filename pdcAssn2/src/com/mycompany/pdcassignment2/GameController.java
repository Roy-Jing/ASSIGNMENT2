/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 *
 * @author Roy
 */
public class GameController implements ActionListener {
    
    private GameGUI GUI;
    private GameModel model;
   
    
    //controller cannot addview during construction as that would mean the view is
    //local to the controller only.
    public void addView(GameGUI GUI){
        this.GUI = GUI;
        
    }
    
    
    
    private void addModel(GameModel model){
        this.model = model;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
       
            
    }
    
    
    
}


