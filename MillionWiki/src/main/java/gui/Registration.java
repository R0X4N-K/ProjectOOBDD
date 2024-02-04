package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registration {
    private JTextField mailTxtFld;
    private JLabel passwordLbl;
    private JPasswordField passwordTxtFld;
    private JButton submitBtn;
    private JButton toLoginPanelBtn;
    private JButton toHomePanelBtn;
    private JLabel nicknameLbl;
    private JTextField nicknameTxtFld;
    private JLabel passwordLbl2;
    private JPasswordField passwordTxtFld2;
    private JPanel mainPanelRegistration;
    private JLabel mailLbl;
    private JLabel registrationPanelTitleLbl;
    private Window window;

    public Registration() {
        toLoginPanelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window = Window.checkWindow(window, mainPanelRegistration);
                window.switchPanel(window.getLoginPanel());
            }
        });
        toHomePanelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window = Window.checkWindow(window, mainPanelRegistration);
                window.switchPanel(window.getHomePanel());
            }
        });

    }
    public JPanel getPanel() {
        return mainPanelRegistration;
    }
}
