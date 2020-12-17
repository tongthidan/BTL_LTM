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
import model.Game;
import model.Message;
import model.Turn;
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

    public void sendRespondUser(User user, Message mess) {
        for (ServerControl sc : ServerThread.clients) {
            if (sc.user.getId() == user.getId()) {
                try {
                    sc.oos.writeObject(mess);
                } catch (IOException ex) {
                    Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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
                            User nuser = userDao.login((User) userReceive);
                            if (nuser != null) {
                                user = nuser;
                                response = new Message(user, Message.Label.LOGIN_SUCCESS);
                            } else {
                                response = new Message(user, Message.Label.LOGIN_FAIL);
                            }

                            oos.writeObject(response);
                            break;

                        case LOGOUT:
                            userDao.updateStatus((User) userReceive, "OFFLINE");
                            break;

                        case REGISTER:
                            user = userDao.insertUser((User) userReceive);
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
                                if (sc.user.getId() == ((User) userReceive).getId()) {
                                    //        System.out.println(sc.user.getName());
                                    mesSend = new Message(this.user, Message.Label.INVITE_USER);
                                    sc.oos.writeObject(mesSend);
                                }
                            }
                            break;
                        case REJECT_INVITE:
                            // User accountRecived = (User) obj;
                            for (ServerControl sc : ServerThread.clients) {
                                if (sc.user.getId() == ((User) userReceive).getId()) {
                                    Message mesSend = new Message(user, Message.Label.REJECT_INVITE);
                                    sc.oos.writeObject(mesSend);
                                    System.out.println("chuyen loi tu choi cua doi thu" + sc.user.getId());
                                }
                            }
                            break;

                        case ACCEPT_INVITE:
                            GameDao gameDao = new GameDao();
                            Game g = new Game();
                            Game game = gameDao.insertGame(g);

                            request = (Message) o;
                            User user1 = (User) request.getObject();
                            User user2 = this.user;

                            for (ServerControl sc : ServerThread.clients) {
                                if (user1.getId() == sc.user.getId() || user2.getId() == sc.user.getId()) {
                                    System.out.println(this.oos + "    " + sc.oos);
                                    Message m = new Message(game, Message.Label.ACCEPT_INVITE);

                                    this.oos.writeObject(m);
                                    sc.oos.writeObject(m);
                                }

                            }
                            break;
                        case GET_SCOREBOARD:
//                            User rankUser = (User) obj;
                            ArrayList<User> RankingResUsers = userDao.Ranking();
                            System.out.println(RankingResUsers);
                            if (RankingResUsers != null) {
                                response = new Message(RankingResUsers, Message.Label.REPLY_SCOREBOARD);
                                //System.out.println("Da tra ve Ranking");
                            }
                            oos.writeObject(response);
                            break;

                        case GAME:
                            Turn turn = (Turn) userReceive;
                            User user = turn.getUser();
                            Game newgame = turn.getGame();
                            int time = turn.getTime();

                            TurnDao turnDao = new TurnDao();
                            turnDao.insertTurn(turn);

                            ArrayList<Turn> list = new ArrayList<>();
                            list = turnDao.selectTurnByGameId(newgame);

                            Message win = new Message(null, Message.Label.USER_WIN);
                            Message lose = new Message(null, Message.Label.USER_LOSE);
                            Message draw = new Message(null, Message.Label.USER_DRAW);

                            if (list.size() == 2) {
                                Turn turn1 = list.get(0);
                                Turn turn2 = list.get(1);

                                UserDao udao = new UserDao();
                                if (turn1.getTime() > turn2.getTime()) {
                                    udao.updatePoinWinUser(turn1.getUser());
                                    sendRespondUser(turn1.getUser(), win);
                                    sendRespondUser(turn2.getUser(), lose);

                                } else if (turn1.getTime() < turn2.getTime()) {
                                    udao.updatePoinWinUser(turn2.getUser());
                                    sendRespondUser(turn1.getUser(), lose);
                                    sendRespondUser(turn2.getUser(), win);
                                } else {
                                    udao.updatePointDrawUser(turn1.getUser());
                                    udao.updatePointDrawUser(turn2.getUser());
                                    sendRespondUser(turn1.getUser(), draw);
                                    sendRespondUser(turn2.getUser(), draw);
                                }
                            }

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
