package com.mtbadi39.commons.table;

import java.awt.Component;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class DateRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

    private static final long serialVersionUID = 1L;
    private final DateFormat DateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof Date) {
            setHorizontalAlignment(SwingConstants.CENTER);
            setText(DateFormatter.format((Date) value));
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
        setFont(table.getFont());
        return this;
    }
}
