/*
 * The programs are designed for PDC paper
 */
package com.mycompany.pdcassignment2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FilenameFilter;
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
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

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
    
    
    public void reset(){
        try {
            ResultSet rs;
            DatabaseMetaData meta = conn.getMetaData();
            Statement stmt = conn.createStatement();
            
            rs = meta.getTables(null, null, null, new String[]{"TABLE"}); 
            while (rs.next()){
                
                 out.println(rs.getString("TABLE_NAME"));
                
                stmt.executeUpdate("DROP TABLE " + rs.getString("TABLE_NAME"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public DatabaseModel() {
        
        establishConnection();
        if (!this.checkTableExisting("SETTINGS")){
            initialiseTables();
            firstTime = true;
        }
    }
    
   

    public Connection getConnection() {
        return this.conn;
    }

    //Establish connection
    public void establishConnection() {
        if (this.conn == null) {
            try {
                
                conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            
                
            } catch (SQLException ex) {
                    ex.printStackTrace();
            }
        }
    }

    public void savePreferences(Preferences pref){
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO SETTINGS VALUES ( ?, ?, ?, ?, ?)");
            ps.setInt(1, pref.screenDim.width );
            ps.setInt(2, pref.screenDim.height);
            
            ps.setString(3, pref.diffLevel);
            ps.setString(4, pref.bgImage + "");
            ps.setString(5, this.currentUsername);
            
            out.println("pref inserted");
            
            ps.executeUpdate();
            
         } catch (SQLException ex) {
            Logger.getLogger(DatabaseModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
  
    public void closeConnections() {
        if (conn != null) {
            try {
                
                conn.close();
            } catch (SQLException ex) {
                out.println("cannot close connection");
                
            }
        }
    }
    
    public boolean checkForDuplicate(String username){
    
        try {
            ResultSet rs =  myQuery("SELECT * FROM USERS WHERE USERNAME= " + "'" + username + "'");
            
            
            return  rs.next();
        } catch (SQLException ex) {
             out.println("error checking dup");
            
        }
        
        return true;
        
            
    }
    
    public ResultSet myQuery(String sql) {

        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

        } catch (SQLException e) {
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
    
   
    public LinkedHashSet<String> loadPreviousPrefsOf(){
        LinkedHashSet<String> prefs = new LinkedHashSet();
   
        try {            
            ResultSet rs;
            
            
            int i = 0;
            
            rs = myQuery("SELECT * FROM SETTINGS WHERE USERNAME = '" + currentUsername + "'");
            while (rs.next()){
                
                int screenWidth = rs.getInt("SCREENWIDTH");
                int screenHeight = rs.getInt("SCREENHEIGHT");
                String diffLevel = rs.getString("GAME_DIFFICULTY");
                String imgName = rs.getString("IMG_NAME");
                
                prefs.add("" + screenWidth + " x " + screenHeight + "|" + diffLevel + "|" + imgName + "|" + currentUsername);
                
            }
        } catch (SQLException ex) {
             out.println("Error getting prefs!");
        }
        
        return prefs;
        
    }
    
    public boolean createUser(String name, String pswd){
       
        try {
            currentUsername = name;
            
            Statement stmt = conn.createStatement();
            ResultSet rs;
            rs = myQuery("SELECT * FROM USERS");
//            rs.last();
            // (rs.getRow());
//            while (rs.next()){
//                 (rs.getString("USERNAME") + rs.getString("PSWD"));
//                
//            }
            
            if (!myQuery("SELECT * FROM USERS WHERE USERNAME= '" + name + "'").next()){
                stmt.executeUpdate("INSERT INTO USERS VALUES ('" + name + "', '" + pswd + "')");
                 out.println("inserted");
            } else{
                 out.println("duplicate user");
                
                return false;
            }
            
            return true;
            
        } catch (SQLException ex) {
            return false;
        }
    }
    
    
    public boolean checkTableExisting(String newTableName) {
        boolean flag = false;
        try {

            String[] types = {"TABLE"};
            DatabaseMetaData dbmd = conn.getMetaData();
            
            try (ResultSet rsDBMeta = dbmd.getTables(null, null, null, types) //types);
            ) {
                Statement stmt = conn.createStatement();
                
                //Statement dropStatement=null;
                
                while (rsDBMeta.next()) {
                    String tableName = rsDBMeta.getString("TABLE_NAME");
                    //stmt.executeUpdate("DROP table " + tableName);
//
                    if (tableName.compareToIgnoreCase(newTableName) == 0) {
                        flag = true;
                    }

                }
            }
        } catch (SQLException ex) { return false;}
        return flag;
}
    
    String currentUsername;
    
    public void initialiseTables(){
        try{
        Statement stmt = conn.createStatement();
        
        stmt.executeUpdate("CREATE TABLE USERS (USERNAME VARCHAR(10), PSWD VARCHAR(10))");
        stmt.executeUpdate("CREATE TABLE SETTINGS (SCREENWIDTH INT, SCREENHEIGHT INT, GAME_DIFFICULTY VARCHAR(10), IMG_NAME VARCHAR(10), USERNAME VARCHAR(10))");
        

        //need a time played date, high score, coins collected, highestScore
        stmt.executeUpdate("CREATE TABLE SCORE_INFO (HIGHESTSCORE INT, CURRENT_SCORE INT, COINS_COLLECTED INT, TIME_STAMP VARCHAR(20),  USERNAME VARCHAR(20))");
    
        

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
            
//    public void savePreferences(Preferences pref){
//        
//        int screenW = pref.screenDim.width;
//        int screenH = pref.screenDim.height;
//        String diffLevel = pref.diffLevel;
//        String bgImage = pref.bgImage.toString();
//    
//            
//           
//            myUpdate("INSERT INTO SETTINGS (" + 
//                        screenW + ", " + screenH + diffLevel + bgImage + ")");
//            
//            
//    
//    
//            
//    }
   
    public boolean verifyCredentials(String name, String password) throws SQLException{
        ResultSet rs;
        String s = null;
        try{
         rs = myQuery("SELECT * FROM USERS");
       
        
            
        //rs.beforeFirst();
         while(rs.next()){
             out.println(rs.getString("USERNAME"));
             out.println(rs.getString("PSWD"));
            
        }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    
        if ((rs = myQuery("SELECT * FROM USERS WHERE USERNAME = '" + name + "' AND PSWD = '" + password + "'")).next()){
            
           
           
            s = rs.getString("USERNAME")  + rs.getString("PSWD");
    
            return true;
        }
        return false;
        
    }
    
    
    public boolean login(String name, String password){
        
            try{
                    
                    conn = DriverManager.getConnection("jdbc:derby:GameDB;create=true", "pdc", "pdc");
                    if (this.verifyCredentials(name, password)){
                        currentUsername = name;
                        
                        return true;
                    }
                  
            } catch (SQLException e){
                
                return false;
            }
            
            return false;
    }

    public String getCurrentUsername() {
        return currentUsername;
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
    public Dimension screenDim;
    public ImageIcon bgImage;
    public String diffLevel;
    
    Preferences(int dimWidth, int dimHeight, String imgName, String diffLevel){
        screenDim = new Dimension(dimWidth, dimHeight);
        bgImage = new ImageIcon(imgName);
        this.diffLevel = diffLevel;
    }
    Preferences(String dimString, String imgName, String diffLevel){
        String dimensions[] = dimString.split("x");
        
        screenDim = new Dimension(Integer.parseInt(dimensions[0].trim()), Integer.parseInt(dimensions[1].trim()));
        bgImage = new ImageIcon(imgName);
        this.diffLevel = diffLevel;
    }
    
    Preferences(int dimWidth, int dimHeight, ImageIcon imgName, String diffLevel){
        screenDim = new Dimension(dimWidth, dimHeight);
        bgImage = imgName;
        //Toolkit.getDefaultToolkit().getImage(imgName);
        this.diffLevel = diffLevel;
    }
    
    public String unpack(){
        String displayString = screenDim.width + ", " + screenDim.height + "|" + bgImage + "|" + diffLevel;
        return displayString;
        
    }
    
    public static Preferences pack(String prefString){
        
        StringTokenizer tokenizer = new StringTokenizer(prefString, "\\||x");
        
        int width =  Integer.parseInt(tokenizer.nextToken().trim());
        int height = Integer.parseInt(tokenizer.nextToken().trim());
        
        
        String diffLevel = tokenizer.nextToken();
        
        String imgName = tokenizer.nextToken();
        
        return new Preferences(width, height, imgName, diffLevel);
        
    }
    
    public Preferences(){
        
        
        screenDim = new Dimension(100, 50);
        bgImage = getDefaultImg();
        diffLevel = "1";
        
    }
    
    public ImageIcon getDefaultImg(){
        File homeDir = new File("src");
        File[] images = homeDir.listFiles(new FilenameFilter(){
            public boolean accept(File dir, String name){
                if (name.endsWith(".jpg"))
                    return true;
                return false;
                
            }
        });
       
        String randImageName = images[new Random().nextInt(images.length)].getName();
        
        return new ImageIcon(randImageName);
        
        
               
        
        
    }
    public String toString(){
        return "" + screenDim;
        
    }
}


