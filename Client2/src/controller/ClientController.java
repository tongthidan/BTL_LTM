/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import View.GameView;
import View.LoginView;
import View.RankingView;
import View.StartView;
import View.UserOnlineView;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Message;
import model.User;

/**
 *
 * @author KyThuat88
 */
public class ClientController {

    private Socket mySocket;
    //private String serverHost = "192.168.43.67";
    // private String serverHost = "192.168.43.252";
//      private String serverHost = "172.20.10.3";
     private  String serverHost = "localhost";
    private int serverPort = 8888;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    static  UserOnlineView userOnlineView;
           

    public ClientController() {
    }

    public Socket openConnection() {
        try {            
            mySocket = new Socket(serverHost, serverPort);
            ois = new ObjectInputStream(mySocket.getInputStream());
//            System.out.println("new ois");
            oos = new ObjectOutputStream(mySocket.getOutputStream());
//            System.out.println("new oos");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return mySocket;
    }

    public boolean sendData(Object ob) {
        try {
            oos.writeObject(ob);

        } catch (IOException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public Object receiveData() {
        Object result = null;
        try {
            Message o = (Message) ois.readObject();
//            System.out.println(o.getLabel());
            System.out.println("Da nhan data " + o.getLabel());
            switch (o.getLabel()) {
                case LOGIN_SUCCESS:                     
                      return "LOGIN_SUCCESS";

                case LOGIN_FAIL:
                      return "LOGIN_FAIL";
                   
                case REGISTER_SUCCESS:
                    return "REGISTER_SUCCESS";
                   
                case REGISTER_FAIL:
                     return "REGISTER_FAIL";
                case LOGOUT:
                    return "LOGOUT";
                case LIST_USERS:
                   Message listUser = o;
                   return  listUser;
                case lIST_FULL:
                    Message listUserOnline = o;
                    return listUserOnline;
                case REPLY_SCOREBOARD:
                    System.out.println("Case ranking ");
                    Message listRanking = o;
                    return listRanking;
                    
                case INVITE_USER:
                    User userReceive = (User) o.getObject();
                    System.out.println("Da  nhan duoc yeu cau");
                    int isAccept = userOnlineView.showConfirmDialog(userReceive.getName() + " want to challege you in a game");
                                if (isAccept == JOptionPane.YES_OPTION) {
                                    Message response = new Message(userReceive, Message.Label.ACCEPT_INVITE);
                                    System.out.println("accept");
                                    sendData(response);
                                } else {
                                    Message response = new Message(userReceive, Message.Label.REJECT_INVITE);
                                    System.out.println("Reject");
                                    sendData(response);
                                }
                                break;
                case REJECT_INVITE:
                                User userRUser = (User) o.getObject();
                                userOnlineView.showMessage(userRUser.getName() + " dont want to challege you in a game");
                                break;
                            case PLAYING:
                                 User userX = (User) o.getObject();
                                userOnlineView.showMessage(userX.getName() + " playing a game with someone else!");
                                break;
                    
//                    System.out.println("Da nhan loi moi tu clent 1");
//                    Message inviteMessage = o;
//                    User user = (User)inviteMessage.getObject();
//                    Boolean accept = (JOptionPane.showConfirmDialog(null, 
//                                user.getName() + " want to challege you in a chess game") 
//                                == JOptionPane.YES_OPTION);
//                    if(accept){
//                            Message mess = new Message(user, Message.Label.ACCEPT_INVITE);
//                            sendData(mess);
//                            }
//                    else{
//                        Message mess = new Message(user, Message.Label. REJECT_INVITE);
//                            sendData(mess);
//                            }
                
                case ACCEPT_INVITE:
                    Message message = o;
                    System.out.println(o.getObject().toString());
            }

        } catch (IOException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return result;

    }

    public boolean closeConnection() {
        try {
            mySocket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
