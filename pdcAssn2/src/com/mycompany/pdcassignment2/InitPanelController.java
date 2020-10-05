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
        InitPanel p;
        GameModel m;
        //InitPanelModel initMod;
        
        
        public void addView(InitPanel p){
            this.p = p;
        }
        
        public void addModel(GameModel m){
            this.m = m;
        }
        
        
        @Override
        public void actionPerformed(ActionEvent e) {
            JComponent src = (JComponent) e.getSource();
            if (src == p.getNextButton()){
                p.bringToLogin();
            } else if (src == p.getLoginButton()){
                
                
                m.init();
                p.askForUsingPreviousSetting();
                
            } else if (src == p.getCreateNewUserBtn()){
                out.println("create user");
                
            
            }else if (src == p.getYes()){
                m.useDefaultSetting(false);
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(p);

                topFrame.dispose();
                
               
            } else if (src == p.getNo()){
                m.getPreferences();
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(p);

                topFrame.dispose();
                //parent.dispatchEvent(new WindowEvent(parent, WindowEvent.WINDOW_CLOSING));

            } else if (src == p.getConfirmSelectionBtn()){
                
                m.setPreferences(p.collectPreferences());
                m.useDefaultSetting(false);
            }
        }
        
        
}


