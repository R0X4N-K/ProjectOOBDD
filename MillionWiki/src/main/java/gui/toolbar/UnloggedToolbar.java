package gui.toolbar;

import gui.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.Controller;

public class UnloggedToolbar {

    private JButton loginBtn;
    private JButton registrationBtn;
    private JPanel unloggedUserPanel;

    public UnloggedToolbar() {
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getWindow().switchPanel(Controller.getWindow().getLoginPanel());
            }
        });
        registrationBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Controller.getWindow().switchPanel(Controller.getWindow().getRegistrationPanel());
            }
        });
    }


    public JPanel getPanel() {
        return unloggedUserPanel;
    }

}
