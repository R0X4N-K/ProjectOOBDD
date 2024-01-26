package gui;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class Window extends JFrame {
    private static final Logger logger = Logger.getLogger(Window.class.getName());
    private JPanel mainPanelWindow;
    private JPanel toolbarPanel;
    private JPanel windowPane;
    private JPanel homePanel;
    private JPanel loginPanel;
    private JPanel registrationPanel;

    public Window() throws HeadlessException {
        if (GraphicsEnvironment.isHeadless()) {
            logger.severe("Ambiente senza display grafico. Non posso creare JFrame.");
            throw new HeadlessException("Ambiente senza display grafico. Non posso creare JFrame.");
        }
        createUIComponents();
        JFrame frame = new JFrame("Million Wiki");
        frame.setContentPane(mainPanelWindow);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // Inizializzazione prima di tutti i componenti
        mainPanelWindow = new JPanel();
        mainPanelWindow.setLayout(new BorderLayout(0, 0));
        windowPane = new JPanel();
        windowPane.setLayout(new CardLayout(0, 0));
        mainPanelWindow.add(windowPane, BorderLayout.CENTER);
        // Poi chiamata a costruttori delle altre classi
        toolbarPanel = new Toolbar(this).getPanel();
        homePanel = new Home(this).getPanel();
        loginPanel = new Login(this).getPanel();
        registrationPanel = new Registration(this).getPanel();
        // Aggiunta dei componenti al pannello principale
        toolbarPanel.setVisible(true);
        mainPanelWindow.add(toolbarPanel, BorderLayout.NORTH);
        // Aggiunta dei componenti al pannello windowpane
        windowPane.add(homePanel, "Card1");
        windowPane.add(loginPanel, "Card2");
        windowPane.add(registrationPanel, "Card3");
    }

    public void switchPanel(JPanel refPanel) {
        windowPane.removeAll();
        windowPane.add(refPanel);
        windowPane.repaint();
        windowPane.revalidate();
    }

    public JPanel getHomePanel() {
        return this.homePanel;
    }

    public JPanel getLoginPanel() {
        return this.loginPanel;
    }

    public JPanel getRegistrationPanel() {
        return this.registrationPanel;
    }

}
