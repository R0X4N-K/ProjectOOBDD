package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JLabel effettuaIlTuoAccessoLabel;
    private JLabel emailONicknameLabel;
    private JTextField textField1;
    private JLabel passwordLabel;
    private JPasswordField passwordField1;
    private JButton entra;
    private JButton NonRegistrato;
    private JPanel mainPanelLogin;
    private JButton ritornaHome;
    private Window window;

    public Login() {

        ritornaHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window = Window.checkWindow(window, mainPanelLogin);
                window.switchPanel(window.getHomePanel());
            }
        });
        NonRegistrato.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window = Window.checkWindow(window, mainPanelLogin);
                window.switchPanel(window.getRegistrationPanel());
            }
        });



    }



    public JPanel getPanel() {
        return mainPanelLogin;
    }
}
