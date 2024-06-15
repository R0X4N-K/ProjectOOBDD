package gui.session;

import controller.Controller;
import gui.ErrorDisplayer;
import gui.Window;
import model.Cookie;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.*;
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
    private JLabel emailNicknameErrLbl;
    private JLabel passwordErrLbl;
    private JCheckBox rembemberMeCheckbox;
    private JPanel passwordPanel;
    private JToggleButton viewPasswordBtn;

    public Login() {
        toRegistrationPanelBtn.addActionListener(e -> Controller.getWindow().switchPanel(Controller.getWindow().getRegistrationPanel()));

        viewPasswordBtn.addActionListener(actionEvent -> {
            //Funzione mostra password
            if (viewPasswordBtn.isSelected())
                passwordTxtFld.setEchoChar((char) 0);
            else
                passwordTxtFld.setEchoChar('•');
        });

        submitBtn.addActionListener(e -> submit());

    }

    private void submit() {
        try {
            Cookie c = Controller.doLogin(emailNicknameTxtFld.getText(), passwordTxtFld.getText());
            String passwordStored = c.getPassword();
            if (validatePassword(passwordTxtFld.getText(), passwordStored)) {
                System.out.println("Loggato");
                if (rembemberMeCheckbox.isSelected()) {
                    c.writeCookie();
                }
                Controller.setCookie(c);
                Window.switchToLoggedWindow(Controller.getWindow());
            } else {
                ErrorDisplayer.showErrorWithActions(null, "Password errata", "La password inserita non è corretta", e1 -> submit(), null, Controller.getWindow());
            }
        } catch (NullPointerException pointerException) {
            ErrorDisplayer.showErrorWithActions(pointerException, null, null, e1 -> submit(), null, Controller.getWindow());
        } catch (SQLException ex) {
            ErrorDisplayer.showErrorWithActions(ex, "Errore di Database", null, e1 -> submit(), null, Controller.getWindow());
        }
    }

    private boolean validatePassword(String originalPassword, String storedPassword) {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);

        byte[] salt = new byte[0];
        try {
            salt = fromHex(parts[1]);
        } catch (NoSuchAlgorithmException e) {
            ErrorDisplayer.showError(e);
        }
        byte[] hash = new byte[0];
        try {
            hash = fromHex(parts[2]);
        } catch (NoSuchAlgorithmException e) {
            ErrorDisplayer.showError(e);
        }

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(),
                salt, iterations, hash.length * 8);
        SecretKeyFactory skf = null;
        try {
            skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            ErrorDisplayer.showError(e);
        }
        byte[] testHash = new byte[0];
        try {
            testHash = skf.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            ErrorDisplayer.showError(e);
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
