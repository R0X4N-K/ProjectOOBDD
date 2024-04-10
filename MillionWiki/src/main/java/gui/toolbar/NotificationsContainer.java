package gui.toolbar;

import controller.Controller;
import model.ArticleVersion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class NotificationsContainer extends JPopupMenu {
    private JLabel notificationCountLabel;
    private JPanel notificationsContainerMainPanel;
    private JScrollPane notificationsScrollPane;
    private final ArrayList<Notification> notificationsList;
    private JPanel notificationsContainerScrollablePanel;
    private final GridBagConstraints constraints = new GridBagConstraints();

    private JPanel owner = null;
    private final HierarchyBoundsListener closeOnPanelChangeListener = new HierarchyBoundsListener() {

        public void ancestorMoved(HierarchyEvent e) {
            setLocation((int) owner.getLocationOnScreen().getX(), (int) (owner.getLocationOnScreen().getY() + owner.getHeight()));
            setVisible(false);
        }

        public void ancestorResized(HierarchyEvent e) {
            setLocation((int) owner.getLocationOnScreen().getX(), (int) (owner.getLocationOnScreen().getY() + owner.getHeight()));
            setVisible(false);
        }
    };

    private final WindowAdapter closeOnWindowChangeListener = new WindowAdapter() {
        @Override

        public void windowDeactivated(WindowEvent e) {
            super.windowDeactivated(e);
            System.out.println("DEACTIVATED");
            setVisible(false);
        }
    };

    void setPanelOwner(JPanel panelOwner) {
        if (owner != null) {
            owner.removeHierarchyBoundsListener(closeOnPanelChangeListener);
            SwingUtilities.getWindowAncestor(owner).removeWindowListener(closeOnWindowChangeListener);
        }

        owner = panelOwner;
        SwingUtilities.getWindowAncestor(owner).addWindowListener(closeOnWindowChangeListener);
        owner.addHierarchyBoundsListener(closeOnPanelChangeListener);
    }
    public void setNotificationList() {
        ArrayList<ArticleVersion> notificationsPool = Controller.getNotifications();
        notificationsContainerScrollablePanel.removeAll();
        notificationsScrollPane.getVerticalScrollBar().setValue(0);
        notificationsList.clear();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        if (notificationsPool != null) {
            for (ArticleVersion version : notificationsPool) {
                Notification n = new Notification(version);
                notificationsList.add(n);
                notificationsContainerScrollablePanel.add(n.getPanel(), constraints);
            }
        } else {
            System.out.println("WARNING! La pool di notifiche è uguale a NULL");
        }
        notificationCountLabel.setText("Hai " + notificationsList.size() + " nuove notifiche");

        setMaximumSize(new Dimension(515, getMaximumSize().height));
    }

    public void demoList() { // TODO: Rimuovere Funzione
        removeAll();
        for (int i = 0; i < 100; i++) {
            Notification n = new Notification();
            notificationsContainerScrollablePanel.add(n.getPanel(), constraints);
        }
    }

    public int getNotificationNumber() {
        return notificationsList.size();
    }

    public NotificationsContainer() {
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.gridx = 0;

        add(notificationsContainerMainPanel);
        notificationsContainerMainPanel.add(notificationsScrollPane);
        notificationsScrollPane.getVerticalScrollBar().setUnitIncrement(12);
        notificationsList = new ArrayList<Notification>();

    }

    public JPanel getPanel() {
        return notificationsContainerMainPanel;
    }

}
