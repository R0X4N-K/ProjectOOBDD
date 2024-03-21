package gui.session;

import controller.Controller;
import gui.Window;
import model.Cookie;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

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
    private JCheckBox rembemberMeCheckbox;
    private JPanel passwordPanel;
    private JToggleButton viewPasswordBtn;
    private Window window;
    private Controller controller;

    public Login() {

        toHomePanelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window = Window.checkWindow(window, getPanel());
                window.switchPanel(window.getHomePanel());
            }
        });
        toRegistrationPanelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window = Window.checkWindow(window, getPanel());
                window.switchPanel(window.getRegistrationPanel());
            }
        });

        viewPasswordBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Funzione mostra password

            }
        });

        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Cookie c = Controller.doLogin(emailNicknameTxtFld.getText(), passwordTxtFld.getText());
                    String passwordStored = c.getPassword();
                    if (validatePassword(passwordTxtFld.getText(), passwordStored)) {
                        System.out.println("Loggato");
                        window = Window.checkWindow(window, getPanel());
                        window.switchToLoggedWindow(window, c);
                        if (rembemberMeCheckbox.isSelected()) {
                            c.writeCookie();
                        }
                        Controller.setCookie(c);
                    } else {
                        System.out.println("Stuprato");
                    }
                } catch (NullPointerException pointerException) {
                    System.out.println("Stuprato");
                    throw new NullPointerException();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });

    }

    private boolean validatePassword(String originalPassword, String storedPassword) {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);

        byte[] salt = new byte[0];
        try {
            salt = fromHex(parts[1]);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash = new byte[0];
        try {
            hash = fromHex(parts[2]);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(),
                salt, iterations, hash.length * 8);
        SecretKeyFactory skf = null;
        try {
            skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] testHash = new byte[0];
        try {
            testHash = skf.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        int diff = hash.length ^ testHash.length;
        for (int i = 0; i < hash.length && i < testHash.length; i++) {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    private byte[] fromHex(String hex) throws NoSuchAlgorithmException {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    public JPanel getPanel() {
        return mainPanelLogin;
    }

}
