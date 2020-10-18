/*
 * The programs are designed for PDC paper
 */
package com.mycompany.pdcassignment2;

import java.awt.Color;
import java.awt.Dimension;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Quan Bai and Weihua Li
 * ****************************************************************** @Note
 * Database Connection Component
 *
 * @Embedded database example: private String
 * url="jdbc:derby:PlayerDB;create=true";
 * @Embedded database can be copied to the root of the project folder
 * @Embedded derby dataBase must have two jars referenced: derbyclient.jar and
 * derby.jar
 * @Online URL example: url="jdbc:derby://localhost:1527/PlayerDB;create=true"
 * ********************************************************************
 */
public class DatabaseModel {
    
    private int highestScore;
    private int numCoinsCollected;
    private Date dateLastPlayed;
    private Preferences preferences;
    private static final String USER_NAME = "pdc";
    private static final String PASSWORD = "pdc";
    private boolean firstTime = false;
    //You need to start the Java DB, 
    //The database will be created at C:\Users\YOUR_ID\.netbeans-derby
    //If you right click Java DB and create a database manually, the location will be the same
    //The database cannot be deleted unless the Java DB is stopped.
    //If you only observe "other schemas", you need to find USER_NAME from it, and set it as default schema
    //If the database does not appear in IDE after creating through codes, please restart Netbeans (this is a bug) 

//    private static final String URL = "jdbc:derby://localhost:1527/CarDB;create=true";
    //Embedded database: 
    //You do NOT need to start the javaDB, the database will be created at the root of the project folder
    private static final String URL = "jdbc:derby:gameDB;create=true";  //derby.jar

    private Connection conn;
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
    
    public DatabaseModel() {
        
        establishConnection();
        if (!this.checkTableExisting("SETTINGS")){
            initialiseTables();
            firstTime = true;
        }
    }
    

    public Preferences getPreference( ){
        Statement stmt;
        if (!firstTime)
            try {

                stmt = conn.createStatement();
                 ResultSet rs = this.myQuery("SELECT * FROM SETTINGS");
                 rs.last();
                Preferences pf = new Preferences(false);
                pf.setScreenDim(new Dimension(rs.getInt("SCREENWIDTH"), rs.getInt("SCREENHEIGHT")));
                pf.setBgColour(new Color(rs.getInt("BGCOLOR")));
                return pf;
            } catch (SQLException ex) {

            }
       
                
                
        return null;
         

        
    }
    
   

    public Connection getConnection() {
        return this.conn;
    }

    //Establish connection
    public void establishConnection() {
        if (this.conn == null) {
            try {
                
                conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
                
                System.out.println(URL + "   CONNECTED....");
                
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());

            }
        }
    }
    
  
    public void closeConnections() {
        if (conn != null) {
            try {
                this.updateInfo(data);
                conn.close();
            } catch (SQLException ex) {
            }
        }
    }

    public ResultSet myQuery(String sql) {

        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void myUpdate(String sql) {

        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void createUser(String name, String pswd){
       
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO USERS (" + name + ", " + pswd + ")");
        } catch (SQLException ex) {
            out.println("cannot create user");
        }
    }
    
    
    private boolean checkTableExisting(String newTableName) {
        boolean flag = false;
        try {

            System.out.println("check existing tables.... ");
            String[] types = {"TABLE"};
            DatabaseMetaData dbmd = conn.getMetaData();
            
            ResultSet rsDBMeta = dbmd.getTables(null, null, "%", types);//types);
            
          Statement stmt = conn.createStatement();
          
            //Statement dropStatement=null;
            
            while (rsDBMeta.next()) {
                String tableName = rsDBMeta.getString("TABLE_NAME");
                stmt.executeUpdate("DROP table " + tableName);
//                
//                if (tableName.compareToIgnoreCase(newTableName) == 0) {
//                    System.out.println(tableName + "  is there");
//                    flag = true;
                
            }
            rsDBMeta.close();
        } catch (SQLException ex) { return false;}
        return false;
}
    
    public void initialiseTables(){
        try{
        Statement stmt = conn.createStatement();
        
        //stmt.addBatch("CREATE TABLE USERS (USERNAME VARCHAR(10), PSWD VARCHAR(10))");
        stmt.executeUpdate("CREATE TABLE SETTINGS (SCREENWIDTH INT, SCREENHEIGHT INT, GAME_DIFFICULTY CHAR)");
        //need a time played date, high score, coins collected, highestScore
        stmt.executeUpdate("CREATE TABLE SCORE_INFO (HIGHESTSCORE INT, CURRENT_SCORE INT, COINS_COLLECTED INT, TIME_STAMP VARCHAR(20))");
    
        out.println("executed batch");
        

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    
    public void updateInfo(Data data){
        if (highestScore > data.currentScore){
            highestScore = data.currentScore;
        }
        
        Date date = new Date(data.timestamp);
        DateFormat fmt = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        String formated = fmt.format(date);

    
            myUpdate("INSERT INTO SCORE_INFO (" + highestScore + ", "+
                    data.currentScore + ", " + data.numCoinsCollected + 
                    ", " + formated + ")");
            
            if (!data.usePrevious){
                myUpdate("INSERT INTO SETTINGS (" + data.difficulty + ", " + 
                        data.screenWidth + ", " + data.screenHeight + ")");
                
            }
    }
   
    
    
    public boolean login(String name, String password){
        
            try{
                if (USER_NAME.equals(name) && PASSWORD.equals(password)){
                    conn = DriverManager.getConnection("jdbc:derby:GameDB;create=true", "pdc", "pdc");
                    return true;
                }
                else
                    return false;
               
            } catch (SQLException e){
                out.println("cannot create connection");
                
                return false;
            }
            
    }
}
    
            
       
class Data{
    public int currentScore,
            numCoinsCollected;
    
    long timestamp;
    //////////////////////////
    boolean usePrevious;
    String difficulty;
    int screenHeight, screenWidth;
    
    public String username;
    
    
}


class Preferences{
    private Dimension screenDim;
    private Color bgColour;
    private String diffLevel;
    
    public String getDifficulty(){
        return diffLevel;
    }
    public void setDifficulty(String d){
        diffLevel = d;
    }
    public String getDiffLevel() {
        return diffLevel;
    }

    public void setDiffLevel(String diffLevel) {
        this.diffLevel = diffLevel;
    }
    
    
    public Preferences(boolean useDefault){
        if (useDefault){
            screenDim = new Dimension(100, 50);
             bgColour =  Color.WHITE;
             
        }
    }
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
    
    public String toString(){
        return "" + screenDim;
        
    }
}


