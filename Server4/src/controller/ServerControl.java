/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Message;
import model.User;

/**
 *
 * @author KyThuat88
 */
public class ServerControl implements Runnable {

    private Socket client;
    private UserDao userDao = new UserDao();
    private User user;
    private ServerControl opSc;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    ObjectOutputStream objos;
    private Message mesSend;

    public ServerControl(Socket client) {
        this.client = client;
        try {
            oos = new ObjectOutputStream(client.getOutputStream());
            ois = new ObjectInputStream(client.getInputStream());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
    //    User userReceive = new User();
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Object o = ois.readObject();
                
                System.out.println("Ket noi cmnr");
                if (o instanceof Message) {
                    Message request = (Message) o;
                    Object userReceive = request.getObject();
                    Message response = new Message();
                    
                    switch (request.getLabel()) {
                        case LOGIN:
                            User nuser = userDao.login((User)userReceive);
                            if (nuser != null) {
                                user = nuser;
                                response = new Message(user, Message.Label.LOGIN_SUCCESS);
                            } else {
                                response = new Message(user, Message.Label.LOGIN_FAIL);
                            }

                            oos.writeObject(response);
                            break;

                        case LOGOUT:
                            userDao.updateStatus((User)userReceive, "OFFLINE");
                            break;
                            
                        case REGISTER:
                            user = userDao.insertUser((User)userReceive);
                            if (user != null) {
                                
                                response = new Message(user, Message.Label.REGISTER_SUCCESS);
                            } else {
                                response = new Message(user, Message.Label.REGISTER_FAIL);
                            }
                            oos.writeObject(response);
                            break;
                            
                        case LIST_USERS:
                            ArrayList<User> listUserOnline = new ArrayList<>();
                            for (ServerControl sc : ServerThread.clients) {
                                if (sc.user != user) {
                                    listUserOnline.add(sc.user);
                                }
                            }
                            mesSend = new Message(listUserOnline, Message.Label.lIST_FULL);
                            oos.writeObject(mesSend);
                            break;
                        case CHALLENGE:
                            System.out.println(user.getName());
                            for (ServerControl sc : ServerThread.clients) {
                                if (sc.user.getId() == ((User)userReceive).getId()) {
                            //        System.out.println(sc.user.getName());
                                    mesSend = new Message(this.user, Message.Label.INVITE_USER);
                                    sc.oos.writeObject(mesSend);
                                }
                            }
                            break;
                        case REJECT_INVITE:
                           // User accountRecived = (User) obj;
                            for (ServerControl sc : ServerThread.clients) {
                                if (sc.user.getId() == ((User)userReceive).getId()) {
                                    Message mesSend = new Message(user, Message.Label.REJECT_INVITE);
                                    sc.oos.writeObject(mesSend);
                                    System.out.println("chuyen loi tu choi cua doi thu" + sc.user.getId());
                                }
                            }
                            break;

                        case ACCEPT_INVITE:
                            request = (Message) o;
                            User user1 = (User) request.getObject();
                            User user2 = this.user;
//                            int id1 = user.getId();
//                            int id2 = user2.getId();
//                            System.out.println(user2.getName() + " " + user.getName());
                            String str = "de bai";
                            for (ServerControl sc : ServerThread.clients) {
                                if (user1.getId() == sc.user.getId() || user2.getId() == sc.user.getId()) {
                                    System.out.println(this.oos + "    " +sc.oos);
                                    Message m = new Message(str, Message.Label.ACCEPT_INVITE); 
                                    
                                //    objos.writeObject(m);
                                    this.oos.writeObject(m);
                                    sc.oos.writeObject(m);                                                                      
                                }
                                
                            }
                            break;
                        case GET_SCOREBOARD:
                            ArrayList<User> RankingResUsers = userDao.Ranking();
                            System.out.println(RankingResUsers);
                            if (RankingResUsers != null) {
                                response = new Message(RankingResUsers, Message.Label.REPLY_SCOREBOARD);

                            }
                            oos.writeObject(response);
                            break;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
