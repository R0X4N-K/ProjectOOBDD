package gui.toolbar;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoggedToolbar {
    private JPanel loggedUserPanel;
    private JButton showNotificationsButton;
    private JButton showProfileButton;
    private final NotificationsContainer notificationsContainer = new NotificationsContainer();
    private boolean setPopupMenu = false;

    public LoggedToolbar() {
        getPanel().setComponentPopupMenu(notificationsContainer);
        showNotificationsButton.addActionListener(e -> {
            if (!setPopupMenu)
                notificationsContainer.setPanelOwner(getPanel());

            notificationsContainer.setNotificationList();
            notificationsContainer.show(showNotificationsButton, showNotificationsButton.getLocation().x, showNotificationsButton.getLocation().y + showNotificationsButton.getHeight());
        });
        showProfileButton.addActionListener(e -> {
            Controller.getWindow().getprofileWindow().setProfileWindow();
            Controller.getWindow().getprofileWindow().setVisible(true);
        });
    }
    public JPanel getPanel() {
        return loggedUserPanel;
    }

}
