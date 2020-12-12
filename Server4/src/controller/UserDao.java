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
public class UserDao extends Dao{

    public UserDao() {
        super();
    }
    
    public Boolean login(User user){
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                user.setId(rs.getInt("id"));
             //   user.setOnline("ONLINE");
                updateStatus(user, "ONLINE");
                return true;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return false;
    }
    
    public void updateStatus(User user, String status){
        String query = "UPDATE users SET status = ? WHERE id = ?";
        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, status);
            ps.setInt(2, user.getId());
            ps.executeUpdate();
            user.setOnline(status);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public boolean insertUser(User user){
        String query = "INSERT INTO users(username, password, name, point, status) VALUE (?,?,?,?,?)";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setInt(4, 0);
            ps.setString(5, "ONLINE");
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
//    public ArrayList<String> listPlayer(){
//        ArrayList<String> players = new ArrayList<>();
//        String query = "SELECT username FROM users WHERE status = 'OFFLINE'";
//        try{
//            PreparedStatement ps = conn.prepareStatement(query);
//            ResultSet rs = ps.executeQuery();
//            while(rs.next()){
//                players.add(rs.getString("username"));
//            }
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }
//        
//        return players;
//    }
     public ArrayList<User> listUserOnline(){
        ArrayList<User> listUserOnline  = new ArrayList<>();
        String query = "SELECT * FROM users WHERE status = 'ONLINE'";
        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String name = rs.getString("name");          
                int point = rs.getInt("point");
                 String status = rs.getString("status");
                User userOnline = new User(id,username,name,point,status);
                listUserOnline.add(userOnline);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return listUserOnline;
    }
       public ArrayList<User> listRanking(){
        ArrayList<User> listRanking  = new ArrayList<>();
        String query = "SELECT * FROM users";
        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String name = rs.getString("name");          
                int point = rs.getInt("point");
                
                User listRUser = new User(id,username,name,point);
                listRanking.add(listRUser);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return listRanking;
    }
    
}
