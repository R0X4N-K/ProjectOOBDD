package gui.profileWindow;

import controller.Controller;
import gui.ErrorDisplayer;
import gui.profileWindow.profileCard.ProfileCard;
import model.Article;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class ProfileWindow extends JDialog {
    private JPanel profileWindowMainPanel;
    private JButton buttonToProfile;
    private JButton buttonToCreatedPages;
    private JButton buttonToProposal;
    private JScrollPane profileWindowJScrollPane;
    private JPanel profileWindowJScrollPaneJPanel;
    private JPanel profilePanelCards;
    private ProfileCard profileCard;
    private CreatedPagesCard createdPagesCard;
    private ProposalCard proposalCard;
    private JButton buttonToNotification;
    private NotificationCard notificationCard;

    public ProfileWindow(JFrame parent) {
        super(parent, true);
        setContentPane(profileWindowMainPanel);
        pack();
        setTitle("Il mio profilo");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        profileWindowJScrollPane.setBorder(new MatteBorder(
                0, 0, 0, 1, Color.LIGHT_GRAY
        ));
        buttonToProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread thread = null;
                if (thread == null || !thread.isAlive()) {
                    thread = new Thread(() -> {
                        switchPanel(profileCard.getPanel());
                        setProfileCard();
                    });
                }
                thread.setDaemon(true);
                thread.start();
            }
        });
        buttonToCreatedPages.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread thread = null;
                if (thread == null || !thread.isAlive()) {
                    thread = new Thread(() -> {
                        switchPanel(createdPagesCard.getPanel());
                        setCreatedPagesCard();
                    });
                }
                thread.setDaemon(true);
                thread.start();
            }
        });
        buttonToProposal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread thread = null;
                if (thread == null || !thread.isAlive()) {
                    thread = new Thread(() -> {
                        switchPanel(proposalCard.getPanel());
                        setProposalCard();
                    });
                }
                thread.setDaemon(true);
                thread.start();

            }
        });
        buttonToNotification.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread thread = null;
                if (thread == null || !thread.isAlive()) {
                    thread = new Thread(() -> {
                        switchPanel(notificationCard.getPanel());
                        notificationCard.setNotificationCard();
                    });
                }
                thread.setDaemon(true);
                thread.start();
            }
        });
    }

    public JDialog getDialog() {
        return this;
    }

    public void setProfileCard() {
        profileCard.setProfile();
    }

    public void setCreatedPagesCard() {
        createdPagesCard.setCreatedPages();
    }

    public void setProposalCard() {
        proposalCard.setProposalCard();
    }

    public void setProfileWindow() {
        setProfileCard();
        switchPanel(profileCard.getPanel());
    }

    public void switchPanel(JPanel refPanel) {
        profilePanelCards.removeAll();
        profilePanelCards.add(refPanel);
        profilePanelCards.repaint();
        profilePanelCards.revalidate();
    }

    static MouseAdapter getMouseAdapter(JTable adapterTable) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = adapterTable.rowAtPoint(e.getPoint());
                int col = adapterTable.columnAtPoint(e.getPoint());
                if (col == 0) {
                    String link = (String) adapterTable.getValueAt(row, col);
                    String idString = link.substring(link.indexOf("'") + 1, link.indexOf("'", link.indexOf("'") + 1));
                    int id = Integer.parseInt(idString);
                    Controller.getWindow().getprofileWindow().setVisible(false);
                    try {
                        Article article = Controller.getArticlesById(id);
                        Controller.getWindow().getPage().setTitlePageField(article.getTitle());
                        Controller.getWindow().getPage().setTextPageField(Controller.getLastArticleVersionByArticleId(id).getText());
                        Controller.getWindow().switchPanel(Controller.getWindow().getPage().getPanel());
                    } catch (SQLException | IllegalArgumentException ex) {
                        ErrorDisplayer.showError(ex);
                    }
                }
            }
        };
    }

    static void getMouseMotionListener(JTable listenedTable) {
        listenedTable.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int col = listenedTable.columnAtPoint(e.getPoint());
                if (col == 0 || col == 1) {
                    listenedTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    listenedTable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }

}
