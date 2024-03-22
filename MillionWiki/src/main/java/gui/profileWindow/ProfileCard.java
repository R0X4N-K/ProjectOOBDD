package gui.profileWindow;

import controller.Controller;
import gui.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileCard {
    private JPanel profileCardMainPanel;
    private JLabel welcomeJLabel;
    private JButton logoutButton;
    private ProfileWindow profileWindow;

    public ProfileCard() {
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getWindow().switchToUnloggedWindow(Controller.getWindow());
                welcomeJLabel.setText("Benvenuto!");
                Controller.getWindow().getprofileWindow().getDialog().dispose();


            }
        });
    }

    void setWelcomeMessage(String nickname) {
        welcomeJLabel.setText("Benvenuto, " + nickname + "!");
    }

    public JPanel getPanel() {
        return profileCardMainPanel;
    }

}
