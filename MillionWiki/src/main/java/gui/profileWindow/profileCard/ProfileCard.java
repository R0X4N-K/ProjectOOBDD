package gui.profileWindow.profileCard;

import controller.Controller;
import gui.Window;
import gui.profileWindow.ProfileWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileCard {
    private JPanel profileCardMainPanel;
    private JButton changePasswordButton;
    private JButton changeEmailButton;
    private JLabel welcomeJLabel;
    private JButton logoutButton;
    private JButton changeNicknameButton;
    private JLabel emailJLabel;
    private ProfileWindow profileWindow;

    public ProfileCard() {
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window.switchToUnloggedWindow(Controller.getWindow());
                Controller.getWindow().getprofileWindow().getDialog().dispose();
                welcomeJLabel.setText("Benvenuto!");
            }
        });
        changeNicknameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProfileChangeDialog("Nickname", ProfileCard.this).setVisible(true);
            }
        });

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProfileChangeDialog("Password", ProfileCard.this).setVisible(true);
            }
        });

        changeEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProfileChangeDialog("Email", ProfileCard.this).setVisible(true);
            }
        });
    }

    void setWelcomeMessage(String nickname) {
        welcomeJLabel.setText("Benvenuto, " + nickname + "!");
    }
    void setEmailJLabel(String email){
        emailJLabel.setText(("La tua Email è: " + email));
    }

    public JPanel getPanel() {
        return profileCardMainPanel;
    }

    public void setProfile() {
        setWelcomeMessage(Controller.getAuthorById(Controller.getCookie().getId()).getNickname());
        setEmailJLabel(Controller.getAuthorById(Controller.getCookie().getId()).getEmail());
    }
}
