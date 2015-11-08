/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmistorebank.helper;

import com.sun.org.apache.xml.internal.security.Init;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import sun.security.rsa.RSAPublicKeyImpl;

/**
 *
 * @author dx
 */
public class CryptoHelper {

    /**
     * HardCoded Private Key
     */
    private static final BigInteger n = new BigInteger("130344748598190411575516246260694471317150654449161893572015670468361105580848861943157665709815609217661298445700170906326928286643175257541131435681044875754515981117056546390465083368732772245215870646328794815538955199577971664625137659716897219245514200770332773314673823117583828351766457691030488797969");
    private static final BigInteger e = new BigInteger("65537");
    private static final BigInteger d = new BigInteger("65175357608109919394077656742953718129652363493889486127759182160431411719859273477230826942195363139337484933176596435606351220734804052514196212021725740099776788547763825571039695563512850667669754380827723539148454335355977637322227705967502365379701229308795346021374389880313691976229926537543768962273");
    private static final BigInteger p = new BigInteger("12108672814412605310309061672697042939831216174433732978787742346938374659558504224462003111646778857500581629718577615775745572087828405296463673451264261");
    private static final BigInteger q = new BigInteger("10764577637529754933473859055220181083735114353128790307404507860877125658568825007272599589584676315550807851037886194044027912917482586651142187492630429");
    private static final BigInteger pe = new BigInteger("9046261475796420055897466738156958915116895895946174437453735154047281689447235665930826805506043706928047938634374433123457813152330048371537518951444233");
    private static final BigInteger qe = new BigInteger("7576119497857087543013591542518437714379391023972190563025968919586758945351283297380848315754355479420495477808299139421712734536504330519919028916452973");
    private static final BigInteger coeff = new BigInteger("2816916256338465891853902702171847121634585850120233710710735875036211411803012290022027806153958197733683346969041159402111703761189905372386930633788827");
    public static PrivateKey privKey;
    
    /**
     * HardCoded Public Key
     */
    public static PublicKey pubKey;
    
    public static void GenerateRSAKeys() {
        try {
            RSAPrivateCrtKeySpec keySpec = new RSAPrivateCrtKeySpec(n, e, d, p, q, pe, qe, coeff);
            KeyFactory fact = KeyFactory.getInstance("RSA");
            privKey = (RSAPrivateKey)fact.generatePrivate(keySpec);
            pubKey = new RSAPublicKeyImpl(n, e);
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(CryptoHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String sha256(String password) throws NoSuchAlgorithmException {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");        
        byte[] passBytes = password.getBytes();
        byte[] passHash = sha256.digest(passBytes);
        return String.format("%064x", new java.math.BigInteger(1, passHash));
    }

    public static String createPassword(String dummyRawPassword) throws NoSuchAlgorithmException {
        String dummyPassword = CryptoHelper.sha256(dummyRawPassword);
        String salt = dummyPassword.substring(3, 23);
        String saltedPassword = salt + dummyRawPassword;
        
        String storedDummyPassword = CryptoHelper.sha256(saltedPassword);            
        for(int i = 1; i < 1000; i++) {
            storedDummyPassword = CryptoHelper.sha256(storedDummyPassword);
        }

        return storedDummyPassword;
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
            Logger.getLogger(CryptoHelper.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CryptoHelper.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CryptoHelper.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CryptoHelper.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
