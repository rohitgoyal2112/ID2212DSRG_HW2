package rmistore.commons.interfaces;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import rmistorebank.helper.CryptoHelper;


@SuppressWarnings("serial")
public class AuthenticationImpl extends UnicastRemoteObject implements Authentication {
    private String bankName;
    private String user = "Someone";
    private Map<String, AuthUser> accounts = new HashMap<>();

    /**
     * Diffie Hellman
     */
    private int bitLength = 1024;
    private SecureRandom rnd = new SecureRandom();
    private BigInteger g, p, b;
    
    /**
     * AES
     */
    private BigInteger s = null;

    public AuthenticationImpl(String bankName) throws RemoteException {
        //        super();
        this.bankName = bankName;
        
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            
            Connection con = DriverManager.getConnection(
                    "jdbc:derby://localhost:1527/Authentication", 
                    "authentication", "authentication");
            
            Statement stmt = con.createStatement();
        
            ResultSet rs = stmt.executeQuery("SELECT * FROM AUTHENTICATION.AUTHUSER");
            while (rs.next()) {
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                
                accounts.put(username, new AuthUser(username, password));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AuthenticationImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AuthenticationImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int login(String username, String password) throws RemoteException {
        
        username = CryptoHelper.AESDecrypt(username, s);
        password = CryptoHelper.AESDecrypt(password, s);
        
        this.user = username;
        System.out.println(user + " is trying to login!");
        
        AuthUser account = accounts.get(username);
        if(account == null) {
            return 500;
        }
        int loginStatus = account.login(password);
        if(loginStatus == 200) {
            System.out.println("[" + user + "] Login Successful!");
        }
        else {
            System.out.println("[" + user + "] Login Failed!");            
        }
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(AuthenticationImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return loginStatus;
    }
    
    @Override
    public String print(String filename, String printer) {
        if(checkIsAuthenticated()) {
            filename = CryptoHelper.AESDecrypt(filename, s);
            printer = CryptoHelper.AESDecrypt(printer, s);
            
            System.out.println(user + " is printing file: " + filename + 
                " on Printer: " + printer);
            
            return CryptoHelper.AESEncrypt("200", s);
        }
        else {
            System.out.println("Someone is trying to 'print' without "
                    + "authentication!");
            
            if(s == null) {
                return "500";
            }
            else {
                return CryptoHelper.AESEncrypt("500", s);
            }
        }
    }

    @Override
    public String queue() {        
        if(checkIsAuthenticated()) {
            
            System.out.println(user + " wants to know current queue!");
            
            return CryptoHelper.AESEncrypt("200", s);
        }
        else {
            System.out.println("Someone is trying to 'queue' without "
                    + "authentication!");
            
            if(s == null) {
                return "500";
            }
            else {
                return CryptoHelper.AESEncrypt("500", s);
            }
        }
    }

    @Override
    public String topQueue(String job) {
        if(checkIsAuthenticated()) {
            job = CryptoHelper.AESDecrypt(job, s);
            Integer intJob = Integer.parseInt(job);
            
            System.out.println(user + " wants to move job " + intJob + " to the top "
                + "of the queue!");
            
            return CryptoHelper.AESEncrypt("200", s);
        }
        else {
            System.out.println("Someone is trying to 'topQueue' without "
                    + "authentication!");
            
            if(s == null) {
                return "500";
            }
            else {
                return CryptoHelper.AESEncrypt("500", s);
            }
        }
    }

    @Override
    public String start() {
        if(checkIsAuthenticated()) {
            
            System.out.println(user + " wants to start the printer server!");
            
            return CryptoHelper.AESEncrypt("200", s);
        }
        else {
            System.out.println("Someone is trying to 'start' without "
                    + "authentication!");
            
            if(s == null) {
                return "500";
            }
            else {
                return CryptoHelper.AESEncrypt("500", s);
            }
        }
    }

    @Override
    public String stop() {
        if(checkIsAuthenticated()) {
            
            System.out.println(user + " wants to stop the printer server!");
            
            return CryptoHelper.AESEncrypt("200", s);
        }
        else {
            System.out.println("Someone is trying to 'stop' without "
                    + "authentication!");
            
            if(s == null) {
                return "500";
            }
            else {
                return CryptoHelper.AESEncrypt("500", s);
            }
        }
    }

    @Override
    public String restart() {
        if(checkIsAuthenticated()) {
            
            System.out.println(user + " wants to restart the printer server!");
            
            return CryptoHelper.AESEncrypt("200", s);
        }
        else {
            System.out.println("Someone is trying to 'restart' without "
                    + "authentication!");
            
            if(s == null) {
                return "500";
            }
            else {
                return CryptoHelper.AESEncrypt("500", s);
            }
        }
    }

    @Override
    public String status() {
        if(checkIsAuthenticated()) {
            
            System.out.println(user + " wants to know the printer status!");
            
            return CryptoHelper.AESEncrypt("200", s);
        }
        else {
            System.out.println("Someone is trying to 'status' without "
                    + "authentication!");
            
            if(s == null) {
                return "500";
            }
            else {
                return CryptoHelper.AESEncrypt("500", s);
            }
        }
    }

    @Override
    public String readConfig(String parameter) {
        if(checkIsAuthenticated()) {
            parameter = CryptoHelper.AESDecrypt(parameter, s);
            
            System.out.println(user + " tries to access parameter [" + parameter 
                + "]");
            
            return CryptoHelper.AESEncrypt("200", s);
        }
        else {
            System.out.println("Someone is trying to 'status' without "
                    + "authentication!");
            
            if(s == null) {
                return "500";
            }
            else {
                return CryptoHelper.AESEncrypt("500", s);
            }
        }
    }

    @Override
    public String setConfig(String parameter, String value) {
        if(checkIsAuthenticated()) {
            parameter = CryptoHelper.AESDecrypt(parameter, s);
            value = CryptoHelper.AESDecrypt(value, s);
            
            System.out.println(user + " tries to change parameter [" + parameter 
                + "] to [" + value + "]");
            
            return CryptoHelper.AESEncrypt("200", s);
        }
        else {
            System.out.println("Someone is trying to 'status' without "
                    + "authentication!");
            
            if(s == null) {
                return "500";
            }
            else {
                return CryptoHelper.AESEncrypt("500", s);
            }
        }
    }

    @Override
    public int publicValueExchange(BigInteger p, BigInteger g) throws RemoteException {
        this.p = p;
        this.g = g;
        
        return 200;
    }
    
    @Override
    public String keyExchange(String clientValue) throws RemoteException {
        String decryptedClientValue = CryptoHelper.RSADecrypt(clientValue,
                CryptoHelper.privKey);
        BigInteger biClientValue = new BigInteger(decryptedClientValue);
        b = new BigInteger(bitLength, rnd);
        BigInteger B = g.modPow(b, p);
        s = biClientValue.modPow(b, p);
        String encryptedResponse = CryptoHelper.RSAEncrypt(B.toString(),
                CryptoHelper.privKey);
        return encryptedResponse;
    }

    private boolean checkIsAuthenticated() {
        return user != "Someone";
    }
}
