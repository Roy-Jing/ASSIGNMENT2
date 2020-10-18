/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static java.lang.System.out;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Roy
 */
public class InitPanel extends JPanel implements Observer {
    private JButton loginButton;
    private JFrame parentFrame;
    private JComboBox diffSelection;
    
    public JButton getLoginButton() {
        return loginButton;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JTextField getPasswordField() {
        return passwordField;
    }

    public JButton getNextButton() {
        return nextButton;
    }
    
    
    public GridBagConstraints getGbc() {
        return gbc;
    }

    public JScrollPane getMessagePane() {
        return messagePane;
    }

    public InitPanelController getController() {
        return controller;
    }
    private JTextField usernameField;
    private JTextField passwordField;
    private JButton nextButton;
    private JButton createNewUserBtn = new JButton("create user");
    private JComboBox screenDimSelection;
    private JComboBox bgSelection;
    private JButton confirmBtn;
    private JPanel promptWindow;
    public JButton getConfirmBtn() {
        
        return confirmBtn;
    }

    public void setConfirmBtn(JButton confirmBtn) {
        this.confirmBtn = confirmBtn;
    }
    JButton yes = new JButton("Yes");

    public JButton getYes() {
        return yes;
    }

    public JButton getNo() {
        return no;
    }
        JButton no = new JButton("No, use default settings");
    public JButton getCreateNewUserBtn() {
        return createNewUserBtn;
    }
    
    
    
    private GridBagConstraints gbc = new GridBagConstraints();
    private JScrollPane messagePane;
    private InitPanelController controller;
    private GameModel gameModel;

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }
    
    
    public void askForUsingPreviousSetting(){
        //GameModel's preferences at this point is not null.
        this.removeAll();
        out.println("ask for using previos");
        
        //promptWindow = new JPanel();
        yes.addActionListener(controller);
        //this.add(promptWindow);
        
        this.setLayout(new BoxLayout( this, BoxLayout.Y_AXIS));
        
        JLabel message = new JLabel("Previous settings exist, would you like to use them?");
      
        JPanel buttonPanel = new JPanel();
        
        buttonPanel.add(yes);
        buttonPanel.add(no);
        
        yes.addActionListener(controller);
        no.addActionListener(controller);
        
        add(message);
        add(buttonPanel);
        //parentFrame.add(promptWindow);
        
        //JFrame promptWind = new JFrame();
        //add(promptWindow);
        this.setSize(100, 100);
        //setVisible(true);
        this.repaint();
        this.revalidate();
               
    }
    
    
    InitPanel(){
        
        
        this.setLayout(new GridBagLayout());
        
        usernameField = new JTextField("            ");
        passwordField = new JTextField("            ");
        
        
        loginButton = new JButton("Login");
        loginButton.addActionListener(controller);
        
        
    }
    
    public static void main(String args[]){
        //if preferences is null (non-existent0
            //bring user to preferences menu
             
          
        //JFrame f = new JFrame();
        //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        InitPanel p = new InitPanel();
        
        
        InitPanelController controller = new InitPanelController();
        GameGUI gameGUI = new GameGUI();
        
        GameModel gM = new GameModel();
      
        gM.addObserver(p);
        controller.addModel(gM);
        controller.addView(p);
        
        p.addController(controller);
        gM.addObserver(gameGUI);
        //f.add(p);
      
        gM.init();
        
                
        
   
        //p.dispTips();
        //f.setVisible(true);
        
        
        
       
    }
    
    public void addController(ActionListener listener){
        controller = (InitPanelController) listener;
    }
   
    
    public void dispTips(){
        
        JTextArea text = 
        new JTextArea("Welcome to T-Rex The Game.\nThis game is very similar to\n"
                        + "the offline game Google T-Rex.\n Use the keys WASD to control"
                        + "the movement of the Dinosaur.\n"
                        + "Use 'w' to jump, 'a' to move backward,\n"
                        + "d to move forward, and finally s to \"hunch\" the Dinosaur. \n"
                        + "Avoid any randomly generated incoming obstacles.\n"
                        + "as you go, you'll see your current score increase.\n "
                        + "(Hit enter to continue)" 
                

                        + "There is a special moving obstacle in the form of 2 horizontal\n"
                        + "lines, (aka a Cloud, as shown in the figure above).\n "
                        + "You will see them as you progress.\n"
                        + "This obstacle is special in that you need not avoid this\n"
                        + "obstacle and you can choose to\n"
                        + "\"land\" your Dinosaur on top of it, by first pressing\n"
                        + "'w' key, Dinosaur will \"jump\" onto the Cloud if the positions\n"
                        + "of the Dinosaur align with that of the Cloud \n (Hit enter to continue)\n"
                
                        +"Finally, upon launching the game, you will see a Window pops up\n"
                        
                        + "IT IS VERY IMPORTANT THAT before you start playing, you CLICK once on the\n"
                        + "window.\nDuring the game run, do not click outside this window, as\n"
                        + "that will block the KeyListener from receiving you input (key presses)\n"
                        + "If the dinosaur is not moving but should, then try to click on the Window.\n"
           

                        + "To exit the game properly, close the JFrame window, and in the command\n"
                        + "line, type \"y\" when prompted (Hit enter to continue)."

                        + "That's everything, have fun!");
        messagePane = new JScrollPane(text);
        nextButton = new JButton("next");
       
        
        add(this.messagePane);
        out.println(controller);
          
        nextButton.addActionListener(controller);
          
        gbc.gridx = gbc.gridy = 2;
        add(nextButton);
        
    }
    
    private JButton confirmSelectionBtn = new JButton("Confirm!");

    public JButton getConfirmSelectionBtn() {
        return confirmSelectionBtn;
    }
    public void askForPreferences(){
        diffSelection = new JComboBox();
        
        bgSelection = new JComboBox();
        this.screenDimSelection = new JComboBox();
        
        bgSelection.addItem(Color.WHITE);
        bgSelection.addItem(Color.GREEN);
        bgSelection.setSelectedIndex(0);
        this.screenDimSelection.addItem(new Dimension(100, 50));
                this.screenDimSelection.addItem(new Dimension(200, 100));;
                this.screenDimSelection.addItem(new Dimension(250, 100));
        screenDimSelection.setSelectedIndex(0);
        removeAll();
        diffSelection.addItem("Easy");
        diffSelection.addItem("Medium");
        diffSelection.addItem("Hard");
        
        this.setLayout(new GridLayout(2, 4));
        
        this.add(new JLabel("Select a background colour"));
        this.add(bgSelection);
        
        this.add(new JLabel("Select a screen dimension"));
        this.add(screenDimSelection);
        
        this.add(this.confirmSelectionBtn);
        
        this.add(new JLabel("Select a difficulty"));
        this.add(diffSelection);
        
        
        confirmSelectionBtn.addActionListener(controller);
        this.repaint();
        this.revalidate();
        
        
    }
    public void displayError(String errMessage){
        
        JOptionPane.showMessageDialog(this, errMessage, "ERROR",
                        JOptionPane.ERROR_MESSAGE);
        
    }

    public Preferences collectPreferences(){
        Preferences collected = new Preferences(true);
        collected.setBgColour((Color)this.bgSelection.getSelectedItem());
        //collected.setDifficulty(diffSelection.getSelectedItem());
        
        collected.setScreenDim((Dimension)this.screenDimSelection.getSelectedItem());
        
        return collected;
        
    }
    
    
    @Override
    public void update(Observable o, Object arg) {
        
        //init() in GameModel will set arg to null
        if (arg instanceof Boolean){
            this.parentFrame = new JFrame("Init Panel");
            parentFrame.add(this);
            parentFrame.setVisible(true);
            
            out.println("updating initpanel");
            
            boolean prevExist = (boolean) arg;
            if (!prevExist)
                dispTips();
            else{ 
                //this.printWelcomeBack();
            
                this.askForUsingPreviousSetting();
            }
        }
            
       
    }
    
    private void addAt(int x, int y, JComponent comp){
        gbc.gridx = x;
        gbc.gridy = y;
        this.add(comp, gbc);
        
    }
    
    public void bringToLogin(){
        
        removeAll(); 
        this.setLayout(new GridBagLayout());
        addAt(1, 0, new JLabel("Username"));
        
        addAt(1,1, getUsernameField());
        
        addAt(1, 0, new JLabel("Password"));
        
        addAt(4, 1, getPasswordField());

        addAt(4, 3, getLoginButton());
        out.println(controller);
        loginButton.addActionListener(controller);
        
        repaint();
        revalidate();
    }
    
    

        
}

