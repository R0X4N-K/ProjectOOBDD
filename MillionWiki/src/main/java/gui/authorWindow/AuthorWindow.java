package gui.authorWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthorWindow extends JDialog{
    private JScrollPane authorWindowJScrollPane;
    private JPanel authorWindowJScrollPaneJPanel;
    private JButton buttonToProfile;
    private JButton buttonToCreatedPages;
    private JButton buttonToStatistics;
    private JPanel authorPanelCards;
    private JPanel authorWindowMainPanel;
    private CreatedPagesAuthorWindow createdPagesAuthorWindow;
    private ProfileAuthorWindow profileAuthorWindow;
    private int idAuthor = -1;


    public AuthorWindow(JFrame parent){
        super(parent, true);
        setContentPane(authorWindowMainPanel);
        pack();
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buttonToProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setProfileCard();
                switchPanel(profileAuthorWindow.getPanel());
            }
        });
        buttonToCreatedPages.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCreatedPagesCard();
                switchPanel(createdPagesAuthorWindow.getPanel());
            }
        });
       /* buttonToStatistics.addActionListener(new ActionListener() {
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

  public void setProfileCard() {
        profileAuthorWindow.setProfile();
    }


    public void setCreatedPagesCard(){
        createdPagesAuthorWindow.setCreatedPages();
    }

    public void setAuthorWindow() {
        //setProfileCard();
        setProfileCard();
        switchPanel(profileAuthorWindow.getPanel());
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
