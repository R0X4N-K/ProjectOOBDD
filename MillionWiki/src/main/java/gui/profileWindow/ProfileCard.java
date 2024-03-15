package gui.profileWindow;

import javax.swing.*;

public class ProfileCard {
    private JPanel profileCardMainPanel;
    private JLabel welcomeJLabel;
    void setWelcomeMessage(String nickname) {
        welcomeJLabel.setText("Benvenuto, " + nickname + "!");
    }
    public JPanel getPanel() {
        return profileCardMainPanel;
    }
}
