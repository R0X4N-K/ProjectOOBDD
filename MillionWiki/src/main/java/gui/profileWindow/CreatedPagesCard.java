package gui.profileWindow;

import controller.Controller;
import model.Article;
import model.ArticleVersion;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static controller.Controller.getAllArticleVersionByArticleId;

public class CreatedPagesCard {
    private JPanel createdPagesCardPanel;
        private JTable createdPagesJTable;
    private JScrollPane createdPagesJTableJScrollPane;
    private JScrollPane createdPagesCardPanelJScrollPane;


    public JPanel getPanel() {
        return createdPagesCardPanel;
    }

    private void createUIComponents() {
        createdPagesJTable = createJTable();
    }

    private JTable createJTable() {
        int idAuthor= Controller.getCookie().getId();
        List<Article> articles = Controller.getArticlesByIdAuthor(idAuthor);

        // Convert the list of articles to a 2D array for the table model
        Object[][] data = new Object[articles.size()][6];
        for (int i = 0; i < articles.size(); i++) {
            Article article = articles.get(i);
            ArrayList<ArticleVersion> articleVersions = getAllArticleVersionByArticleId(article.getId());
            Date lastRevisionDate = getLastRevisionDate(articleVersions);
            data[i][0] = article.getTitle();
            data[i][1] = article.getCreationDate();
            if (lastRevisionDate != null) {
                data[i][2] = lastRevisionDate;
            } else {
                data[i][2] = "N/A";
            }
            data[i][3] = articleVersions.size();
            data[i][4] = getCountWaitingProposal(articleVersions);
            data[i][5] = getCountAcceptedProposal(articleVersions);
        }

        String[] columns = {"Titolo", "Data Creazione", "Ultima Revisione", "Modifiche Ricevute","Modifiche in Attesa", "Modifiche Apportate"};

        JTable table = new JTable(data, columns);
        return table;
    }
    private int getReceivedProposal(ArrayList<ArticleVersion> articleVersions) {
        return articleVersions.size();
    }
    private int getCountWaitingProposal(ArrayList<ArticleVersion> articleVersions) {
        int waitingCount = 0;
        for (ArticleVersion articleVersion : articleVersions) {
            if (articleVersion.getStatus() == ArticleVersion.Status.WAITING) {
                waitingCount++;
            }
        }
        return waitingCount;
    }
    private int getCountAcceptedProposal(ArrayList<ArticleVersion> articleVersions) {
        int waitingCount = 0;
        for (ArticleVersion articleVersion : articleVersions) {
            if (articleVersion.getStatus() == ArticleVersion.Status.ACCEPTED) {
                waitingCount++;
            }
        }
        return waitingCount;
    }
    private Date getLastRevisionDate(ArrayList<ArticleVersion> articleVersions) {
        Date lastRevisionDate = null;
        for (ArticleVersion articleVersion : articleVersions) {
            Date revisionDate = articleVersion.getRevisionDate();
            if (revisionDate != null && (lastRevisionDate == null || revisionDate.after(lastRevisionDate))) {
                lastRevisionDate = revisionDate;
            }
        }
        return lastRevisionDate;
    }
}
