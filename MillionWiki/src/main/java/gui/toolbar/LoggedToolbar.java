package gui.toolbar;

import javax.swing.*;

public class LoggedToolbar {
    private JPanel loggedUserPanel;
    private JButton showNotificationsButton;
    private Notifications notifications = new Notifications();

    public LoggedToolbar(){
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(notifications.getPanel());
        showNotificationsButton.addActionListener(e -> {
            popupMenu.show(showNotificationsButton, showNotificationsButton.getWidth() / 6, showNotificationsButton.getHeight() / 2);
        });
    }
    public JPanel getPanel() {
        return loggedUserPanel;
    }
}
