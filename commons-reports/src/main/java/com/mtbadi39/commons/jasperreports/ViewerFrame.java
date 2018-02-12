package com.mtbadi39.commons.jasperreports;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.WindowConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

public class ViewerFrame extends JDialog {

    private static final long serialVersionUID = 1L;

    public ViewerFrame(JasperPrint jasperPrint) throws JRException {

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Aperçu avant impression");
        setIconImage(new ImageIcon(getClass().getResource("/images/jasperreports/printer.png")).getImage());

        JRViewerPlus viewer = new JRViewerPlus(jasperPrint);
        //viewer.setFitWidthZoomRatio();
        getContentPane().add(viewer);

        pack();
        setModal(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - 1024) / 2, (screenSize.height - 768) / 2, 1024, 736);

    }
}
