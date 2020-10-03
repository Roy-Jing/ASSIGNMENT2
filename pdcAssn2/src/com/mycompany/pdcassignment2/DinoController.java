/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Roy
 */
public class DinoController implements KeyListener {

    private Dinosaur dino;
    private boolean isJumping = false;
    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void addDino(Dinosaur dino){
        this.dino = dino;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
        
        if (key == 'a'){
            dino.moveBackward();
            
        } else if (key == 'w'){
            dino.jump();
            
        } else if (key == 's'){
            dino.hunch();
            
        } else{
            dino.moveForward();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
