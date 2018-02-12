package com.mtbadi39.commons.database;

import com.mtbadi39.commons.logging.LogUtils;
import com.mtbadi39.commons.utils.CryptoUtil;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataBaseUtil {

    private static final Logger logger = LoggerFactory.getLogger(DataBaseUtil.class);
    private static final CryptoUtil crpt = new CryptoUtil();
    private static String dbms;
    private static String dialect;
    private static String url;
    private static String driver;
    private static String username;
    private static String password;
    private static final String salt = "Souf$2977";

    public static HashMap<String, String> getConnexionPrams(DbConnection connexion) throws Exception {

        HashMap<String, String> params = new HashMap<String, String>();
        File fichier = new File(connexion.getFichier());
        if (!fichier.exists()) {
            throw new Exception(" Le fichier spécifié est introuvable : ( " + fichier.getPath() + " )");
        } else {
            try {

                String sTexteClaire = crpt.decrypteFileToText(salt, fichier.getPath());
                StringTokenizer st = new StringTokenizer(sTexteClaire, "\r\n");
                System.out.println("sTexteClaire : " + st.countTokens());
                //while (st.hasMoreTokens()) {
                //    System.out.println("  => " + st.nextToken());
                //}
                dbms = st.nextToken();
                dialect = st.nextToken();
                driver = st.nextToken();
                url = st.nextToken();
                username = st.nextToken();
                password = st.nextToken();

                dialect = !dialect.equals("--") ? dialect : null;
                username = !username.equals("--") ? username : null;
                password = !password.equals("--") ? password : null;

                params.put("dbms", dbms);
                params.put("dialect", dialect);
                params.put("driver", driver);
                params.put("url", url);
                params.put("username", username);
                params.put("password", password);

            } catch (Exception e) {
                e.printStackTrace();
                logger.error(LogUtils.resolveException("DataBaseUtil", "getConnexionPrams", e));
                //Alert.error(null, "Une erreur s'est produite :\n" + e.getLocalizedMessage());
                throw new Exception(e);
            }
        }
        return params;
    }

    public static void createDbConnectionFile(DbConnection connexion, HashMap<String, String> params) throws Exception {
        try {
            dbms = params.get("dbms");
            dialect = params.get("dialect");
            driver = params.get("driver");
            url = params.get("url");
            username = params.get("username");
            password = params.get("password");

            String str = dbms + "\r\n";
            str = str + dialect + "\r\n";
            str = str + driver + "\r\n";
            str = str + url + "\r\n";
            str = str + username + "\r\n";
            str = str + password + "\r\n";

            //System.out.println(str);
            crpt.crypteTextToFile(salt, str, connexion.getFichier());
        } catch (Exception e) {
            logger.error(LogUtils.resolveException("DataBaseUtil", "createDbConnectionFile", e));
            //Alert.error(null, "Une erreur s'est produite :\n" + e.getLocalizedMessage());
            throw new Exception(e);
        }
    }

    public static boolean isConnexionOK(DbConnection connexion) {
        boolean test = false;
        Connection connection;
        Statement statement;
        ResultSet resultset;
        try {
            HashMap<String, String> params = getConnexionPrams(connexion);

            //Set<String> keys = params.keySet();
            //for (String key : keys) {
            //    System.out.println(key + " = " + params.get(key));
            //}
            dbms = params.get("dbms");
            dialect = params.get("dialect");
            driver = params.get("driver");
            url = params.get("url");
            username = params.get("username");
            password = params.get("password");

            System.out.println(" isConnexionOK dbms     = " + dbms);
            System.out.println(" isConnexionOK dialect  = " + dialect);
            System.out.println(" isConnexionOK driver   = " + driver);
            System.out.println(" isConnexionOK url      = " + url);
            System.out.println(" isConnexionOK username = " + username);
            System.out.println(" isConnexionOK password = " + password);

            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            resultset = statement.executeQuery("SELECT 1");
            resultset.next();
            int i = resultset.getInt(1);
            if (i == 1) {
                test = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LogUtils.resolveException("DataBaseUtil", "isConnexionOK", e));
            //Alert.error(null, "Une erreur s'est produite :\n" + e.getLocalizedMessage());
            test = false; //Si non erreur de connection
        }
        return test;
    }

    public static boolean isDateOK(DbConnection connexion) {
        boolean test;
        Connection connection;
        Statement statement;
        ResultSet resultset;
        try {
            HashMap<String, String> params = DataBaseUtil.getConnexionPrams(connexion);
            dbms = params.get("dbms");
            dialect = params.get("dialect");
            driver = params.get("driver");
            url = params.get("url");
            username = params.get("username");
            password = params.get("password");

            Class.forName(driver);

            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            resultset = statement.executeQuery("SELECT CURRENT_TIMESTAMP");

            resultset.next();
            Timestamp date_server = resultset.getTimestamp(1);
            Timestamp date_client = new Timestamp(new Date().getTime());

            //System.out.println("date_client : " + date_client);
            //System.out.println("date_server : " + date_server);
            long decalage = date_client.getTime() - date_server.getTime(); // en milliseconds
            if (Math.abs(decalage) < 600000) { // 10 Minutes
                test = true;   //Tous est Ok
            } else {
                ServerDateErrorDialog d = new ServerDateErrorDialog(date_client, date_server, null, true);
                d.setAlwaysOnTop(true);
                d.setLocationRelativeTo(null);
                d.setVisible(true);
                test = false;   // Erreur date systeme
            }
        } catch (Exception e) {
            logger.error(LogUtils.resolveException("DataBaseUtil", "isDateOK", e));
            //Alert.error(null, "Une erreur s'est produite :\n" + e.getLocalizedMessage());
            test = false; //Si non erreur de connexion
        }
        return test;
    }
}
