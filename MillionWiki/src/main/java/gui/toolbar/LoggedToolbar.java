package gui.toolbar;

import controller.Controller;
import gui.Window;
import gui.profileWindow.ProfileWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoggedToolbar {
    private JPanel loggedUserPanel;
    private JButton showNotificationsButton;
    private JButton showProfileButton;
    private final NotificationContainer notificationContainer = new NotificationContainer();

    public LoggedToolbar() {
        JPopupMenu popupMenuNotifications = new JPopupMenu();
        popupMenuNotifications.add(notificationContainer.getPanel());

        showNotificationsButton.addActionListener(e -> {
            notificationContainer.demoList();
            popupMenuNotifications.show(showNotificationsButton, showNotificationsButton.getWidth() / 6, showNotificationsButton.getHeight() / 2);
        });
        showProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getWindow().getprofileWindow().setVisible(true);
            }
        });
    }
    public JPanel getPanel() {
        return loggedUserPanel;
    }

}
