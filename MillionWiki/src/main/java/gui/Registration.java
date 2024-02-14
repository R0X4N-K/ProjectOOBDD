package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

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
    private JLabel passwordErrLbl;
    private JLabel passwordErrLbl2;
    private JLabel nicknameErrLbl;
    private JLabel mailErrLbl;
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
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(nicknameTxtFld.getText());
                System.out.println(mailTxtFld.getText());
                System.out.println(passwordTxtFld.getText());
            }
        });

        nicknameTxtFld.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                checkNicknameFld(nicknameTxtFld.getText());
            }
        });

        mailTxtFld.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                checkEmailFld(mailTxtFld.getText());
            }
        });

        passwordTxtFld.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                checkPasswordFld(passwordTxtFld.getText());
            }
        });
        passwordTxtFld2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                checkPasswordFld2(passwordTxtFld2.getText());
            }
        });

    }


    public void checkNicknameFld(String text) {
        if (checkNickname(text) && !checkNicknameIsRegistered(text)) {
            nicknameTxtFld.setBorder(new LineBorder(Color.GREEN));
            setErrLbl(nicknameErrLbl, true, "Nickname valido !", Color.GREEN);
        }
        else{
            nicknameTxtFld.setBorder(new LineBorder(Color.RED));
            setErrLbl(nicknameErrLbl, true, "Nickname non valido !", Color.RED);
        }
    }
    public void checkEmailFld(String text) {
        if (checkEmailSyntax(text) && !checkEmailIsRegistered(text)) {
            mailTxtFld.setBorder(new LineBorder(Color.GREEN));
            setErrLbl(mailErrLbl, true, "Mail valida !", Color.GREEN);
        }
        else{
            mailTxtFld.setBorder(new LineBorder(Color.RED));
            setErrLbl(mailErrLbl, true, "Mail non valida !", Color.RED);
        }
    }

    public void checkPasswordFld(String text) {
        if(checkPasswordSintax(text)){
            passwordTxtFld.setBorder(new LineBorder(Color.GREEN));
            setErrLbl(passwordErrLbl, true, "Password valida !", Color.GREEN);
        }
        else{
            passwordTxtFld.setBorder(new LineBorder(Color.RED));
            setErrLbl(passwordErrLbl, true, "Password non valida !", Color.RED);
        }
    }

    public void checkPasswordFld2(String text) {
        if(passwordTxtFld.getText().equals(passwordTxtFld2.getText()) && !passwordTxtFld.getText().isBlank()&& !passwordTxtFld2.getText().isBlank()){
            passwordTxtFld2.setBorder(new LineBorder(Color.GREEN));
            setErrLbl(passwordErrLbl2, true, "Le password corrispondono !", Color.GREEN);
        }
        else{
            passwordTxtFld2.setBorder(new LineBorder(Color.RED));
            setErrLbl(passwordErrLbl2, true, "Le password non corrispondono !", Color.RED);
        }
    }


    public boolean checkEmailSyntax(String text){
        String regex = "^[\\w!#$%&'*+/=?^`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^`{|}~-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        if(text.matches(regex)){
            return true;
        }
        return false;
    }

    public boolean checkNickname(String text){
        if (text.length() < 4 || text.length() > 18) {
            return false;
        }
        return true;
    }

    public boolean checkEmailIsRegistered(String email){
       return Controller.getInstance().isAuthorRegisteredWithEmail(email);}

    public boolean checkNicknameIsRegistered(String nickname){
       return Controller.getInstance().isAuthorRegisteredWithNickname(nickname);
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
        if (!hasSpecialSymbol) {
            return false;
        }

        // La password è valida
        return true;
    }

    public void setErrLbl(JLabel lblTarget, Boolean visibleState, String msg, Color msgFgColor){
        lblTarget.setVisible(visibleState);
        lblTarget.setText(msg);
        lblTarget.setForeground(msgFgColor);
    }

    public JPanel getPanel() {
        return mainPanelRegistration;
    }
}
