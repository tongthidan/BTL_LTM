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
import javax.security.auth.spi.LoginModule;
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
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Object o = ois.readObject();
                System.out.println("Ket noi cmnr");
                if (o instanceof Message) {
                    Message request = (Message) o;
                    Object obj = request.getObject();

                    Message response = null; 
                    System.out.println(((Message) o).getLabel());
                    switch (request.getLabel()) {
                        case LOGIN:
                            User mUser = (User) obj;
                            Boolean result = userDao.login(mUser);
                            user = mUser;
                            if (result) {
                                response = new Message(user, Message.Label.LOGIN_SUCCESS);
                                
                            } else {
                                response = new Message(user, Message.Label.LOGIN_FAIL);
                            }

                            oos.writeObject(response); 
                            break;
                        
                        case LOGOUT:
                            userDao.updateStatus(user, "OFFLINE");
                            break;
                        case REGISTER:                            
                            User nUser = (User) obj;
                            boolean resultz = userDao.insertUser(nUser);
                            user = nUser;
                            if (resultz) {
                                response = new Message(user, Message.Label.REGISTER_SUCCESS);
                                
                            } else {
                                response = new Message(user, Message.Label.REGISTER_FAIL);
                            }

                            oos.writeObject(response); 
                            break;
                            
                        case LIST_USERS:
                            User userOnline = (User) obj;
                            ArrayList<User> resultUser = userDao.listUserOnline();
                           // System.out.println(resultUser);
                            if(resultUser != null){
                                response = new Message(resultUser, Message.Label.lIST_FULL);
                                
                            }
                            else{
                                response = new Message(resultUser, Message.Label.LIST_NULL);
                            }
                            oos.writeObject(response);
                            break; 
                           case CHALLENGE:
                               User userReceive = (User) obj;
                               System.out.println(userReceive.getName());
                               for (ServerControl sc : ServerThread.clients) {
                                   System.out.println(sc.user.getName() +"\n");
                                if (sc.user.getId() == userReceive.getId()) {
                                    System.out.println(sc.user.getName());
                                    Message mesSend = new Message(user, Message.Label.INVITE_USER);
                                    sc.oos.writeObject(mesSend);
                                    System.out.println("da chuyen loi moi cho doi thu " + userReceive.getName());
                                }
                                else{
                                    System.out.println("Khong tim thay ");
                                }
                                if (sc.user.getId() == userReceive.getId()
                                        && sc.user.getOnline().equals("BUSY")) {
                                     Message mesSend = new Message(userReceive, Message.Label.PLAYING);
                                    oos.writeObject(mesSend);
                                }
                            }
                            break;
                            case REJECT_INVITE:
                                User accountRecived = (User) obj;
                            for (ServerControl sc : ServerThread.clients) {
                                if (sc.user.getId() == accountRecived.getId()) {
                                     Message mesSend = new Message(user, Message.Label.REJECT_INVITE);
                                    sc.oos.writeObject(mesSend);
                                    System.out.println("chuyen loi tu choi cua doi thu" + sc.user.getId());
                                }
                            }
//                            mesSend = new Message(account, Type.REJECT_CHALLENGE);
//                            oos.writeObject(mesSend);
                            break;


//                        case INVITE_USER:
//                            User user1 = (User) obj;
//                            int id = user1.getId();
//                            for(ServerControl sc: ServerThread.clients){
//                                    if(id==sc.user.getId()){
//                                        opSc = sc;
//                                        opSc.opSc = this;
//                                            objos = sc.oos;
//                                        sc.objos = oos;
//                                        Message m = new Message(user.getId(), Message.Label.INVITE_USER);
//                                        objos.writeObject(m);
//                                        System.out.println("da gui cho client 2");
//                                    }
//                                }
//                        case ACCEPT_INVITE:
//                            User user2 = (User)obj;
//                            int id1 = user.getId();
//                            int id2 = user2.getId();
//                            String str = "de bai";
//                            for(ServerControl sc: ServerThread.clients){
//                                if(id1==sc.user.getId() || id2==sc.user.getId()){
//                                        opSc = sc;
//                                        opSc.opSc = this;
//                                            objos = sc.oos;
//                                        sc.objos = oos;
//                                        Message m = new Message(str, Message.Label.ACCEPT_INVITE);
//                                        objos.writeObject(m);
//                                        
//                                    }
//                            }
                             
                        case GET_SCOREBOARD:
                            User rankUser = (User) obj;
                            ArrayList<User> RankingResUsers = userDao.listRanking();
                            System.out.println(RankingResUsers);
                            if(RankingResUsers != null){
                                response = new Message(RankingResUsers, Message.Label.REPLY_SCOREBOARD);   
                                //System.out.println("Da tra ve Ranking");
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
