/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pdcassignment2;

/**
 *
 * @author Roy
 */
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TestGame {

    public static void main(String[] args) {
        new TestGame();
    }

    public TestGame() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame();
                JPanel panel = new JPanel();
                panel.setLayout(null);

                final int FRAME_WIDTH = 800;
                final int FRAME_HEIGHT = 600;

                frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
                frame.setTitle("Move the Ball");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                final WallComponent wc1 = new WallComponent(400, 400);
                final BallComponent bc = new BallComponent(400, 300);
                panel.add(wc1);
                panel.add(bc);
                frame.add(panel);

                KeyboardController kc = new KeyboardController(bc);
                frame.addKeyListener(kc);

                frame.setVisible(true);

                class AnimationListener implements ActionListener {

                    public void actionPerformed(ActionEvent event) {
                        bc.tick();
                        wc1.checkOverlap(bc);
                    }
                }

                ActionListener aListener = new AnimationListener();

                final Timer timer = new Timer(200, aListener);
                timer.start();

            }
        });
    }

    public class KeyboardController implements KeyListener {

        BallComponent bComp;

        public KeyboardController(BallComponent t) {
            bComp = t;
        }

        /**
         * Handle the key pressed event from the text field.
         */
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == 38) {
                System.out.println("Pressed Up!");
                bComp.moveUp();
            }
            if (keyCode == 37) {
                System.out.println("Pressed Left!");
                bComp.moveLeft();
            }
            if (keyCode == 39) {
                System.out.println("Pressed Right!");
                bComp.moveRight();
            }
            if (keyCode == 40) {
                System.out.println("Pressed Down!");
                bComp.moveDown();
            }
        }

        /**
         * Handle the key released event from the text field.
         */
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == 38) {
                System.out.println("Released Up!");
                bComp.stopY();
            }
            if (keyCode == 37) {
                System.out.println("Released Left!");
                bComp.stopX();
            }
            if (keyCode == 39) {
                System.out.println("Released Right!");
                bComp.stopX();
            }
            if (keyCode == 40) {
                System.out.println("Pressed Down!");
                bComp.stopY();
            }
        }

        public void keyTyped(KeyEvent e) {
        }

    }

    public class BallComponent extends JComponent {

        int xSpeed;
        int ySpeed;

        public BallComponent(int x, int y) {
            super();
            setBounds(x, y, 10, 10);
        }
        Ellipse2D.Double ball = new Ellipse2D.Double(0, 0, 9, 9);

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(Color.RED);
            g2.fill(ball);
            g2.draw(ball);
        }

        public void moveLeft() {
            xSpeed = -1;
        }

        public void moveRight() {
            xSpeed = 1;
        }

        public void moveUp() {
            ySpeed = -1;
        }

        public void moveDown() {
            ySpeed = 1;
        }

        public void tick() {
            int x = getX() + xSpeed;
            int y = getY() + ySpeed;

            setLocation(x, y);

            repaint();
        }

        public void stopY() {
            ySpeed = 0;
        }

        public void stopX() {
            xSpeed = 0;
        }

    }

    public class WallComponent extends JComponent {

        public WallComponent(int x, int y) {
            super();
            setBounds(x, y, 40, 40);
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            Rectangle wall = new Rectangle(0, 0, 40, 40);
            g2.setColor(Color.YELLOW);
            g2.fill(wall);
            g2.draw(wall);
        }

        public void checkOverlap(BallComponent bc) {

            System.out.println(" me: " + getBounds());
            System.out.println("you: " + bc.getBounds());

            if (getBounds().intersects(bc.getBounds())) {
                bc.stopY();
                bc.stopX();
            }
        }
    }
}
