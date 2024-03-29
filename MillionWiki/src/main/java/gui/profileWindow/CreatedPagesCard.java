package gui.profileWindow;

import controller.Controller;
import gui.Page;
import model.Article;
import model.ArticleVersion;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static controller.Controller.getAllArticleVersionByArticleId;

public class CreatedPagesCard {
    private JPanel createdPagesCardPanel;
    private JTable createdPagesJTable;
    private JScrollPane createdPagesJTableJScrollPane;
    private JScrollPane createdPagesCardPanelJScrollPane;


    public CreatedPagesCard() {


        createdPagesJTable.addMouseListener(new MouseAdapter() {    //MouseListener per gestire i clic del mouse sulla cella
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = createdPagesJTable.rowAtPoint(e.getPoint());
                int col = createdPagesJTable.columnAtPoint(e.getPoint());
                if (col == 0) {
                    String link = (String) createdPagesJTable.getValueAt(row, col);
                    // Estrazione dell'idArticle dall'URL dell'hyperlink
                    String idString = link.substring(link.indexOf("'") + 1, link.lastIndexOf("'"));
                    int id = Integer.parseInt(idString);
                    Controller.getWindow().getprofileWindow().setVisible(false);
                    // Mostra la Page con l'articolo corrispondente
                    Article article = Controller.getArticlesById(id);
                    Controller.getWindow().getPage().setTitlePageField(article.getTitle());
                    Controller.getWindow().getPage().setTextPageField(Controller.getLastArticleVersionByArticleId(id).getText());
                    Controller.getWindow().switchPanel(Controller.getWindow().getPage().getPanel());
                }
            }
        });
    }




    public JPanel getPanel() {
        return createdPagesCardPanel;
    }

    private void createUIComponents() {

        if(Controller.getCookie() == null){
               createdPagesJTable= new JTable();
        }
        else {
            createdPagesJTable = createJTable();
        }
    }

    private JTable createJTable() {
        int idAuthor= Controller.getCookie().getId();
        List<Article> articles = Controller.getArticlesByIdAuthor(idAuthor);
        Object[][] data = new Object[articles.size()][6];
        for (int i = 0; i < articles.size(); i++) {
            Article article = articles.get(i);
            ArrayList<ArticleVersion> articleVersions = getAllArticleVersionByArticleId(article.getId());
            Date lastRevisionDate = getLastRevisionDate(articleVersions);
            data[i][0] = "<html><a href='"+article.getId()+"'>" + article.getTitle() + "</a></html>";
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

        String[] columns = {"Titolo", "Data Creazione", "Ultima Revisione", "Mod. Ricevute","Mod. in Attesa", "Mod. Apportate"};
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Tutte le celle non sono modificabili
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(100);  // Imposta la larghezza preferita della colonna
        }
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
