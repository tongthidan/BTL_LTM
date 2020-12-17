/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.AncestorListener;

public class puzzle extends JFrame implements ActionListener, Runnable {

    JButton b1, b2, b3, b4, b5, b6, b7, b8, b9;
    JLabel time;
    public int clock = 60;
    public boolean running = true;
    public puzzle() {
        super("puzzle game");

        b1 = new JButton("1");
        b2 = new JButton(" ");
        b3 = new JButton("3");
        b4 = new JButton("4");
        b5 = new JButton("5");
        b6 = new JButton("6");
        b7 = new JButton("7");
        b8 = new JButton("8");
        b9 = new JButton("2");
        time = new JLabel(clock + "");
        //next = new JButton("next");

        b1.setBounds(10, 30, 50, 40);
        b2.setBounds(70, 30, 50, 40);
        b3.setBounds(130, 30, 50, 40);
        b4.setBounds(10, 80, 50, 40);
        b5.setBounds(70, 80, 50, 40);
        b6.setBounds(130, 80, 50, 40);
        b7.setBounds(10, 130, 50, 40);
        b8.setBounds(70, 130, 50, 40);
        b9.setBounds(130, 130, 50, 40);
        time.setBounds(70, 200, 100, 40);

        add(b1);
        add(b2);
        add(b3);
        add(b4);
        add(b5);
        add(b6);
        add(b7);
        add(b8);
        add(b9);
        add(time);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
        b8.addActionListener(this);
        b9.addActionListener(this);
        //    time.addAncestorListener();

        time.setBackground(Color.black);
        time.setForeground(Color.black);
        setSize(250, 300);
        setLayout(null);
        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }//end of constuctor
    
    public void setTime(){
        Thread t = new Thread(){
            @Override
            public void run() {
               while(clock >0){                  
                   try {
                       clock -= 1;
                        time.setText(clock+"");
                       sleep(1000);
                   } catch (InterruptedException ex) {
                       Logger.getLogger(puzzle.class.getName()).log(Level.SEVERE, null, ex);
                   }
               }
            }
            
        };
        t.start();
    }

    public void actionPerformed(ActionEvent e) {

        if (clock > 0) {
            if (e.getSource() == b1) {
                String s = b1.getLabel();
                if (b2.getLabel().equals(" ")) {
                    b2.setLabel(s);
                    b1.setLabel(" ");
                } else if (b4.getLabel().equals(" ")) {
                    b4.setLabel(s);
                    b1.setLabel(" ");
                }
            }//end of if

            if (e.getSource() == b3) {
                String s = b3.getLabel();
                if (b2.getLabel().equals(" ")) {
                    b2.setLabel(s);
                    b3.setLabel(" ");
                } else if (b6.getLabel().equals(" ")) {
                    b6.setLabel(s);
                    b3.setLabel(" ");
                }
            }//end of if

            if (e.getSource() == b2) {
                String s = b2.getLabel();
                if (b1.getLabel().equals(" ")) {
                    b1.setLabel(s);
                    b2.setLabel(" ");
                } else if (b3.getLabel().equals(" ")) {
                    b3.setLabel(s);
                    b2.setLabel(" ");
                } else if (b5.getLabel().equals(" ")) {
                    b5.setLabel(s);
                    b2.setLabel(" ");
                }
            }//end of if

            if (e.getSource() == b4) {
                String s = b4.getLabel();
                if (b1.getLabel().equals(" ")) {
                    b1.setLabel(s);
                    b4.setLabel(" ");
                } else if (b7.getLabel().equals(" ")) {
                    b7.setLabel(s);
                    b4.setLabel(" ");
                } else if (b5.getLabel().equals(" ")) {
                    b5.setLabel(s);
                    b4.setLabel(" ");
                }
            }//end of if

            if (e.getSource() == b5) {
                String s = b5.getLabel();
                if (b2.getLabel().equals(" ")) {
                    b2.setLabel(s);
                    b5.setLabel(" ");
                } else if (b4.getLabel().equals(" ")) {
                    b4.setLabel(s);
                    b5.setLabel(" ");
                } else if (b6.getLabel().equals(" ")) {
                    b6.setLabel(s);
                    b5.setLabel(" ");
                } else if (b8.getLabel().equals(" ")) {
                    b8.setLabel(s);
                    b5.setLabel(" ");
                }
            }//end of if

            if (e.getSource() == b6) {

                String s = b6.getLabel();
                if (b9.getLabel().equals(" ")) {
                    b9.setLabel(s);
                    b6.setLabel(" ");
                } else if (b3.getLabel().equals(" ")) {
                    b3.setLabel(s);
                    b6.setLabel(" ");
                } else if (b5.getLabel().equals(" ")) {
                    b5.setLabel(s);
                    b6.setLabel(" ");
                }

            }//end of if

            if (e.getSource() == b7) {
                String s = b7.getLabel();
                if (b4.getLabel().equals(" ")) {
                    b4.setLabel(s);
                    b7.setLabel(" ");
                } else if (b8.getLabel().equals(" ")) {
                    b8.setLabel(s);
                    b7.setLabel(" ");
                }

            }//end of if

            if (e.getSource() == b8) {
                String s = b8.getLabel();
                if (b7.getLabel().equals(" ")) {
                    b7.setLabel(s);
                    b8.setLabel(" ");
                } else if (b9.getLabel().equals(" ")) {
                    b9.setLabel(s);
                    b8.setLabel(" ");
                } else if (b5.getLabel().equals(" ")) {
                    b5.setLabel(s);
                    b8.setLabel(" ");
                }

            }//end of if

            if (e.getSource() == b9) {
                String s = b9.getLabel();
                if (b6.getLabel().equals(" ")) {
                    b6.setLabel(s);
                    b9.setLabel(" ");//deprecated
                } else if (b8.getLabel().equals(" ")) {
                    b8.setLabel(s);
                    b9.setLabel(" ");
                }
                if (b1.getLabel().equals("1") && b2.getLabel().equals("2") && b3.getLabel().equals("3") && b4.getLabel().equals("4") && b5.getLabel().equals("5") && b6.getLabel().equals("6") && b7.getLabel().equals("7") && b8.getLabel().equals("8") && b9.getLabel().equals(" ")) {
                    JOptionPane.showMessageDialog(puzzle.this, "!!! Finish !!!");
                    running = false;
                    return;
                    //return clock;
                }
            }//end of if
        } 
//        else {
//            running = false;
//            JOptionPane.showMessageDialog(puzzle.this, "!!! Het gio !!!");
//            return;
//        }

        if(clock <=0){
            running = false;
            return;
        }

    }//end of actionPerformed

    public static void main(String[] args) {
        puzzle p =  new puzzle();
        p.setTime();
    }//end of main

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

}//end of class

    