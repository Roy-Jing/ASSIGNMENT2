/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import static java.lang.System.out;

import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

/**
 * @author Roy
 */
public class SettingSelectionModel extends Observable {
    private SortedSet<Image> images = new TreeSet();
    private DatabaseModel dbM;

    public void setDbM(DatabaseModel dbM) {
        this.dbM = dbM;
    }

 

    private boolean usePrevious = false;

    public void setUsePrevious(boolean usePrevious) {
        this.usePrevious = usePrevious;
    }
    
    public void loadDefaultSetting() {
        if (this.loadDefaultClicked == false){
            loadDefaultClicked = true;
            usePrevious = false;
            setChanged();

            notifyObservers(new DefaultSelections());
            this.loadDefaultClicked = true;
            setChanged();
            notifyObservers(goBack);

        } else{
            setChanged();
            this.notifyObservers();
        }


    }
    private boolean loadDefaultClicked = false, loadPreviouClicked = false;
   
    public boolean getUsePrevious() {
        return usePrevious;
    }

    public void setModel(DatabaseModel dbM) {
        this.dbM = dbM;


    }
   public boolean checkPreviousSettingsExist(){        
        return !dbM.loadPreviousPrefsOf().isEmpty();
    }
    
   Boolean goBack = true;
    public void loadPreviousSetting() {
        if (!this.loadPreviouClicked){
            usePrevious = true;
           
            loadPreviouClicked = true;
            
            //prefSet is set of prestrings
            prefSet = dbM.loadPreviousPrefsOf();
            
            //inform setting window of the pref string!
            this.setChanged();
            notifyObservers(prefSet);
                        this.setChanged();
            notifyObservers(goBack);

        } else{
            setChanged();
            this.notifyObservers();
        }
        
        


    }
    LinkedHashSet<String> prefSet;

    void disableGoBack() {
        goBack = false;
    }
    
}

class DefaultSelections {
    //LinkedHashSet<ImageIcon> images;
    private static int SCREEN_WIDTH, SCREEN_HEIGHT;
    
    static{
        SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    }
    private static String screenDims[] = { "" + (SCREEN_WIDTH +" x " + (SCREEN_HEIGHT - 40)), 
                                           "" + ((int) (SCREEN_WIDTH * 0.7) +" x " + (int)(SCREEN_HEIGHT * 0.7)),
                                           "" + ((int)(SCREEN_WIDTH) * 0.5) +" x " + (int) (SCREEN_HEIGHT * 0.5)
    
    };
    
    LinkedHashSet<String> diffLevel;
    LinkedHashSet<String> dimensions;

    List<String> fileNames;// add by YJ
    LinkedHashSet<ImageIcon> imagesIcons;//add by YJ
    ImageIcon[] scaledImages;
    
    public ImageIcon[] getScaledImage(){
        int i = 0;
        scaledImages = new ImageIcon[imagesIcons.size()];
        
        for (ImageIcon icon : imagesIcons){
             scaledImages[i++] = new ImageIcon(icon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));

        }
        
        return scaledImages;
        
    }
    
    public ImageIcon getImageIconAt(int i){
        int j = 0;
        for (ImageIcon icon : imagesIcons){
            if (j++ == i)
                return icon;
        }
        
        return null;
    }


    private LinkedHashSet<ImageIcon> getImages() {
        fileNames = new ArrayList<>();
      
        File[] defaultFolder = new File("src/BackgroundsFolder").listFiles();
        int i = 0;
        imagesIcons = new LinkedHashSet();
        

        for (File f : defaultFolder) {
            if (f.getName().toLowerCase().endsWith(".png")) {
                ImageIcon icon = new  ImageIcon("src/BackgroundsFolder/" + f.getName());
                imagesIcons.add(icon);
                
                icon.setDescription(f.getName());
            }
           
        }

        return imagesIcons;
    }

    // add by YJ end

    DefaultSelections() {
        diffLevel = new LinkedHashSet();
        dimensions = new LinkedHashSet();
        imagesIcons = this.getImages(); //add by YJ
        for (String level : new String[]{"Easy", "Medium", "Hard"})
            diffLevel.add(level);

        
        for (String dimension : screenDims) {
            dimensions.add(dimension);
        }
        

    }

  
    
}


    
