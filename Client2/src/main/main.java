/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import View.LoginView;
import controller.ClientController;

/**
 *
 * @author KyThuat88
 */
public class main {

    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        loginView.setVisible(true);
        ClientController clientController = new ClientController();
        clientController.setLoginView(loginView);
    }    
}
