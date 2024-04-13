package gui.toolbar;

import controller.Controller;
import gui.page.PageLinker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class LoggedToolbar {
    private JPanel loggedUserPanel;
    private JButton showNotificationsButton;
    private JButton showProfileButton;
    private final NotificationsContainer notificationsContainer = new NotificationsContainer();
    private boolean setPopupMenu = false;
    private Thread thread = null;

    public LoggedToolbar() {

        getPanel().setComponentPopupMenu(notificationsContainer);
        showNotificationsButton.addActionListener(e -> {
            if (!setPopupMenu)
                notificationsContainer.setPanelOwner(getPanel());
            if(thread == null || !thread.isAlive()){
                thread = new Thread(() ->
                {
                    notificationsContainer.setCompactNotificationList();
                    if (SwingUtilities.getWindowAncestor(this.getPanel()).isActive())
                        notificationsContainer.show(showNotificationsButton, showNotificationsButton.getLocation().x, showNotificationsButton.getLocation().y + showNotificationsButton.getHeight());
                });
                thread.start();
            }
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
