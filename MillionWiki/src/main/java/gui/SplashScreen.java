package gui;

import controller.Controller;


import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SplashScreen extends JFrame {
    private JPanel mainPanelSplashScreen;
    private JProgressBar progressBar;


    public SplashScreen() {
        setLayout(new BorderLayout());
        setUndecorated(true);
        setAlwaysOnTop(true);
        setSize(1200, 500);
        setPreferredSize(new Dimension(1200, 500));
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        setResizable(false);

        add(mainPanelSplashScreen, BorderLayout.CENTER);
        add(createLogsFrame(), BorderLayout.SOUTH);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);


    }

    private JPanel createLogsFrame() {
        progressBar = new JProgressBar(0, 20);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setForeground(Color.decode("#2c82c9"));
        progressBar.setBackground(Color.BLACK);

        progressBar.setUI(new BasicProgressBarUI() {
            protected Color getSelectionBackground() { return Color.WHITE; }
            protected Color getSelectionForeground() { return Color.WHITE; }
        });


        Font font = new Font("Arial", Font.BOLD, 16);  // Crea un nuovo font con dimensione 16
        progressBar.setFont(font);

        JPanel logsPanel = new JPanel(new BorderLayout());
        logsPanel.setBackground(Color.black);

        logsPanel.add(progressBar, BorderLayout.CENTER);

        logsPanel.revalidate();
        logsPanel.repaint();

        return logsPanel;

    }

    public void incrementProgressBar(){
        progressBar.setValue(progressBar.getValue() + 1);

    }


    public JPanel getPanel() {
        return mainPanelSplashScreen;
    }
}
