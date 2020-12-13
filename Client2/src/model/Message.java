/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

public class Message implements Serializable{
    private static final long serialVersionUID = 6529685098267757691L;
    
    private Object object;
    private Label label;

    public Message() {
    }

    public Message(Object object, Label label) {
        this.object = object;
        this.label = label;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
     public static enum Label {
        //login,register
        LOGIN,LOGOUT,
        LOGIN_SUCCESS,
        LOGIN_FAIL,
        REGISTER,
        REGISTER_FAIL,
        REGISTER_SUCCESS,
        //yeu cau choi game
        REQUEST_PLAY,
        ACCEPT_REQUEST,
        DENY_REQUEST,
        //choi game
        START_GAME,
        SEND_CHOICE,
        REPLY_RESULT,
        
        //hien thi danh sach nguoi choi online 
        LIST_USERS,// danh sach nguoi choi online
        lIST_FULL,// co phan tu trong danh sach
        LIST_NULL,// khong co phan tu trong danh sach 
        //Ranking
        GET_SCOREBOARD,
        REPLY_SCOREBOARD,
        //moi
        INVITE_USER
    }
}
