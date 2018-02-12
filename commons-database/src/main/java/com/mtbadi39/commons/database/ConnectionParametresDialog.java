package com.mtbadi39.commons.database;

import com.mtbadi39.commons.alert.AlertUtils;
import com.mtbadi39.commons.logging.LogUtils;
import java.awt.Frame;
import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDialog;
import org.apache.commons.lang3.JavaVersion;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionParametresDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private Logger logger = LoggerFactory.getLogger(ConnectionParametresDialog.class);
    private String dbms = null;
    private String dialect = "";
    private String driver = "";
    private String server;
    private String database;
    private String port;
    private String url;
    private String url_extra = "";
    private String username;
    private String password;
    private DbConnection connection;
    private Map<String, DBMS> dbms_list;
    private boolean isODBC = true;

    public ConnectionParametresDialog(Frame parent, boolean modal, DbConnection connection) {
        super(parent, modal);
        initComponents();
        pJDBC.setVisible(false);
        pODBC.setSize(400, 190);
        pJDBC.setSize(400, 190);
        //setSize(430, 372);
        setSize(430, 372);

        initDbmsList();
        showParamsPanel();
        //setAlwaysOnTop(true);
        setLocationRelativeTo(null);

        this.connection = connection;
        try {
            HashMap<String, String> params = DataBaseUtil.getConnexionPrams(connection);
            dbms = params.get("dbms");
            dialect = params.get("dialect");
            driver = params.get("driver");
            url = params.get("url");
            username = params.get("username");
            password = params.get("password");
        } catch (Exception e) {
            logger.error(LogUtils.resolveException("ConnectionParametresDialog", "ConnectionParametresDialog", e));
        }

        txtConnexion.setText(this.connection.getNom());

        if (dbms != null) {
            cmbSGBD.setSelectedItem(dbms);
        } else {
            cmbSGBD.setSelectedIndex(0);
        }
        txtServer.setText("");
        txtBase.setText("");
        txtPort.setText("");
        txtUser.setText("");
        txtPassword.setText("");
        System.out.println("==== " + jSeparator1.getWidth());
    }

    @SuppressWarnings("unchecked")
    private void initDbmsList() {
        dbms_list = new HashMap<String, DBMS>();
        // name,  jdbcDriver,  hibernateDialect,  defaultPort,  urlExtra
        //dbms_list.put("Odbc", new DBMS("Odbc", "sun.jdbc.odbc.JdbcOdbcDriver", "", "", ""));
        dbms_list.put("PostgreSQL", new DBMS("PostgreSQL", "org.postgresql.Driver", "org.hibernate.dialect.PostgreSQLDialect", "", ""));
        dbms_list.put("MySQL", new DBMS("MySQL", "com.mysql.jdbc.Driver", "org.hibernate.dialect.MySQLDialect", "3306", "?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull"));
        dbms_list.put("Oracle", new DBMS("Oracle", "oracle.jdbc.driver.OracleDriver", "org.hibernate.dialect.Oracle10gDialect", "", ""));
        dbms_list.put("SQLServer", new DBMS("SQLServer", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "org.hibernate.dialect.SQLServerDialect", "", ""));
        dbms_list.put("Ingres", new DBMS("Ingres", "com.ingres.jdbc.IngresDriver", "org.hibernate.dialect.IngresDialect", "II", ";cursor_mode=readonly;autocommit_mode=multi;date_alias=ingresdate"));
        cmbSGBD.removeAllItems();
        cmbSGBD.addItem("< Sélectionner >");
        for (String dbms_i : dbms_list.keySet()) {
            cmbSGBD.addItem(dbms_i);
        }

        pJDBC.setVisible(false);
    }

    private boolean connectionCreated() {
        boolean ret = false;
        String msg = "";
        try {
            if (isODBC) {
                //System.get
                if (SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_8)) {
                    msg = "ODBC n'est plus disponible dans Java 1.8 et plus !\n"
                            + "Veuillez utiliser JDBC";
                } else {
                    String dsn = txtDsnName.getText().trim();
                    if (dsn.isEmpty()) {
                        msg = "Veuillez spécifier le DSN (Nom de la source de données ODBC) !";
                    } else {
                        dbms = "Odbc";
                        dialect = "";
                        driver = "sun.jdbc.odbc.JdbcOdbcDriver";
                        url = "jdbc:odbc:" + dsn;
                        username = txtDsnUser.getText().trim() + "";
                        password = String.copyValueOf(txtDsnPassword.getPassword()) + "";
                    }
                }
            } else if (cmbSGBD.getSelectedIndex() == 0) {
                msg = "Veuillez sélectionner le SGBD approprié !";
            } else {
                dbms = (String) cmbSGBD.getSelectedItem();
                server = txtServer.getText().trim();
                database = txtBase.getText().trim();
                port = txtPort.getText().trim();
                username = txtUser.getText().trim();
                password = String.copyValueOf(txtPassword.getPassword());

                if (server.isEmpty()
                        || database.isEmpty()
                        || port.isEmpty()
                        || username.isEmpty()) {
                    //|| password.isEmpty()) {
                    msg = "Tous les champs sont obligatoires.";
                } else {
                    /*
                         Locale
                         Ingres
                         MySQL
                         PostgreSQL
                         Oracle
                         SQL Server
                     */
 /*
                         if (dbms.equals("odbc")) {
                         driver = "sun.jdbc.odbc.JdbcOdbcDriver";
                         String dsn = "";
                         url = "jdbc:odbc:" + dsn;
                         } else {
                     */
                    dialect = "";
                    if (dbms.equals("Ingres")) {
                        driver = "com.ingres.jdbc.IngresDriver";
                        dialect = "org.hibernate.dialect.IngresDialect";
                        url_extra = ";CURSOR=READONLY;auto=multi";
                        port = port + "7";
                    }

                    if (dbms.equals("MySQL")) {
                        driver = "com.mysql.jdbc.Driver";
                        dialect = "org.hibernate.dialect.MySQLDialect";
                        url_extra = "?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
                    }

                    if (dbms.equals("PostgreSQL")) {
                        driver = "org.postgresql.Driver";
                        dialect = "org.hibernate.dialect.PostgreSQLDialect";
                        url_extra = "";//";CURSOR=READONLY;auto=multi"
                    }

                    if (dbms.equals("Oracle")) {
                        driver = "oracle.jdbc.driver.OracleDriver";
                        dialect = "org.hibernate.dialect.Oracle10gDialect";
                        url_extra = "";//"?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
                    }

                    if (dbms.equals("SQLServer")) {
                        driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                        dialect = "org.hibernate.dialect.SQLServerDialect";
                        url_extra = "";// ";integratedSecurity=true;SelectMethod=cursor";
                    }
                    if (dbms.equals("SQLServer")) {
                        url = "jdbc:" + dbms.toLowerCase() + "://" + server + ":" + port + ";databaseName=" + database + url_extra;
                    } else {
                        url = "jdbc:" + dbms.toLowerCase() + "://" + server + ":" + port + "/" + database + url_extra;
                    }

                }
            }
            if (!msg.isEmpty()) {
                AlertUtils.showError(this, null, msg);
                ret = false;
            } else {
                System.out.println(" 1-dbms     = " + (dbms == null));
                System.out.println(" 1-dialect  = " + (dialect == null));
                System.out.println(" 1-driver   = " + (driver == null));
                System.out.println(" 1-url      = " + (url == null));
                System.out.println(" 1-username = " + (username == null));
                System.out.println(" 1-password = " + (password == null));

                dialect = dialect != null ? dialect : "";
                username = username != null ? username : "";
                password = password != null ? password : "";

                dialect = !dialect.isEmpty() ? dialect : "--";
                username = !username.isEmpty() ? username : "--";
                password = !password.isEmpty() ? password : "--";

                System.out.println(" dbms     = " + dbms);
                System.out.println(" dialect  = " + dialect);
                System.out.println(" driver   = " + driver);
                System.out.println(" url      = " + url);
                System.out.println(" username = " + username);
                System.out.println(" password = " + password);
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("dbms", dbms);
                params.put("dialect", dialect);
                params.put("driver", driver);
                params.put("url", url);
                params.put("username", username);
                params.put("password", password);
                DataBaseUtil.createDbConnectionFile(this.connection, params);
                if (DataBaseUtil.isConnexionOK(this.connection)) {
                    btnTestConnexion.setEnabled(false);
                    AlertUtils.showInfo(this, this.connection.getNom(), "Connexion réussie.");
                    ret = true;
                } else {
                    btnTestConnexion.setEnabled(true);
                    AlertUtils.showError(this, null, "Impossible de se connecter !");
                    ret = false;
                }
            }
        } catch (Exception e) {
            logger.error(LogUtils.resolveException("ConnectionParametresDialog", "connectionCreated", e));
            AlertUtils.showError(this, null, "Une erreur s'est produite :\n" + e.getCause().getMessage());
        }
        return ret;
    }

    private void showParamsPanel() {
        pJDBC.setVisible(!isODBC);
        pODBC.setVisible(isODBC);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnTestConnexion = new javax.swing.JButton();
        btnAnnuler = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txtConnexion = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        pJDBC = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cmbSGBD = new javax.swing.JComboBox();
        txtServer = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPort = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtBase = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        pODBC = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtDsnName = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtDsnUser = new javax.swing.JTextField();
        txtDsnPassword = new javax.swing.JPasswordField();
        jLabel11 = new javax.swing.JLabel();
        cmbProvider = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Paramètres de la Connexion");
        setResizable(false);

        btnTestConnexion.setText("Tester");
        btnTestConnexion.setPreferredSize(new java.awt.Dimension(120, 25));
        btnTestConnexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestConnexionActionPerformed(evt);
            }
        });
        jPanel1.add(btnTestConnexion);

        btnAnnuler.setText("Annuler"); // NOI18N
        btnAnnuler.setPreferredSize(new java.awt.Dimension(120, 25));
        btnAnnuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnnulerActionPerformed(evt);
            }
        });
        jPanel1.add(btnAnnuler);

        jLabel7.setText("Nom de la Connexion :");

        txtConnexion.setEditable(false);

        jLabel8.setText("Fournisseur :");

        pJDBC.setBorder(javax.swing.BorderFactory.createTitledBorder("Paramètres JDBC"));
        pJDBC.setPreferredSize(new java.awt.Dimension(400, 190));

        jLabel6.setText("SGBD :");

        jLabel1.setText("Serveur :");

        cmbSGBD.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "< Sélectionner >", "Ingres", "MySQL", "PostgreSQL", "Oracle", "SQLServer" }));
        cmbSGBD.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSGBDItemStateChanged(evt);
            }
        });

        txtServer.setEnabled(false);

        jLabel3.setText("Port :");

        txtPort.setEnabled(false);

        jLabel2.setText("Base de Données :");

        txtBase.setEnabled(false);

        jLabel4.setText("Utilisateur :");

        txtUser.setEnabled(false);

        jLabel5.setText("Mot de Passe :");

        txtPassword.setEnabled(false);

        javax.swing.GroupLayout pJDBCLayout = new javax.swing.GroupLayout(pJDBC);
        pJDBC.setLayout(pJDBCLayout);
        pJDBCLayout.setHorizontalGroup(
            pJDBCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pJDBCLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pJDBCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pJDBCLayout.createSequentialGroup()
                        .addGroup(pJDBCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pJDBCLayout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbSGBD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pJDBCLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtServer, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pJDBCLayout.createSequentialGroup()
                        .addGroup(pJDBCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pJDBCLayout.createSequentialGroup()
                                .addGroup(pJDBCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pJDBCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pJDBCLayout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBase, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pJDBCLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel4, jLabel5, jLabel6});

        pJDBCLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbSGBD, txtBase, txtPassword, txtServer, txtUser});

        pJDBCLayout.setVerticalGroup(
            pJDBCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pJDBCLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pJDBCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbSGBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pJDBCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pJDBCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pJDBCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pJDBCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pODBC.setBorder(javax.swing.BorderFactory.createTitledBorder("Paramètres ODBC"));
        pODBC.setPreferredSize(new java.awt.Dimension(400, 190));

        jLabel9.setText("Nom DSN :");

        jLabel10.setText("Utilisateur :");

        jLabel11.setText("Mot de Passe :");

        javax.swing.GroupLayout pODBCLayout = new javax.swing.GroupLayout(pODBC);
        pODBC.setLayout(pODBCLayout);
        pODBCLayout.setHorizontalGroup(
            pODBCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pODBCLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pODBCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pODBCLayout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDsnName, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pODBCLayout.createSequentialGroup()
                        .addGroup(pODBCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pODBCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDsnPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDsnUser, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(114, Short.MAX_VALUE))
        );
        pODBCLayout.setVerticalGroup(
            pODBCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pODBCLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pODBCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtDsnName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addGroup(pODBCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtDsnUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pODBCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDsnPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap())
        );

        cmbProvider.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ODBC", "JDBC" }));
        cmbProvider.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbProviderItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtConnexion))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pJDBC, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pODBC, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 180, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {pJDBC, pODBC});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtConnexion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cmbProvider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pJDBC, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pODBC, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {pJDBC, pODBC});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnnulerActionPerformed
        dispose();
    }//GEN-LAST:event_btnAnnulerActionPerformed

    private void cmbSGBDItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSGBDItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            txtServer.setEnabled(cmbSGBD.getSelectedIndex() > 0);
            txtBase.setEnabled(cmbSGBD.getSelectedIndex() > 0);
            txtPort.setEnabled(cmbSGBD.getSelectedIndex() > 0);
            txtUser.setEnabled(cmbSGBD.getSelectedIndex() > 0);
            txtPassword.setEnabled(cmbSGBD.getSelectedIndex() > 0);
            //btnTestConnexion.setEnabled(cmbSGBD.getSelectedIndex() > 0);
        }
    }//GEN-LAST:event_cmbSGBDItemStateChanged

    private void btnTestConnexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestConnexionActionPerformed
        if (connectionCreated()) {
            dispose();
        }
    }//GEN-LAST:event_btnTestConnexionActionPerformed

    private void cmbProviderItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbProviderItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            isODBC = (cmbProvider.getSelectedIndex() == 0);
            showParamsPanel();
        }
    }//GEN-LAST:event_cmbProviderItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnnuler;
    private javax.swing.JButton btnTestConnexion;
    private javax.swing.JComboBox cmbProvider;
    private javax.swing.JComboBox cmbSGBD;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel pJDBC;
    private javax.swing.JPanel pODBC;
    private javax.swing.JTextField txtBase;
    private javax.swing.JTextField txtConnexion;
    private javax.swing.JTextField txtDsnName;
    private javax.swing.JPasswordField txtDsnPassword;
    private javax.swing.JTextField txtDsnUser;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPort;
    private javax.swing.JTextField txtServer;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
