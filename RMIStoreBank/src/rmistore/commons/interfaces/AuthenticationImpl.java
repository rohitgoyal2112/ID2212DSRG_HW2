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
    
    private byte[] aesKey;

    public AuthenticationImpl(String bankName) throws RemoteException {
        //        super();
        this.bankName = bankName;
        
        try {
            String dummyUsername = "David";
            String dummyRawPassword = "hello";
            String storedDummyPassword = CryptoHelper.createPassword(dummyRawPassword);
            AuthUser dummyAccount = new AuthUser(dummyUsername, storedDummyPassword);
            
            accounts.put(dummyUsername, dummyAccount);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AuthenticationImpl.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Cannot create dummy user!");
        }
    }

    @Override
    public int login(String username, String password) throws RemoteException {
        
        username = decrypt(username);
        password = decrypt(password);
        
        this.user = username;
        System.out.println(user + " is trying to login!");
        
        AuthUser account = accounts.get(username);
        int loginStatus = account.login(password);
        if(loginStatus == 200) {
            System.out.println("[" + user + "] Login Successful!");
        }
        else {
            System.out.println("[" + user + "] Login Failed!");            
        }
        
        return loginStatus;
    }
    
    @Override
    public void print(String filename, String printer) {
        System.out.println(user + " is printing file: " + filename + 
                " on Printer: " + printer);
    }

    @Override
    public void queue() {
        System.out.println(user + " wants to know current queue!");
    }

    @Override
    public void topQueue(int job) {
        System.out.println(user + " wants to move job " + job + " to the top "
                + "of the queue!");
    }

    @Override
    public void start() {
        System.out.println(user + " wants to start the printer server!");
    }

    @Override
    public void stop() {
        System.out.println(user + " wants to stop the printer server!");
    }

    @Override
    public void restart() {
        System.out.println(user + " wants to restart the printer server!");
    }

    @Override
    public void status() {
        System.out.println(user + " wants to know the printer status!");
    }

    @Override
    public void readConfig(String parameter) {
        System.out.println(user + " tries to access parameter [" + parameter 
                + "]");
    }

    @Override
    public void setConfig(String parameter, String value) {
        System.out.println(user + " tries to change parameter [" + parameter 
                + "] to [" + value + "]");
    }

    @Override
    public BigInteger keyExchange(BigInteger publicValue) throws RemoteException {
        try {
            int bitLength = 1024;
            SecureRandom rnd = new SecureRandom();
            BigInteger p = new BigInteger("115481693128219746027292404981969544601997551324282642958753451061578742568604732285229496133000193574054800397370858217239146430682139426970820864276322564054320996503716368604641809196544929212678114449080765683010828171658052171878283443796436206652582424500418377898296330521820418327775939717214641622813");
            BigInteger g = new BigInteger("130061477972544545413085647006475706101463355894791236593049191588006481360609853881647773411707401104636320439963372218249619404282485368443895767482232388747183738433715051827984361088798758894404563139527415286956932285744248967716619452302501549290497201518217605225203869832405927553034454336207873427691");
            
            BigInteger b = new BigInteger(bitLength, rnd);
            
            BigInteger B = g.modPow(b, p);
            
            BigInteger s = publicValue.modPow(b, p);
            
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            this.aesKey = sha.digest(s.toByteArray());
            this.aesKey = Arrays.copyOf(this.aesKey, 16);
            
            return B;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AuthenticationImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private String decrypt(String encrypted) {
        try {
            
            byte[] encryptedBytes = Base64.decode(encrypted);
            
            // Generate the secret key specs.
            SecretKeySpec secretKeySpec = new SecretKeySpec(this.aesKey, "AES");
            
            Cipher cipher = Cipher.getInstance("AES");            
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            
            byte[] original = cipher.doFinal(encryptedBytes);
            
            return new String(original);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | Base64DecodingException ex) {
            Logger.getLogger(AuthenticationImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
