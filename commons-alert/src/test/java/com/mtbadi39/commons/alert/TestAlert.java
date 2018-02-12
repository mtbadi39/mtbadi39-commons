package com.mtbadi39.commons.alert;

import javax.swing.UIManager;

/**
 *
 * @author badi.mohammedtahar
 */
public class TestAlert {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            String msg = "";

            for (int i = 0; i < 80; i++) {
                if (i == 50) {
                    msg += " ";
                }
                msg += "1";
            }
            msg += "\n";

            for (int i = 0; i < 80; i++) {
                if (i == 50) {
                    msg += " ";
                }
                msg += "2";
            }
            msg += "\n";

            for (int i = 0; i < 80; i++) {
                if (i == 50) {
                    msg += " ";
                }
                msg += "3";
            }
            msg += "\n";

            AlertUtils.showError(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
