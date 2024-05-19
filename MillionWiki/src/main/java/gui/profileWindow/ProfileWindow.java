package gui.profileWindow;

import gui.profileWindow.profileCard.ProfileCard;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    public void setCreatedPagesCard(){
        createdPagesCard.setCreatedPages();
    }
    public void setProposalCard(){
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

}
