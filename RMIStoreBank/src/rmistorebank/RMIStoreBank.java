/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmistorebank;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmibank.commons.interfaces.Bank;
import rmibank.commons.interfaces.BankImpl;

/**
 *
 * @author davidsoendoro
 */
public class RMIStoreBank {

    public RMIStoreBank(String[] args){
        try{
        try {
            Runtime.getRuntime().exec("rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false");
            LocateRegistry.getRegistry(1100).list();
        } catch ( IOException ex) {
            System.out.println("exception is :"+ex);
            LocateRegistry.createRegistry(1100);
            System.out.println("Created registry");
        }
        }
        catch(RemoteException ex){
            System.out.println("exception "+ex);
        }
        try {            
            Bank rmiBankObj = new BankImpl("Nordea");
            Naming.rebind("Nordea", rmiBankObj);
        } catch (RemoteException | MalformedURLException ex) {
          //  Logger.getLogger(RMIStoreServer.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception here:"+ex);
        } 
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        RMIStoreBank rMIStoreBank= new RMIStoreBank(args);
    }
    
}
