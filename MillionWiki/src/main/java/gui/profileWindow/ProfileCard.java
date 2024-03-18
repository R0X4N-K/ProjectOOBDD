package gui.profileWindow;

import gui.Window;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileCard {
    private JPanel profileCardMainPanel;
    private JLabel welcomeJLabel;
    private JButton logoutButton;
    private ProfileWindow profileWindow;
    private Window window;

    public ProfileCard() {
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            window = Window.checkWindow(window, getPanel());
                window.switchToUnloggedWindow(window);
                profileWindow = ProfileWindow.checkProfileWindow(profileWindow, getPanel());
                profileWindow.getDialog().dispose();
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
