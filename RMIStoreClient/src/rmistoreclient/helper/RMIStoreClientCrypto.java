/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmistoreclient.helper;

import com.sun.org.apache.xml.internal.security.Init;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import sun.security.rsa.RSAPrivateCrtKeyImpl;
import sun.security.rsa.RSAPrivateKeyImpl;
import sun.security.rsa.RSAPublicKeyImpl;

/**
 *
 * @author dx
 */
public class RMIStoreClientCrypto {
    /**
     * HardCoded Public Key
     */
    private static final BigInteger n = new BigInteger("130344748598190411575516246260694471317150654449161893572015670468361105580848861943157665709815609217661298445700170906326928286643175257541131435681044875754515981117056546390465083368732772245215870646328794815538955199577971664625137659716897219245514200770332773314673823117583828351766457691030488797969");
    private static final BigInteger e = new BigInteger("65537");
    public static PublicKey pubKey;
    
    /**
     * AES Key
     */
    public static BigInteger s;

    public static void GenerateRSAKeys() {
        try {
            pubKey = new RSAPublicKeyImpl(n, e);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(RMIStoreClientCrypto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String RSAEncrypt(String message, Key key) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            
            String cipherText = "";
            byte[] input = message.getBytes();
            
            int inputLength = input.length;
            int rounds = inputLength / 117 + 1;
            byte[] output = new byte[rounds * 128];
            for(int i = 0; i < rounds; i++) {
                int offset = i * 117;
                int strLen = inputLength - offset;
                if(strLen > 117) {
                    strLen = 117;
                }
                byte[] cipherBytes = cipher.doFinal(input, offset, strLen);
                System.arraycopy(cipherBytes, 0, output, i * 128, 128);
            }
            
            String retval = Base64.encode(output);
            return retval;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(RMIStoreClientCrypto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String RSADecrypt(String clientValue, Key key) {        
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            
            String plainText = "";
            Init.init();
            byte[] input = Base64.decode(clientValue);
            
            int inputLength = input.length;
            int rounds = inputLength / 128;
            for(int i = 0; i < rounds; i++) {
                int offset = i * 128;
                int strLen = inputLength - offset;
                if(strLen > 128) {
                    strLen = 128;
                }
                byte[] plainBytes = cipher.doFinal(input, offset, strLen);
                String tempPlainString = new String(plainBytes);
                plainText += tempPlainString;
            }
            
            return plainText;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | Base64DecodingException ex) {
            Logger.getLogger(RMIStoreClientCrypto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String AESEncrypt(String text, BigInteger s) {
        try {
            byte[] key = s.toByteArray();
            key = Arrays.copyOf(key, 16);
            
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            
            // Instantiate the cipher
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            
            byte[] encryptedText = cipher.doFinal(text.getBytes());
            
            String textString = Base64.encode(encryptedText);
            
            return textString;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(RMIStoreClientCrypto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String AESDecrypt(String encrypted, BigInteger s) {
        try {
            
            byte[] encryptedBytes = Base64.decode(encrypted);
            
            // Generate the secret key specs.
            byte[] aesKey = s.toByteArray();
            aesKey = Arrays.copyOf(aesKey, 16);
            SecretKeySpec secretKeySpec = new SecretKeySpec(aesKey, "AES");
            
            Cipher cipher = Cipher.getInstance("AES");            
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            
            byte[] original = cipher.doFinal(encryptedBytes);
            
            return new String(original);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | Base64DecodingException ex) {
            Logger.getLogger(RMIStoreClientCrypto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
