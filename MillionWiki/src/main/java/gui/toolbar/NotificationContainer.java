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
        int n_notification = 0;
        notificationContainerPanel.removeAll();
        notificationContainerPanel.add(newsNumber);
        if (notificationsPool != null) {
            notificationList.clear();
            for (ArticleVersion version : notificationsPool) {
                notificationList.add(notificationList.size(), new Notification());
                //notificationList.add(notificationList.size(), new Notification(version));
            }
        } else {
            System.out.println("WARNING! La pool di notifiche è uguale a NULL");
        }
        newsNumber.setText("Hai" + Integer.toString(n_notification) + "nuove notifiche");
    }

    public void demoList() { // TODO: Rimuovere Funzione
        JPanel panel = new Notification().getPanel();
        notificationContainerPanel.removeAll();
        notificationContainerPanel.add(newsNumber);
        //System.out.println(getPanel().toString());
        //System.out.println(panel.toString());
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
