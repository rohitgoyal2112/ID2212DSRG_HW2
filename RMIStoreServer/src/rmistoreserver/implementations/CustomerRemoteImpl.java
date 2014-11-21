/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmistoreserver.implementations;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import rmistore.commons.exceptions.Rejected;
import rmistoreserver.Item;
import rmistoreserver.implementations.ServerRemoteImpl;
/**
 *
 * @author davidsoendoro
 */
public class CustomerRemoteImpl extends UnicastRemoteObject 
implements rmistore.commons.interfaces.CustomerRemote {
    int myId;
    ServerRemoteImpl serverRemoteObj;
    
    public CustomerRemoteImpl(int myId, ServerRemoteImpl serverRemoteObj) throws RemoteException {
    this.myId=myId;
    this.serverRemoteObj=serverRemoteObj;
    }
    public void sellItem(String itemName, double price) throws Rejected,RemoteException{
        
        Item item=new Item(this.myId,itemName,price);
        if(this.serverRemoteObj.addItem(serverRemoteObj.getItemId(), item)==false){
            throw new Rejected("Could not be added to sell list");
        }
            
    }
    public void buyItem(int itemId)throws Rejected,RemoteException{
        //check if user has sufficient balance
        if(this.serverRemoteObj.buyItem(itemId)==true){
        //debit money
        this.serverRemoteObj.getClientObj(myId).receiveMessage("You bought!");
        }
        else{
        this.serverRemoteObj.getClientObj(myId).receiveMessage("You could not buy!");
        } 
    }
    public void wishItem(int itemId, double price)throws Rejected,RemoteException{
        
    }
    public ArrayList<Item> getUserItems()throws Rejected,RemoteException{
      return this.serverRemoteObj.getUserItemsFromHash(myId);
    }
    public boolean unRegister()throws Rejected,RemoteException{
        this.serverRemoteObj.unregister(myId);
       return true; 
    }
}
