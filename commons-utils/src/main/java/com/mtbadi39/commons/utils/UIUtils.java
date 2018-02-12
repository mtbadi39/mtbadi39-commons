package com.mtbadi39.commons.utils;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.util.Locale;
import javax.swing.SwingUtilities;

public class UIUtils {

    public static void applyOrientation(Container container) {

        container.setLocale(Locale.getDefault());

        for (int i = 0; i < container.getComponentCount(); i++) {
            Component component = container.getComponent(i);
            if (component instanceof Container) {
                applyOrientation((Container) component);
            } else {
                component.setLocale(Locale.getDefault());
                component.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
                component.applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
                component.validate();
            }
        }
        container.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
        container.applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
        container.validate();

        /*
         * ask each node in the tree to initialize its UI property with the
         * current look and feel.
         */
        SwingUtilities.updateComponentTreeUI(container);
    }
}
