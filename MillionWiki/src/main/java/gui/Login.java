package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

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
    private Window window;
    private Controller controller;

    public Login() {

        toHomePanelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window = Window.checkWindow(window, mainPanelLogin);
                window.switchPanel(window.getHomePanel());
            }
        });
        toRegistrationPanelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window = Window.checkWindow(window, mainPanelLogin);
                window.switchPanel(window.getRegistrationPanel());
            }
        });

        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Email o Nickname: " + emailNicknameTxtFld.getText());
                System.out.println("Password: " + passwordTxtFld.getText());
            }
        });

        emailNicknameTxtFld.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                checkEmailNicknameFld(emailNicknameTxtFld.getText());
            }
        });

        passwordTxtFld.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                checkPasswordFld(passwordTxtFld.getText());
            }
        });

    }


    public void checkEmailNicknameFld(String text) {
        if(checkEmailSintax(text) && !checkEmailNicknameIsRegistered(text) || checkNickname(text)){
            passwordTxtFld.setEnabled(true);
            emailNicknameTxtFld.setBorder(new LineBorder(Color.GREEN));

            setErrLbl(emailNicknameErrLbl, true, "Valido !", Color.GREEN);
        }
        else{
            passwordTxtFld.setEnabled(false);
            emailNicknameTxtFld.setBorder(new LineBorder(Color.RED));

            setErrLbl(emailNicknameErrLbl, true, "Non valido ! !", Color.RED);
        }

        /*  sintax
            fetch from db if already exist
         */

    }

    public void checkPasswordFld(String text) {
        if(checkPasswordSintax(text)){
            submitBtn.setEnabled(true);
            passwordTxtFld.setBorder(new LineBorder(Color.GREEN));

            setErrLbl(passwordErrLbl, true, "Password valida !", Color.GREEN);
        }
        else{
            submitBtn.setEnabled(false);
            passwordTxtFld.setBorder(new LineBorder(Color.RED));

            setErrLbl(passwordErrLbl, true, "Password non valida !", Color.RED);

        }
    }


    public boolean checkEmailSintax(String text){
        String regex = "^[\\w!#$%&'*+/=?^`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^`{|}~-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        if(text.matches(regex)){
            return true;
        }
        return false;
    }

    public boolean checkNickname(String text){
        if (text.length() < 4 || text.length() > 8) {
            return false;
        }
        return true;
    }

    public boolean checkEmailNicknameIsRegistered(String text){
        return new Controller().isAuthorRegistered(text);
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
        return mainPanelLogin;
    }
}
