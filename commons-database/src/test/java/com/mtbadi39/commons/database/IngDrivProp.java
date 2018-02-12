package com.mtbadi39.commons.database;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;
import java.sql.DriverPropertyInfo;

/*
 ** Name: IngDrivProp.java
 **
 ** Description:
 **     Displays the Ingres JDBC driver properties in XML format. Requires
 **     JDK 1.5.x and beyond for compiling/running
 **
 ** History:
 **    31-Dec-2007 ( Usha Rajsekar )
 **       Created.
 **
 **
 */
public class IngDrivProp {

    public static final String ING_DRIVER = "com.ingres.jdbc.IngresDriver";
    public static final String ING_DRIVER_URL = "jdbc:ingres://10.128.0.220:BG7/sgc85";

    public static void main(String[] args) throws Exception {

        try {
            Class.forName(ING_DRIVER);
        } catch (Exception ex) {
            throw new Exception(" Unable to load Ingres JDBC driver class: " + ex);
        }
        try {
            StringBuilder str_buf = new StringBuilder();
            Properties prop = new Properties();
            Driver drv = DriverManager.getDriver(ING_DRIVER_URL);
            DriverPropertyInfo[] prop_info = drv.getPropertyInfo(ING_DRIVER_URL, prop);
            // Process property info
            if (prop_info != null) {
                str_buf.append("\n<driver_properties>\n\n");
                for (DriverPropertyInfo prop_info1 : prop_info) {
                    String name = prop_info1.name;
                    boolean req = prop_info1.required;
                    str_buf.append("  <property name=\"");
                    str_buf.append(name);
                    str_buf.append("\" required=\"");
                    str_buf.append(req);
                    str_buf.append("\">\n");
                    String val = prop_info1.value;
                    str_buf.append("    <value>");
                    str_buf.append(val);
                    str_buf.append("</value>\n");
                    String desc = prop_info1.description;
                    str_buf.append("    <description>");
                    str_buf.append(desc);
                    str_buf.append("</description>\n");
                    String[] val_choices = prop_info1.choices;
                    if (val_choices != null) {
                        str_buf.append("    <choices>\n");
                        for (String val_choice : val_choices) {
                            str_buf.append("      <choice>");
                            str_buf.append(val_choice);
                            str_buf.append("</choice>\n");
                        }
                        str_buf.append("    </choices>\n");
                    }
                    str_buf.append("  </property>\n");
                }

            }
            str_buf.append("\n</driver_properties>\n");
            System.out.println(str_buf.toString());
        } catch (Exception ex) {
            throw new Exception(" Unable to generate Ingres JDBC driver properties: " + ex);
        }

    }//main
}
