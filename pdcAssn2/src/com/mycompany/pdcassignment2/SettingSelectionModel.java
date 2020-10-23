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
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Observable;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

/**
 *
 * @author Roy
 */
public class SettingSelectionModel extends Observable{
    private SortedSet<Image> images = new TreeSet();
    private DatabaseModel dbM;
    private String username;
  
    public void setDbM(DatabaseModel dbM) {
        this.dbM = dbM;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
    private boolean usePrevious = false;

        public void setUsePrevious(boolean usePrevious) {
            this.usePrevious = usePrevious;
        }
    public void loadDefaultSetting(){
        
        usePrevious = false;
        setChanged();
        
        notifyObservers(new DefaultSelections());
        
        
        

    }
    public boolean getUsePrevious(){
        return usePrevious;
    }
    
    public void setModel(DatabaseModel dbM){
        this.dbM = dbM;
        
        
    }
    
    
    public void loadPreviousSetting(){
        
        usePrevious = true;
        
        //prefSet is set of prestrings
        LinkedHashSet<String> prefSet = dbM.loadPreviousPrefsOf(username);
        
        //inform setting window of the pref string!
        this.setChanged();
        notifyObservers(prefSet);
        
        
    }
   
   
   }

class DefaultSelections{
    LinkedHashSet<ImageIcon> images;
    LinkedHashSet<String> diffLevel;
    LinkedHashSet<String> dimensions;
    
    private LinkedHashSet<ImageIcon> constructImages(){
        
        File src = new File("src");        
        File[] defaultFolder = new File("src").listFiles();
                  
        for (File f : defaultFolder){
            if (f.getName().toLowerCase().endsWith(".png")){
                out.println(f.getName() + "loading...");
                
                images.add(new ImageIcon(f.getName()));
                
            }
        }
        
        return images;
    }
    DefaultSelections(){
        images = new LinkedHashSet();
        diffLevel = new LinkedHashSet();
        dimensions = new LinkedHashSet();
        init();
        
          
    }
    
    public void init(){
        images =this.constructImages();
        for (String level : new String[]{"Easy", "Medium", "Hard"})
            diffLevel.add(level);
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        
        for (String dimension : new String[]{"1000 x 500", "750 x 400", "100 x 50"}){
            dimensions.add(dimension);
        }
   
}
}


    
