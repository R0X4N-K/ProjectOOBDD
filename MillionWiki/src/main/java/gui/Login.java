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
            emailNicknameTxtFld.setBorder(new LineBorder(Color.GREEN));
            passwordTxtFld.setEnabled(true);
        }
        else{
            passwordTxtFld.setEnabled(false);
            emailNicknameTxtFld.setBorder(new LineBorder(Color.RED));
        }

        /*  sintax
            fetch from db if already exist
         */

    }

    public void checkPasswordFld(String text) {
        if(checkPasswordSintax(text)){
            passwordTxtFld.setBorder(new LineBorder(Color.GREEN));
            submitBtn.setEnabled(true);
        }
        else{
            submitBtn.setEnabled(false);
            passwordTxtFld.setBorder(new LineBorder(Color.RED));
        }
    }


    public boolean checkEmailSintax(String text){
        String regex = "^[\\w!#$%&'*+/=?^`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^`{|}~-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        return text.matches(regex);
    }

    public boolean checkNickname(String text){
        if (text.length() < 4 || text.length() > 15) {
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

    public JPanel getPanel() {
        return mainPanelLogin;
    }
}
