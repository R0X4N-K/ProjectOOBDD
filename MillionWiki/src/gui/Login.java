package gui;

import javax.swing.*;
import java.awt.*;
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

    public Login(Window window) {

        this.window = window;

        /*JFrame frame = new JFrame("Login");
        frame.setContentPane(mainPanelLogin);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        ritornaHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                new Home();
            }
        });
        NonRegistrato.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                new Registration();

            }
        });*/


    }
    public JPanel getPanel() {
        return mainPanelLogin;
    }
}
