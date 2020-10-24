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
        private InitPanel p;
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
            this.p = p;
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
            if (src == p.getNextButton()){
                
                p.bringToLogin(false);
                
            } else if (src == p.getLoginButton()){
                if (initModel.login(p.getUsernameField().getText().trim(), p.getPasswordField().getText().trim())){
                    if (settingsModel.checkPreviousSettingsExist())
                        p.askForUsingPreviousSetting();
                    else
                        settingsModel.loadDefaultSetting();
                    
                     //SwingUtilities.getWindowAncestor(p).dispose();

                } 
                    
                
            } else if (src == p.usePrevious()){
                
                settingsModel.loadPreviousSetting();
                SwingUtilities.getWindowAncestor(p).dispose();

                
               
            } 
            //ask for using previous setting
            else if (src == p.dontUsePrevious()){
                settingsModel.loadDefaultSetting();
               
                //settingsModel.loadPreviousSetting();
                //.bringToNewPreferenceSelection();
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(p);

                topFrame.dispose();
                //parent.dispatchEvent(new WindowEvent(parent, WindowEvent.WINDOW_CLOSING));

            
            } else if (src == p.getCreateNewUserBtn()){
                if (initModel.createUser(p.getUsernameField().getText(), p.getPasswordField().getText())){
                    settingsModel.loadDefaultSetting();
                    

//settingsWind.bringToNewPreferenceSelection();
                }

            }  
            
        }
        
        
}





