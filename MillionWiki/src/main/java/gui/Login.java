package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class Login {
    private JLabel loginPageTitleLbl;
    private JLabel emailNicknameLbl;
    private JTextField emailNicknameTxtFld;
    private JLabel passwordLbl;
    private JPasswordField passwordTxtFld;
    private JButton submitBtn;
    private JButton toRegistrationPanelBtn;
    private JPanel mainPanelLogin;
    private JButton toHomePanelBtn;
    private JLabel emailNicknameErrLbl;
    private JLabel passwordErrLbl;
    private Window window;
    private Controller controller;

    public Login() {

        toHomePanelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window = Window.checkWindow(window, mainPanelLogin);
                window.switchPanel(window.getHomePanel());
            }
        });
        toRegistrationPanelBtn.addActionListener(new ActionListener() {
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
