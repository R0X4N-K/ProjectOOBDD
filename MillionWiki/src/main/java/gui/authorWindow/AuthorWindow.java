package gui.authorWindow;

import javax.swing.*;

public class AuthorWindow extends JDialog{
    private JScrollPane authorWindowJScrollPane;
    private JPanel authorWindowJScrollPaneJPanel;
    private JButton buttonToProfile;
    private JButton buttonToCreatedPages;
    private JButton buttonToStatistics;
    private JPanel authorPanelCards;
    private JPanel authorWindowMainPanel;
    private CreatedPagesAuthorWindow createdPagesAuthorWindow;
    private int idAuthor = -1;


    public AuthorWindow(JFrame parent){
        super(parent, true);
        setContentPane(authorWindowMainPanel);
        pack();
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        /*buttonToProfile.addActionListener(new ActionListener() {
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
        });*/
    }

    public JDialog getDialog() {
        return this;
    }

    /*public void setProfileCard() {
        profileCard.setProfile();
    }*/


    public void setCreatedPagesCard(){
        createdPagesAuthorWindow.setCreatedPages();
    }

    public void setAuthorWindow() {
        //setProfileCard();
        setCreatedPagesCard();
        switchPanel(createdPagesAuthorWindow.getPanel());
    }

    public void switchPanel(JPanel refPanel) {
        authorPanelCards.removeAll();
        authorPanelCards.add(refPanel);
        authorPanelCards.repaint();
        authorPanelCards.revalidate();
    }
    public int getIdAuthor(){
        return idAuthor;
    }
    public void setIdAuthor(int idAuthor){
        this.idAuthor = idAuthor;
    }
}
