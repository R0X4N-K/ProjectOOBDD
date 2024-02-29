package gui.Toolbar;

import gui.Window;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnloggedToolbar {

    private JButton loginBtn;
    private JButton registrationBtn;
    private JPanel unloggedUserPanel;
    private Window window;
    public UnloggedToolbar(){
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window = Window.checkWindow(window, getPanel());
                window.switchPanel(window.getLoginPanel());
            }
        });
        registrationBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window = Window.checkWindow(window, getPanel());
                window.switchPanel(window.getRegistrationPanel());
            }
        });
    }


    public JPanel getPanel() {
        return unloggedUserPanel;
    }
}
