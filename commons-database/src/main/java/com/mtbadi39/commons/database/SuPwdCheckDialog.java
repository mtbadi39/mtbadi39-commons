package com.mtbadi39.commons.database;

import com.mtbadi39.commons.alert.AlertUtils;
import com.mtbadi39.commons.utils.CryptoUtil;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author badi.mohammedtahar
 */
public class SuPwdCheckDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private final CryptoUtil crpt = new CryptoUtil();
    private final String suPasswordFile;
    private boolean ret = false;
    private static final String salt = "Souf$2977";

    public SuPwdCheckDialog(JFrame parent, boolean modal, String suPasswordFile) {
        super(parent, modal);
        initComponents();
        //setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        this.suPasswordFile = suPasswordFile;
    }

    public boolean suPasswordIsOk() {
        return ret;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        btnOpen = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        txtPassWord = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Panneau de Configuration");
        setResizable(false);

        btnOpen.setText("Ouvrir");
        btnOpen.setPreferredSize(new java.awt.Dimension(120, 25));
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });
        jPanel2.add(btnOpen);

        btnCancel.setText("Annuler");
        btnCancel.setPreferredSize(new java.awt.Dimension(120, 25));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jPanel2.add(btnCancel);

        txtPassWord.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPassWordFocusGained(evt);
            }
        });

        jLabel1.setText("Vous devez entrer le mot de passe, afin");

        jLabel3.setText("d'accéder au Panneau de Configuration :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPassWord, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassWord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        String password = crpt.decrypteFileToText(salt, suPasswordFile);
        if (String.valueOf(txtPassWord.getPassword()).equals(password)) {
            ret = true;
            dispose();
        } else {
            AlertUtils.showError(this, null, "Impossible d'afficher le Panneau de Configuration !\n >> Mot de Passe incorrecte.");
        }
    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        ret = false;
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void txtPassWordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassWordFocusGained
        txtPassWord.selectAll();
    }//GEN-LAST:event_txtPassWordFocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOpen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField txtPassWord;
    // End of variables declaration//GEN-END:variables
}
