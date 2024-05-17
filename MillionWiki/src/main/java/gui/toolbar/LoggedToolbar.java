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
    private final NotificationsContainer notificationsContainer = new NotificationsContainer(showNotificationsButton);
    private Thread thread = null;

    public LoggedToolbar() {


        showNotificationsButton.addActionListener(e -> {
            notificationsContainer.loading();
            notificationsContainer.updateDialogPos();
            notificationsContainer.setVisible(true);
            if(thread == null || !thread.isAlive()){
                notificationsContainer.loading();
                notificationsContainer.updateDialogPos();
                notificationsContainer.setVisible(true);
                thread = new Thread(notificationsContainer::setCompactNotificationList);
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
