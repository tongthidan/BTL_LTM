/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
public class gamepanel extends JPanel implements KeyListener,MouseListener,MouseMotionListener{
    protected static BufferedImage images[][];
    protected static BufferedImage imagesmain, busua, bg,logo;
    private static int rdi, rdj;
    private static int i, j, dem = 0, tti = 0, ttj = 0;
    public static int[][] idd = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
    public static int[][] ida = {{2, 1, 0}, {5, 4, 3}, {8, 7, 6}};
    protected static BufferedImage imagestg[][];
    private static int tmp, tg, tg2;
    public static int phut = 1, giay = 30, giays = 60;
    public static int play = 0;
    Button button;
    private boolean kiemtraclick=false;
    winner winner;
    private int x = 445, y = 100;
 private Thread thread;

    public gamepanel() throws IOException
    {
        int dem=0;
      
      
        Button.bgColorud=Color.GREEN;
       button=new RectangleButton("PLAY", x, y, 100, 50, 22, 28, Color.green);
        try{
            images = new BufferedImage[3][3];
            logo=ImageIO.read(gamepanel.class.getResource("/images/logo.png"));
           busua=ImageIO.read(gamepanel.class.getResource("/images/ngu.png"));
              bg=ImageIO.read(gamepanel.class.getResource("/images/main.jpg"));
            imagesmain = ImageIO.read(gamepanel.class.getResource("/images/gamedoremon.jpg"));
            images[0][0] = imagesmain.getSubimage(0, 0, 180, 150);
            images[0][1] = imagesmain.getSubimage(180, 0, 180, 150);
            images[0][2] = imagesmain.getSubimage(360, 0, 200, 150);
            images[1][0] = imagesmain.getSubimage(0, 150, 180, 150);
            images[1][1] = imagesmain.getSubimage(180, 150, 180, 150);
            images[1][2] = imagesmain.getSubimage(360, 150, 200, 150);
            images[2][0] = imagesmain.getSubimage(0, 300, 180, 100);
//            images[2][1] = imagesmain.getSubimage(180, 300, 180, 100);
            images[2][2] = imagesmain.getSubimage(360, 300, 200, 100);
          
        }catch(IOException e)
        {
            System.out.println("lỗi:"+e.getMessage());
        }
        
    }
    
    public void paint(Graphics g)
    {
        g.setColor(Color.pink);
        g.fillRect(0, 0, 1000, 600);
       
        int x=0,y=0,weight=200,height=200;
       
    
        
        for(i=0;i<3;i++)
        {
           x=0;
            for(j=2;j>=0;j--)
            {
                
          
              
                g.drawImage(images[i][j], x, y, weight,height,null);
                
                 x+=201;
               
            }
     
            y+=201;
        }
        g.setColor(Color.red);
        g.setFont(new Font("TimesRoman", Font.BOLD, 15));
        g.drawString("Hình Ban Đầu:", 770,230);
          g.setFont(new Font("TimesRoman", Font.BOLD, 30));
      g.drawImage(imagesmain, 680, 250,300,300,null);
      if(giay>=0)
      {
          g.drawImage(busua, 680, 150,110,100 ,null);
           g.drawString(""+phut+":"+giay, 780, 200);
      }
      else if(giay<0)
      {
          g.drawImage(busua, 680, 150,110,100 ,null);
            g.drawString(""+giays, 780, 200);
      }
       if(play==0)
        {
        g.drawImage(bg, 0,0,1000,600, null);
        Button.color=Color.RED;
        button.draw(g);
        g.setColor(Color.magenta);
        g.setFont(new Font("son", Font.BOLD, 40));
        g.drawString("TRÒ ", 260,120);
        g.drawString("CHƠI ", 340,85);
        g.drawString("GHÉP ", 550,85);
        g.drawString("TRANH ", 660,120);
        g.drawImage(logo, 448, 12,105,90,null);

        


        }
        System.out.printf("\nmảng id hiện tại:\n");   
    for(i=0;i<3;i++)
    {
        for(j=0;j<3;j++)
        {
        System.out.printf("%d ",ida[i][j]);
        }
           System.out.println(""); 
    }

    }

public boolean winner(int [][]a,int [][]b)
{
    for(i=0;i<3;i++)
    {
        for(j=0;j<3;j++)
        {
        if(a[i][j]!=b[i][j]) return false;
        }
    }
    return true;
}
public int chuyendoi(int cma )
{
    if(cma==0)
    {
        return 2;
    }
    else if(cma==2) return 1;
    else return 0;
}
    public boolean kiemtra(BufferedImage a)
    {
        if(a==null)
        {
            return true;
        }
        else return false;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
        {
            play=1;
            repaint();
    
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN && play==1)
        { dem=0;
        for( i=1;i<3;i++)
        {
            for(j=0;j<3;j++)
            {
                if(kiemtra(images[i][j])==true )
                {
                     if(i!=0)
                     {
                    images[i][j] = null;
                    images[i][j] = images[i - 1][j];
                    images[i - 1][j] = null;
                  if(i!=2)
                  {
                    if(i==1 && j==0)
                    {
                    tmp=ida[0][2];
                    ida[0][2]=ida[1][2];
                    ida[1][2]=tmp;
                    }
                       if(i==1 && j==1)
                       {
                             tmp=ida[0][1];
                    ida[0][1]=ida[1][1];
                    ida[1][1]=tmp;
                       }
                        if(i==1 && j==2)
                       {
                             tmp=ida[1][0];
                    ida[1][0]=ida[0][0];
                    ida[0][0]=tmp;
                       }
                  }
                  else if(i==2)
                  {
                     if( j==0)
                    {
                    tmp=ida[1][2];
                    ida[1][2]=ida[2][2];
                    ida[2][2]=tmp;
                    }
                       if( j==1)
                       {
                             tmp=ida[1][1];
                    ida[1][1]=ida[2][1];
                    ida[2][1]=tmp;
                       }
                        if(j==2)
                       {
                           tmp = ida[1][0];
                           ida[1][0] = ida[2][0];
                           ida[2][0] = tmp;
                  }
                      
                       }
                    dem=1;
                    break;
                }
            }
            }
            if(dem==1) break;
        }
        repaint();
        }
        if(e.getKeyCode()==KeyEvent.VK_UP  && play==1)
        { dem=0;
        for( i=0;i<2;i++)
        {
            for(j=0;j<3;j++)
            {
                if(kiemtra(images[i][j])==true)
                {
                   if(i!=2)
                   {
                    
                    images[i][j]=null;
                    images[i][j]=images[i+1][j];
                    images[i+1][j]=null;
                    if(i!=1)
                    {
                        if(i==0 && j==0)
                        {
                     tmp=ida[1][2];
                    ida[1][2]=ida[0][2];
                    ida[0][2]=tmp;
                        }
                        else if(i==0 && j==1)
                        {
                             tmp = ida[0][1];
                            ida[0][1] = ida[1][1];
                            ida[1][1] = tmp;
                        }
                        else if(i==0 && j==2)
                        {
                             tmp = ida[0][0];
                            ida[0][0] = ida[1][0];
                            ida[1][0] = tmp;
                        }
                    
                    }
                    else if(i==1)
                    {
                        if(j==0)
                        {
                            tmp = ida[1][2];
                            ida[1][2] = ida[2][2];
                            ida[2][2] = tmp;
                        }
                        else if(j==1)
                        {
                             tmp = ida[1][1];
                            ida[1][1] = ida[2][1];
                            ida[2][1] = tmp;
                        }
                          else if(j==2)
                        {
                             tmp = ida[1][0];
                            ida[1][0] = ida[2][0];
                            ida[2][0] = tmp;
                        }   
                    }
                    
                    
                   }

                    dem=1;
                    break;
                }
            }
            if(dem==1) break;
        }
        repaint();
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT  && play==1)
        { dem=0;
        for( i=0;i<3;i++)
        {
            for(j=0;j<3;j++)
            {
                
                if(kiemtra(images[i][j])==true )
                {
                 
                    
                    images[i][j]=null;
                    images[i][j]=images[i][j+1];
                    images[i][j+1]=null;
                  
                    if(j!=0)
                    { 
                        tmp=ida[i][j];
                ida[i][j]=ida[i][j-1];
                ida[i][j-1]=tmp;
                    }
                    if(j==0)
                    {
                       tmp=ida[i][2];
                       ida[i][2]=ida[i][1];
                       ida[i][1]=tmp;
                    }
                
                 
                    
                    dem=1;
                    break;
                
                }
            }
            if(dem==1) break;
        }
        repaint();
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT  && play==1)
        { dem=0;
        for( i=0;i<3;i++)
        {
           
            for(j=0;j<3;j++)
            {
                if(kiemtra(images[i][j])==true)
                {
                   
                    images[i][j]=null;
                    images[i][j]=images[i][j-1];
                    images[i][j-1]=null;
                    
                      if(j!=2)
                    { 
                        tmp=ida[i][j];
                ida[i][j]=ida[i][j+1];
                ida[i][j+1]=tmp;
                    }
                    if(j==2)
                    {
                       tmp=ida[i][0];
                       ida[i][0]=ida[i][1];
                       ida[i][1]=tmp;
                    }
                    dem=1;
                    break;
                }
            }
            if(dem==1) break;
        }
        repaint();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
       if(winner(ida, idd))
       { 
           manhinh.jf1.setVisible(false);
         winner=new winner();
       
       }
      
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("");

    }

    @Override
    public void mousePressed(MouseEvent e) { 
        
        System.out.println("");
          }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(play==0 && e.getX()>=x && e.getX()<=x+100 &&e.getY()>=y && e.getY()<=y+80)
        {
            play=1;
            repaint();
        }
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("vào");    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("ra");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if( e.getX()>=x && e.getX()<=x+100 &&e.getY()>=y && e.getY()<=y+80)
        {
       
           Button.bgColorud=Color.ORANGE;
           repaint();
       }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
        if( e.getX()>=x && e.getX()<=x+100 &&e.getY()>=y && e.getY()<=y+80)
        {
            Button.bgColorud=Color.yellow;
            
            repaint();
        }
        else
        {
            Button.bgColorud=Color.GREEN;
            repaint();
        }
        
    }
 
}

