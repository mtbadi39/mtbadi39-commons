/*
 * (swing1.1beta3)
 *
 */
package com.mtbadi39.commons.groupable_table_header;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class GroupableTableHeaderUI extends BasicTableHeaderUI {

    @Override
    @SuppressWarnings("unchecked")
    public void paint(Graphics g, JComponent c) {

        Rectangle clipBounds = g.getClipBounds();

        if (header.getColumnModel() == null) {
            return;
        }
        ((GroupableTableHeader) header).setColumnMargin();
        int column = 0;
        Dimension size = header.getSize();
        Rectangle cellRect = new Rectangle(0, 0, size.width, size.height);
        HashMap<Object,Object> h = new HashMap<Object,Object>();
        //int columnMargin = header.getColumnModel().getColumnMargin();

        Enumeration<TableColumn> enumeration = header.getColumnModel().getColumns();
        while (enumeration.hasMoreElements()) {
            cellRect.height = size.height;
            cellRect.y = 0;
            TableColumn aColumn = enumeration.nextElement();
            Iterator<Object> cGroups = ((GroupableTableHeader) header).getColumnGroups(aColumn);
            if (cGroups != null) {
                int groupHeight = 0;
                while (cGroups.hasNext()) {
                    ColumnGroup cGroup = (ColumnGroup) cGroups.next();
                    Rectangle groupRect = (Rectangle) h.get(cGroup);
                    if (groupRect == null) {
                        groupRect = new Rectangle(cellRect);
                        Dimension d = cGroup.getSize(header.getTable());
                        groupRect.width = d.width - 2;
                        groupRect.height = d.height;
                        h.put((Object) cGroup, (Object) groupRect);
                    }
                    paintCell(g, groupRect, cGroup);
                    groupHeight += groupRect.height;
                    cellRect.height = size.height - groupHeight;
                    cellRect.y = groupHeight;
                }
            }
            cellRect.width = aColumn.getWidth();//+ columnMargin;
            if (cellRect.intersects(clipBounds)) {
                paintCell(g, cellRect, column);
            }
            cellRect.x += cellRect.width;
            column++;
        }
    }

    private void paintCell(Graphics g, Rectangle cellRect, int columnIndex) {
        TableColumn aColumn = header.getColumnModel().getColumn(columnIndex);
        TableCellRenderer renderer =  header.getDefaultRenderer();
        Component component = renderer.getTableCellRendererComponent(
                header.getTable(), aColumn.getHeaderValue(), false, false, -1, columnIndex);
        rendererPane.add(component);
        rendererPane.paintComponent(g, component, header, cellRect.x, cellRect.y,
                cellRect.width, cellRect.height, true);
    }

    private void paintCell(Graphics g, Rectangle cellRect, ColumnGroup cGroup) {
        TableCellRenderer renderer = cGroup.getHeaderRenderer();
        Component component = renderer.getTableCellRendererComponent(
                header.getTable(), cGroup.getHeaderValue(), false, false, -1, -1);
        rendererPane.add(component);
        rendererPane.paintComponent(g, component, header, cellRect.x, cellRect.y,
                cellRect.width, cellRect.height, true);
    }

    private int getHeaderHeight() {
        int height = 0;
        TableColumnModel columnModel = header.getColumnModel();
        for (int column = 0; column < columnModel.getColumnCount(); column++) {
            TableColumn aColumn = columnModel.getColumn(column);
            TableCellRenderer renderer =  header.getDefaultRenderer();
            Component comp = renderer.getTableCellRendererComponent(
                    header.getTable(), aColumn.getHeaderValue(), false, false, -1, column);
            int cHeight = comp.getPreferredSize().height;
            Iterator<Object> it = ((GroupableTableHeader) header).getColumnGroups(aColumn);
            if (it != null) {
                while (it.hasNext()) {
                    ColumnGroup cGroup = (ColumnGroup) it.next();
                    cHeight += cGroup.getSize(header.getTable()).height;
                }
            }
            height = Math.max(height, cHeight);
        }
        return height;
    }

    private Dimension createHeaderSize(long width) {
        TableColumnModel columnModel = header.getColumnModel();
        width += columnModel.getColumnMargin() * columnModel.getColumnCount();
        if (width > Integer.MAX_VALUE) {
            width = Integer.MAX_VALUE;
        }
        return new Dimension((int) width, getHeaderHeight());
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        long width = 0;
        Enumeration<TableColumn> enumeration = header.getColumnModel().getColumns();
        while (enumeration.hasMoreElements()) {
            TableColumn aColumn = enumeration.nextElement();
            width = width + aColumn.getPreferredWidth();
        }
        return createHeaderSize(width);
    }
}
