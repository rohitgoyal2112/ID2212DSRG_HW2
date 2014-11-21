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
import rmistoreclient.ui.RMIStoreClientRegister;

/**
 *
 * @author davidsoendoro
 */
public class RMIStoreClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new RMIStoreClientRegister().setVisible(true);
    }

}
