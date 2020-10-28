/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

import java.awt.BorderLayout;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FilenameFilter;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.SortedSet;
import java.util.StringTokenizer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Roy
 */
public class SettingSelectionWindow extends JPanel implements Observer{
    private JLabel promptSelectionLbl = new JLabel("Select settings ...");
    private JFrame parentFrame = new JFrame();

    public JFrame getParentFrame() {
        return parentFrame;
    }
    
    private DatabaseModel dbM;
    private JComboBox diffSelection;
    private JComboBox bgSelection;
    private JComboBox screenDimSelection;
    private JButton confirmSelectionBtn = new JButton("Confirm");
    private JComboBox prefSelection = new JComboBox();

    public JButton getConfirmSelectionBtn() {
        return confirmSelectionBtn;
    }
    DefaultSelections defaultSettings = new DefaultSelections();
    
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
        //this.add(comp, gbc);
        
    }
    
    SettingSelectionWindow(){
        this.setLayout(new GridBagLayout());
        
    }
    JTable settingsTable;
    
    public void unpackDefaultSelections(DefaultSelections selections){
        diffSelection = new JComboBox();
        screenDimSelection = new JComboBox();
        defaultSettings = selections;
        
        for (String diffLevel : selections.diffLevel ){
            diffSelection.addItem(diffLevel);
        }
        
        for (String dim : selections.dimensions ){
            screenDimSelection.addItem(dim);

        }
        
        prefSelection.setSize(new Dimension(1000, 1000));
        
        
       prefSelection = new JComboBox(selections.getScaledImage());        
        
        
    }
    
    
     public void bringToNewPreferenceSelection(){
        
        
        removeAll();
        
        this.setLayout(new BorderLayout());
        this.add(new JLabel("Select settings..."), BorderLayout.NORTH);
        
        screenDimSelection.setSelectedIndex(0);
        
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BorderLayout());
         JPanel midPanel = new JPanel();
        eastPanel.setLayout(new BorderLayout());
        JPanel westPanel = new JPanel();
        eastPanel.setLayout(new BorderLayout());

        westPanel.add(this.screenDimSelection, BorderLayout.NORTH);
        westPanel.add(new JLabel("Select a screen dimension"),BorderLayout.SOUTH );
        
        midPanel.add(new JLabel("Select a difficulty"), BorderLayout.NORTH);
        midPanel.add(this.diffSelection, BorderLayout.SOUTH);
                
                
        eastPanel.add(new JLabel("Select a background image"), BorderLayout.NORTH);
        eastPanel.add(this.prefSelection, BorderLayout.SOUTH);
        
        this.add(westPanel, BorderLayout.WEST);
        this.add(midPanel, BorderLayout.CENTER);
        this.add(eastPanel, BorderLayout.EAST);
        
        
    }
        
    public void displayPrevSettings(){
        
        add(prefSelection);
        
    }
    
    private JComboBox previousPrefSelection;
    
    
    public void setPreviousPrefs(LinkedHashSet<String> prefStrings){
        previousPrefSelection = new JComboBox();
        String[] columns = {"Screen Dimension", "Game Difficulty", "Background Image"};

        String[][] data = new String[prefStrings.size()][];
        String[] rows = prefStrings.toArray(new String[prefStrings.size()]);
        
        for (int i =0;i < prefStrings.size(); i++){
            data[i] =rows[i].split("\\|");
            
        }
        settingsTable = new NonEditableTable(data, columns);
        settingsTable.setLocation(5, 5);
        settingsTable.setSelectionModel(new NonEditableTableModel());
        scrollPane = new JScrollPane(settingsTable);
        settingsTable.addMouseListener(new MouseAdapter(){
            private JButton btn = SettingSelectionWindow.this.confirmSelectionBtn;
            
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                btn.setEnabled(true);
            }

        });
        
        
    }

                
    
    class NonEditableTable extends JTable {
        private static final long serialVersionUID = 1L;
        
        NonEditableTable(Object[][] data, String[] columns){
            super(data, columns);
        }

        
        public boolean isCellEditable(int row, int column) {                
                return false;               
        };
        
    }
    
    class NonEditableTableModel extends DefaultListSelectionModel{
        public NonEditableTableModel () {
            setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }

        @Override
        public void clearSelection() {
        }

        @Override
        public void removeSelectionInterval(int index0, int index1) {
        }
    }
    
    JScrollPane scrollPane;
    private JButton goBack = new JButton("Go Back");
    
    public void update(Observable o, Object arg){
        
        //if arg is null then it means we have previous displayed this
        //panel but user has decided to "go back" and then enter again.
        
        if (o instanceof SettingSelectionModel && arg == null){
            parentFrame.setVisible(true);
            return;
            
        }
        
        this.setLayout(new BorderLayout());
        this.add(Box.createHorizontalStrut(10));
                this.add(Box.createVerticalStrut(10));
         
        
        if (arg instanceof DefaultSelections ){
                        confirmSelectionBtn.setEnabled(true);

            this.unpackDefaultSelections((DefaultSelections) arg);
            this.bringToNewPreferenceSelection();
            
        }
        else if (arg instanceof LinkedHashSet ){
            out.println("arg linkedhash");
            confirmSelectionBtn.setEnabled(false);

            setPreviousPrefs((LinkedHashSet<String>) arg);
            this.bringToOldPreferenceSelection();
        } 
        
       
        
            if (arg instanceof Boolean && (boolean) arg){
                 JPanel confirmAndGoBack = new JPanel();

                confirmAndGoBack.add(goBack);
                confirmAndGoBack.add(this.confirmSelectionBtn);

                goBack.addActionListener(controller);
                this.add(confirmAndGoBack, BorderLayout.SOUTH);


            } else{


                this.add(this.confirmSelectionBtn, BorderLayout.SOUTH);
            }
            if ( !addedThis){
                addedThis = true;
                parentFrame.add(this);
                confirmSelectionBtn.addActionListener(controller);

            }
            
            parentFrame.setPreferredSize(new Dimension(1000, 300));
            parentFrame.pack();
            parentFrame.setResizable(false);
            parentFrame.setVisible(true);
            
            
            
        
    }
    
    private boolean addedThis;
   

    private void bringToOldPreferenceSelection() {
        
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(new JLabel("Select from history settings..."), BorderLayout.NORTH );
        
        this.add(this.scrollPane, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
    
    public Preferences getSelection(){
        
        TableModel model = settingsTable.getModel();;
            
        int row = settingsTable.getSelectedRow();
        
         String dimString = (String) model.getValueAt(row, 0);
         String  imgName = (String) model.getValueAt(row, 1 );
                  String diffLevel = (String) model.getValueAt(row, 2 );

                 //    Preferences(int dimWidth, int dimHeight, String imgName, String diffLevel){

        return new Preferences(dimString, "src/BackgroundsFolder/" + imgName, diffLevel );
        
    }
    
    public Preferences getCustomisedSelection(){
        ImageIcon imgName = (defaultSettings.getImageIconAt(this.prefSelection.getSelectedIndex()));
        String diffLevel = (String) this.diffSelection.getSelectedItem();
        String dimensions = (String) this.screenDimSelection.getSelectedItem();
        String[] dim = dimensions.split("x| ");
        StringTokenizer tokenizer = new StringTokenizer(dimensions, "x| ");
        String w = tokenizer.nextToken(), h = tokenizer.nextToken();
        
        int height = Integer.parseInt(h);
        int width = Integer.parseInt(w);
        
        return new Preferences(width, height, imgName, diffLevel);
        
    }
    public JButton getGoBack(){
        return goBack;
    }
    
   
}



class SettingSelectionController implements ActionListener{
    private GameGUI GUI ;
    private SettingSelectionWindow settingsWindow;
    private GameModel gameModel;
    private InitPanel panel;
    
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
  
    public void configureGame(Preferences settings){
        
            
            gameModel.setDimensions(settings.screenDim);
            gameModel.setDiffLevel(settings.diffLevel);
            GUI.loadPreferences(settings);
            
    }
    public void actionPerformed(ActionEvent e){
        Object src = e.getSource();
        
        //this will only be triggered if confirmSelectionBtn is clicked
        if (src == settingsWindow.getConfirmSelectionBtn()){
            
            
                if (settingsModel.getUsePrevious()){
                    
                    Preferences settings = settingsWindow.getSelection();
                    this.configureGame(settings);

                } else if (!settingsModel.getUsePrevious()){
                    
                    Preferences customisedSettings = settingsWindow.getCustomisedSelection();
                    this.configureGame(customisedSettings);

                    //only set if user does not choose to use previous -- meaning new preferences created
                    gameModel.setPreferences(customisedSettings);


                }
                SwingUtilities.getWindowAncestor(settingsWindow).dispose();;
                
                gameModel.init();
        
        } else if (src == settingsWindow.getGoBack()){
            
            settingsWindow.getParentFrame().setVisible(false);
            out.println("going back");
            
            //SwingUtilities.getWindowAncestor(GUI).setVisible(false);
            panel.askForUsingPreviousSetting();
           
        } //else if (src == settingsWindow.getConfirmPrevSelectionBtn())
        
        
        
        
    }
    public void setInitPanel(InitPanel p){
        
        this.panel = p;
    }
    public void addView(SettingSelectionWindow settingsWindow) {
        this.settingsWindow = settingsWindow;
    }
}

   

