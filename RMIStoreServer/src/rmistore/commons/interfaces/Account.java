package rmistore.commons.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import rmistore.commons.exceptions.Rejected;

public interface Account extends Remote {
    public float getBalance() throws RemoteException;

    public void deposit(float value) throws RemoteException, Rejected;

    public void withdraw(float value) throws RemoteException, Rejected;
}
