package gui;

import javax.swing.*;

public class Window {
    private JPanel mainPanelWindow;
    private JPanel toolbarPanel;
    private JPanel windowPane;
    private Toolbar toolbar;
    private Home home;
    private Login login;
    private Registration registration;

    public Window(){
        JFrame frame = new JFrame("Million Wiki");
        frame.setContentPane(mainPanelWindow);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
