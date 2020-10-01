/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.System.out;
import java.util.Observer;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
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

    public JButton getCreateNewUserBtn() {
        return createNewUserBtn;
    }
    
    private GridBagConstraints gbc = new GridBagConstraints();
    private JScrollPane messagePane;
    private InitPanelController controller;
    
    InitPanel(){
        
        this.setLayout(new GridBagLayout());
        usernameField = new JTextField("            ");
        passwordField = new JTextField("            ");
        
        loginButton = new JButton("Login");
        loginButton.addActionListener(controller);
        
    }
    
    public static void main(String args[]){
        
        JFrame f = new JFrame();
        InitPanel p = new InitPanel();
        DatabaseModel dbModel = new DatabaseModel();
        InitPanelController controller = new InitPanelController();
        GameModel gM = new GameModel();
        
        controller.addModel(gM);
        controller.addView(p);
        p.addController(controller);
       
        f.add(p);
        
        
        gM.addDatabse(dbModel);
        p.dispTips();
        f.setVisible(true);
        
        
        
       
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
        gbc.gridx = gbc.gridy = 1;
        
        add(this.messagePane);
        out.println(controller);
          
        nextButton.addActionListener(controller);
          
        gbc.gridx = gbc.gridy = 2;
        add(nextButton);
       
    }
    public void displayError(String errMessage){
        
        JOptionPane.showMessageDialog(this, errMessage, "ERROR",
                        JOptionPane.ERROR_MESSAGE);
        
    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public void bringToLogin(){
        
        removeAll(); 
        add(getUsernameField());
        add(getPasswordField());

        add(getLoginButton());
        out.println(controller);
        getLoginButton().addActionListener(controller);
        
        repaint();
        revalidate();
    }
    
    

        
}

class InitPanelController implements ActionListener{
        InitPanel p;
        GameModel m;
        DatabaseModel dbM = new DatabaseModel();
        public void addView(InitPanel p){
            this.p = p;
        }
        
        public void addModel(GameModel m){
            this.m = m;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == p.getNextButton()){
                p.bringToLogin();
            } else if (e.getSource() == p.getLoginButton()){
                
                if (dbM.login(p.getUsernameField().getText(), p.getPasswordField().getText()) == false){
                    out.println("login unsuccessful");//display login failure
                } 
                
                m.init();
                
            } else if (e.getSource() == p.getCreateNewUserBtn()){
                dbM.createUser(p.getUsernameField().getText(), p.getPasswordField().getText());
                
            }
        }
}

