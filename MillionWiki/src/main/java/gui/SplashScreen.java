package gui;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JFrame {
    private JPanel mainPanelSplashScreen;

    public SplashScreen() {
        setLayout(new BorderLayout());
        setUndecorated(true);
        setAlwaysOnTop(true);
        setSize(1200, 500);
        setPreferredSize(new Dimension(1200, 500));

        add(mainPanelSplashScreen);

        setLocationRelativeTo(null);
        setVisible(true);

    }

    public JPanel getPanel() {
        return mainPanelSplashScreen;
    }
}
