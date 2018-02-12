package com.mtbadi39.commons.table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class GanttTableCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        //border = BorderFactory.createLineBorder(Color.WHITE, 2);
        border = BorderFactory.createCompoundBorder();
        if (value instanceof Number) {
            //bar = new GanttBar();
            //bar.setPourcent(Float.valueOf(value.toString()));

            bar = new JProgressBar(0, 100);
            bar.setStringPainted(true);
            bar.setForeground(Color.blue);
            bar.setBackground(Color.white);
            pourcent = Math.round((Float.valueOf(value.toString()) * 100) / 100);
            bar.setValue(pourcent);
        } else {
            super.setValue(value);
        }

        if (bar != null) {
            bar.setBorder(border);
            bar.setFont(table.getFont());
            return bar;
        } else {
            if (isSelected) {
                setBackground(UIManager.getColor("Table.selectionBackground"));
                setForeground(UIManager.getColor("Table.selectionForeground"));
            } else {
                setBackground(UIManager.getColor("Table.background"));
                setForeground(UIManager.getColor("Table.foreground"));
            }
            setFont(table.getFont());
            setBorder(border);
            return this;
        }
    }
    /**
     * reference to gantt bar object to use for rendering
     */
    //private GanttBar bar;
    private JProgressBar bar;
    private int pourcent;
    private Border border;
}
