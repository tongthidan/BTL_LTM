/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Game;

/**
 *
 * @author KyThuat88
 */
public class GameDao extends Dao{
    public GameDao(){
        super();
    }    
    
    public Game insertGame(Game g){
        String query = "INSER INTO game() VALUES ()";
        PreparedStatement ps;
        try {    
            ps = conn.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GameDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }
}
