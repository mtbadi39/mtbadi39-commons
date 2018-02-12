package com.mtbadi39.commons.table;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

public class GanttBar extends JComponent {

    private static final long serialVersionUID = 1L;

    public GanttBar() {
        super();
    }

    @Override
    public void paint(Graphics g) {
        // paint parent(s) first
        super.paint(g);
        // draw gantt bar
        int leftOffset = 0;
        int widthInPixel = getWidth() - 2 * leftOffset;

        int rightOffset = Math.round((pourcent * 100) / widthInPixel);
        g.setColor(Color.BLUE);
        g.fillRect(leftOffset,
                TOP_OFFSET,
                rightOffset,
                getHeight() - TOP_OFFSET * 2);
    }

    public void setPourcent(float pourcent) {
        this.pourcent = pourcent;
    }
    private float pourcent;
    /**
     * number of pixel for top margin of bar / group polygon
     */
    private static final int TOP_OFFSET = 2;
}
