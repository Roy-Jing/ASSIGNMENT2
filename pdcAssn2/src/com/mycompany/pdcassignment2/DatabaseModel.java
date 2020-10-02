/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

import java.awt.Color;
import java.awt.Dimension;
import static java.lang.System.out;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Roy
 */
public class DatabaseModel {
    private String username;
    private String pswd;
    
    private int highestScore;
    private int numCoinsCollected;
    private Date dateLastPlayed;
    private Preferences preferences;
    private Connection conn = null;
    
   
    
    public Preferences getPreference( ){
        Statement stmt;
        try {
            stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM UserInfo");
                
                while (rs.next()){
                    Preferences pf = new Preferences();
                    pf.setScreenDim(new Dimension(rs.getInt("SCREENWIDTH"), rs.getInt("SCREENHEIGHT")));
                    pf.setBgColour(new Color(rs.getInt("BGCOLOR")));
                    return pf;
                }
        } catch (SQLException ex) {
        }
       
                
                
        return null;
         

        
    }
    
    public void createUser(String name, String pswd){
       
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO USERS (" + name + ", " + pswd + ")");
        } catch (SQLException ex) {
            out.println("cannot create user");
        }
    }
       
    public void createIfNonExistent() throws SQLException{
        Statement stmt = conn.createStatement();
            DatabaseMetaData meta = conn.getMetaData();
            
          
            
            if ( meta.getTables(null, null, null, null) == null){
                stmt.addBatch("CREATE TABLE USERS (USERNAME VARCHAR(10), PSWD VARCHAR(10))");
                

                stmt.addBatch("CREATE TABLE USERINFO (USERNAME VARCHAR(10), HIGHSCORE INT, BGCOLOR VARCHAR(10), SCREENWIDTH INT, SCREENHEIGHT INT)");
                stmt.executeBatch();
            }
    }
    public boolean verifyCredentials(String name, String pswd) throws SQLException{
        Statement stmt = conn.createStatement();
           try{
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS");
            while (rs.next()){
            if (rs.getString("USERNAME").equals(name)){
                if (rs.getString("PSWD").equals(pswd)){
                    return true;
                }
            }
        }
        } catch (SQLException nonExistent){
            
        }
        
        
        return false;
    }
    
    
    public 
    
    public boolean login(String username, String pswd){
        
            try{
                conn = DriverManager.getConnection("jdbc:derby:gameDB;create=true", "pdc", "pdc");
                
                this.createIfNonExistent();
                return true;
            } catch (SQLException e){
                out.println("cannot create connection");
                
                return false;
            }
            
    }
}
    
            
       
    


class Preferences{
    private Dimension screenDim;
    private Color bgColour;
    
    public Dimension getScreenDim() {
        return screenDim;
    }

    public void setScreenDim(Dimension screenDim) {
        this.screenDim = screenDim;
    }

    public Color getBgColour() {
        return bgColour;
    }

    public void setBgColour(Color bgColour) {
        this.bgColour = bgColour;
    }
    
    
}

