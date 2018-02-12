package com.mtbadi39.commons.database;

import com.mtbadi39.commons.alert.AlertUtils;
import com.mtbadi39.commons.utils.CryptoUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.UIManager;

/**
 *
 * @author badi.mohammedtahar
 */
public class MainTest {

    private static DbConnection sgc;
    private static DbConnection nps;
    private static DbConnection odbc;
    private static DbConnection staurne;
    private final static CryptoUtil crpt = new CryptoUtil();

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String suFileName = getAppSuFile();

        sgc = new DbConnection();
        sgc.setId(0);
        sgc.setNom("Base de données SGC");
        sgc.setFichier(getAppConfDir() + "/sgc.cert");

        nps = new DbConnection();
        nps.setId(1);
        nps.setNom("Base de données NPS");
        nps.setFichier(getAppConfDir() + "/nps.cert");

        odbc = new DbConnection();
        odbc.setId(2);
        odbc.setNom("Base de données ODBC");
        odbc.setFichier(getAppConfDir() + "/odbc.cert");

        odbc = new DbConnection();
        odbc.setId(3);
        odbc.setNom("Base de données Saturne");
        odbc.setFichier(getAppConfDir() + "/saturne.cert");

        //boolean v = !DataBaseUtil.isConnexionOK(nps) && !DataBaseUtil.isConnexionOK(sgc);
        //while (v) {
        //    ConnectionsErrorDialog d = new ConnectionsErrorDialog(null, true, DbConnectionList, suFileName);
        //    d.setVisible(true);
        //    v = !DataBaseUtil.isConnexionOK(nps) && !DataBaseUtil.isConnexionOK(sgc);
        //}
        if (DataBaseUtil.isConnexionOK(sgc)) {
            sgc.setValide(true);
        } else {
            sgc.setValide(false);
        }

        if (DataBaseUtil.isConnexionOK(nps)) {
            nps.setValide(true);
        } else {
            nps.setValide(false);
        }

        if (DataBaseUtil.isConnexionOK(odbc)) {
            odbc.setValide(true);
        } else {
            odbc.setValide(false);
        }

        if (sgc.estValide() && nps.estValide() && odbc.estValide()) {
            //if (ServerDateTest.isOK()) {
            AlertUtils.showInfo(null, "Application", "Lancé");
            //}

        } else {
            List<DbConnection> DbConnectionList = new ArrayList<DbConnection>();
            DbConnectionList.add(sgc);
            DbConnectionList.add(nps);
            DbConnectionList.add(odbc);
            ConnectionsErrorDialog d = new ConnectionsErrorDialog(null, true, DbConnectionList, suFileName);
            d.setVisible(true);
            if (d.DbConnectionsIsOk()) {
                //if (ServerDateTest.isOK()) {
                AlertUtils.showInfo(null, "Application", "Lancé");
                //}
            } else {
                System.exit(0);
            }
        }
    }

    public static String getAppConfDir() {
        return System.getenv().get("ALLUSERSPROFILE") + "/.mtbadi/commons/database/etc";
    }

    public static String getAppSuFile() {
        File suf = new File(getAppConfDir() + "/su.cert");
        if (!suf.exists()) {
            crpt.crypteTextToFile("Souf$2977", "0000", suf.getPath());
        }
        return suf.getPath();
    }

}
