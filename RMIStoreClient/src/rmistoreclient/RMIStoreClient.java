/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmistoreclient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmistore.commons.interfaces.ServerRemote;
import rmistoreclient.helper.RMIStoreClientHelper;

/**
 *
 * @author davidsoendoro
 */
public class RMIStoreClient extends Thread {

    private ServerRemote rmistoreObj;
    
    private RMIStoreClient(String[] args) {
        try {
            rmistoreObj = (ServerRemote)Naming.lookup(RMIStoreClientHelper.RMIStoreName);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(RMIStoreClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            rmistoreObj.register("Test");
        } catch (RemoteException ex) {
            Logger.getLogger(RMIStoreClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new RMIStoreClient(args).run();
    }

}
