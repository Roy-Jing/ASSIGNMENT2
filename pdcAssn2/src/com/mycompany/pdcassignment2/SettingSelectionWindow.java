/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.io.File;
import java.io.FilenameFilter;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.SortedSet;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Roy
 */
public class SettingSelectionWindow extends JPanel implements Observer{
    private JLabel promptSelectionLbl = new JLabel("Select settings ...");
    private JFrame parentFrame = new JFrame();
    
    private DatabaseModel dbM;
    private JComboBox diffSelection;
    private JComboBox bgSelection;
    private JComboBox screenDimSelection;
    private JButton confirmSelectionBtn = new JButton("Confirm");
    private JComboBox prefSelection = new JComboBox();

    public JButton getConfirmSelectionBtn() {
        return confirmSelectionBtn;
    }
    
    private GridBagConstraints gbc = new GridBagConstraints();
    private SettingSelectionController controller;

    public void setDbM(DatabaseModel dbM) {
        this.dbM = dbM;
    }

    public void setController(SettingSelectionController controller) {
        this.controller = controller;
    }
    
           
    private void addAt(int x, int y , JComponent comp, int ... width){
        
        
        gbc.gridx = x;
        gbc.gridy = y;
        if (width.length != 0){
            gbc.gridwidth = width[0];
            if (width.length > 1){
                gbc.gridheight = width[1];
                
            }
        }
        this.add(comp, gbc);
        
    }
    
    SettingSelectionWindow(){
        this.setLayout(new GridBagLayout());
        
    }
    
    public void unpackDefaultSelections(DefaultSelections selections){
        diffSelection = new JComboBox();
        screenDimSelection = new JComboBox();
        
        for (String diffLevel : selections.diffLevel ){
            diffSelection.addItem(diffLevel);
        }
        
        for (String dim : selections.dimensions ){
            screenDimSelection.addItem(dim);

        }
        Object[] images = selections.images.toArray(new Object[selections.images.size()]);
        
       
        prefSelection = new JComboBox(images);
        
        
        prefSelection.setPreferredSize(new Dimension(1000, 1000));
        
    }
    
    
     public void bringToNewPreferenceSelection(){
        
        
        removeAll();
        
        this.setLayout(new GridBagLayout());
        this.addAt(1, 1, new JLabel("Select settings..."));
        gbc.ipadx = 2;
        gbc.ipady = 2;
        
        screenDimSelection.setSelectedIndex(0);
        this.addAt(2, 1 , new JLabel("Select a screen dimension"));
        this.addAt(2, 2, this.screenDimSelection);
        this.addAt(3, 1, new JLabel("Select a difficulty"));
        
        
        
        this.addAt(3, 2, diffSelection);
        this.addAt(4, 1, new JLabel("Background image"));
        this.addAt(4, 2, prefSelection, 100, 100);
        
        this.addAt(4, 4, this.confirmSelectionBtn);
        
        
        confirmSelectionBtn.addActionListener(controller);
        
        
    }
        
    public void displayPrevSettings(){
        
        add(prefSelection);
        
    }
    
    private JComboBox previousPrefSelection;
    
    
    public void setPreviousPrefs(LinkedHashSet<String> prefStrings){
        previousPrefSelection = new JComboBox();
        
        for (String prefString : prefStrings){
                previousPrefSelection.addItem(prefString);
            }
        
        
    }
   
    public void update(Observable o, Object arg){
        
        if (arg instanceof DefaultSelections ){
            this.unpackDefaultSelections((DefaultSelections) arg);
            this.bringToNewPreferenceSelection();
            
        }
        else if (arg instanceof LinkedHashSet ){
            
            setPreviousPrefs((LinkedHashSet<String>) arg);
            
            this.bringToOldPreferenceSelection();
        }
        this.add(this.confirmSelectionBtn);
        this.confirmSelectionBtn.addActionListener(controller);
        parentFrame.add(this);
        parentFrame.setVisible(true);

    }

    private void bringToOldPreferenceSelection() {
        
        this.addAt(1, 3, this.previousPrefSelection);
        this.addAt(1, 1, new JLabel("Select from history settings..."));
        
    }
    
    public Preferences getSelection(){
        String prefString = (String) previousPrefSelection.getSelectedItem();
        return Preferences.pack(prefString);
        
    }
    
    public Preferences getCustomisedSelection(){
        ImageIcon imgName = (ImageIcon) this.prefSelection.getSelectedItem();
        String diffLevel = (String) this.diffSelection.getSelectedItem();
        String dimensions = (String) this.screenDimSelection.getSelectedItem();
        String[] dim = dimensions.split("x| ");
        StringTokenizer tokenizer = new StringTokenizer(dimensions, "x| ");
        String w = tokenizer.nextToken(), h = tokenizer.nextToken();
        
        int height = Integer.parseInt(h);
        int width = Integer.parseInt(w);
        
        return new Preferences(width, height, imgName, diffLevel);
        
    
    
}
}


class SettingSelectionController implements ActionListener{
    private GameGUI GUI ;
    private SettingSelectionWindow settingsWindow;
    private GameModel gameModel;
    SettingSelectionModel settingsModel;
    
   
    public void setGUI(GameGUI GUI) {
        this.GUI = GUI;
    }
    
    public void setView(SettingSelectionWindow wind){
        settingsWindow = wind;
    }
    
    public void addModel(GameModel m){
        this.gameModel = m;
    }
    
    public void addModel(SettingSelectionModel m){
        this.settingsModel = m;
    } 
  
    public void configureGameModel(Preferences settings){
        
            GUI.loadPreferences(settings);
            gameModel.setDimensions(settings.screenDim);
            gameModel.setDiffLevel(settings.diffLevel);
            
    }
    public void actionPerformed(ActionEvent e){
        Object src = e.getSource();
        
        //this will only be triggered if confirmSelectionBtn is clicked
        if (src == settingsWindow.getConfirmSelectionBtn()){
        if (settingsModel.getUsePrevious()){
            //watch out for prefString, might cause exception
            //selecting a preference
            Preferences settings = settingsWindow.getSelection();
            this.configureGameModel(settings);
            
        } else if (!settingsModel.getUsePrevious()){
            Preferences customisedSettings = settingsWindow.getCustomisedSelection();
            this.configureGameModel(customisedSettings);
            
            //only set if user does not choose to use previous -- meaning new preferences created
            gameModel.setPreferences(customisedSettings);
             
            
        }
        gameModel.init();
        
        }
        
        
    }

    public void addView(SettingSelectionWindow settingsWindow) {
        this.settingsWindow = settingsWindow;
    }
}

   

