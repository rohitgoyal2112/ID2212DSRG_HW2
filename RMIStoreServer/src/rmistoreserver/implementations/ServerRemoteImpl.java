/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmistoreserver.implementations;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author davidsoendoro
 */
public class ServerRemoteImpl extends UnicastRemoteObject 
implements rmistore.commons.interfaces.ServerRemote {

    public ServerRemoteImpl(String RMIStoreName) throws RemoteException {
    }

    @Override
    public synchronized rmistore.commons.interfaces.CustomerRemote register(String name,
            rmistore.commons.interfaces.ClientRemote clientRemote) 
            throws RemoteException, rmistore.commons.exceptions.Rejected {
        rmistore.commons.interfaces.CustomerRemote client = new CustomerRemoteImpl();
        
        System.out.println("Register called! " + name);
        clientRemote.receiveMessage("Hello World!");
        
        return client;
    }
    
}
