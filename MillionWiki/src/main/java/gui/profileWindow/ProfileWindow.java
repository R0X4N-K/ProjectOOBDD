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
    private ProfileCard profileCard1;
    private CreatedPagesCard createdPagesCard1;
    private StatisticsCard statisticsCard1;

    public ProfileWindow(JFrame parent) {
        super(parent, true); // Set the dialog to be modal
        setContentPane(profileWindowMainPanel);
        pack();
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // The dialog will be hidden and disposed
        buttonToProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanel(profileCard1.getPanel());
            }
        });
        buttonToCreatedPages.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanel(createdPagesCard1.getPanel());
            }
        });
        buttonToStatistics.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanel(statisticsCard1.getPanel());
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
        profileCard1.setWelcomeMessage(nickname);
    }

    public void setProfileWindow(String nickname) {
        setProfileCard(nickname);
    }

    public void switchPanel(JPanel refPanel) {
        profilePanelCards.removeAll();
        profilePanelCards.add(refPanel);
        profilePanelCards.repaint();
        profilePanelCards.revalidate();
    }

}
