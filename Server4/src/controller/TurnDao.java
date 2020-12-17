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
import model.Game;
import model.Turn;
import model.User;

/**
 *
 * @author KyThuat88
 */
public class TurnDao extends Dao{
    public TurnDao(){
        super();
    }
    
    public Turn insertTurn(Turn turn){
        String query = "INSER INTO game_puzzle(iduser, idgame, time) VALUES (?, ?,?)";
        PreparedStatement ps;
        try {                       
            ps = conn.prepareStatement(query);
            ps.setInt(1, turn.getUser().getId());
            ps.setInt(2, turn.getGame().getId());
            ps.setInt(3, turn.getTime());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GameDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return turn;
    }
    
    public ArrayList<Turn> selectTurnByGameId(Game game){
        ArrayList<Turn> list = new ArrayList<>();
        String query = "SELECT * FROM game_puzzle WHERE idgame = ?";
        
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, game.getId());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int iduser = rs.getInt("iduser");
                int idgame = rs.getInt("idgame");
                int time = rs.getInt("time");
                
                User u = new User(iduser);
                Game g = new Game(idgame);
                
                Turn t = new Turn(id, time, g, u);
                list.add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TurnDao.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        return list;
    }
}
