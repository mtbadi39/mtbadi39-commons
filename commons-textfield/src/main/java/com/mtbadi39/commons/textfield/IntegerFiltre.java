package com.mtbadi39.commons.textfield;

import java.awt.Toolkit;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class IntegerFiltre extends PlainDocument {

    private static final long serialVersionUID = 1L;
    private int _max_digits = 0;

    public IntegerFiltre(int max_digits) {
        super();
        _max_digits = max_digits;
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
            if (Character.isDigit(car)) {
                sb.append(car);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }
        super.insertString(offs, sb.toString(), a);

        try {
            long d = Long.parseLong(this.getText(0, this.getLength()));
        } catch (NumberFormatException e) {
            System.out.println(e);
            super.remove(offs, sb.length());
            Toolkit.getDefaultToolkit().beep();
        } catch (BadLocationException e) {
            System.out.println(e);
            super.remove(offs, sb.length());
            Toolkit.getDefaultToolkit().beep();
        }
        if (_max_digits > 0 && this.getLength() > _max_digits) {
            //System.out.println("this.getLength()=" + this.getLength());
            super.remove(offs, sb.length());
            Toolkit.getDefaultToolkit().beep();
        }
    }
}
