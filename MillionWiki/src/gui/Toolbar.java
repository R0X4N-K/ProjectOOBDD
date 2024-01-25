package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar {
    private JPanel mainToolbar;
    private JButton homeButton;
    private JTextField textField1;
    private JPanel unloggedUserPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JPanel Toolbar;
    private Window window;
    public Toolbar() {

    }
    public Toolbar(Window window) {
        this.window = window;
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.switchPanel(window.getHomePanel());
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.switchPanel(window.getLoginPanel());
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.switchPanel(window.getRegistrationPanel());
            }
        });
    }

    public JPanel getPanel() {
        return Toolbar;
    }

}
