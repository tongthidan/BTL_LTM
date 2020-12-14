/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import controller.ClientController;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Message;
import model.User;

/**
 *
 * @author Admin
 */
public class LoginView extends javax.swing.JFrame {

    static ArrayList<User> listUserOnline = new ArrayList<>();
    static ArrayList<User> listRanking = new ArrayList<>();
    static UserOnlineView userOnlineView;

    public LoginView() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        login_txtusername = new javax.swing.JTextField();
        login_txtpass = new javax.swing.JTextField();
        login_btnLogin = new javax.swing.JButton();
        login_btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Candara", 1, 24)); // NOI18N
        jLabel1.setText("Please Login");

        jLabel2.setText("Username");

        jLabel3.setText("Password");

        login_txtusername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                login_txtusernameActionPerformed(evt);
            }
        });

        login_btnLogin.setBackground(new java.awt.Color(153, 153, 255));
        login_btnLogin.setText("Login");
        login_btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                login_btnLoginActionPerformed(evt);
            }
        });

        login_btnCancel.setBackground(new java.awt.Color(255, 51, 102));
        login_btnCancel.setText("Cancel");
        login_btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                login_btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(login_btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86)
                        .addComponent(login_btnCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(66, 66, 66)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(login_txtpass, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(login_txtusername, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(128, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(login_txtusername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(login_txtpass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 71, 71)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(login_btnLogin)
                    .addComponent(login_btnCancel))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void login_txtusernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_login_txtusernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_login_txtusernameActionPerformed

    private void login_btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_login_btnLoginActionPerformed
        String username = login_txtusername.getText();
        String password = login_txtpass.getText();

        User user = new User(username, password);
        Message mess = new Message(user, Message.Label.LOGIN);
        ClientController.sendData(mess);

//        Message message = new Message(user, Message.Label.LIST_USERS);
//        LoginView.clientController.sendData(message);
//        Message listUserMessage = (Message) LoginView.clientController.receiveData();
//        if(listUserMessage.getLabel().toString()== "LIST_USERS"){
//            UserOnlineView userOnlineView = Us
//        }
        this.dispose();

    }//GEN-LAST:event_login_btnLoginActionPerformed
    public void getMessage(Object object) {
        String result = (String) object;
        if (result.equals("LOGIN_SUCCESS")) {
            UserOnlineView userOnlineView = new UserOnlineView();
            userOnlineView.setVisible(true);
        } else if (result.equals("LOGIN_FAIL")) {
            JOptionPane.showMessageDialog(this, "Invalid username or password!");
        }
        this.dispose();
    }
    private void login_btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_login_btnCancelActionPerformed
        // TODO add your handling code here:

        StartView startView = new StartView();
        startView.setVisible(true);
    }//GEN-LAST:event_login_btnCancelActionPerformed
    public void showMessage(String smg) {
        JOptionPane.showMessageDialog(this, smg);
    }

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton login_btnCancel;
    private javax.swing.JButton login_btnLogin;
    private javax.swing.JTextField login_txtpass;
    private javax.swing.JTextField login_txtusername;
    // End of variables declaration//GEN-END:variables
}
