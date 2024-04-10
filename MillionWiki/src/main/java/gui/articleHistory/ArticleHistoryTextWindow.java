package gui.articleHistory;

import controller.Controller;
import model.ArticleVersion;

import javax.swing.*;

public class ArticleHistoryTextWindow extends JDialog{
    private JPanel articleHistoryTextWindowMainPanel;
    private JScrollPane articleHistoryTextWindwoJScrollPane;
    private JLabel articleTitleJLabel;
    private JLabel authorProposalJLabel;
    private JTextPane textPane;
    private int idArticleVersion;
    private int idAuthorProposal;

    public ArticleHistoryTextWindow(JFrame parent){
        super(parent, true);
        setContentPane(articleHistoryTextWindowMainPanel);
        pack();
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    public JPanel getPanel(){
        return articleHistoryTextWindowMainPanel;
    }
    public void setArticleHistoryTextWindow() {
        ArticleVersion articleVersion = Controller.getArticleVersionByIdArticleVersion(idArticleVersion);
        articleTitleJLabel.setText("Titolo Articolo: " + articleVersion.getParentArticle().getTitle());
        authorProposalJLabel.setText("Autore Proposta: "+ articleVersion.getAuthorProposal().getNickname());
        textPane.setContentType("text/html");
        textPane.setText(articleVersion.getText());
    }
    public void setIdArticleVersion(int idArticleVersion) {
        this.idArticleVersion = idArticleVersion;
    }
}
