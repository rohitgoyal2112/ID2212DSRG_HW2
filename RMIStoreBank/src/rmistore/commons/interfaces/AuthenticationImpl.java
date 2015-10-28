package rmistore.commons.interfaces;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("serial")
public class AuthenticationImpl extends UnicastRemoteObject implements Authentication {
    private String bankName;
    private Map<String, Account> accounts = new HashMap<>();

    public AuthenticationImpl(String bankName) throws RemoteException {
//        super();
        this.bankName = bankName;
    }

    @Override
    public int login(String username, String password) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void print(String filename, String printer) {
        String user = "Someone";
        System.out.println(user + " is printing file: " + filename + 
                " on Printer: " + printer);
    }

    @Override
    public void queue() {
        String user = "Someone";
        System.out.println(user + " wants to know current queue!");
    }

    @Override
    public void topQueue(int job) {
        String user = "Someone";
        System.out.println(user + " wants to move job " + job + " to the top "
                + "of the queue!");
    }

    @Override
    public void start() {
        String user = "Someone";
        System.out.println(user + " wants to start the printer server!");
    }

    @Override
    public void stop() {
        String user = "Someone";
        System.out.println(user + " wants to stop the printer server!");
    }

    @Override
    public void restart() {
        String user = "Someone";
        System.out.println(user + " wants to restart the printer server!");
    }

    @Override
    public void status() {
        String user = "Someone";
        System.out.println(user + " wants to know the printer status!");
    }

    @Override
    public void readConfig(String parameter) {
        String user = "Someone";
        System.out.println(user + " tries to access parameter [" + parameter 
                + "]");
    }

    @Override
    public void setConfig(String parameter, String value) {
        String user = "Someone";
        System.out.println(user + " tries to change parameter [" + parameter 
                + "] to [" + value + "]");
    }
}
