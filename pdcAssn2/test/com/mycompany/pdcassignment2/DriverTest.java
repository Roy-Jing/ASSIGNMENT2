/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Roy
 */
public class DriverTest {
    
    private Connection conn;
    public DriverTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws SQLException {
        
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        try {
            conn = DriverManager.getConnection("jdbc:derby:PlayerDB;create=true", "pdc", "pdc");
        } catch (SQLException ex) {
            fail("Set up failed");
        }

        
    }
    
    @After
    public void tearDown() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DriverTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Test
    public void testInitialisingDatabase() {
        try {
            out.println("setting up database");
            DatabaseModel dbM = new DatabaseModel();
            dbM.reset();
            
            dbM.initialiseTables();
            DatabaseMetaData data = conn.getMetaData();
            
            Statement stmt = conn.createStatement();
            
            boolean usersExist = dbM.checkTableExisting("USERS");
            boolean settingsExist = dbM.checkTableExisting("SETTINGS");
            
            assertEquals(true, usersExist && settingsExist);
        } catch (SQLException ex) {
            Logger.getLogger(DriverTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    @Test
    public void testSavingPreferencesToDatabase(){
        
            DatabaseModel dbM = new DatabaseModel();
            dbM.currentUsername = "John Doe";
            
            dbM.savePreferences(new Preferences(100, 100, "img1.jpg", "Easy"));
            LinkedHashSet<String> prefSet = dbM.loadPreviousPrefsOf();
            
            LinkedHashSet<String> expectedPrefSet = new LinkedHashSet();
            expectedPrefSet.add("100 x 100|Easy|img1.jpg|John Doe");
            
            assertEquals(expectedPrefSet, prefSet);
            

        
    }
    
    @Test
    public void testCollideableCollisionHandler(){
        GameModel.setFrameHeight(1000); GameModel.setFrameWidth(1000);

        Dinosaur d = new Dinosaur(new MoveableObject("Dino"));
      
        CollisionHandler.addDino(d);
        Cloud cloud = new Cloud(new MoveableObject("Cloud"));
        
         CollideableCollisionHandler handler = new CollideableCollisionHandler(cloud);
        cloud.setCollisionHandler(handler);
        GameModel model = new GameModel();
        model.setInterrupted(false);
        cloud.addModel(model);
        cloud.setCoordX(100);
        cloud.setCoordY(100);
        d.setCoordX(100);
        d.setCoordY(96);
        
        d.setVelocityY(5);
        d.isJumping(true);
        cloud.doRun();
       
        boolean collide = handler.checkForCollision();
        assertEquals(collide, true);
    }
    
    
}
