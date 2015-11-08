/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmistore.commons.interfaces;

import java.math.BigInteger;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author dx
 */
public interface Authentication extends Remote {
    
    /**
     * Let client gives public value of p and g
     * @param p
     * @param g
     * @return
     * @throws RemoteException 
     */
    public int publicValueExchange(BigInteger p, BigInteger g) throws RemoteException;
    
    /**
     * Do Diffie Hellman Key Exchange
     * @param clientValue
     * @return
     * @throws RemoteException 
     */
    public String keyExchange(String clientValue) throws RemoteException;
    
    /**
     * Do login to the server
     * @param username
     * @param password
     * @return
     * @throws java.rmi.RemoteException 
     */
    public int login(String username, String password) throws RemoteException;
    
    /**
     * Prints file filename on the specified printer
     * @param filename
     * @param printer 
     * @throws java.rmi.RemoteException 
     */
    public String print(String filename, String printer) throws RemoteException;   
    
    /**
     * Lists the print queue on the user's display in lines 
     * of the form <job number>   <file name>
     * @throws java.rmi.RemoteException
     */
    public String queue() throws RemoteException;
    
    /**
     * Moves job to the top of the queue
     * @param job 
     * @throws java.rmi.RemoteException 
     */
    public String topQueue(String job) throws RemoteException;   
    
    /**
     * Starts the print server
     * @throws java.rmi.RemoteException
     */
    public String start() throws RemoteException;
    
    /**
     * Stops the print server
     * @throws java.rmi.RemoteException
     */
    public String stop() throws RemoteException;
    
    /**
     * Stops the print server, clears the print queue 
     * and starts the print server again
     * @throws java.rmi.RemoteException
     */
    public String restart() throws RemoteException;
    
    /**
     * Prints status of printer on the user's display
     * @throws java.rmi.RemoteException
     */
    public String status() throws RemoteException;
    
    /**
     * Prints the value of the parameter on the user's display
     * @param parameter 
     * @throws java.rmi.RemoteException 
     */
    public String readConfig(String parameter) throws RemoteException;
    
    /**
     * Sets the parameter to value
     * @param parameter
     * @param value 
     * @throws java.rmi.RemoteException 
     */
    public String setConfig(String parameter, String value) throws RemoteException;
}
