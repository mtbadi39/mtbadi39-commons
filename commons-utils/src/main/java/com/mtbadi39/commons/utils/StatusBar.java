package com.mtbadi39.commons.utils;

import com.mtbadi39.commons.alert.AlertUtils;
import com.mtbadi39.commons.logging.LogUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatusBar extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(StatusBar.class);
    private Image gripperIcon;
    private final Icon userIcon;
    private final JLabel messageField;
    private final JLabel userField;
    private JLabel dateField;
    private JLabel timeField;
    private final JLabel capsLockField;
    private final JLabel numLockField;
    private final JLabel scrollLockField;
    static boolean capsBoolean = false;
    static boolean numBoolean = false;
    static boolean scrollBoolean = false;
    //private Font _font = new Font("Tahoma", 0, 11);

    public StatusBar(Boolean showDateTime) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(10, 23));
        setBackground(SystemColor.control);

        messageField = new JLabel();
        //messageField.setFont(_font);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rightPanel.setOpaque(false);
        if (showDateTime) {
            JSeparator sp1 = new JSeparator();
            sp1.setOrientation(JSeparator.VERTICAL);
            sp1.setPreferredSize(new Dimension(1, 16));
            rightPanel.add(sp1);

            dateField = new JLabel(showTodayDate(), JLabel.CENTER);
            //dateField.setFont(_font);
            dateField.setPreferredSize(new Dimension(160, 16));
            rightPanel.add(dateField);

            JSeparator sp2 = new JSeparator();
            sp2.setOrientation(JSeparator.VERTICAL);
            sp2.setPreferredSize(new Dimension(1, 16));
            rightPanel.add(sp2);

            timeField = new JLabel("00:00:01", JLabel.CENTER);
            //timeField.setFont(_font);
            timeField.setPreferredSize(new Dimension(44, 16));
            showSysTime(timeField);
            rightPanel.add(timeField);
        }

        JSeparator sp3 = new JSeparator();
        sp3.setOrientation(JSeparator.VERTICAL);
        sp3.setPreferredSize(new Dimension(1, 16));
        rightPanel.add(sp3);

        userIcon = new ImageIcon(getClass().getResource("/images/statusbar/user2.png"));
        userField = new JLabel(userIcon, JLabel.LEADING);
        //userField = new JLabel();
        //userField.setFont(_font);
        userField.setPreferredSize(new Dimension(300, 16));
        rightPanel.add(userField);

        JSeparator sp4 = new JSeparator();
        sp4.setOrientation(JSeparator.VERTICAL);
        sp4.setPreferredSize(new Dimension(1, 16));
        rightPanel.add(sp4);

        capsLockField = new JLabel("MAJ", JLabel.CENTER);
        capsLockField.setPreferredSize(new Dimension(30, 16));
        //capsLockField.setFont(_font);
        capsBoolean = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
        getCapsStatus();
        rightPanel.add(capsLockField);

        JSeparator sp5 = new JSeparator();
        sp5.setOrientation(JSeparator.VERTICAL);
        sp5.setPreferredSize(new Dimension(1, 16));
        rightPanel.add(sp5);

        numLockField = new JLabel("NUM", JLabel.CENTER);
        numLockField.setPreferredSize(new Dimension(30, 16));
        //numLockField.setFont(_font);
        numBoolean = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_NUM_LOCK);
        getNumStatus();
        rightPanel.add(numLockField);

        JSeparator sp6 = new JSeparator();
        sp6.setOrientation(JSeparator.VERTICAL);
        sp6.setPreferredSize(new Dimension(1, 16));
        rightPanel.add(sp6);

        scrollLockField = new JLabel("SCRL", JLabel.CENTER);
        scrollLockField.setPreferredSize(new Dimension(28, 16));
        //scrollLockField.setFont(_font);
        scrollBoolean = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_SCROLL_LOCK);
        getScrollStatus();
        rightPanel.add(scrollLockField);

        JSeparator sp7 = new JSeparator();
        sp7.setOrientation(JSeparator.VERTICAL);
        sp7.setPreferredSize(new Dimension(1, 16));
        rightPanel.add(sp7);

        JLabel lbl = new JLabel();
        lbl.setPreferredSize(new Dimension(16, 16));
        rightPanel.add(lbl);
        //rightPanel.add(new JLabel(new AngledLinesWindowsCornerIcon(), JLabel.TRAILING));

        add(messageField, BorderLayout.CENTER);
        if ("ar".equals(Locale.getDefault().getLanguage())) {
            add(rightPanel, BorderLayout.WEST);
        } else {
            add(rightPanel, BorderLayout.EAST);
        }

        UIUtils.applyOrientation((Container) this);

    }

    public void traiteKeyEvent(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_CAPS_LOCK) {
            capsBoolean = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
            getCapsStatus();
        }
        if (ke.getKeyCode() == KeyEvent.VK_NUM_LOCK) {
            numBoolean = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_NUM_LOCK);
            getNumStatus();
        }
        if (ke.getKeyCode() == KeyEvent.VK_SCROLL_LOCK) {
            scrollBoolean = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_SCROLL_LOCK);
            getScrollStatus();
        }

    }

    /*
     * public void addKeyListener(Component c) {
     * System.out.println("c.getName()=" + c.getClass()); c.addKeyListener(new
     * KeyAdapter() {
     *
     * @Override public void keyReleased(KeyEvent ke) {
     * System.out.println("ke.getKeyCode()=" + ke.getKeyCode()); if
     * (ke.getKeyCode() == KeyEvent.VK_CAPS_LOCK) { capsBoolean =
     * Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
     * getCapsStatus(); } if (ke.getKeyCode() == KeyEvent.VK_NUM_LOCK) {
     * numBoolean =
     * Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_NUM_LOCK);
     * getNumStatus(); } if (ke.getKeyCode() == KeyEvent.VK_SCROLL_LOCK) {
     * scrollBoolean =
     * Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_SCROLL_LOCK);
     * getScrollStatus(); } } }); }
     */
    public void setMessage(String message) {
        messageField.setText(message);
    }

    public void clearMessage() {
        messageField.setText("");
    }

    public void setUser(String username) {
        userField.setText(username);
    }

    public void clearUser() {
        userField.setText("");
    }

    private void getCapsStatus() {
        if (capsBoolean == true) {
            capsLockField.setEnabled(true);
        }
        if (capsBoolean == false) {
            capsLockField.setEnabled(false);
        }
    }

    private void getNumStatus() {
        if (numBoolean == true) {
            numLockField.setEnabled(true);
        }
        if (numBoolean == false) {
            numLockField.setEnabled(false);
        }
    }

    private void getScrollStatus() {
        if (scrollBoolean == true) {
            scrollLockField.setEnabled(true);
        }
        if (scrollBoolean == false) {
            scrollLockField.setEnabled(false);
        }
    }

    private String showTodayDate() {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMMM yyyy");
        return sdf.format(dt);
    }

    private void showSysTime(final JLabel tf) {
        final SimpleDateFormat timef = new SimpleDateFormat("HH:mm:ss");
        String s = timef.format(new Date(System.currentTimeMillis()));
        tf.setText(s);
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = timef.format(new Date(System.currentTimeMillis()));
                tf.setText(s);
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);
            int y;
            int x;
            /*
             URL url;
             if ("ar".equals(Locale.getDefault().getLanguage())) {
             url = StatusBar.class.getResource("/images/gripper_ar.png");
             gripperIcon = new ImageIcon(url).getImage();
             x = 1;
             } else {
             url = StatusBar.class.getResource("/images/gripper_fr.png");
             gripperIcon = new ImageIcon(url).getImage();
             x = getWidth() - gripperIcon.getWidth(null) - 1;
             }

             g.drawImage(gripperIcon, x, getHeight() - gripperIcon.getHeight(null) - 1, this);
             */
            y = 0;
            g.setColor(new Color(156, 154, 140));
            g.drawLine(0, y, getWidth(), y);
            y++;
            g.setColor(new Color(196, 194, 183));
            g.drawLine(0, y, getWidth(), y);
            y++;
            g.setColor(new Color(218, 215, 201));
            g.drawLine(0, y, getWidth(), y);
            y++;
            g.setColor(new Color(233, 231, 217));
            g.drawLine(0, y, getWidth(), y);

            y = getHeight() - 3;
            g.setColor(new Color(233, 232, 218));
            g.drawLine(0, y, getWidth(), y);
            y++;
            g.setColor(new Color(233, 231, 216));
            g.drawLine(0, y, getWidth(), y);
            y = getHeight() - 1;
            g.setColor(new Color(221, 221, 220));
            g.drawLine(0, y, getWidth(), y);
        } catch (Exception e) {
            LOGGER.error(LogUtils.resolveException("StatusBar", "paintComponent", e));
            AlertUtils.showError(null, null, "Une erreur s'est produite :\n" + e.getLocalizedMessage());
        }
    }
}
