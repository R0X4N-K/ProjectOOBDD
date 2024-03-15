package gui.profileWindow;

import javax.swing.*;

public class ProfileWindow extends JDialog {
    private JPanel profileWindowMainPanel;
    private JButton buttonToProfile;
    private JButton buttonToCreatedPages;
    private JButton buttonToStatistics;
    private JScrollPane profileWindowJScrollPane;
    private JPanel profileWindowJScrollPaneJPanel;
    private JPanel profilePanelCards;
    private ProfileCard profileCard1;
    private StatisticsCard statisticsCard1;
    private CreatedPagesCard createdPagesCard1;

    public ProfileWindow(JFrame parent) {
        super(parent, true); // Set the dialog to be modal
        setContentPane(profileWindowMainPanel);
        pack();
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // The dialog will be hidden and disposed
    }


}
