package com.mtbadi39.commons.table;

import java.awt.Component;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class NumberRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

    private static final long serialVersionUID = 1L;
    private final NumberFormat decFormatter = new DecimalFormat("#,##0.00");
    private final NumberFormat intFormatter = new DecimalFormat("#,##0");

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setHorizontalAlignment(SwingConstants.RIGHT);

        if (value instanceof Double) {
            Double v = (Double) value;
            setText(decFormatter.format(v));
        } else if (value instanceof Float) {
            Float v = (Float) value;
            setText(decFormatter.format(v));
        } else if (value instanceof Integer) {
            Integer v = (Integer) value;
            setText(intFormatter.format(v));
        } else if (value instanceof Long) {
            Long v = (Long) value;
            setText(intFormatter.format(v));
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
