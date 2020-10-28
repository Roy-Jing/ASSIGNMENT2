/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

/**
 *  Entry point for the game. Contains main method.
 * 
 * @author Roy
 */


public class Driver {
     public static void main(String args[]){
         
         //setting up all relationships between the MVCs...
     
        InitPanel initPanel = new InitPanel();
        GameGUI gameGUI = new GameGUI();
        DatabaseModel dbM = new DatabaseModel();
        GameModel gM = new GameModel();
        gM.addObserver(initPanel);
        gM.addObserver(gameGUI);
        gM.addDatabse(dbM);
        
        
        InitPanelController initController = new InitPanelController();
        
        InitPanelModel initModel = new InitPanelModel();
        initPanel.addController(initController);
        initModel.addObserver(initPanel);
        initModel.setDbM(dbM);
         SettingSelectionWindow settingsWindow = new SettingSelectionWindow();
        SettingSelectionController settingController = new SettingSelectionController();
        settingsWindow.setController(settingController);
        
         SettingSelectionModel settingsModel = new SettingSelectionModel();
        settingsModel.setDbM(dbM);
        settingsModel.addObserver(gameGUI);
        settingsModel.addObserver(settingsWindow);
       
        settingController.addModel(gM);
        settingController.addModel(settingsModel);
        settingController.addView(settingsWindow);
        settingController.setGUI(gameGUI);
        settingController.setInitPanel(initPanel);
        initController.addModel(initModel);
        initController.addView(initPanel);
        initController.addModel(settingsModel);
        
        GameController gameController = new GameController();
        gameController.addModel(gM);
        gameController.addView(gameGUI);

        gameGUI.addController(gameController);
        gameGUI.setModel(gM);
        //dbM.reset();
        //dbM.initialiseTables();
        
        initModel.init();

      
       
    }
    
}
