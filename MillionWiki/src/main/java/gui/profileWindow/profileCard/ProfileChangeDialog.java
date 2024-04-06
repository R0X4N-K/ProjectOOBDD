package gui.profileWindow.profileCard;

import controller.Controller;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class ProfileChangeDialog extends JDialog {
    private JTextField inputField;
    private JButton confirmButton;
    private JPanel profileChangeDialogMainPanel;
    private JPasswordField passwordField;
    private JPanel inputFieldJPanel;
    private String changeType;
    private ProfileCard profileCard;
    private CardLayout cardLayout;

    public ProfileChangeDialog(String changeType, ProfileCard profileCard) {
        this.changeType = changeType;
        this.profileCard = profileCard;
        confirmButton.setEnabled(false);
        Dimension preferredSize = new Dimension(200, 30);
        inputField.setPreferredSize(preferredSize);
        passwordField.setPreferredSize(preferredSize);

        // Ottieni il CardLayout e imposta il campo di input appropriato
        cardLayout = (CardLayout) inputFieldJPanel.getLayout();
        if (changeType.equals("Password")) {
            cardLayout.show(inputFieldJPanel, "Card2");
            inputField = passwordField;
        } else {
            cardLayout.show(inputFieldJPanel, "Card1");
        }

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newValue = inputField.getText();
                switch (changeType) {
                    case "Nickname":
                        Controller.changeNickname(newValue);
                        break;
                    case "Password":
                        Controller.changePassword(passwordEncryption(newValue));
                        break;
                    case "Email":
                        Controller.changeEmail(newValue);
                        break;
                }
                profileCard.setProfile();
                dispose();
            }
        });

        // Aggiungi un DocumentListener al JTextField
        inputField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validate();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validate();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validate();
            }

            // Metodo di validazione
            void validate() {
                String inputText = inputField.getText().trim();
                boolean isValid = false;
                if (inputText.isEmpty()) {
                    confirmButton.setEnabled(false);
                } else {
                    switch (changeType) {
                        case "Nickname":
                            isValid = checkNickname(inputText);
                            break;
                        case "Password":
                            isValid = checkPasswordSintax(inputText);
                            break;
                        case "Email":
                            isValid = checkEmailSyntax(inputText);
                            break;
                    }
                    confirmButton.setEnabled(isValid);
                }
            }
        });

        setModal(true);
        setContentPane(profileChangeDialogMainPanel);
        pack();
        setLocationRelativeTo(null);
        // Il tasto invio preme conferma
        getRootPane().setDefaultButton(confirmButton);
    }

    public boolean checkEmailSyntax(String text) {
        String regex = "^[\\w!#$%&'*+/=?^`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^`{|}~-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        return text.matches(regex);
    }

    public boolean checkNickname(String text) {
        return text.length() >= 4 && text.length() <= 18;
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
    }

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
}