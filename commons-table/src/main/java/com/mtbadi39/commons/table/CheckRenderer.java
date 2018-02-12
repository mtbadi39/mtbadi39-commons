package com.mtbadi39.commons.table;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class CheckRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setHorizontalAlignment(SwingConstants.CENTER);
        if (isSelected) {
            setIcon(new ImageIcon(getClass().getResource("/images/table/checked.png")));
            setBackground(UIManager.getColor("Table.selectionBackground"));
            setForeground(UIManager.getColor("Table.selectionForeground"));
        } else {
            setIcon(new ImageIcon(getClass().getResource("/images/table/unchecked.png")));
            setBackground(UIManager.getColor("Table.background"));
            setForeground(UIManager.getColor("Table.foreground"));
        }
        setFont(table.getFont());
        setValue(null);
        return this;
    }
}
