/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import controller.ClientController;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Message;
import model.User;

/**
 *
 * @author Admin
 */
public class UserOnlineView extends javax.swing.JFrame {

    private static ArrayList<User> listUserOnline = new ArrayList<>();
    static ArrayList<User> listRanking = new ArrayList<>();
    DefaultTableModel tableModel;
    static UserOnlineView userOnlineView;

    public UserOnlineView() {
        initComponents();
        tableModel = (DefaultTableModel) tblUserOnline.getModel();
    }

    public void setTable(ArrayList<User> listUserOnline) {
        this.listUserOnline = listUserOnline;
        tableModel.setRowCount(0);
        for (User i : listUserOnline) {
            tableModel.addRow(i.toObject());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblUserOnline = new javax.swing.JTable();
        UserOnline_btnInvite = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        UserOnline_btnRanking = new javax.swing.JButton();
        UserOnline_Refresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        tblUserOnline.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "User Name", "FullName", "Point", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblUserOnline);

        UserOnline_btnInvite.setText("Invite");
        UserOnline_btnInvite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserOnline_btnInviteActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("MATCH PHOTO GAME ");

        UserOnline_btnRanking.setText("Ranking");
        UserOnline_btnRanking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserOnline_btnRankingActionPerformed(evt);
            }
        });

        UserOnline_Refresh.setText("Refresh");
        UserOnline_Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserOnline_RefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(UserOnline_btnInvite, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addComponent(UserOnline_btnRanking))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(40, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(UserOnline_Refresh)
                .addGap(77, 77, 77))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(16, 16, 16)
                .addComponent(UserOnline_Refresh)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UserOnline_btnInvite)
                    .addComponent(UserOnline_btnRanking))
                .addGap(62, 62, 62))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void UserOnline_btnInviteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserOnline_btnInviteActionPerformed
        int row = tblUserOnline.getSelectedRow();
        int id = (int) tblUserOnline.getValueAt(row, 0);
        String name = tblUserOnline.getValueAt(row, 2).toString();
        int point = (int) tblUserOnline.getValueAt(row, 3);
        for (User acc : listUserOnline) {
            if (acc.getName().equals(name) && acc.getPoint() == point) {
                Message message = new Message(acc, Message.Label.CHALLENGE);
                ClientController.sendData(message);
                System.out.println("Client 1 da gui yeu cau choi toi " + acc.getName());
            }
        }
    }//GEN-LAST:event_UserOnline_btnInviteActionPerformed

    private void UserOnline_btnRankingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserOnline_btnRankingActionPerformed
        Message messageSendRank = new Message(null, Message.Label.GET_SCOREBOARD);
        ClientController.sendData(messageSendRank);
//        Message messageReceiRanking = (Message) LoginView.clientController.receiveData();
//        listRanking = (ArrayList<User>) messageReceiRanking.getObject();
//        if (messageReceiRanking.getLabel().toString() == "REPLY_SCOREBOARD") {
//            RankingView rankingView = new RankingView();
//            rankingView.setVisible(true);
//        } else {
//            System.out.println("Ranking view fail");
//        }
    }//GEN-LAST:event_UserOnline_btnRankingActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        Message message = new Message(null, Message.Label.LOGOUT);
        ClientController.sendData(message);
//        Message messageLog = (Message) LoginView.clientController.receiveData();
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    private void UserOnline_RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserOnline_RefreshActionPerformed
//        tblUserOnline.removeAll();
        Message message = new Message(null, Message.Label.LIST_USERS);
        ClientController.sendData(message);
    }//GEN-LAST:event_UserOnline_RefreshActionPerformed
    public void getMessage(Object object) {

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton UserOnline_Refresh;
    private javax.swing.JButton UserOnline_btnInvite;
    private javax.swing.JButton UserOnline_btnRanking;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblUserOnline;
    // End of variables declaration//GEN-END:variables
}
