/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author KyThuat88
 */
public class Turn implements Serializable{
    private int id;
    private int time;
    private Game game;
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Turn() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Turn(int id, int time, Game game, User user) {
        this.id = id;
        this.time = time;
        this.game = game;
        this.user = user;
    }

    public Turn(int time, Game game, User user) {
        this.time = time;
        this.game = game;
        this.user = user;
    }

    
    
}
