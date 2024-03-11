package gui.toolbar;

import model.ArticleVersion;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class NotificationContainer {
    private JPanel notificationContainerPanel;
    private JLabel newsNumber;
    private ArrayList<Notification> notificationList;

    private void setNotificationList(ArrayList<ArticleVersion> notificationsPool) {
        if (notificationsPool != null) {
            notificationList.clear();
            for (ArticleVersion version : notificationsPool) {
                notificationList.add(notificationList.size(), new Notification());
            }
        } else {
            // TODO: WARNING! La pool di notifiche è uguale a NULL
        }
    }

    public void demoList() { // TODO: Rimuovere Funzione
        JPanel panel = new Notification().getPanel();
        System.out.println(getPanel().toString());
        System.out.println(panel.toString());
        notificationContainerPanel.add(panel);
    }

    public int getNotificationNumber() {
        return notificationList.size();
    }

    public NotificationContainer() {
        notificationList = new ArrayList<Notification>();
    }

    public JPanel getPanel() {
        return notificationContainerPanel;
    }

}
