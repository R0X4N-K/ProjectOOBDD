package gui;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar {
    private JPanel commonToolbar;
    private JButton homeButton;
    private JTextField textField1;
    private JPanel unloggedUserPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JPanel mainPanelToolbar;
    private Window window;
    public Toolbar() {
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window = Window.checkWindow(window, mainPanelToolbar);
                window.switchPanel(window.getHomePanel());
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window = Window.checkWindow(window, mainPanelToolbar);
                window.switchPanel(window.getLoginPanel());
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window = Window.checkWindow(window, mainPanelToolbar);
                window.switchPanel(window.getRegistrationPanel());
            }
        });
    }
    public JPanel getPanel() {
        return mainPanelToolbar;
    }
}
