package com.mtbadi39.commons.alert;

import java.awt.Component;
import javax.swing.JOptionPane;

public class AlertUtils {

    public static void showInfo(Component parent, String title, String message) {
        if (title == null || title.isEmpty()) {
            title = "Information";
        }
        JOptionPane.showMessageDialog(parent, formatMessage(message), title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showInfo(String message) {
        AlertUtils.showInfo(null, null, message);
    }

    public static void showError(Component parent, String title, String message) {
        if (title == null || title.isEmpty()) {
            title = "Erreur fatale";
        }
        JOptionPane.showMessageDialog(parent, formatMessage(message), title, JOptionPane.ERROR_MESSAGE);
    }

    public static void showError(String message) {
        AlertUtils.showError(null, null, message);
    }

    public static void showWarning(Component parent, String title, String message) {
        if (title == null || title.isEmpty()) {
            title = "Avertissement !!";
        }
        JOptionPane.showMessageDialog(parent, formatMessage(message), title, JOptionPane.WARNING_MESSAGE);
    }

    public static void showWarning(String message) {
        AlertUtils.showWarning(null, null, message);
    }

    public static int showQuestion(Component parent, String title, String message) {
        if (title == null || title.isEmpty()) {
            title = "Confirmer ?";
        }
        return JOptionPane.showConfirmDialog(parent, formatMessage(message), title, JOptionPane.YES_NO_OPTION);
    }

    public static int showQuestion(String message) {
        return AlertUtils.showQuestion(null, null, message);
    }

    private static String formatMessage(String message) {
        String str = "";
        int longue = 0;
        message = message.replace('\n', ' ');
        message = message.replace('\r', ' ');
        String[] split = message.split(" ");
        for (String ss : split) {
            longue += ss.length();
            if (longue > 60) {
                //nouvel ligne
                str += "\r\n" + ss;
                longue = ss.length();
            } else {
                //meme ligne
                str += ss + " ";
                longue++; // ss.length() déja ajouter, ++ pour espace
            }
            //System.out.println(ss + " : " + longue);
        }
        return str;
    }
}
