package gui.profileWindow.profileCard;

import controller.Controller;
import gui.Window;
import gui.authorWindow.ProfileAuthorWindow;
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
    private JPanel ratingPnl;
    private JProgressBar progressBar;
    private JLabel ratingLbl;
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

    void setEmailJLabel(String email) {
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

    void setRating(float rating) {
        ratingLbl.setFont(new Font(
                ratingLbl.getFont().getFontName(),
                Font.BOLD,
                22)
        );
        ratingPnl.setBorder(
                new TitledBorder("Valutazione")
        );

        ratingLbl.setOpaque(true);

        if (rating != 0) {
            rating = rating * 100;
            progressBar.setValue((int) rating);
            progressBar.setVisible(true);
            ratingLbl.setText(String.format("%.1f", rating) + " %");

            if (rating > 50) {
                ratingLbl.setForeground(Color.decode("#5EAC24"));
                ratingLbl.setIcon(
                        new ImageIcon(
                                ProfileAuthorWindow.class.getResource(
                                        "/icons/up.png"
                                )
                        )
                );
                progressBar.setForeground(Color.decode("#5EAC24"));
                progressBar.setBackground(Color.GRAY);

            } else if (rating < 50) {
                ratingLbl.setForeground(Color.decode("#ED1C24"));
                ratingLbl.setIcon(
                        new ImageIcon(
                                ProfileAuthorWindow.class.getResource(
                                        "/icons/down.png"
                                )
                        )
                );

                progressBar.setForeground(Color.decode("#ED1C24"));
                progressBar.setBackground(Color.GRAY);
            } else if (rating == 50) {
                ratingLbl.setForeground(Color.decode("#FDBF40"));
                ratingLbl.setIcon(
                        new ImageIcon(
                                ProfileAuthorWindow.class.getResource(
                                        "/icons/middle.png"
                                )
                        )
                );

                progressBar.setForeground(Color.decode("#FDBF40"));
                progressBar.setBackground(Color.GRAY);
            }

        } else {
            ratingLbl.setForeground(Color.GRAY);
            ratingLbl.setText("...");
            ratingLbl.setIcon(null);
            progressBar.setVisible(false);
        }
    }

    public JPanel getPanel() {
        return profileCardMainPanel;
    }

    public void setProfile() {
        try {
            //TODO: sistemare
            setWelcomeMessage(Controller.getAuthorById(Controller.getCookie().getId()).getNickname());
            setEmailJLabel(Controller.getAuthorById(Controller.getCookie().getId()).getEmail());
            setRating(Controller.getAuthorById(Controller.getCookie().getId()).getRating());
        } catch (SQLException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

}
