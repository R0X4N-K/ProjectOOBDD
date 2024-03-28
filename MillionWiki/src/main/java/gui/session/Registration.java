package gui.session;

import controller.Controller;
import gui.Window;
import model.Cookie;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

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
    private JLabel passwordErrLbl;
    private JLabel passwordErrLbl2;
    private JLabel nicknameErrLbl;
    private JLabel mailErrLbl;
    private JCheckBox rembemberMeCheckbox;
    private Boolean submitBtnState;

    public Registration() {
        toLoginPanelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getWindow().switchPanel(Controller.getWindow().getLoginPanel());
            }
        });
        toHomePanelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getWindow().switchPanel(Controller.getWindow().getHomePanel());
            }
        });


        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(nicknameTxtFld.getText());
                System.out.println(mailTxtFld.getText());
                System.out.println(passwordTxtFld.getText());
                String passwordEncrypted = passwordEncryption(passwordTxtFld.getText());

                if (Controller.doRegistration(mailTxtFld.getText(), nicknameTxtFld.getText(), passwordEncrypted)) {
                    System.out.println("Registrazione avvenuta con successo");


                    try {
                        Cookie c = new Cookie(Controller.getAuthorByNickname(nicknameTxtFld.getText()).getId(), passwordEncrypted);
                        Controller.setCookie(c);
                        Window.switchToLoggedWindow(Controller.getWindow(), c);
                        if (rembemberMeCheckbox.isSelected()) {
                            c.writeCookie();
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    System.out.println("Registrazione fallita");
                }
            }
        });

        nicknameTxtFld.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                checkNicknameFld(nicknameTxtFld.getText());
                checkAllFld();
            }
        });

        mailTxtFld.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                checkEmailFld(mailTxtFld.getText());
                checkAllFld();
            }
        });

        passwordTxtFld.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                checkPasswordFld(passwordTxtFld.getText());
                checkAllFld();
            }
        });
        passwordTxtFld2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                checkPasswordFld2(passwordTxtFld2.getText());
                checkAllFld();
            }
        });

    }

    public void checkAllFld() {
        submitBtn.setEnabled(nicknameErrLbl.getForeground().equals(Color.GREEN) && mailErrLbl.getForeground().equals(Color.GREEN) && passwordErrLbl.getForeground().equals(Color.GREEN) && passwordErrLbl2.getForeground().equals(Color.GREEN));
    }


    public void checkNicknameFld(String text) {
        if (checkNickname(text) && !checkNicknameIsRegistered(text)) {
            nicknameTxtFld.setBorder(new LineBorder(Color.GREEN));
            setErrLbl(nicknameErrLbl, true, "Nickname valido !", Color.GREEN);
        } else {
            nicknameTxtFld.setBorder(new LineBorder(Color.RED));
            setErrLbl(nicknameErrLbl, true, "Nickname non valido !", Color.RED);
        }
    }

    public void checkEmailFld(String text) {
        if (checkEmailSyntax(text) && !checkEmailIsRegistered(text)) {
            mailTxtFld.setBorder(new LineBorder(Color.GREEN));
            setErrLbl(mailErrLbl, true, "Mail valida !", Color.GREEN);
        } else {
            mailTxtFld.setBorder(new LineBorder(Color.RED));
            setErrLbl(mailErrLbl, true, "Mail non valida !", Color.RED);
        }
    }

    public void checkPasswordFld(String text) {
        if (checkPasswordSintax(text)) {
            passwordTxtFld.setBorder(new LineBorder(Color.GREEN));
            setErrLbl(passwordErrLbl, true, "Password valida !", Color.GREEN);
        } else {
            passwordTxtFld.setBorder(new LineBorder(Color.RED));
            setErrLbl(passwordErrLbl, true, "Password non valida !", Color.RED);
        }
    }

    public void checkPasswordFld2(String text) {
        if (passwordTxtFld.getText().equals(passwordTxtFld2.getText()) && !passwordTxtFld.getText().isBlank() && !passwordTxtFld2.getText().isBlank()) {
            passwordTxtFld2.setBorder(new LineBorder(Color.GREEN));
            setErrLbl(passwordErrLbl2, true, "Le password corrispondono !", Color.GREEN);
        } else {
            passwordTxtFld2.setBorder(new LineBorder(Color.RED));
            setErrLbl(passwordErrLbl2, true, "Le password non corrispondono !", Color.RED);
        }
    }


    public boolean checkEmailSyntax(String text) {
        String regex = "^[\\w!#$%&'*+/=?^`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^`{|}~-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        return text.matches(regex);
    }

    public boolean checkNickname(String text) {
        return text.length() >= 4 && text.length() <= 18;
    }

    public boolean checkEmailIsRegistered(String email) {
        return Controller.isAuthorRegisteredWithEmail(email);
    }

    public boolean checkNicknameIsRegistered(String nickname) {
        return Controller.isAuthorRegisteredWithNickname(nickname);
    }


    //aggiunte funzioni per hashing della password
    public String passwordEncryption(String password) {
        //hashing algorithm
        try {
            return generateStrongPasswordHash(password);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateStrongPasswordHash(String password)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private String toHex(byte[] array) throws NoSuchAlgorithmException {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);

        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }


    public boolean checkPasswordSintax(String text) {
        // Controlla la lunghezza della password
        if (text.length() < 8 || text.length() > 20) {
            return false;
        }

        // Controlla se la password contiene almeno una lettera maiuscola
        boolean hasUpperCase = false;
        for (char c : text.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
                break;
            }
        }
        if (!hasUpperCase) {
            return false;
        }

        // Controlla se la password contiene almeno una lettera minuscola
        boolean hasLowerCase = false;
        for (char c : text.toCharArray()) {
            if (Character.isLowerCase(c)) {
                hasLowerCase = true;
                break;
            }
        }
        if (!hasLowerCase) {
            return false;
        }

        // Controlla se la password contiene almeno un numero
        boolean hasNumber = false;
        for (char c : text.toCharArray()) {
            if (Character.isDigit(c)) {
                hasNumber = true;
                break;
            }
        }
        if (!hasNumber) {
            return false;
        }

        // Controlla se la password contiene almeno un simbolo speciale
        boolean hasSpecialSymbol = false;
        for (char c : text.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                hasSpecialSymbol = true;
                break;
            }
        }
        return hasSpecialSymbol;

        // La password è valida
    }

    public void setErrLbl(JLabel lblTarget, Boolean visibleState, String msg, Color msgFgColor) {
        lblTarget.setVisible(visibleState);
        lblTarget.setText(msg);
        lblTarget.setForeground(msgFgColor);

    }


    public JPanel getPanel() {
        return mainPanelRegistration;
    }

}
