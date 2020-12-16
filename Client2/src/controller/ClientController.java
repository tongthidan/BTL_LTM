/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import View.LoginView;
import View.RankingView;
import View.RegisterView;
import View.UserOnlineView;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Message;
import static model.Message.Label.ACCEPT_INVITE;
import static model.Message.Label.REJECT_INVITE;
import model.User;
import puzzle.puzzle;

/**
 *
 * @author KyThuat88
 */
public class ClientController {

    private Socket mySocket;
    //private String serverHost = "192.168.43.67";
    // private String serverHost = "192.168.43.252";
    private String serverHost = "192.168.1.248";
   // private String serverHost = "172.20.10.2";
 //  private String serverHost = "localhost";
    private int serverPort = 8000;
    ObjectInputStream ois;
    private static ObjectOutputStream oos;
    private User userCurrent;
    private User userReceive;
    
    Runnable listenChallenge;
    static Thread thread;
    private LoginView loginView;
    private RegisterView registerView;
    static UserOnlineView userOnlineView;
    static RankingView rankingView;
    
    public ClientController(){
        openConnection();
        receiveData();
    }

    public void setLoginView(LoginView loginView, RegisterView registerView) {
        this.loginView = loginView;
        this.registerView = registerView;
    }
    
    public void openConnection() {
        try {
            mySocket = new Socket(serverHost, serverPort);
            ois = new ObjectInputStream(mySocket.getInputStream());
            oos = new ObjectOutputStream(mySocket.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean sendData(Object ob) {
        try {
            oos.writeObject(ob);
        } catch (IOException ex) {
            Logger.getLogger(ClientController.class
                    .getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public void receiveData() {
        listenChallenge = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Message result = null;
                    try {
                        Message message = (Message) ois.readObject();
                        System.out.println("Da nhan data " + message.getLabel());
                        switch (message.getLabel()) {
                            case LOGIN_SUCCESS:
                                userCurrent = (User) message.getObject();
                                userOnlineView = new UserOnlineView();
                                userOnlineView.setVisible(true);
                                sendData(new Message(null, Message.Label.LIST_USERS));
                                break;
                            case LOGIN_FAIL:
                                loginView.showMessage("Invalid username or password!");
                                break;
                            case REGISTER_SUCCESS:
                                userCurrent = (User) message.getObject();
                               registerView.showMessage("Register successful");
                                loginView = new LoginView();
                                loginView.setVisible(true);
                                 break;

                            case REGISTER_FAIL:
                                registerView.showMessage("Register fail");
                                break;
                            case LOGOUT:
//                            return "LOGOUT";
                                break;
                            case lIST_FULL:
                                userOnlineView.setTable((ArrayList<User>) message.getObject());
                                break;
                            case REPLY_SCOREBOARD:
                                rankingView = new RankingView();
                                rankingView.setVisible(true);
                               rankingView.setTable((ArrayList<User>) message.getObject());
                                break;
                            case INVITE_USER:
                                userReceive = (User) message.getObject();
                                int isAccept = userOnlineView.showConfirmDialog(userReceive.getName() + " want to challege you in a game");
                                if (isAccept == JOptionPane.YES_OPTION) {
                                    Message response = new Message(userReceive, ACCEPT_INVITE);
                                    sendData(response);
                                } else {
                                    Message response = new Message(userReceive, REJECT_INVITE);
                                    sendData(response);
                                }
                                break;
                            case REJECT_INVITE:
                                userReceive = (User) message.getObject();
                                userOnlineView.showMessage(userReceive.getName() + " dont want to challege you in a game");
                                break;

                            case ACCEPT_INVITE:
                                puzzle p =  new puzzle();
                                p.setTime(); 
                                break;
                                
                            

                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ClientController.class
                                .getName()).log(Level.SEVERE, null, ex);

                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ClientController.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        thread = new Thread(listenChallenge);
        thread.start();
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


