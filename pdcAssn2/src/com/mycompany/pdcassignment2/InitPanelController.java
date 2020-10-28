/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import static java.lang.System.out;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Roy
 */

class InitPanelController implements ActionListener{
        private InitPanel initPanel;
        private InitPanelModel initModel;
        private SettingSelectionModel settingsModel;
        //private SettingSelectionWindow settingsWind;
        //game model is unnecessary
        
       
    public void setInitModel(InitPanelModel initModel) {
        this.initModel = initModel;
    }

    public void setSettingsModel(SettingSelectionModel settingsModel) {
        this.settingsModel = settingsModel;
    }
        
  
        
        //InitPanelModel initMod;
//        public void addView(SettingSelectionWindow settingsWind){
//            this.settingsWind = settingsWind;
//            
//        }
        
        public void addView(InitPanel p){
            this.initPanel = p;
        }
        
        public void addModel(InitPanelModel m){
            this.initModel = m;
        }
        
        public void addModel(SettingSelectionModel m){
            this.settingsModel = m;
        }
        
        
        
        @Override
        public void actionPerformed(ActionEvent e) {
            JComponent src = (JComponent) e.getSource();
            if (src == initPanel.getNextButton()){
                
                initPanel.bringToLogin(false);
                
            } else if (src == initPanel.getLoginButton()){
                if (initModel.login(initPanel.getUsernameField().getText().trim(), initPanel.getPasswordField().getText().trim())){
                    if (settingsModel.checkPreviousSettingsExist()){
                        
                        initPanel.askForUsingPreviousSetting();
                     
                    }
                    else{
                        
                        //there is no game instances for this user yet...so
                        //load default setting
                        settingsModel.disableGoBack();
                        settingsModel.loadDefaultSetting();
                        initPanel.getParentFrame().setVisible(false);

                     //SwingUtilities.getWindowAncestor(p).dispose();
                    }
                    
                } else{
                    initPanel.displayError("Login is invalid!");
                }
                    
                
            } else if (src == initPanel.usePrevious()){
                
                settingsModel.loadPreviousSetting();
                //SwingUtilities.getWindowAncestor(p).dispose();
                 initPanel.getParentFrame().setVisible(false);

                 
               
            } 
            //ask for using previous setting
            else if (src == initPanel.dontUsePrevious()){
                settingsModel.loadDefaultSetting();
                initPanel.getParentFrame().setVisible(false);

                //settingsModel.loadPreviousSetting();
                //.bringToNewPreferenceSelection();

            
            } else if (src == initPanel.getCreateNewUserBtn()){
                
                if (initModel.createUser(initPanel.getUsernameField().getText(), initPanel.getPasswordField().getText())){
                                        this.settingsModel.disableGoBack();

                    settingsModel.loadDefaultSetting();
                    
                    initPanel.getParentFrame().setVisible(false);
//settingsWind.bringToNewPreferenceSelection();
                } else{
                    initPanel.displayError("The username you entered is a duplicate. Try again.");
                
                }
                
                
                
            }   
            
        }
        
        
}





