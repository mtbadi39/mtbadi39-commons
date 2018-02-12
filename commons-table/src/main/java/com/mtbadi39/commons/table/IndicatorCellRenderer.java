/* (swing1.1) */
package com.mtbadi39.commons.table;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * @version 1.0 03/03/99
 */
public class IndicatorCellRenderer extends JProgressBar implements TableCellRenderer {

    private static final long serialVersionUID = 1L;
    private Map<Integer, Color> limitColors;
    private int[] limitValues;

    public IndicatorCellRenderer() {
        super(JProgressBar.HORIZONTAL);
        setBorderPainted(false);
    }

    public IndicatorCellRenderer(int min, int max) {
        super(JProgressBar.HORIZONTAL, min, max);
        setBorderPainted(false);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        int n = 0;
        if (!(value instanceof Number)) {
            String str;
            if (value instanceof String) {
                str = (String) value;
            } else {
                str = value.toString();
            }
            try {
                n = Integer.parseInt(str);
            } catch (NumberFormatException ex) {
            }
        } else {
            n = ((Number) value).intValue();
        }
        Color color = getColor(n);
        if (color != null) {
            setForeground(color);
        }
        setFont(table.getFont());
        setValue(n);
        return this;
    }

    public void setLimits(Map<Integer, Color> limitColors) {
        this.limitColors = limitColors;
        int i = 0;
        int n = limitColors.size();
        limitValues = new int[n];
        Iterator<Integer> it = limitColors.keySet().iterator();
        while (it.hasNext()) {
            limitValues[i++] = it.next();
        }
        sort(limitValues);
    }

    private Color getColor(int value) {
        Color color = null;
        if (limitValues != null) {
            int i;
            for (i = 0; i < limitValues.length; i++) {
                if (limitValues[i] < value) {
                    color = limitColors.get(limitValues[i]);
                }
            }
        }
        return color;
    }

    private void sort(int[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            int k = i;
            for (int j = i + 1; j < n; j++) {
                if (a[j] < a[k]) {
                    k = j;
                }
            }
            int tmp = a[i];
            a[i] = a[k];
            a[k] = tmp;
        }
    }
}
