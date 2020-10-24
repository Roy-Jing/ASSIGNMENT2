/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

/**
 *
 * @author Roy
 */
public class Driver {
     public static void main(String args[]){
        //if preferences is null (non-existent0
            //bring user to preferences menu
             
          
        //JFrame f = new JFrame();
        //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        
        initController.addModel(initModel);
        initController.addView(initPanel);
        initController.addModel(settingsModel);
        
        GameController gameController = new GameController();
        gameController.addView(gameGUI);
        gameController.addModel(gM);
        gameGUI.addController(gameController);
        gameGUI.setModel(gM);
       // dbM.reset();
        //dbM.initialiseTables();
        
        initModel.init();

      
       
    }
    
}
