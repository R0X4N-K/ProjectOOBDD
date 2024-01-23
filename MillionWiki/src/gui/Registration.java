package gui;

import javax.swing.*;

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
        /*JFrame frame = new JFrame("Login");
        frame.setContentPane(mainPanelRegistration);
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
        });*/

    }
    public JPanel getPanel() {
        return mainPanelRegistration;
    }
}
