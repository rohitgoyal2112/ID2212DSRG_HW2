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
public class CustomerRemoteImpl extends UnicastRemoteObject 
implements rmistore.commons.interfaces.CustomerRemote {

    public CustomerRemoteImpl() throws RemoteException {
    }
    
}
