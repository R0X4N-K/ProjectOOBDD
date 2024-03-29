package gui.profileWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileWindow extends JDialog {
    private JPanel profileWindowMainPanel;
    private JButton buttonToProfile;
    private JButton buttonToCreatedPages;
    private JButton buttonToStatistics;
    private JScrollPane profileWindowJScrollPane;
    private JPanel profileWindowJScrollPaneJPanel;
    private JPanel profilePanelCards;
    private ProfileCard profileCard;
    private CreatedPagesCard createdPagesCard;
    private StatisticsCard statisticsCard;

    public ProfileWindow(JFrame parent) {
        super(parent, true); // Set the dialog to be modal
        setContentPane(profileWindowMainPanel);
        pack();
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // The dialog will be hidden and disposed
        buttonToProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanel(profileCard.getPanel());
            }
        });
        buttonToCreatedPages.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanel(createdPagesCard.getPanel());
            }
        });
        buttonToStatistics.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanel(statisticsCard.getPanel());
            }
        });
    }

    JDialog getDialog() {
        return this;
    }

    public static ProfileWindow checkProfileWindow(ProfileWindow profileWindow, Component leaf) throws NullPointerException {
        if (leaf != null) {
            if (profileWindow == null)
                profileWindow = (ProfileWindow) SwingUtilities.getAncestorOfClass(ProfileWindow.class, leaf);
            return profileWindow;
        } else {
            throw new NullPointerException("Non è possibile trovare \"profileWindow\", poiché \"leaf\" è null");
        }
    }

    public void setProfileCard(String nickname) {
        profileCard.setWelcomeMessage(nickname);
    }
    public void setCreatedPagesCard(){
        createdPagesCard.setCreatedPages();
    }
    public void setProfileWindow(String nickname) {
        setProfileCard(nickname);
        setCreatedPagesCard();
    }

    public void switchPanel(JPanel refPanel) {
        profilePanelCards.removeAll();
        profilePanelCards.add(refPanel);
        profilePanelCards.repaint();
        profilePanelCards.revalidate();
    }

}
