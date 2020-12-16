/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game;

import static com.game.gamepanel.bg;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

/**
 *
 * @author Administrator
 */
public class manhinh extends JFrame {

     public static int SCREEN_WiDTH = 1000;
    public static int SCREEN_HEIGHT = 600;
    gamepanel game;
    public static JFrame jf1;
    BufferedImage bg;

    public manhinh() throws IOException {
         bg=ImageIO.read(manhinh.class.getResource("/images/main.jpg"));
        jf1 = new JFrame("Game Ghép Tranh");
        jf1.setIconImage(bg);
        Toolkit toolkit = jf1.getToolkit();
        Dimension dimension = toolkit.getScreenSize();

        jf1.setBounds((dimension.width - SCREEN_WiDTH) / 2, (dimension.height - SCREEN_HEIGHT) / 2, SCREEN_WiDTH, SCREEN_HEIGHT);
        jf1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf1.setVisible(true);
        jf1.setResizable(false);
        
        game = new gamepanel();
   
        jf1.add(game);
        
        jf1.addKeyListener(game);
        jf1.addMouseListener(game);
        jf1.addMouseMotionListener(game);

        while (true) {
            if (gamepanel.giays == 0) {
                jf1.setVisible(false);
                winner thua = new winner();
                break;
            }
            if (gamepanel.giay < 0 && game.winner(gamepanel.ida, gamepanel.idd) == false) {
                
               
                    gamepanel.giays--;
                
                
            }
            game.repaint();
             if(gamepanel.play==1)
                {
            gamepanel.giay--;
                }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("lỗi" + ex.getMessage());
            }

        }

    }

    public static void main(String[] args) throws IOException {
            manhinh mh = new manhinh();

    }
}
