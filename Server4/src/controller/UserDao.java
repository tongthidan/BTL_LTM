/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Message;
import model.User;

/**
 *
 * @author khanh
 */
public class UserDao extends Dao {

    public UserDao() {
        super();
    }

    public User login(User user) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {                
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setName(rs.getString("name"));
                user.setPoint(rs.getInt("point"));
                user.setOnline("ONLINE");
                return user;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public void updateStatus(User user, String status) {
        String query = "UPDATE users SET status = ? WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, status);
            ps.setInt(2, user.getId());
            ps.executeUpdate();
            user.setOnline(status);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public User insertUser(User user) {
        String query = "INSERT INTO users(username, password, name, point, status) VALUE (?,?,?,?,?)";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setInt(4, 0);
            ps.setString(5,"OFFLINE");

            int result = ps.executeUpdate();
            if (result == 1) {
                return user;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null;

    }

    public ArrayList<User> listUserOnline() {
        ArrayList<User> listUserOnline = new ArrayList<>();
        String query = "SELECT * FROM users WHERE status = 'ONLINE'";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String name = rs.getString("name");
                int point = rs.getInt("point");
                String status = rs.getString("status");
                User userOnline = new User(id, username, name, point, status);
                listUserOnline.add(userOnline);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listUserOnline;
    }

    public ArrayList<User> Ranking() {
        ArrayList<User> listRanking = new ArrayList<>();
        try {
            PreparedStatement pre = conn.prepareStatement("select * from users;");
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                User user = new User();
                 user.setId(rs.getInt("id"));
              
                user.setUsername(rs.getString("username"));
                user.setName(rs.getString("name"));
                user.setPoint(rs.getInt("point"));
                 listRanking.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  listRanking;
        }
    
    public void updatePointDrawUser(User user){
        
    }
    
    public void updatePoinWinUser(User user){
        
    }

    
    
    
   
}
