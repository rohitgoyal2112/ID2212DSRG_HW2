/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmistoreclient.implementations;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import rmistore.commons.interfaces.CustomerRemote;
import rmistoreclient.interfaces.BalanceDisplayer;

/**
 *
 * @author davidsoendoro
 */
public class ClientRemoteImpl extends UnicastRemoteObject 
implements rmistore.commons.interfaces.ClientRemote {

    private BalanceDisplayer balanceDisplayer;
    
    public ClientRemoteImpl() throws RemoteException {
    }

    public BalanceDisplayer getBalanceDisplayer() {
        return balanceDisplayer;
    }

    public void setBalanceDisplayer(BalanceDisplayer balanceDisplayer) {
        this.balanceDisplayer = balanceDisplayer;
    }

    @Override
    public boolean receiveMessage(String message) throws RemoteException {
        System.out.println(message);
        
        return true;
    }

    @Override
    public void updateBalance(double balance) throws RemoteException {
        balanceDisplayer.displayBalance(balance);
    }
    
}
