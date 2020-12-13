/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author khanh
 */
public class User implements Serializable, Comparable<User> {
    private static final long serialVersionUID = 6529685098267757691L;
    private int id;
    private String username;
    private String password;
    private String name;
    private String online;
    private int point;
    private int count;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int id, String username, String password, String name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }
    

    public User(int id, String username, String name, int point, String online) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.point = point;
        this.online =online;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object[] toObject() {
        return new Object[]{id, username, name, point,online};
    }

    public Object[] toObjectR() {
        return new Object[]{count, username, name, point,online};
    }
    @Override
   public int compareTo(User o){
       if(point == o.point){
           return 0;
       }
       else if(point > o.point){
           return 1;
       }
       else{
           return -1;
       }
   }
}