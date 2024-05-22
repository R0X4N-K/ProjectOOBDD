package gui.toolbar;

import controller.Controller;
import gui.ErrorDisplayer;
import model.ArticleVersion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class NotificationsContainer extends AnchoredDialog {
    private JLabel notificationCountLabel;
    private JPanel notificationsLoadingPane;
    protected JPanel mainNotificationsContainerPanel;
    private JLabel loadingLabel;
    private JScrollPane notificationsScrollPane;
    private final ArrayList<CompactNotification> compactNotificationsList;
    private JPanel notificationsContainerScrollablePanel;
    private JPanel notificationsVisualizerPanel;
    private final GridBagConstraints constraints = new GridBagConstraints();
    private int isMouseEntered = 0;

    private final WindowAdapter closeOnWindowChangeListener = new WindowAdapter() {
        @Override
        public void windowDeactivated(WindowEvent e) {
            super.windowDeactivated(e);
            setVisible(false);
        }
    };

    private final MouseAdapter mouseClosure = new MouseAdapter() {
        @Override
        public void mouseExited(MouseEvent e) {
            decrementIsMouseEntered(mainNotificationsContainerPanel);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            incrementIsMouseEntered(mainNotificationsContainerPanel);
        }
    };


    public void setCompactNotificationList() {
        notificationsContainerScrollablePanel.removeAll();
        notificationsScrollPane.getVerticalScrollBar().setValue(0);
        notificationsScrollPane.getHorizontalScrollBar().setValue(0);
        compactNotificationsList.clear();
        int totalHeight = 0;
        try {
            ArrayList<ArticleVersion> notificationsPool = Controller.getNotifications();

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
                            cn.getPanel().addMouseListener(mouseClosure);
                            compactNotificationsList.add(cn);
                            addNotification(cn);
                            totalHeight += cn.getPanel().getPreferredSize().height;
                        } catch (Exception e) {
                            System.err.println("Article_Version == NULL, creazione fallita");
                        }
                    }
                }
            } else {
                System.out.println("WARNING! La pool di notifiche è uguale a NULL");
            }
            notificationCountLabel.setText("<html> Hai " + "<b>" + compactNotificationsList.size() + "</b>" + " nuove notifiche" + "</html>");

        } catch (SQLException | IllegalArgumentException e) {
            ErrorDisplayer.showError(e);
        }

        notificationsContainerScrollablePanel.setPreferredSize(new Dimension(notificationsContainerScrollablePanel.getPreferredSize().width, totalHeight));
        notificationsContainerScrollablePanel.revalidate();
        notificationsContainerScrollablePanel.repaint();

        loaded();
    }

    /*private Thread getAddNotificationThread(CompactNotification cn) {
        return new Thread(() -> {addNotification(cn);});
    }*/

    private void addNotification(CompactNotification cn) {
        notificationsContainerScrollablePanel.add(cn.getPanel(), constraints);
        cn.getPanel().setSize(cn.getPanel().getPreferredSize());
    }

    public NotificationsContainer(JComponent anchorTo) {
        super(anchorTo, new GridLayout(),
            532,
            158,
            null, SizeAnchoring.NONE,
           AnchoringPointX.CENTER, AnchoringPointY.UP, SpawnPoint.DOWN);

        super.getPanel().add(mainNotificationsContainerPanel);

        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.gridx = 0;
        notificationsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        notificationsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        notificationsScrollPane.getVerticalScrollBar().setUnitIncrement(12);
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

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                setVisible(false);
            }
        });

        Toolkit.getDefaultToolkit().addAWTEventListener( listener, AWTEvent.MOUSE_EVENT_MASK );
        addMouseListener(mouseClosure);
        notificationsScrollPane.getHorizontalScrollBar().addMouseListener(mouseClosure);
        notificationsScrollPane.getVerticalScrollBar().addMouseListener(mouseClosure);

    }

    private void resetPanels() {
        int heightNotifications = 0;
        if (!compactNotificationsList.isEmpty()) {
            for (CompactNotification cn : compactNotificationsList) {
                cn.getPanel().setSize(compactNotificationsList.getLast().getPanel().getSize());
            }
            heightNotifications = (int) ((compactNotificationsList.getFirst().getPanel().getPreferredSize().getHeight() * compactNotificationsList.size()) - mainNotificationsContainerPanel.getPreferredSize().getHeight());
            heightNotifications = Math.max(heightNotifications, 0);
        }


        notificationsVisualizerPanel.setSize(new Dimension(mainNotificationsContainerPanel.getPreferredSize().width,
                mainNotificationsContainerPanel.getPreferredSize().height)); //+ heightNotifications));

        notificationsScrollPane.setSize(new Dimension(notificationsVisualizerPanel.getSize().width,
                notificationsVisualizerPanel.getSize().height - notificationCountLabel.getHeight()));

        notificationsContainerScrollablePanel.setSize(notificationsScrollPane.getSize());
        mainNotificationsContainerPanel.setSize(notificationsVisualizerPanel.getSize());
        setSize(mainNotificationsContainerPanel.getSize());
    }

    public void loading() {
        ((CardLayout) mainNotificationsContainerPanel.getLayout()).show(mainNotificationsContainerPanel, "Card2");
        resetPanels();
    }

    private void loaded() {
        setSize(mainNotificationsContainerPanel.getPreferredSize());
        ((CardLayout) mainNotificationsContainerPanel.getLayout()).show(mainNotificationsContainerPanel, "Card1");
        notificationsContainerScrollablePanel.setVisible(!compactNotificationsList.isEmpty());
        resetPanels();
    }

    private void printPanelsSize() {
        System.out.println(getSize());
        System.out.println(mainNotificationsContainerPanel.getSize());
        System.out.println(notificationsVisualizerPanel.getSize());
        System.out.println(notificationsScrollPane.getSize());
        System.out.println(notificationsContainerScrollablePanel.getSize() + "\n\n");
    }

    public void decrementIsMouseEntered (Component c) {
        isMouseEntered = isMouseEntered == 0 ? 0 : isMouseEntered - 1;
    }

    public void incrementIsMouseEntered (Component c) {
        isMouseEntered = isMouseEntered == getComponentCount() ? 0 : isMouseEntered + 1;
    }


    final AWTEventListener listener = event -> {
        MouseEvent me = ( MouseEvent ) event;
        Component c = me.getComponent ();

        if ( event.getID () == MouseEvent.MOUSE_PRESSED ) {
            setVisible(isMouseEntered > 0);
        }
    };
}
