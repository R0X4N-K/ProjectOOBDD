package gui.toolbar;

import controller.Controller;
import model.ArticleVersion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class NotificationsContainer extends AnchoredDialog {
    private JLabel notificationCountLabel;
    private JPanel notificationsLoadingPane;
    protected JPanel mainNotificationsContainerPanel;
    private JLabel loadingLabel;
    private JScrollPane notificationsScrollPane;
    private final ArrayList<Notification> notificationsList;
    private final ArrayList<CompactNotification> compactNotificationsList;
    private JPanel notificationsContainerScrollablePanel;
    private JPanel notificationsVisualizerPanel;
    private final GridBagConstraints constraints = new GridBagConstraints();

    private Component owner = null;

    private final WindowAdapter closeOnWindowChangeListener = new WindowAdapter() {
        @Override
        public void windowDeactivated(WindowEvent e) {
            super.windowDeactivated(e);
            setVisible(false);
        }
    };

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
        notificationCountLabel.setText("<html> Hai " + "<b>" + notificationsList.size() + "</b>" + " nuove notifiche" + "</html>");

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
        notificationCountLabel.setText("<html> Hai " + "<b>" + compactNotificationsList.size() + "</b>" + " nuove notifiche" + "</html>");

        loaded();
    }

    private Thread getAddNotificationThread(CompactNotification cn) {
        Thread thread = new Thread(() -> {
            notificationsContainerScrollablePanel.add(cn.getPanel(), constraints);
            resetPanels();
            if (mainNotificationsContainerPanel.getSize().width < cn.getPanel().getSize().width) {
                notificationsVisualizerPanel.setSize(new Dimension(cn.getPanel().getSize().width, mainNotificationsContainerPanel.getSize().height));
                notificationsScrollPane.setSize(notificationsVisualizerPanel.getSize());
                notificationsContainerScrollablePanel.setSize(notificationsVisualizerPanel.getSize());
                mainNotificationsContainerPanel.setSize(new Dimension(notificationsVisualizerPanel.getWidth(), notificationsVisualizerPanel.getHeight() + notificationCountLabel.getHeight()));
                setSize(mainNotificationsContainerPanel.getSize());
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

    public NotificationsContainer(JComponent anchorTo) {
        super(anchorTo, null,
            532,
            158,
            null, SizeAnchoring.NONE,
           AnchoringPointX.CENTER, AnchoringPointY.UP, SpawnPoint.DOWN);

        super.getPanel().add(mainNotificationsContainerPanel);

        constraints.anchor = GridBagConstraints.NORTH;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.gridx = 0;
        notificationsScrollPane.getVerticalScrollBar().setUnitIncrement(12);
        notificationsList = new ArrayList<Notification>();
        compactNotificationsList = new ArrayList<CompactNotification>();

        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {

            }

            @Override
            public void componentMoved(ComponentEvent e) {
                updateDialogPos();
            }

            @Override
            public void componentShown(ComponentEvent e) {
                if(!Arrays.stream(SwingUtilities.getWindowAncestor(anchorComponent).getWindowListeners()).toList().contains(closeOnWindowChangeListener))
                    SwingUtilities.getWindowAncestor(anchorComponent).addWindowListener(closeOnWindowChangeListener);
            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

    }

    private void resetPanels() {
        notificationsVisualizerPanel.setSize(new Dimension(mainNotificationsContainerPanel.getPreferredSize().width, mainNotificationsContainerPanel.getPreferredSize().height - notificationCountLabel.getHeight()));
        notificationsScrollPane.setSize(notificationsVisualizerPanel.getSize());
        notificationsContainerScrollablePanel.setSize(notificationsVisualizerPanel.getSize());
        mainNotificationsContainerPanel.setSize(mainNotificationsContainerPanel.getPreferredSize());
        setSize(mainNotificationsContainerPanel.getSize());
    }

    public void loading() {
        ((CardLayout) mainNotificationsContainerPanel.getLayout()).show(mainNotificationsContainerPanel, "Card2");
        setSize(mainNotificationsContainerPanel.getPreferredSize());
        mainNotificationsContainerPanel.setSize(mainNotificationsContainerPanel.getPreferredSize());
        System.out.println(getSize());
    }

    private void loaded() {
        setSize(mainNotificationsContainerPanel.getPreferredSize());
        ((CardLayout) mainNotificationsContainerPanel.getLayout()).show(mainNotificationsContainerPanel, "Card1");
        notificationsContainerScrollablePanel.setVisible(!compactNotificationsList.isEmpty());
        System.out.println(getSize());
        System.out.println(mainNotificationsContainerPanel.getSize());
        System.out.println(notificationsVisualizerPanel.getSize());
        System.out.println(notificationsContainerScrollablePanel.getSize());
    }

}
