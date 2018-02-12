package com.mtbadi39.commons.database;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class ConnectionStatusRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        Boolean v;
        if (value instanceof Boolean) {
            //setHorizontalAlignment(SwingConstants.CENTER);
            //setHorizontalTextPosition(SwingConstants.CENTER);
            v = (Boolean) value;
            if (v == true) {
                setIcon(new ImageIcon(getClass().getResource("/images/database/succes.png")));
                //setForeground(new Color(0, 102, 0));
                setText(" Connexion réussie");
            } else if (v == false) {
                setIcon(new ImageIcon(getClass().getResource("/images/database/error.png")));
                //setForeground(new Color(204, 0, 0));
                setText(" Impossible de se connecter !");
            } else {
                setIcon(null);
            }
        } else {
            super.setValue(value);
        }
        if (isSelected) {
            setBackground(UIManager.getColor("Table.selectionBackground"));
            setForeground(UIManager.getColor("Table.selectionForeground"));
        } else {
            setBackground(UIManager.getColor("Table.background"));
            setForeground(UIManager.getColor("Table.foreground"));
        }
        //setValue(null);
        return this;
    }
}
