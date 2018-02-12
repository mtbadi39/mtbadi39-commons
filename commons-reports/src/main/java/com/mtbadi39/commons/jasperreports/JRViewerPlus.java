package com.mtbadi39.commons.jasperreports;

import com.mtbadi39.commons.alert.AlertUtils;
import com.mtbadi39.commons.logging.LogUtils;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import net.sf.jasperreports.swing.JRViewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRViewerPlus.java 1229 2006-04-19 10:27:35Z teodord $
 */
public class JRViewerPlus extends JRViewer {

    private static final long serialVersionUID = 1L;
    final static Logger LOGGER = LoggerFactory.getLogger(JRViewerPlus.class);
    private final DateFormat dateFormatter = new SimpleDateFormat("ddMMyyyy-HHmmss");
    protected JButton btnPDF;
    protected JButton btnRTF;
    protected JButton btnDocx;
    protected JButton btnXls;
    protected JButton btnXlsx;

    public JRViewerPlus(final JasperPrint jrPrint) throws JRException {
        super(jrPrint);
        //super.btnFitWidth.doClick();
        //super.btnActualSize.doClick();
        //JRViewerToolbar tb = super.createToolbar();
        //tb.remove(tb.btnSave);
        //tlbToolBar.remove(super.tlbToolBar.btnSave);
        //tlbToolBar.remove(btnSave);
        //tlbToolBar.remove(btnReload);

        btnPDF = new JButton();
        btnPDF.setIcon(new ImageIcon(getClass().getResource("/images/jasperreports/pdf.png")));
        btnPDF.setToolTipText("Exporter vers PDF");
        btnPDF.setPreferredSize(new Dimension(23, 23));
        btnPDF.setMaximumSize(new Dimension(23, 23));
        btnPDF.setMinimumSize(new Dimension(23, 23));
        btnPDF.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                pdfExport(jrPrint);
            }
        });

        btnRTF = new JButton();
        btnRTF.setIcon(new ImageIcon(getClass().getResource("/images/jasperreports/doc.png")));
        btnRTF.setToolTipText("Exporter vers RTF");
        btnRTF.setPreferredSize(new Dimension(23, 23));
        btnRTF.setMaximumSize(new Dimension(23, 23));
        btnRTF.setMinimumSize(new Dimension(23, 23));
        btnRTF.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                rtfExport(jrPrint);
            }
        });

        btnDocx = new JButton();
        btnDocx.setIcon(new ImageIcon(getClass().getResource("/images/jasperreports/docx.png")));
        btnDocx.setToolTipText("Exporter vers Word");
        btnDocx.setPreferredSize(new Dimension(23, 23));
        btnDocx.setMaximumSize(new Dimension(23, 23));
        btnDocx.setMinimumSize(new Dimension(23, 23));
        btnDocx.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                docxExport(jrPrint);
            }
        });

        btnXls = new JButton();
        btnXls.setIcon(new ImageIcon(getClass().getResource("/images/jasperreports/xls.png")));
        btnXls.setToolTipText("Exporter vers Excel 97-2003");
        btnXls.setPreferredSize(new Dimension(23, 23));
        btnXls.setMaximumSize(new Dimension(23, 23));
        btnXls.setMinimumSize(new Dimension(23, 23));
        btnXls.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                xlsExport(jrPrint);
            }
        });

        btnXlsx = new JButton();
        btnXlsx.setIcon(new ImageIcon(getClass().getResource("/images/jasperreports/xlsx.png")));
        btnXlsx.setToolTipText("Exporter vers Excel");
        btnXlsx.setPreferredSize(new Dimension(23, 23));
        btnXlsx.setMaximumSize(new Dimension(23, 23));
        btnXlsx.setMinimumSize(new Dimension(23, 23));
        btnXlsx.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                xlsxExport(jrPrint);
            }
        });

        JPanel pnlSep0101 = new JPanel();
        pnlSep0101.setMaximumSize(new Dimension(10, 10));
        tlbToolBar.add(pnlSep0101, 0);
        //tlbToolBar.add(Box.createRigidArea(new Dimension(10, 1)), 0);
        tlbToolBar.add(btnPDF, 2);
        tlbToolBar.add(btnRTF, 3);
        tlbToolBar.add(btnDocx, 4);
        tlbToolBar.add(btnXls, 5);
        tlbToolBar.add(btnXlsx, 6);
        //tlbToolBar.add(Box.createRigidArea(new Dimension(10, 1)), 5);
        JPanel pnlSep0102 = new JPanel();
        pnlSep0102.setMaximumSize(new Dimension(10, 10));
        tlbToolBar.add(pnlSep0102, 7);
        //lblStatus.requestFocus();

    }

    /*
    @Override
    protected JRViewerToolbar createToolbar() {
        JRViewerToolbar tb = new JRViewerToolbar(super.viewerContext);

        return tb;
    }
     */
    private void pdfExport(JasperPrint jasperPrint) {
        FileSystemView vueSysteme = FileSystemView.getFileSystemView();
        File defaut = vueSysteme.getHomeDirectory();
        JFileChooser chooser = new JFileChooser(defaut);

        chooser.setSelectedFile(new File(jasperPrint.getName() + "-" + dateFormatter.format(new Date())));

        FileFilter fileFilter = new FileNameExtensionFilter("Document PDF (*.pdf)", "pdf");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(fileFilter);
        chooser.setDialogTitle("Exporter vers PDF");
        int ret = chooser.showSaveDialog(this);

        if (ret != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File f = chooser.getSelectedFile();
        String name = f.getName();
        String destFile = f.getPath();
        if (!name.contains(".pdf")) {
            destFile += ".pdf";
        }
        try {
            JRPdfExporter exporter = new JRPdfExporter();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(destFile));
            /*
             SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
             configuration.setEncrypted(true);
             configuration.set128BitKey(true);
             configuration.setUserPassword("mtbadi39");
             configuration.setOwnerPassword("Souf$2977");
             configuration.setPermissions(PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING | PdfWriter.ALLOW_SCREENREADERS);
             exporter.setConfiguration(configuration);
             */
            exporter.exportReport();
        } catch (JRException e) {
            LOGGER.error(LogUtils.resolveException("JRViewerPlus", "pdfExport", e));
            AlertUtils.showError(this, null, "Erreur lors l'exportation vers PDF :\n" + e.getMessage());
        }
    }

    private void rtfExport(JasperPrint jasperPrint) {
        FileSystemView vueSysteme = FileSystemView.getFileSystemView();
        File defaut = vueSysteme.getHomeDirectory();
        JFileChooser chooser = new JFileChooser(defaut);
        chooser.setSelectedFile(new File(jasperPrint.getName() + "-" + dateFormatter.format(new Date())));

        FileFilter fileFilter = new FileNameExtensionFilter("Document au format RTF (*.rtf)", "rtf");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(fileFilter);
        chooser.setDialogTitle("Exporter vers RTF");
        int ret = chooser.showSaveDialog(this);

        if (ret != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File f = chooser.getSelectedFile();
        String name = f.getName();
        String destFile = f.getPath();
        if (!name.contains(".rtf")) {
            destFile += ".rtf";
        }
        try {
            JRRtfExporter exporter = new JRRtfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleWriterExporterOutput(destFile));
            exporter.exportReport();
        } catch (JRException e) {
            LOGGER.error(LogUtils.resolveException("JRViewerPlus", "rtfExport", e));
            AlertUtils.showError(this, null, "Erreur lors l'exportation vers RTF :\n" + e.getMessage());
        }
    }

    private void docxExport(JasperPrint jasperPrint) {
        FileSystemView vueSysteme = FileSystemView.getFileSystemView();
        File defaut = vueSysteme.getHomeDirectory();
        JFileChooser chooser = new JFileChooser(defaut);
        chooser.setSelectedFile(new File(jasperPrint.getName() + "-" + dateFormatter.format(new Date())));

        FileFilter fileFilter = new FileNameExtensionFilter("Document Word 2007(*.docx)", "docx");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(fileFilter);
        chooser.setDialogTitle("Exporter vers Word");
        int ret = chooser.showSaveDialog(this);

        if (ret != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File f = chooser.getSelectedFile();
        String name = f.getName();
        String destFile = f.getPath();
        if (!name.contains(".docx")) {
            destFile += ".docx";
        }
        try {
            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(destFile));
            exporter.exportReport();
        } catch (JRException e) {
            LOGGER.error(LogUtils.resolveException("JRViewerPlus", "docxExport", e));
            AlertUtils.showError(this, null, "Erreur lors l'exportation vers Word :\n" + e.getMessage() + "\n");
        }
    }

    private void xlsExport(JasperPrint jasperPrint) {
        FileSystemView vueSysteme = FileSystemView.getFileSystemView();
        File defaut = vueSysteme.getHomeDirectory();
        JFileChooser chooser = new JFileChooser(defaut);
        chooser.setSelectedFile(new File(jasperPrint.getName() + "-" + dateFormatter.format(new Date())));

        FileFilter fileFilter = new FileNameExtensionFilter("Classeur Excel 97-2003 (*.xls)", "xls");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(fileFilter);
        chooser.setDialogTitle("Exporter vers Excel");
        int ret = chooser.showSaveDialog(this);

        if (ret != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File f = chooser.getSelectedFile();
        String name = f.getName();
        String destFile = f.getPath();
        if (!name.contains(".xls")) {
            destFile += ".xls";
        }
        try {
            JRXlsExporter exporter = new JRXlsExporter();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(destFile));
            SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
            configuration.setOnePagePerSheet(true);
            configuration.setDetectCellType(true);
            configuration.setCollapseRowSpan(false);
            exporter.setConfiguration(configuration);

            exporter.exportReport();
        } catch (JRException e) {
            LOGGER.error(LogUtils.resolveException("JRViewerPlus", "xlsExport", e));
            AlertUtils.showError(this, null, "Erreur lors l'exportation vers Excel :\n" + e.getMessage() + "\n");
        }
    }

    private void xlsxExport(JasperPrint jasperPrint) {
        FileSystemView vueSysteme = FileSystemView.getFileSystemView();
        File defaut = vueSysteme.getHomeDirectory();
        JFileChooser chooser = new JFileChooser(defaut);
        chooser.setSelectedFile(new File(jasperPrint.getName() + "-" + dateFormatter.format(new Date())));

        FileFilter fileFilter = new FileNameExtensionFilter("Classeur Excel 2007 (*.xlsx)", "xlsx");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(fileFilter);
        chooser.setDialogTitle("Exporter vers Excel 2007");
        int ret = chooser.showSaveDialog(this);

        if (ret != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File f = chooser.getSelectedFile();
        String name = f.getName();
        String destFile = f.getPath();
        if (!name.contains(".xlsx")) {
            destFile += ".xlsx";
        }
        try {
            JRXlsxExporter exporter = new JRXlsxExporter();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(destFile));
            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setOnePagePerSheet(true);
            configuration.setDetectCellType(true);
            configuration.setCollapseRowSpan(false);
            exporter.setConfiguration(configuration);

            exporter.exportReport();
        } catch (JRException e) {
            LOGGER.error(LogUtils.resolveException("JRViewerPlus", "xlsxExport", e));
            AlertUtils.showError(this, null, "Erreur lors l'exportation vers Excel :\n" + e.getMessage() + "\n");
        }
    }
}
