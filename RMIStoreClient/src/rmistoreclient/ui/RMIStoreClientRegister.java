/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmistoreclient.ui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JOptionPane.showMessageDialog;
import rmistore.commons.exceptions.Rejected;
import rmistore.commons.interfaces.Bank;
import rmistore.commons.interfaces.ClientRemote;
import rmistore.commons.interfaces.CustomerRemote;
import rmistore.commons.interfaces.ServerRemote;
import rmistoreclient.helper.RMIStoreClientHelper;
import rmistoreclient.implementations.AccountThreadImpl;
import rmistoreclient.implementations.ClientRemoteImpl;
import rmistoreclient.implementations.CustomerRemoteThreadImpl;

/**
 *
 * @author davidsoendoro
 */
public class RMIStoreClientRegister extends javax.swing.JFrame {

    private ServerRemote rmistoreObj;
    
    /**
     * Creates new form RMIStoreClientMain
     */
    public RMIStoreClientRegister() {
        initComponents();

        new RequestThread(RMIStoreClientHelper.STARTRMI_COMMAND).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextFieldUsername = new javax.swing.JTextField();
        jLabelUsername = new javax.swing.JLabel();
        jButtonRegister = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelUsername.setText("Username");

        jButtonRegister.setText("Register");
        jButtonRegister.setEnabled(false);
        jButtonRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegisterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonRegister)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelUsername)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(206, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUsername))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonRegister)
                .addContainerGap(231, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegisterActionPerformed
        jButtonRegister.setEnabled(false);
        new RequestThread(RMIStoreClientHelper.REGISTER_COMMAND).start();
    }//GEN-LAST:event_jButtonRegisterActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RMIStoreClientRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RMIStoreClientRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RMIStoreClientRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RMIStoreClientRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RMIStoreClientRegister().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonRegister;
    private javax.swing.JLabel jLabelUsername;
    private javax.swing.JTextField jTextFieldUsername;
    // End of variables declaration//GEN-END:variables

    private class RequestThread extends Thread {

        private int command;
        
        public RequestThread(int command) {
            this.command = command;
        }
        
        @Override
        public void run() {
            try {
                switch(command) {
                    case RMIStoreClientHelper.STARTRMI_COMMAND:
                        rmistoreObj = (ServerRemote)Naming.lookup(
                                RMIStoreClientHelper.RMIStoreName);
                        jButtonRegister.setEnabled(true);
                        break;
                    case RMIStoreClientHelper.REGISTER_COMMAND:
                        RMIStoreClientHelper.clientRemoteObj = new ClientRemoteImpl();
                        
                        CustomerRemote customerRemote = rmistoreObj.register(
                                jTextFieldUsername.getText(), RMIStoreClientHelper.clientRemoteObj);
                        RMIStoreClientHelper.customerRemoteObj = 
                                new CustomerRemoteThreadImpl(customerRemote);
                        
                        // Connect to Bank
                        Bank rmiBankObj = (Bank)Naming.lookup(
                                RMIStoreClientHelper.RMIBankName);
                        RMIStoreClientHelper.accountObj = new AccountThreadImpl(
                                rmiBankObj.getAccount(jTextFieldUsername.getText()));
                        
                        RMIStoreClientMain rmiStoreClientMain = new RMIStoreClientMain(
                            RMIStoreClientRegister.this, jTextFieldUsername.getText());
                        rmiStoreClientMain.setVisible(true);
                        
                        break;
                }
            }
            catch (RemoteException | NotBoundException | MalformedURLException | Rejected ex) {
                showMessageDialog(null, ex.getMessage());
                
                Logger.getLogger(RMIStoreClientRegister.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
            finally {
                if(command != RMIStoreClientHelper.STARTRMI_COMMAND) {
                    jButtonRegister.setEnabled(true);
                }
            }
        }
        
    }
}
