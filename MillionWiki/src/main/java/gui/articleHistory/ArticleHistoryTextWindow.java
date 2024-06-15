package gui.articleHistory;

import controller.Controller;
import gui.ErrorDisplayer;
import model.ArticleVersion;

import javax.swing.*;
import java.sql.SQLException;

public class ArticleHistoryTextWindow extends JDialog {
    private JPanel articleHistoryTextWindowMainPanel;
    private JScrollPane articleHistoryTextWindwoJScrollPane;
    private JLabel articleTitleJLabel;
    private JLabel authorProposalJLabel;
    private JTextPane textPane;
    private int idArticleVersion;
    private int idAuthorProposal;

    public ArticleHistoryTextWindow(JFrame parent) {
        super(parent, true);
        setContentPane(articleHistoryTextWindowMainPanel);
        pack();
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public JPanel getPanel() {
        return articleHistoryTextWindowMainPanel;
    }

    public void setArticleHistoryTextWindow() {
        textPane.setContentType("text/html");
        textPane.setEditable(false);
        textPane.getCaret().setVisible(false);
        textPane.setCaretColor(textPane.getBackground());
        try {
            ArticleVersion articleVersion = Controller.getArticleVersionByIdArticleVersion(idArticleVersion);
            articleTitleJLabel.setText("Titolo Articolo: " + articleVersion.getParentArticle().getTitle());
            authorProposalJLabel.setText("Autore Proposta: " + articleVersion.getAuthorProposal().getNickname());
            textPane.setText(articleVersion.getText());
        } catch (SQLException e) {
            ErrorDisplayer.showError(e);
            articleTitleJLabel.setText("Titolo Articolo: " + "!ERRORE DI VISUALIZZAZIONE!");
            authorProposalJLabel.setText("Autore Proposta: " + "!ERRORE DI VISUALIZZAZIONE!");
            textPane.setText("!ERRORE DI VISUALIZZAZIONE!");
        }

    }

    public void setIdArticleVersion(int idArticleVersion) {
        this.idArticleVersion = idArticleVersion;
    }

}
