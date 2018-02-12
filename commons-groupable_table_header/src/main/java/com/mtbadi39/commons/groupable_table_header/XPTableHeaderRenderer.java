package com.mtbadi39.commons.groupable_table_header;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class XPTableHeaderRenderer
        extends DefaultTableCellRenderer
        implements MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    private final JTableHeader header;
    private final DefaultTableCellRenderer oldRenderer;
    private int rolloverColumn = -1;

    public XPTableHeaderRenderer(JTableHeader header) {
        this.header = header;
        this.oldRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        header.addMouseListener((MouseListener) this);
        header.addMouseMotionListener((MouseMotionListener) this);
    }

    private void updateRolloverColumn(MouseEvent e) {
        int col = header.columnAtPoint(e.getPoint());
        if (col != rolloverColumn) {
            rolloverColumn = col;
            header.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        updateRolloverColumn(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        updateRolloverColumn(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        rolloverColumn = -1;
        header.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        rolloverColumn = -1;
        header.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        updateRolloverColumn(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        JLabel comp
                = (JLabel) oldRenderer.getTableCellRendererComponent(table,
                        value, isSelected,
                        hasFocus || (column == rolloverColumn),
                        row, column);
        comp.setBorder(new EmptyBorder(3, 8, 4, 8));
        comp.setHorizontalTextPosition(SwingConstants.CENTER);
        comp.setHorizontalAlignment(SwingConstants.CENTER);
        comp.setFont(new Font("SansSerif", Font.BOLD, 11));
        return comp;
    }
}
