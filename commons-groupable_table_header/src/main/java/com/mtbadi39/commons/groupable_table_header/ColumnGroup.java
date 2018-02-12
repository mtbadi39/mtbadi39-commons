/*
 * (swing1.1beta3)
 *
 */
package com.mtbadi39.commons.groupable_table_header;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * ColumnGroup
 *
 * @version 1.0 10/20/98
 * @author Nobuo Tamemasa
 */
public class ColumnGroup {

    private static final long serialVersionUID = 1L;
    protected TableCellRenderer renderer;
    protected List childs;
    protected String text;
    protected int margin = 0;
    protected Color foreground = null;
    protected Color background = null;
    protected Font font = null;

    public ColumnGroup(String text) {
        this(null, text);
    }

    public ColumnGroup(TableCellRenderer renderer, String text) {
        if (renderer == null) {

            this.renderer = new DefaultTableCellRenderer() {
                private static final long serialVersionUID = 1L;

                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    JTableHeader header = table.getTableHeader();
                    if (header != null) {
                        setForeground((foreground != null) ? foreground : header.getForeground());
                        setBackground((background != null) ? background : header.getBackground());
                        setFont((font != null) ? font : header.getFont());
                    }
                    setHorizontalAlignment(JLabel.CENTER);
                    setText((value == null) ? "" : value.toString());
                    setBorder(UIManager.getBorder("TableHeader.cellBorder"));

                    return this;
                }
            };
        } else {
            this.renderer = renderer;
        }
        this.text = text;
        childs = new ArrayList();
    }

    /**
     * @param obj TableColumn or ColumnGroup
     */
    @SuppressWarnings("unchecked")
    public void add(Object obj) {
        if (obj == null) {
            return;
        }
        childs.add(obj);
    }

    /**
     * @param c TableColumn
     * @param g
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object> getColumnGroups(TableColumn c, List<Object> g) {
        g.add(this);
        if (childs.contains(c)) {
            return g;
        }
        for (Object obj : childs) {
            if (obj instanceof ColumnGroup) {
                List<Object> groups = ((ColumnGroup) obj).getColumnGroups(c, g);
                if (groups != null) {
                    return groups;
                }
            }
        }
        return null;
    }

    public TableCellRenderer getHeaderRenderer() {
        return renderer;
    }

    public void setHeaderRenderer(TableCellRenderer renderer) {
        if (renderer != null) {
            this.renderer = renderer;
        }
    }

    public Object getHeaderValue() {
        return text;
    }

    public Dimension getSize(JTable table) {
        Component comp = renderer.getTableCellRendererComponent(
                table, getHeaderValue(), false, false, -1, -1);
        int height = comp.getPreferredSize().height;
        int width = 0;
        int intercellSpacing_width = table.getIntercellSpacing().width;

        for (Object obj : childs) {
            if (obj instanceof TableColumn) {
                TableColumn aColumn = (TableColumn) obj;
                width += aColumn.getWidth() - intercellSpacing_width;
                width += margin;
            } else {
                width += ((ColumnGroup) obj).getSize(table).width - intercellSpacing_width;
            }
        }
        return new Dimension(width + 2 * intercellSpacing_width, height);
    }

    public void setColumnMargin(int margin) {
        this.margin = margin;
        for (Object obj : childs) {
            if (obj instanceof ColumnGroup) {
                ((ColumnGroup) obj).setColumnMargin(margin);
            }
        }
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public Color getForeground() {
        return foreground;
    }

    public void setForeground(Color foreground) {
        this.foreground = foreground;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
