package com.game;



import com.game.manhinh;
import static com.game.manhinh.SCREEN_HEIGHT;
import static com.game.manhinh.SCREEN_WiDTH;
import static com.game.manhinh.jf1;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class winner extends JFrame{
    
    
    public winner()
    {
        Toolkit toolkit=this.getToolkit();
          Dimension dimension = toolkit.getScreenSize();

      this.setBounds((dimension.width - SCREEN_WiDTH) / 2, (dimension.height - SCREEN_HEIGHT) / 2, SCREEN_WiDTH, SCREEN_HEIGHT);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setVisible(true); 
    }
    public void paint(Graphics g)
    {
     g.setColor(Color.pink);
     g.fillRect(0, 0, manhinh.SCREEN_WiDTH, manhinh.SCREEN_HEIGHT);
     g.setColor(Color.red);
     if(gamepanel.giays>0)
     {
     g.setFont(new Font("TimesRoman", Font.BOLD, 80));
     g.drawString("Bạn Đã Thắng", 150, 300);
     }
     else if( gamepanel.giays==0)
     {
           g.setFont(new Font("TimesRoman", Font.BOLD, 80));
          g.drawString("Bạn Đã Thua", 150, 300);
     }
    }
    
    
}
