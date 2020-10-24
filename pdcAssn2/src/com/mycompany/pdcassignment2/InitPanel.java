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
import java.awt.Image;
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
    private JButton confirmBtn;
    public JButton getConfirmBtn() {
        
        return confirmBtn;
    }

    public void setConfirmBtn(JButton confirmBtn) {
        this.confirmBtn = confirmBtn;
    }
    JButton usePrevious = new JButton("Yes");

    public JButton usePrevious() {
        return usePrevious;
    }

    public JButton dontUsePrevious() {
        return dontUsePrevious;
    }
        JButton dontUsePrevious = new JButton("dontUsePrevious, use default settings");
    public JButton getCreateNewUserBtn() {
        return createNewUserBtn;
    }
    
    
    
    private GridBagConstraints gbc = new GridBagConstraints();
    private JScrollPane messagePane;
    private InitPanelController controller;

    public void askForUsingPreviousSetting(){
        //GameModel's preferences at this point is not null.
        this.removeAll();
        
        //promptWindow = new JPanel();
        usePrevious.addActionListener(controller);
        //this.add(promptWindow);
        //fillSettings()
       
        this.setLayout(new BoxLayout( this, BoxLayout.Y_AXIS));
        
        JLabel message = new JLabel("Previous settings exist, would you like to use them?");
        
        JPanel buttonPanel = new JPanel();
        
        buttonPanel.add(usePrevious);
        buttonPanel.add(dontUsePrevious);
        
        usePrevious.addActionListener(controller);
        dontUsePrevious.addActionListener(controller);
        
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
        
        
        usernameField = new JTextField();
        passwordField = new JTextField();
        
        usernameField.setColumns(15);
        passwordField.setColumns(15);
        
        parentFrame = new JFrame("Initial config...");
        parentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        loginButton = new JButton("Login");
        loginButton.addActionListener(controller);
        
        
        
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
          
        nextButton.addActionListener(controller);
          
        addAt(2, 2, nextButton );
        
    }
    
    private JButton confirmSelectionBtn = new JButton("Confirm!");

    public JButton getConfirmSelectionBtn() {
        return confirmSelectionBtn;
    }
   
    public void displayError(String errMessage){
        
        JOptionPane.showMessageDialog(this, errMessage, "ERROR",
                        JOptionPane.ERROR_MESSAGE);
        
    }

    
    @Override
    public void update(Observable o, Object arg) {
        
        //init() in GameModel will set arg to null
        if (arg instanceof Boolean){
            
            parentFrame.add(this);
            parentFrame.setVisible(true);
            
            boolean prevExist = (boolean) arg;
            if (!prevExist)
                dispTips();
            else{ 
                this.bringToLogin(true);
                
                //this.askForUsingPreviousSetting();
            }
        } else if (arg instanceof Error){
            
            this.displayError(((Error) arg).errMsg);
        }
            
       
    }
    
    private void addAt(int x, int y, JComponent comp, int ...width){
        
        
        gbc.gridx = x;
        gbc.gridy = y;
        if (width.length != 0)
            gbc.gridwidth = width[0];
        else
            gbc.gridwidth = 5;
        
        this.add(comp, gbc);
        
    }
    
    
  
    public void bringToLogin(boolean canLogin){
        
        this.removeAll();
        this.setLayout(new GridBagLayout());
        
        addAt(1, 1, new JLabel("Username"));
        
        addAt(1, 2,  usernameField);
        
        addAt(10, 1, new JLabel("Password"));
        
        addAt(10, 2, passwordField);
     
        addAt(10, 3, loginButton);
        
        if (canLogin){
            loginButton.addActionListener(controller);
        } else{
            loginButton.setEnabled(false);
        }
      
        addAt(3, 3, this.createNewUserBtn);
        createNewUserBtn.addActionListener(controller);
        
        repaint();
        revalidate();
    }
    
    

        
}

