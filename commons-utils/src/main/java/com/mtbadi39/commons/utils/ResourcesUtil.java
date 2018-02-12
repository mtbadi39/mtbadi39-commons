package com.mtbadi39.commons.utils;

import com.mtbadi39.commons.alert.AlertUtils;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourcesUtil {

    final static Logger logger = LoggerFactory.getLogger(ResourcesUtil.class);
    final static String baseName = "application";
    final static String fileName = "/application";
    static ResourceBundle resources;

    static {

        try {
            resources = ResourceBundle.getBundle(baseName);
        } catch (MissingResourceException mre) {
            AlertUtils.showError(null, null, " Le fichier spécifié est introuvable : (" + fileName + ".properties)");
            logger.error(" Le fichier spécifié est introuvable : (" + fileName + ".properties)");
            System.exit(1);
        }
    }

    public String getResourceString(String nm) {
        String str;
        try {
            str = resources.getString(nm);
        } catch (MissingResourceException mre) {
            str = null;
        }
        return str;
    }

    public URL getResource(String key) {
        String name = getResourceString(key);
        if (name != null) {
            URL url = this.getClass().getResource(name);
            return url;
        }
        return null;
    }

    public ImageIcon getImageIcon(String key) {
        URL url = getResource(key);
        return new ImageIcon(url);
    }
}
