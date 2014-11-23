/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmistoreclient.helper;

import rmistoreclient.implementations.CustomerRemoteThreadImpl;

/**
 *
 * @author davidsoendoro
 */
public class RMIStoreClientHelper {
    
    public static String RMIStoreName = "rmi://localhost:1100/RMIStore";
    
    // SERVER REMOTE COMMAND
    public final static int STARTRMI_COMMAND = 0;
    public final static int REGISTER_COMMAND = 1;
 
    // CUSTOMER REMOTE COMMAND
    public final static int SELLITEM_COMMAND = 10;
 
    public static CustomerRemoteThreadImpl customerRemoteObj;
}
