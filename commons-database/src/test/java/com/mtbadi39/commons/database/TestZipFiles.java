package com.mtbadi39.commons.database;

/**
 *
 * @author badi.mohammedtahar
 */
public class TestZipFiles {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ZipFiles.zip("D:/badi/nps-con-params.zip", getAppConfDir());
            ZipFiles.unzip("D:/badi/nps-con-params.zip");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getAppConfDir() {
        return System.getenv().get("ALLUSERSPROFILE") + "/.mtbadi.commons.database/etc";
    }
}
