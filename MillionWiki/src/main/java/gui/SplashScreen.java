package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;

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
        add(mainPanelSplashScreen);

        setLocationRelativeTo(null);
        setVisible(true);

        createLogsFrame();

    }

    private void createLogsFrame() {
        JDialog logsDialog = new JDialog(this);
        logsDialog.setLayout(new BorderLayout());
        logsDialog.setAlwaysOnTop(true);
        logsDialog.setUndecorated(true);
        logsDialog.setSize(new Dimension(1200, 20));  // Dimensione per occupare tutta la larghezza
        logsDialog.setResizable(false);

        Point location = this.getLocation();
        int x = location.x;
        int y = location.y + this.getHeight() - logsDialog.getHeight();  // Posizione per essere in basso

        logsDialog.setLocation(x, y);  // Imposta la posizione calcolata


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

        logsDialog.add(logsPanel, BorderLayout.CENTER);
        logsDialog.setVisible(true);


        logsPanel.revalidate();
        logsPanel.repaint();

    }

    public void incrementProgressBar(){
        progressBar.setValue(progressBar.getValue() + 1);

    }


    public JPanel getPanel() {
        return mainPanelSplashScreen;
    }
}
