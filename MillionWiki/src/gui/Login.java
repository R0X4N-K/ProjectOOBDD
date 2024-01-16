package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JTextArea entraTextArea;
    private JTextArea loginTextArea;
    private JTextField textField1;
    private JTextArea passwordTextArea;
    private JPasswordField passwordField1;
    private JButton entra;
    private JButton NonRegistrato;
    private JPanel mainPanelLogin;

    public Login() {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(mainPanelLogin);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
