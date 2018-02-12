package com.mtbadi39.commons.database;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.Timer;

public class ServerDateErrorDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private static final Calendar gc_client = new GregorianCalendar();
    private static final Calendar gc_server = new GregorianCalendar();
    private static Timer timer;
    private static final SimpleDateFormat datef = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

    public ServerDateErrorDialog(Timestamp date_client, Timestamp date_server, JFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        //setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
        ImageIcon errorIcon = new ImageIcon(getClass().getResource("/images/database/error32.png"));
        lblTitre1.setIcon(errorIcon);
        gc_client.setTime(date_client);
        gc_server.setTime(date_server);

        lblDecalage.setText(Calcule(date_client, date_server));
        lblFHClient.setText(gc_client.getTimeZone().getID());
        lblFHServeur.setText(gc_server.getTimeZone().getID());
        lblDateClient.setText(datef.format(gc_client.getTime()));
        lblDateServeur.setText(datef.format(gc_server.getTime()));
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gc_client.add(Calendar.SECOND, 1);
                gc_server.add(Calendar.SECOND, 1);
                lblDateClient.setText(datef.format(gc_client.getTime()));
                lblDateServeur.setText(datef.format(gc_server.getTime()));
            }
        });
        timer.start();
    }

    private static String Calcule(Timestamp date_client, Timestamp date_serveur) {
        int nbAnnees;
        int nbMois;
        int nbJours;
        int nbHeurs;
        int nbMinutes;
        String difference = "";
        Calendar gc1 = new GregorianCalendar();
        Calendar gc2 = new GregorianCalendar();
        gc1.setTime(date_client);
        gc2.setTime(date_serveur);

        nbAnnees = gc1.get(Calendar.YEAR) - gc2.get(Calendar.YEAR);
        difference += nbAnnees + " An" + (nbAnnees > 1 ? "s" : "") + " : ";

        gc2.add(Calendar.YEAR, nbAnnees);
        nbMois = gc1.get(Calendar.MONTH) - gc2.get(Calendar.MONTH);
        if (nbMois < 0) {
            nbMois *= -1;
        }
        difference += nbMois + " Mois : ";

        gc2.add(Calendar.MONTH, nbMois);
        nbJours = gc1.get(Calendar.DAY_OF_MONTH) - gc2.get(Calendar.DAY_OF_MONTH);
        if (nbJours < 0) {
            nbJours *= -1;
        }
        difference += nbJours + " Jour" + (nbJours > 1 ? "s" : "") + " : ";

        gc2.add(Calendar.DAY_OF_MONTH, nbJours);
        System.out.println("nbJours client  = " + gc1.getTime());
        System.out.println("nbJours serveur = " + gc2.getTime());
        System.out.println("gc1.get(Calendar.HOUR) = " + gc1.get(Calendar.HOUR_OF_DAY));
        System.out.println("gc2.get(Calendar.HOUR) = " + gc2.get(Calendar.HOUR_OF_DAY));
        nbHeurs = gc1.get(Calendar.HOUR_OF_DAY) - gc2.get(Calendar.HOUR_OF_DAY);
        if (nbHeurs < 0) {
            nbHeurs *= -1;
        }
        difference += nbHeurs + " Heur" + (nbHeurs > 1 ? "s" : "") + " : ";

        gc2.add(Calendar.HOUR, nbHeurs);
        System.out.println("nbHeurs nbHeurs=" + nbHeurs);
        System.out.println("nbHeurs client  = " + gc1.getTime());
        System.out.println("nbHeurs serveur = " + gc2.getTime());
        nbMinutes = gc1.get(Calendar.MINUTE) - gc2.get(Calendar.MINUTE);
        if (nbMinutes < 0) {
            nbMinutes *= -1;
        }
        difference += nbMinutes + " Minute" + (nbMinutes > 1 ? "s" : "");
        return difference;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitre1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblFHClient = new javax.swing.JLabel();
        lblDateClient = new javax.swing.JLabel();
        lblFHServeur = new javax.swing.JLabel();
        lblDateServeur = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnQuitter = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        lblDecalage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Erreur :: Décalage de temps");
        setResizable(false);

        lblTitre1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitre1.setForeground(new java.awt.Color(204, 0, 0));
        lblTitre1.setText("Décalage de temps entre le serveur et le client !");

        jLabel3.setText("L'application détecte qu'il y'a une décalage de temps plus de 10 minutes entre le serveur ");

        jLabel6.setText("base de données et votre machine. Veuillez corriger ces dates avant de continuer");

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Date et Heurs");
        jLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel2.setOpaque(true);

        jLabel4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Fuseau horaire");
        jLabel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel4.setOpaque(true);

        lblFHClient.setBackground(new java.awt.Color(0, 0, 0));
        lblFHClient.setForeground(new java.awt.Color(0, 255, 0));
        lblFHClient.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFHClient.setText("Fuseau horaire");
        lblFHClient.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblFHClient.setOpaque(true);

        lblDateClient.setBackground(new java.awt.Color(0, 0, 0));
        lblDateClient.setForeground(new java.awt.Color(0, 255, 0));
        lblDateClient.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDateClient.setText("Date et Heurs");
        lblDateClient.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblDateClient.setOpaque(true);

        lblFHServeur.setBackground(new java.awt.Color(0, 0, 0));
        lblFHServeur.setForeground(new java.awt.Color(0, 255, 0));
        lblFHServeur.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFHServeur.setText("Fuseau horaire");
        lblFHServeur.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblFHServeur.setOpaque(true);

        lblDateServeur.setBackground(new java.awt.Color(0, 0, 0));
        lblDateServeur.setForeground(new java.awt.Color(0, 255, 0));
        lblDateServeur.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDateServeur.setText("Date et Heurs");
        lblDateServeur.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblDateServeur.setOpaque(true);

        jLabel13.setBackground(new java.awt.Color(0, 0, 0));
        jLabel13.setForeground(new java.awt.Color(0, 255, 0));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Votre Machine :");
        jLabel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel13.setOpaque(true);

        jLabel14.setBackground(new java.awt.Color(0, 0, 0));
        jLabel14.setForeground(new java.awt.Color(0, 255, 0));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Serveur :");
        jLabel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel14.setOpaque(true);

        jLabel15.setBackground(new java.awt.Color(204, 204, 204));
        jLabel15.setForeground(new java.awt.Color(204, 204, 204));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText(".");
        jLabel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel15.setOpaque(true);

        btnQuitter.setText("Quitter l'Application");
        btnQuitter.setPreferredSize(new java.awt.Dimension(160, 25));
        btnQuitter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitterActionPerformed(evt);
            }
        });
        jPanel2.add(btnQuitter);

        jLabel16.setBackground(new java.awt.Color(204, 204, 204));
        jLabel16.setForeground(new java.awt.Color(204, 0, 0));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Décalage :");
        jLabel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel16.setOpaque(true);

        lblDecalage.setBackground(new java.awt.Color(204, 204, 204));
        lblDecalage.setForeground(new java.awt.Color(204, 0, 0));
        lblDecalage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDecalage.setText("Serveur :");
        lblDecalage.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblDecalage.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDateClient, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblFHClient, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblTitre1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(5, 5, 5)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblDecalage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblDateServeur, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblFHServeur, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(lblDateServeur)
                            .addComponent(lblFHServeur)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblDateClient)
                        .addComponent(lblFHClient)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(lblDecalage))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnQuitterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitterActionPerformed
        dispose();
    }//GEN-LAST:event_btnQuitterActionPerformed
    //--
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnQuitter;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblDateClient;
    private javax.swing.JLabel lblDateServeur;
    private javax.swing.JLabel lblDecalage;
    private javax.swing.JLabel lblFHClient;
    private javax.swing.JLabel lblFHServeur;
    private javax.swing.JLabel lblTitre1;
    // End of variables declaration//GEN-END:variables
}
