package com.mtbadi39.commons.database;

import com.mtbadi39.commons.alert.AlertUtils;
import com.mtbadi39.commons.logging.LogUtils;
import com.mtbadi39.commons.table.CheckRenderer;
import com.mtbadi39.commons.table.EntityTableModel;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableColumnModel;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControlPanel extends JDialog {

    private static final long serialVersionUID = 1L;
    //private boolean con_ok_sgc = false;
    //private boolean con_ok_telrel = false;
    //private ImageIcon icon = null;
    private DbConnection selectedDbConnection;
    private final List<DbConnection> DbConnectionList;
    // private HashMap<String, String> sgc_bd;
    //private HashMap<String, String> telrel_bd;
    private final String suPasswordFile;
    private static final Logger logger = LoggerFactory.getLogger(ControlPanel.class);

    public ControlPanel(JFrame parent, boolean modal, List<DbConnection> DbConnectionList, String suPasswordFile) {
        super(parent, modal);
        initComponents();
        //setAlwaysOnTop(true);
        setLocationRelativeTo(null);

        this.DbConnectionList = DbConnectionList;
        this.suPasswordFile = suPasswordFile;

        ConnectionsList.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ConnectionsList.getSelectionModel().addListSelectionListener(new RowSelectionListener());
        ConnectionsList.addMouseListener(new RowDoubleClickListener());

        ConnectionsList.addHighlighter(HighlighterFactory.createSimpleStriping());
        ColorHighlighter colorHighlighter = new ColorHighlighter(HighlightPredicate.ROLLOVER_ROW, new Color(0xEECC99), null);
        ConnectionsList.addHighlighter(colorHighlighter);

        showConnectionsStatus();
    }

    private void showConnectionsStatus() {
        EntityTableModel model = new EntityTableModel(DbConnection.class, DbConnectionList);
        model.addColumn("#", "id");
        model.addColumn("Nom", "nom");
        model.addColumn("Etat", "valide");

        ConnectionsList.setModel(model);
        TableColumnModel columnModel = ConnectionsList.getColumnModel();

        columnModel.getColumn(0).setCellRenderer(new CheckRenderer());
        columnModel.getColumn(0).setMaxWidth(22);
        columnModel.getColumn(0).setMinWidth(22);

        columnModel.getColumn(2).setCellRenderer(new ConnectionStatusRenderer());

        btnExporter.setEnabled(ConnectionTestIsOk());
    }

    public boolean ConnectionTestIsOk() {
        boolean bol = true;
        for (DbConnection c : DbConnectionList) {
            bol = bol && c.estValide();
        }
        return bol;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        btnFermer = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        ConnectionsList = new org.jdesktop.swingx.JXTable();
        jLabel10 = new javax.swing.JLabel();
        btnExporter = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnEditPassWord = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Panneau de Configuration");
        setResizable(false);

        btnFermer.setText("Fermer");
        btnFermer.setMaximumSize(new java.awt.Dimension(127, 23));
        btnFermer.setMinimumSize(new java.awt.Dimension(127, 23));
        btnFermer.setPreferredSize(new java.awt.Dimension(120, 25));
        btnFermer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFermerActionPerformed(evt);
            }
        });
        jPanel2.add(btnFermer);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Paramètres de Connexion"));

        jLabel9.setText("- Cliquer deux fois sur le nom de connexion pour afficher et/ou modifier leur paramètres ;");

        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        ConnectionsList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(ConnectionsList);

        jLabel10.setText("- Cliquer sur Exporter Tous pour utiliser ces paramètres sur d'autres machines.");

        btnExporter.setText("Exporter Tous");
        btnExporter.setEnabled(false);
        btnExporter.setPreferredSize(new java.awt.Dimension(120, 25));
        btnExporter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExporterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnExporter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExporter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Mot de Passe"));

        jLabel1.setText("Afin de protégé le Panneau de Configuration, veuillez modifier le mot de passe :");

        btnEditPassWord.setText("Modifier");
        btnEditPassWord.setPreferredSize(new java.awt.Dimension(120, 25));
        btnEditPassWord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditPassWordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditPassWord, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnEditPassWord, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFermerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFermerActionPerformed
        dispose();
    }//GEN-LAST:event_btnFermerActionPerformed

    private void btnEditPassWordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditPassWordActionPerformed
        SuPwdEditDialog d = new SuPwdEditDialog(null, true, suPasswordFile);
        d.setVisible(true);
    }//GEN-LAST:event_btnEditPassWordActionPerformed

    private void btnExporterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExporterActionPerformed
        try {
            ArrayList<File> filesToAdd = new ArrayList<File>();
            for (DbConnection c : DbConnectionList) {
                filesToAdd.add(new File(c.getFichier()));
            }

            filesToAdd.add(new File(suPasswordFile));

            FileSystemView vueSysteme = FileSystemView.getFileSystemView();
            File defaut = vueSysteme.getHomeDirectory();
            JFileChooser chooser = new JFileChooser(defaut);
            System.out.println("defaut=" + defaut);
            chooser.setSelectedFile(new File(defaut.getPath() + "/params.zip"));

            FileFilter fileFilter = new FileNameExtensionFilter("Paramètres de Connexion", "zip");
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.addChoosableFileFilter(fileFilter);
            chooser.setDialogTitle("Exportation des Paramètres de Connexion");
            int ret = chooser.showSaveDialog(this);

            if (ret != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File f = chooser.getSelectedFile();
            String name = f.getName();
            String path = f.getPath();
            if (!name.contains(".zip")) {
                path += ".zip";
            }
            //File fichier = new File(path);
            ZipFile zipFile = new ZipFile(path);

            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // set compression method to deflate compression
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
            parameters.setPassword("Souf$2977");

            zipFile.addFiles(filesToAdd, parameters);

        } catch (Exception e) {
            logger.error(LogUtils.resolveException("ControlPanel", "btnExporterActionPerformed", e));
            AlertUtils.showError(null, null, "Une erreur s'est produite :\n" + e.getCause().getMessage());
        }
    }//GEN-LAST:event_btnExporterActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXTable ConnectionsList;
    private javax.swing.JButton btnEditPassWord;
    private javax.swing.JButton btnExporter;
    private javax.swing.JButton btnFermer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables

    private DbConnection getSelectedConnection() {
        DbConnection x = null;
        EntityTableModel _model = (EntityTableModel) ConnectionsList.getModel();
        int TableSelectedRow = ConnectionsList.getSelectedRow();
        if (TableSelectedRow > -1) {
            int ModelSelectedRow = ConnectionsList.convertRowIndexToModel(TableSelectedRow);
            x = (DbConnection) _model.getValueAt(ModelSelectedRow);
        }
        return x;
    }

    private class RowSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
            }
            //selected_motif = getSelectedMotif();
            //btnModifier.setEnabled(selected_motif != null);
            //btnSupprimer.setEnabled(selected_motif != null);
            //btnSelectionner.setEnabled(selected_motif != null);
        }
    }

    private class RowDoubleClickListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent event) {
            //JTable table = (JTable) event.getSource();
            //Point p = event.getPoint();
            //int row = table.rowAtPoint(p);
            if (event.getClickCount() == 2) {
                selectedDbConnection = getSelectedConnection();
                ConnectionParametresDialog d = new ConnectionParametresDialog(null, true, selectedDbConnection);
                d.setAlwaysOnTop(true);
                d.setLocationRelativeTo(null);
                d.setVisible(true);
                if (DataBaseUtil.isConnexionOK(selectedDbConnection)) {
                    selectedDbConnection.setValide(true);
                } else {
                    selectedDbConnection.setValide(false);
                }
                showConnectionsStatus();
            }
        }
    }
}
