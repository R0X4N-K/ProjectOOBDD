package gui.toolbar;

import controller.Controller;
import model.ArticleVersion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class NotificationsContainer extends JPopupMenu {
    private JLabel notificationCountLabel;
    private JPanel notificationsContainerMainPanel;
    private JPanel notificationsLoadingPane;
    private JLabel loadingLabel;
    private JScrollPane notificationsScrollPane;
    private final ArrayList<Notification> notificationsList;
    private final ArrayList<CompactNotification> compactNotificationsList;
    private JPanel notificationsContainerScrollablePanel;
    private JPanel notificationsVisualizerPanel;
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

    }

    public void setCompactNotificationList() {
        ArrayList<ArticleVersion> notificationsPool = Controller.getNotifications();
        notificationsContainerScrollablePanel.removeAll();
        notificationsScrollPane.getVerticalScrollBar().setValue(0);
        compactNotificationsList.clear();

        if (notificationsPool != null) {
            for (ArticleVersion version : notificationsPool) {
                int i = 0;
                boolean foundSameVersion = false;
                while (i < compactNotificationsList.size() && !foundSameVersion) {
                    if (version.getParentArticle().getId() == compactNotificationsList.get(i).getArticleVersion().getParentArticle().getId()) {
                        foundSameVersion = true;
                        try {
                            compactNotificationsList.get(i).incrementModificationsCount(version);
                        } catch (Exception e) {
                            System.err.println("Article_Version == NULL, incremento fallito");
                        }
                    }
                    i++;
                }

                if (!foundSameVersion){
                    try {
                        CompactNotification cn = new CompactNotification(version);
                        compactNotificationsList.add(cn);
                        Thread thread = getAddNotificationThread(cn);
                        thread.start();
                    } catch (Exception e) {
                        System.err.println("Article_Version == NULL, creazione fallita");
                    }
                }
            }
        } else {
            System.out.println("WARNING! La pool di notifiche è uguale a NULL");
        }
        notificationCountLabel.setText("Hai " + compactNotificationsList.size() + " nuove notifiche");

        loaded();
    }

    private Thread getAddNotificationThread(CompactNotification cn) {
        Thread thread = new Thread(() -> {
            notificationsContainerScrollablePanel.add(cn.getPanel(), constraints);
            if (notificationsContainerMainPanel.getPreferredSize().width < cn.getPanel().getPreferredSize().width) {
                notificationsContainerMainPanel.setPreferredSize(new Dimension(cn.getPanel().getPreferredSize().width, notificationsContainerMainPanel.getPreferredSize().height));
            }
            notificationsContainerScrollablePanel.repaint();
        });
        return thread;
    }

    public void demoList() { // TODO: Rimuovere Funzione
        notificationsContainerScrollablePanel.removeAll();
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
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.gridx = 0;

        add(notificationsContainerMainPanel);
        notificationsScrollPane.getVerticalScrollBar().setUnitIncrement(12);
        notificationsList = new ArrayList<Notification>();
        compactNotificationsList = new ArrayList<CompactNotification>();

    }

    public void loading() {
        ((CardLayout) notificationsContainerMainPanel.getLayout()).show(notificationsContainerMainPanel, "Card2");
    }

    private void loaded() {
        ((CardLayout) notificationsContainerMainPanel.getLayout()).show(notificationsContainerMainPanel, "Card1");
    }

    public JPanel getPanel() {
        return notificationsContainerMainPanel;
    }

}
