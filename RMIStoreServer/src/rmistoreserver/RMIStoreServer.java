/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmistoreserver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmistoreserver.helper.RMIStoreServerHelper;
import rmistoreserver.implementations.ServerRemoteImpl;
import rmistore.commons.interfaces.ServerRemote;

/**
 *
 * @author davidsoendoro
 */
public class RMIStoreServer extends Thread {
    public RMIStoreServer(String[] args) {
        try {
            Runtime.getRuntime().exec("rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false");
            LocateRegistry.createRegistry(1099);
            LocateRegistry.getRegistry();
        } catch (IOException ex) {
            System.out.println("Port already opened!");
        }
            
        try {            
            ServerRemote rmistoreObj = new ServerRemoteImpl(RMIStoreServerHelper.RMIStoreName);
            Naming.rebind(RMIStoreServerHelper.RMIStoreName, rmistoreObj);
        } catch (RemoteException | MalformedURLException ex) {
            Logger.getLogger(RMIStoreServer.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new RMIStoreServer(args).run();
    }
    
}
