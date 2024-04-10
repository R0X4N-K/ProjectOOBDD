package gui.authorWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthorWindow extends JDialog{
    private JPanel authorWindowButtonsJPanel;
    private JButton buttonToProfile;
    private JButton buttonToCreatedPages;
    private JPanel authorPanelCards;
    private JPanel authorWindowMainPanel;
    private CreatedPagesAuthorWindow createdPagesAuthorWindow;
    private ProfileAuthorWindow profileAuthorWindow;
    private JPanel authorWindowJPanelButtons2;
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
                Thread thread = null;
                if (thread == null || !thread.isAlive()) {
                    thread = new Thread(() -> {
                        setProfileCard();
                        switchPanel(profileAuthorWindow.getPanel());
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
                        switchPanel(createdPagesAuthorWindow.getPanel());
                        setCreatedPagesCard();
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
        profileAuthorWindow.setProfile();
    }


    public void setCreatedPagesCard(){
        createdPagesAuthorWindow.setCreatedPages();
    }

    public void setAuthorWindow() {
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
