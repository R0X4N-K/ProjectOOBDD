package gui;

import javax.swing.*;

public class Window {
    private JPanel mainPanelWindow;
    private JPanel toolbarPanel;
    private JPanel mainToolbar;
    private JPanel unloggedUserPanel;
    private JPanel windowPane;
    
    private JButton homeButton;
    private JTextField textField1;
    private JButton loginButton;
    private JButton registerButton;

    public Window(){
        JFrame frame = new JFrame("Home");
        frame.setContentPane(mainPanelWindow);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
