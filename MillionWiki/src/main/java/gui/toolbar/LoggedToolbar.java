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

        try {
            String nickname = Controller.getAuthorById(Controller.getCookie().getId()).getNickname();
            showProfileButton.setText(nickname);
        }catch (Exception e){
            e.printStackTrace();
        }

        showNotificationsButton.addActionListener(e -> {
            if (!setPopupMenu)
                notificationsContainer.setPanelOwner(showNotificationsButton);
            if (SwingUtilities.getWindowAncestor(this.getPanel()).isActive()) {
                notificationsContainer.loading();
                notificationsContainer.show(showNotificationsButton, showNotificationsButton.getLocation().x, showNotificationsButton.getLocation().y + showNotificationsButton.getHeight());
            }
            if(thread == null || !thread.isAlive()){
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
