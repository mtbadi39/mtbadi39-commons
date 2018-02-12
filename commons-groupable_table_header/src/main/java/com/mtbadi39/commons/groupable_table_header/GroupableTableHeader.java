/*
 * (swing1.1beta3)
 *
 */
package com.mtbadi39.commons.groupable_table_header;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * GroupableTableHeader
 *
 * @version 1.0 10/20/98
 * @author Nobuo Tamemasa
 */
public class GroupableTableHeader extends JTableHeader {

    private static final long serialVersionUID = 1L;
    private static final String uiClassID = "GroupableTableHeaderUI";
    protected List<ColumnGroup> columnGroups = null;

    public GroupableTableHeader(TableColumnModel model) {
        super(model);
        setUI(new GroupableTableHeaderUI());
        setReorderingAllowed(false);
    }

    @Override
    public final void setReorderingAllowed(boolean b) {
        reorderingAllowed = false;
    }

    @SuppressWarnings("unchecked")
    public void addColumnGroup(ColumnGroup g) {
        if (columnGroups == null) {
            columnGroups = new ArrayList<ColumnGroup>();
        }
        columnGroups.add(g);
    }

    public Iterator<Object> getColumnGroups(TableColumn col) {
        if (columnGroups == null) {
            return null;
        }
        for (ColumnGroup cGroup : columnGroups) {
            //ColumnGroup cGroup = (ColumnGroup) obj;
            List<Object> v_ret =  cGroup.getColumnGroups(col, new ArrayList<Object>());
            if (v_ret != null) {
                return v_ret.iterator();
            }
        }
        return null;
    }

    public void setColumnMargin() {
        if (columnGroups == null) {
            return;
        }
        int columnMargin = getColumnModel().getColumnMargin();
        for (ColumnGroup cGroup : columnGroups) {
            //ColumnGroup cGroup = (ColumnGroup) obj;
            cGroup.setColumnMargin(columnMargin);
        }
    }
}
