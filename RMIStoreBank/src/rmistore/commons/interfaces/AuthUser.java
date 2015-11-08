/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmistore.commons.interfaces;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmistorebank.helper.CryptoHelper;

/**
 *
 * @author dx
 */
public class AuthUser {
    
    private String name;
    private String password;
    
    /**
     * Constructs a persistently named object.
     */
    public AuthUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public int login(String givenPassword) {
        try {
            String challengePassword = CryptoHelper.createPassword(givenPassword);
            
            if(challengePassword.equals(password)) {
                return 200;
            }
            else {
                return 500;
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AccountImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 500;
        }
    }
}
