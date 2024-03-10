package gui.toolbar;

import javax.swing.*;

public class LoggedToolbar {
    private JPanel loggedUserPanel;
    private JButton showNotificationsButton;
    private JButton showProfileButton;
    private Notifications notifications = new Notifications();
    private Profile profile = new Profile();

    public LoggedToolbar(){
        JPopupMenu popupMenuNotifications = new JPopupMenu();
        JPopupMenu popupMenuProfile = new JPopupMenu();
        popupMenuProfile.add(profile.getPanel());
        popupMenuNotifications.add(notifications.getPanel());
        showProfileButton.addActionListener(e -> {
            popupMenuProfile.show(showProfileButton, showProfileButton.getWidth() / 6, showProfileButton.getHeight() / 2);
        });
        showNotificationsButton.addActionListener(e -> {
            popupMenuNotifications.show(showNotificationsButton, showNotificationsButton.getWidth() / 6, showNotificationsButton.getHeight() / 2);
        });
    }
    public JPanel getProfilePanel() {
        return profile.getPanel();
    }
    public void setNicknameProfileNicknameJLabel(String nickname) {
        profile.setNicknameJLabel(nickname);
    }
    public JPanel getPanel() {
        return loggedUserPanel;
    }
}
