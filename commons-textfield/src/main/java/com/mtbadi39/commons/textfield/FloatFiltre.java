package com.mtbadi39.commons.textfield;

import java.awt.Toolkit;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class FloatFiltre extends PlainDocument {

    private static final long serialVersionUID = 1L;

    public FloatFiltre() {
        super();
    }

    /**
     * Redefini la methode de la classe PlainDocument permettant ainsi
     * d'autoriser uniquement les caracteres desires
     *
     * @param offs
     * @param str
     * @param a
     * @throws javax.swing.text.BadLocationException
     */
    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        StringBuilder sb = new StringBuilder();
        char car;
        for (int i = 0; i < str.length(); i++) {
            car = str.charAt(i);
            if (car == ',') {
                car = '.';
            }
            if (Character.isDigit(car) || (car == '.')) {
                sb.append(car);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }
        super.insertString(offs, sb.toString(), a);

        try {
            Double d = Double.parseDouble(this.getText(0, this.getLength()));
        } catch (BadLocationException e) {
            super.remove(offs, sb.length());
            Toolkit.getDefaultToolkit().beep();
        } catch (NumberFormatException e) {
            super.remove(offs, sb.length());
            Toolkit.getDefaultToolkit().beep();
        }
    }
}
