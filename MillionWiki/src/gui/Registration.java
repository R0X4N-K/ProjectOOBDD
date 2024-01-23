package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registration {
    private JLabel effettuaLaTuaRegistrazioneLabel;
    private JLabel inserisciLaTuaMailLabel;
    private JTextField mailTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordTextField;
    private JButton registratiButton;
    private JButton loginButton;
    private JButton ritornaHomeButton;
    private JLabel nickNameLabel;
    private JTextField nickNameTextField;
    private JLabel passwordLabel2;
    private JPasswordField passwordTextField2;
    private JPanel mainPanelRegistration;
    private Window window;

    public Registration(Window window) {
        this.window = window;
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.switchPanel(window.getLoginPanel());
            }
        });
        ritornaHomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.switchPanel(window.getHomePanel());
            }
        });

    }
    public JPanel getPanel() {
        return mainPanelRegistration;
    }
}
