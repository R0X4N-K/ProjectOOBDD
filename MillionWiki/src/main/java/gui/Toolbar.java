package gui;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar {
    private JPanel commonToolbar;
    private JButton homeBtn;
    private JTextField searchTxtFld;
    private JPanel unloggedUserPanel;
    private JButton loginBtn;
    private JButton registrationBtn;
    private JPanel mainPanelToolbar;
    private Window window;
    public Toolbar() {
        homeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window = Window.checkWindow(window, mainPanelToolbar);
                window.switchPanel(window.getHomePanel());
            }
        });
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window = Window.checkWindow(window, mainPanelToolbar);
                window.switchPanel(window.getLoginPanel());
            }
        });
        registrationBtn.addActionListener(new ActionListener() {
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
