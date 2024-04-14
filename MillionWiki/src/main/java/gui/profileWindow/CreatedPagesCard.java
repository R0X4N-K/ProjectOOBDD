package gui.profileWindow;

import controller.Controller;
import model.Article;
import model.ArticleVersion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static controller.Controller.getAllArticleVersionByArticleId;

public class CreatedPagesCard {
    private JPanel createdPagesCardMainPanel;
    private JTable reloadedJTable;
    private JScrollPane reloadedJScrollPane;
    private JPanel cardLayoutPanel;
    private JPanel reloaded;
    private JPanel reloading;
    private JLabel reloadingJLabel;


    public CreatedPagesCard() {
    }


    private MouseAdapter createArticleTitleMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = reloadedJTable.rowAtPoint(e.getPoint());
                int col = reloadedJTable.columnAtPoint(e.getPoint());
                if (col == 0) {
                    String link = (String) reloadedJTable.getValueAt(row, col);
                    String idString = link.substring(link.indexOf("'") + 1, link.lastIndexOf("'"));
                    int id = Integer.parseInt(idString);
                    Controller.getWindow().getprofileWindow().setVisible(false);
                    Article article = Controller.getArticlesById(id);
                    Controller.getWindow().getPage().openPage(article.getTitle(), Controller.getLastArticleVersionByArticleId(id).getText(), id);
                    Controller.getWindow().switchPanel(Controller.getWindow().getPage().getPanel());
                }
            }
        };
    }
    private MouseAdapter createArticleHistoryMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = reloadedJTable.rowAtPoint(e.getPoint());
                int col = reloadedJTable.columnAtPoint(e.getPoint());
                if (col == 1) {
                    String link = (String) reloadedJTable.getValueAt(row, 0);
                    String idString = link.substring(link.indexOf("'") + 1, link.lastIndexOf("'"));
                    int id = Integer.parseInt(idString);
                    Controller.getWindow().getArticleHistory().setIdArticle(id);
                    Controller.getWindow().getArticleHistory().setArticleHistory();
                    Controller.getWindow().getArticleHistory().setVisible(true);
                }
            }
        };
    }
    private void addHandCursorToTable() {
        reloadedJTable.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int col = reloadedJTable.columnAtPoint(e.getPoint());
                if (col == 0 || col == 1) {
                    reloadedJTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    reloadedJTable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }

    public JPanel getPanel() {
        return createdPagesCardMainPanel;
    }
    public void switchPanel(JPanel refPanel) {
        cardLayoutPanel.removeAll();
        cardLayoutPanel.add(refPanel);
        cardLayoutPanel.repaint();
        cardLayoutPanel.revalidate();
    }

    private void createUIComponents() {
               reloadedJTable = new JTable();

    }

    private JTable createJTable() {
        int idAuthor= Controller.getCookie().getId();
        List<Article> articles = Controller.getArticlesByIdAuthor(idAuthor);
        Object[][] data = new Object[articles.size()][7]; // Modifica la dimensione dell'array a 7
        for (int i = 0; i < articles.size(); i++) {
            Article article = articles.get(i);
            ArrayList<ArticleVersion> articleVersions = getAllArticleVersionByArticleId(article.getId());
            Date lastRevisionDate = getLastRevisionDate(articleVersions);
            data[i][0] = "<html><a href='" + article.getId() + "'>" + article.getTitle() + "</a></html>";
            data[i][1] = "<html><a href='"+ article.getId() + "'>Link</a></html>"; // Aggiunge la nuova colonna "Storico"
            data[i][2] = article.getCreationDate();
            if (lastRevisionDate != null) {
                data[i][3] = lastRevisionDate;
            } else {
                data[i][3] = "N/A";
            }
            data[i][4] = articleVersions.size();
            data[i][5] = getCountWaitingProposal(articleVersions);
            data[i][6] = getCountAcceptedProposal(articleVersions);
        }

        String[] columns = {"Titolo", "Storico", "Data Creazione", "Ultima Revisione", "Mod. Ricevute","Mod. in Attesa", "Mod. Apportate"}; // Aggiunge la nuova colonna "Storico"
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
            if (i == 1) {
                column.setPreferredWidth(50); // Imposta la larghezza preferita della colonna
            } else {
                column.setPreferredWidth(100); // Imposta la larghezza preferita della colonna
            }
            // Imposta la larghezza preferita della colonna
            if (i>0){ // Imposta le colonne non modificabili dalla seconda in poi
                column.setResizable(false);
            }
        }
        return table;
    }

    public void setCreatedPagesJTable(){
        reloadedJTable = createJTable();
    }
    public void setCreatedPages() {
        switchPanel(reloading);
        reloadingJLabel.setText("Caricamento");
        setCreatedPagesJTable();
        if (reloadedJTable.getRowCount() > 0){
            switchPanel(reloaded);
            reloadedJTable.addMouseListener(createArticleTitleMouseListener());
            reloadedJTable.addMouseListener(createArticleHistoryMouseListener());
            addHandCursorToTable();
            reloadedJScrollPane.setViewportView(reloadedJTable);
            reloadedJScrollPane.revalidate();
            reloadedJScrollPane.repaint();}
        else {
            reloadingJLabel.setText("Nessuna pagina ancora creata");
        }
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
