package com.mtbadi39.commons.table;

import java.awt.Component;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class TimeStampRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

    private static final long serialVersionUID = 1L;
    private final DateFormat DateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private Timestamp aTimestamp;
    private BigInteger aBigInteger;
    private Long aLong;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setHorizontalAlignment(SwingConstants.CENTER);
        if (value instanceof Timestamp) {
            aTimestamp = (Timestamp) value;
            setText(DateFormatter.format(aTimestamp));
        } else if (value instanceof Long) {
            aLong = (Long) value;
            setText(DateFormatter.format(new Timestamp(aLong)));
        } else if (value instanceof BigInteger) {
            aBigInteger = (BigInteger) value;
            setText(DateFormatter.format(new Timestamp(aBigInteger.longValue())));
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
