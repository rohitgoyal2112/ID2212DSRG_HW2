/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmistorebank;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmistorebank.helper.CryptoHelper;

/**
 *
 * @author dx
 */
public class AuthUserInit extends Thread {
    
    private Connection con;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new AuthUserInit(args).run();
    }

    private AuthUserInit(String[] args) {
        try {
            // Load driver
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            
            con = DriverManager.getConnection(
                    "jdbc:derby://localhost:1527/Authentication", 
                    "authentication", "authentication");
            
            try {
                String dummyUsername = "David";
                String dummyRawPassword = "hello";
                String storedDummyPassword = CryptoHelper.createPassword(
                        dummyRawPassword);
                createUser(dummyUsername, storedDummyPassword);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(AuthUserInit.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AuthUserInit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createUser(String username, String password) throws SQLException {
        Statement stmt = con.createStatement();
        
        int rs = stmt.executeUpdate("INSERT INTO AUTHENTICATION.AUTHUSER "
                + "(username, password) VALUES ('" + username + "','" 
                + password + "')");
    }
    
}
