package gui.profileWindow;

import gui.profileWindow.profileCard.ProfileCard;

import javax.swing.*;
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
    private ProposalCard proposalCard;

    public ProfileWindow(JFrame parent) {
        super(parent, true);
        setContentPane(profileWindowMainPanel);
        pack();
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        buttonToProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setProfileCard();
                switchPanel(profileCard.getPanel());
            }
        });
        buttonToCreatedPages.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCreatedPagesCard();
                switchPanel(createdPagesCard.getPanel());
            }
        });
        buttonToStatistics.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setProposalCard();
                switchPanel(proposalCard.getPanel());
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
