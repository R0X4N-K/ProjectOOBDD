package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registrati {
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
    private JPanel mainPanelRegistrati;

    public Registrati() {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(mainPanelRegistrati);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                new Login();
            }
        });
        ritornaHomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                new Home();
            }
        });
    }
}
