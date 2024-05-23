package gui.profileWindow.profileCard;

import controller.Controller;
import gui.Window;
import gui.profileWindow.ProfileWindow;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ProfileCard {
    private JPanel profileCardMainPanel;
    private JButton changePasswordButton;
    private JButton changeEmailButton;
    private JLabel welcomeJLabel;
    private JButton logoutButton;
    private JButton changeNicknameButton;
    private JLabel emailJLabel;
    private JPanel profileButtons;
    private ProfileWindow profileWindow;

    public ProfileCard() {
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window.switchToUnloggedWindow(Controller.getWindow());
                Controller.getWindow().getprofileWindow().getDialog().dispose();
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
        welcomeJLabel.setText("<html><span style='font-size:22px; color:black;'>Benvenuto, </span>" +
                "<span style='font-size:22px; font-weight:bold; color:#2c82c9'>" + nickname + "</span>" +
                "<span style='font-size:22px; color:black;'>!</span></html>");
    }
    void setEmailJLabel(String email){
        emailJLabel.setText(email);
        emailJLabel.setFont(new Font(
                emailJLabel.getFont().getFontName(),
                Font.PLAIN,
                15)
        );
        emailJLabel.setBorder(
                new TitledBorder("Email")
        );
    }

    public JPanel getPanel() {
        return profileCardMainPanel;
    }

    public void setProfile() {
        try {
            setWelcomeMessage(Controller.getAuthorById(Controller.getCookie().getId()).getNickname());
            setEmailJLabel(Controller.getAuthorById(Controller.getCookie().getId()).getEmail());
        } catch (SQLException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}
