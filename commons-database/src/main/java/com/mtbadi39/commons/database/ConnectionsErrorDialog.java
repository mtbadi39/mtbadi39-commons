package com.mtbadi39.commons.database;

import com.mtbadi39.commons.alert.AlertUtils;
import com.mtbadi39.commons.logging.LogUtils;
import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import net.lingala.zip4j.core.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author badi.mohammedtahar
 */
public class ConnectionsErrorDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private final String suPasswordFile;
    private final List<DbConnection> DbConnectionList;
    private boolean ret = false;
    private static final Logger logger = LoggerFactory.getLogger(ConnectionsErrorDialog.class);

    public ConnectionsErrorDialog(JFrame parent, boolean modal, List<DbConnection> DbConnectionList, String suPasswordFile) {
        super(parent, modal);
        initComponents();
        //setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(getClass().getResource("/images/database/error.png")).getImage());
        this.suPasswordFile = suPasswordFile;
        this.DbConnectionList = DbConnectionList;
    }

    public boolean DbConnectionsIsOk() {
        return ret;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitre1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnImporter = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        btnAdminPanel = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnQuitter = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Base de données injoignable");
        setResizable(false);

        lblTitre1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitre1.setForeground(new java.awt.Color(204, 0, 0));
        lblTitre1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/database/error32.png"))); // NOI18N
        lblTitre1.setText("Impossible de se connecter au base de données !!");

        jLabel3.setText("     Les paramètres de connexion sont absentes ou incorrectes, une erreur réseau s'est produite, le serveur");

        jLabel6.setText(" base de données est actuellement injoignable ou vous êtes confronté à des difficultés techniques !");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Que dois-je faire ?");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " 1er étape ... ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        jLabel2.setText("- Vérifiez que vous êtes connecté au réseau, pinger le serveur, ... etc.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Si vous êtes un utilisateur ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        btnImporter.setText("Importer");
        btnImporter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImporterActionPerformed(evt);
            }
        });

        jLabel5.setText("- Veuillez importer les Paramètres de Connexion mis à votre disposition, puis essayez de vous connecter;");

        jLabel9.setText("- Si ça ne fonctionnait pas, veuillez contacter votre administrateur base de données.");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnImporter, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(btnImporter, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Si vous êtes un administrateur ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        jLabel8.setText("- Ouvrir le Panneau de Configuration pour changer les Paramètres de Connexion :");

        btnAdminPanel.setText("Ouvrir");
        btnAdminPanel.setPreferredSize(new java.awt.Dimension(120, 25));
        btnAdminPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdminPanelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAdminPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(btnAdminPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Quitter sans rien faire ! ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        btnQuitter.setText("Quitter");
        btnQuitter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitterActionPerformed(evt);
            }
        });

        jLabel10.setText("- Vous pouvez aussi quitter à tout moment sans rien faire ! ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnQuitter, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(btnQuitter, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitre1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitre1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdminPanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdminPanelActionPerformed

        SuPwdCheckDialog d = new SuPwdCheckDialog(null, true, suPasswordFile);
        d.setVisible(true);
        if (d.suPasswordIsOk()) {
            //setVisible(false);
            ControlPanel cp = new ControlPanel(null, true, DbConnectionList, suPasswordFile);
            cp.setVisible(true);
            if (cp.ConnectionTestIsOk()) {
                ret = true;
                dispose();
            } else {
                //this.setVisible(true);
            }
        } else {
            //this.setVisible(true);
        }
    }//GEN-LAST:event_btnAdminPanelActionPerformed

    private void btnQuitterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitterActionPerformed
        this.setVisible(false);
        AlertUtils.showWarning(null, null, "Opération annulée par l'utilisateur.");
        System.exit(0);
    }//GEN-LAST:event_btnQuitterActionPerformed

    private void btnImporterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImporterActionPerformed
        try {

            FileSystemView vueSysteme = FileSystemView.getFileSystemView();
            File defaut = vueSysteme.getHomeDirectory();
            JFileChooser chooser = new JFileChooser(defaut);
            System.out.println("defaut=" + defaut);
            chooser.setSelectedFile(new File(defaut.getPath() + "/params.zip"));

            FileFilter fileFilter = new FileNameExtensionFilter("Paramètres de Connexion", "zip");
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.addChoosableFileFilter(fileFilter);
            chooser.setDialogTitle("Importation des Paramètres de Connexion");
            int d = chooser.showOpenDialog(this);

            if (d != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File f = chooser.getSelectedFile();

            //File fichier = new File(path);
            ZipFile zipFile = new ZipFile(f.getPath());
            if (zipFile.isEncrypted()) {
                zipFile.setPassword("Souf$2977");
            }
            // Extracts all files to the path specified
            String dest = new File(suPasswordFile).getParent();
            System.out.println("dest = " + dest);
            zipFile.extractAll(dest);

            boolean bol = true;
            for (DbConnection c : DbConnectionList) {
                bol = bol && DataBaseUtil.isConnexionOK(c);
            }

            if (bol) {
                AlertUtils.showInfo(this, "Paramètres de Connexion", "Importation réussie.");
                ret = true;
                dispose();
            }
        } catch (Exception e) {
            logger.error(LogUtils.resolveException("ConnectionsErrorDialog", "btnImporterActionPerformed", e));
            AlertUtils.showError(null, null, "Une erreur s'est produite :\n" + e.getCause().getMessage());
        }
    }//GEN-LAST:event_btnImporterActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdminPanel;
    private javax.swing.JButton btnImporter;
    private javax.swing.JButton btnQuitter;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblTitre1;
    // End of variables declaration//GEN-END:variables
}
