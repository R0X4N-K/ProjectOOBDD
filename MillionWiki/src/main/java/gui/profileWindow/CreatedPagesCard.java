package gui.profileWindow;

import controller.Controller;
import gui.ErrorDisplayer;
import model.Article;
import model.ArticleVersion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static controller.Controller.getAllArticleVersionsExcludingTextByArticleId;
import static gui.profileWindow.ProfileWindow.getMouseAdapter;
import static gui.profileWindow.ProfileWindow.getMouseMotionListener;

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
        return getMouseAdapter(reloadedJTable);
    }
    private MouseAdapter createArticleHistoryMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = reloadedJTable.rowAtPoint(e.getPoint());
                int col = reloadedJTable.columnAtPoint(e.getPoint());
                if (col == 1) {
                    String link = (String) reloadedJTable.getValueAt(row, 0);
                    String idString = link.substring(link.indexOf("'") + 1, link.indexOf("'", link.indexOf("'") + 1));
                    int id = Integer.parseInt(idString);
                    Controller.getWindow().getArticleHistory().setIdArticle(id);
                    Controller.getWindow().getArticleHistory().setArticleHistory();
                    Controller.getWindow().getArticleHistory().setVisible(true);
                }
            }
        };
    }
    private void addHandCursorToTable() {
        getMouseMotionListener(reloadedJTable);
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
        reloadingJLabel.setIcon(new ImageIcon(CreatedPagesCard.class.getResource("/icons/loading.gif")));
        reloadingJLabel.setText("Inizio caricamento");
        Object[][] data = new Object[1][7];
        try {
            List<Article> articles = Controller.getArticlesByIdAuthor(idAuthor);
            reloadingJLabel.setText("Caricamento articoli terminato");
            data = new Object[articles.size()][7]; // Modifica la dimensione dell'array a 7
            for (int i = 0; i < articles.size(); i++) {
                Article article = articles.get(i);
                reloadingJLabel.setText("Caricamento versioni articoli per l'articolo: " + article.getTitle());
                ArrayList<ArticleVersion> articleVersions = getAllArticleVersionsExcludingTextByArticleId(article.getId());
                Date lastRevisionDate = getLastRevisionDate(articleVersions);
                reloadingJLabel.setText("Caricamento dell' ultima versione articolo aggiornata di: " + article.getTitle());
                data[i][0] = "<html><a href='" + article.getId() + "'>" + article.getTitle() + "</a></html>";
                data[i][1] = "<html><a href='" + article.getId() + "'>Link</a></html>"; // Aggiunge la nuova colonna "Storico"
                data[i][2] = article.getCreationDate();
                data[i][3] = Objects.requireNonNullElse(lastRevisionDate, "N/A");
                data[i][4] = articleVersions.size();
                data[i][5] = getCountWaitingProposal(articleVersions);
                data[i][6] = getCountAcceptedProposal(articleVersions);
            }
            System.out.println("All done");
        } catch (SQLException | IllegalArgumentException e) {
            ErrorDisplayer.showError(e);
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
        reloadingJLabel.setText("");
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
            reloadingJLabel.setIcon(
                    new ImageIcon(ProposalCard.class.getResource("/icons/404.png"))
            );
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
