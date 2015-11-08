/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmistoreclient.implementations;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;
import rmistore.commons.exceptions.Rejected;
import rmistore.commons.interfaces.Authentication;
import rmistore.commons.model.Response;
import rmistoreclient.helper.RMIStoreClientCrypto;
import rmistoreclient.helper.RMIStoreClientHelper;
import rmistoreclient.interfaces.Callback;

/**
 *
 * @author dx
 */
public class AuthenticationThreadImpl implements Authentication {

    private final Authentication authenticationObj;
    private boolean isCalling = false;
    public Callback callback;
    private JProgressBar jProgressBar;
    
    public AuthenticationThreadImpl(Authentication authenticationObj) {
        this.authenticationObj = authenticationObj;
    }
    
    public void setLoader(JProgressBar jProgressBar) {
        this.jProgressBar = jProgressBar;
    }
 
    public JProgressBar getLoader() {
        return this.jProgressBar;
    }

    @Override
    public int login(final String username, final String password) {
        if(!isCalling) {
            
        }
        new Thread() {

            @Override
            public void run() {
                try {
                    RMIStoreClientHelper.authenticationObj.getLoader().setIndeterminate(true);
                    isCalling = true;
                    int result = authenticationObj.login(username, password);
                    callback.doCallback(new Response("login", result));
                    RMIStoreClientHelper.authenticationObj.getLoader().setIndeterminate(false);
                    isCalling = false;
                } catch (RemoteException ex) {
                    Logger.getLogger(AuthenticationThreadImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }.start();
        
        return 0;
    }

    @Override
    public String print(final String filename, final String printer) {
        if(!isCalling) {
            
        }
        new Thread() {

            @Override
            public void run() {
                try {
                    RMIStoreClientHelper.authenticationObj.getLoader().setIndeterminate(true);
                    isCalling = true;
                    String resp = authenticationObj.print(filename, printer);
                    
                    Response response = new Response("print", 200);
                    if(RMIStoreClientCrypto.s != null) {
                        resp = RMIStoreClientCrypto.AESDecrypt(resp, 
                                RMIStoreClientCrypto.s);
                        response.setResponseStatus(Integer.parseInt(resp));
                    }
                    else {
                        response.setResponseStatus(500);
                    }
                    
                    callback.doCallback(response);
                    RMIStoreClientHelper.authenticationObj.getLoader().setIndeterminate(false);
                    isCalling = false;
                } catch (RemoteException ex) {
                    Logger.getLogger(AuthenticationThreadImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }.start();
        return null;
    }

    @Override
    public String queue() {
        if(!isCalling) {
            
        }
        new Thread() {

            @Override
            public void run() {
                try {
                    RMIStoreClientHelper.authenticationObj.getLoader().setIndeterminate(true);
                    isCalling = true;
                    String resp = authenticationObj.queue();
                                        
                    Response response = new Response("queue", 200);
                    if(RMIStoreClientCrypto.s != null) {
                        resp = RMIStoreClientCrypto.AESDecrypt(resp, 
                                RMIStoreClientCrypto.s);
                        response.setResponseStatus(Integer.parseInt(resp));
                    }
                    else {
                        response.setResponseStatus(500);
                    }

                    callback.doCallback(response);
                    RMIStoreClientHelper.authenticationObj.getLoader().setIndeterminate(false);
                    isCalling = false;
                } catch (RemoteException ex) {
                    Logger.getLogger(AuthenticationThreadImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }.start();
        return null;
    }

    @Override
    public String topQueue(final String job) {
        if(!isCalling) {
            
        }
        new Thread() {

            @Override
            public void run() {
                try {
                    RMIStoreClientHelper.authenticationObj.getLoader().setIndeterminate(true);
                    isCalling = true;
                                        
                    String resp = authenticationObj.topQueue(job);
                                        
                    Response response = new Response("topQueue", 200);
                    if(RMIStoreClientCrypto.s != null) {
                        resp = RMIStoreClientCrypto.AESDecrypt(resp, 
                                RMIStoreClientCrypto.s);
                        response.setResponseStatus(Integer.parseInt(resp));
                    }
                    else {
                        response.setResponseStatus(500);
                    }

                    callback.doCallback(response);
                    RMIStoreClientHelper.authenticationObj.getLoader().setIndeterminate(false);
                    isCalling = false;
                } catch (RemoteException ex) {
                    Logger.getLogger(AuthenticationThreadImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }.start();
        return null;
    }

    @Override
    public String start() {
        if(!isCalling) {
            
        }
        new Thread() {

            @Override
            public void run() {
                try {
                    RMIStoreClientHelper.authenticationObj.getLoader().setIndeterminate(true);
                    isCalling = true;
                    
                    String resp = authenticationObj.start();
                                        
                    Response response = new Response("start", 200);
                    if(RMIStoreClientCrypto.s != null) {
                        resp = RMIStoreClientCrypto.AESDecrypt(resp, 
                                RMIStoreClientCrypto.s);
                        response.setResponseStatus(Integer.parseInt(resp));
                    }
                    else {
                        response.setResponseStatus(500);
                    }

                    callback.doCallback(response);
                    
                    RMIStoreClientHelper.authenticationObj.getLoader().setIndeterminate(false);
                    isCalling = false;
                } catch (RemoteException ex) {
                    Logger.getLogger(AuthenticationThreadImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }.start();
        return null;
    }

    @Override
    public String stop() {
        if(!isCalling) {
            
        }
        new Thread() {

            @Override
            public void run() {
                try {
                    RMIStoreClientHelper.authenticationObj.getLoader().setIndeterminate(true);
                    isCalling = true;
                    
                    String resp = authenticationObj.stop();
                                        
                    Response response = new Response("stop", 200);
                    if(RMIStoreClientCrypto.s != null) {
                        resp = RMIStoreClientCrypto.AESDecrypt(resp, 
                                RMIStoreClientCrypto.s);
                        response.setResponseStatus(Integer.parseInt(resp));
                    }
                    else {
                        response.setResponseStatus(500);
                    }

                    callback.doCallback(response);
                    
                    RMIStoreClientHelper.authenticationObj.getLoader().setIndeterminate(false);
                    isCalling = false;
                } catch (RemoteException ex) {
                    Logger.getLogger(AuthenticationThreadImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }.start();
        return null;
    }

    @Override
    public String restart() {
        if(!isCalling) {
            
        }
        new Thread() {

            @Override
            public void run() {
                try {
                    RMIStoreClientHelper.authenticationObj.getLoader().setIndeterminate(true);
                    isCalling = true;
                    
                    String resp = authenticationObj.restart();
                                        
                    Response response = new Response("restart", 200);
                    if(RMIStoreClientCrypto.s != null) {
                        resp = RMIStoreClientCrypto.AESDecrypt(resp, 
                                RMIStoreClientCrypto.s);
                        response.setResponseStatus(Integer.parseInt(resp));
                    }
                    else {
                        response.setResponseStatus(500);
                    }

                    callback.doCallback(response);
                    
                    RMIStoreClientHelper.authenticationObj.getLoader().setIndeterminate(false);
                    isCalling = false;
                } catch (RemoteException ex) {
                    Logger.getLogger(AuthenticationThreadImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }.start();
        return null;
    }

    @Override
    public String status() {
        if(!isCalling) {
            
        }
        new Thread() {

            @Override
            public void run() {
                try {
                    RMIStoreClientHelper.authenticationObj.getLoader().setIndeterminate(true);
                    isCalling = true;
                    
                    String resp = authenticationObj.status();
                                        
                    Response response = new Response("status", 200);
                    if(RMIStoreClientCrypto.s != null) {
                        resp = RMIStoreClientCrypto.AESDecrypt(resp, 
                                RMIStoreClientCrypto.s);
                        response.setResponseStatus(Integer.parseInt(resp));
                    }
                    else {
                        response.setResponseStatus(500);
                    }

                    callback.doCallback(response);
                    
                    RMIStoreClientHelper.authenticationObj.getLoader().setIndeterminate(false);
                    isCalling = false;
                } catch (RemoteException ex) {
                    Logger.getLogger(AuthenticationThreadImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }.start();
        return null;
    }

    @Override
    public String readConfig(final String parameter) {
        if(!isCalling) {
            
        }
        new Thread() {

            @Override
            public void run() {
                try {
                    RMIStoreClientHelper.authenticationObj.getLoader().setIndeterminate(true);
                    isCalling = true;
                    
                    String resp = authenticationObj.readConfig(parameter);
                                        
                    Response response = new Response("readConfig", 200);
                    if(RMIStoreClientCrypto.s != null) {
                        resp = RMIStoreClientCrypto.AESDecrypt(resp, 
                                RMIStoreClientCrypto.s);
                        response.setResponseStatus(Integer.parseInt(resp));
                    }
                    else {
                        response.setResponseStatus(500);
                    }

                    callback.doCallback(response);
                    
                    RMIStoreClientHelper.authenticationObj.getLoader().setIndeterminate(false);
                    isCalling = false;
                } catch (RemoteException ex) {
                    Logger.getLogger(AuthenticationThreadImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }.start();
        return null;
    }

    @Override
    public String setConfig(final String parameter, final String value) {
        if(!isCalling) {
            
        }
        new Thread() {

            @Override
            public void run() {
                try {
                    RMIStoreClientHelper.authenticationObj.getLoader().setIndeterminate(true);
                    isCalling = true;
                    
                    String resp = authenticationObj.setConfig(parameter, value);
                                        
                    Response response = new Response("setConfig", 200);
                    if(RMIStoreClientCrypto.s != null) {
                        resp = RMIStoreClientCrypto.AESDecrypt(resp, 
                                RMIStoreClientCrypto.s);
                        response.setResponseStatus(Integer.parseInt(resp));
                    }
                    else {
                        response.setResponseStatus(500);
                    }

                    callback.doCallback(response);
                    
                    RMIStoreClientHelper.authenticationObj.getLoader().setIndeterminate(false);
                    isCalling = false;
                } catch (RemoteException ex) {
                    Logger.getLogger(AuthenticationThreadImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }.start();
        return null;
    }

    @Override
    public int publicValueExchange(final BigInteger p, final BigInteger g) throws RemoteException {
        if(!isCalling) {
            
        }
        new Thread() {

            @Override
            public void run() {
                try {
                    RMIStoreClientHelper.authenticationObj.getLoader().setIndeterminate(true);
                    isCalling = true;
                    int status = authenticationObj.publicValueExchange(p, g);
                    Response response = new Response("publicValueExchange", status);
                    callback.doCallback(response);
                    RMIStoreClientHelper.authenticationObj.getLoader().setIndeterminate(false);
                    isCalling = false;
                } catch (RemoteException ex) {
                    Logger.getLogger(AuthenticationThreadImpl.class.getName()).log(Level.SEVERE, null, ex);
                    Response response = new Response("keyExchange", 500);
                    callback.doCallback(response);
                }
            }
            
        }.start();
        return 0;
    }
    
    @Override
    public String keyExchange(final String clientValue) {
        if(!isCalling) {
            
        }
        new Thread() {

            @Override
            public void run() {
                try {
                    /**
                     * GUI showing loading
                     */
                    RMIStoreClientHelper.authenticationObj.getLoader().setIndeterminate(true);
                    isCalling = true;
                    
                    /**
                     * Encrypt clientValue with RSA
                     */
                    String encryptedClientValue = RMIStoreClientCrypto.
                            RSAEncrypt(clientValue, RMIStoreClientCrypto.pubKey);
                    
                    String encryptedResponse = authenticationObj.keyExchange(
                            encryptedClientValue);
                    String BString = RMIStoreClientCrypto.RSADecrypt(
                            encryptedResponse, RMIStoreClientCrypto.pubKey);
                    BigInteger B = new BigInteger(BString);
                    Response response = new Response("keyExchange", 200);
                    response.setResponseArg(B);
                    callback.doCallback(response);
                    RMIStoreClientHelper.authenticationObj.getLoader().setIndeterminate(false);
                    isCalling = false;
                } catch (RemoteException ex) {
                    Logger.getLogger(AuthenticationThreadImpl.class.getName()).log(Level.SEVERE, null, ex);
                    Response response = new Response("keyExchange", 500);
                    callback.doCallback(response);
                }
            }
            
        }.start();
        return null;
    }

}
