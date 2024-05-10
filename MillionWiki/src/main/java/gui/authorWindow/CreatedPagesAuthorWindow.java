package gui.authorWindow;

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

public class CreatedPagesAuthorWindow {
    private JPanel cardLayoutPanel;
    private JPanel cardCardLayoutPanelJTable;
    private JScrollPane createdPagesJTableJScrollPane;
    private JTable createdPagesJTable;
    private JPanel cardCardLayoutPanel2;
    private JLabel cardCardLayoutPanel2JLabel;
    private JPanel createdPagesAuthorMainPanel;

    public CreatedPagesAuthorWindow() {


        createdPagesJTable.addMouseListener(createMouseListener());
    }
    public JPanel getPanel() {
        return createdPagesAuthorMainPanel;
    }
    public void switchPanel(JPanel refPanel) {
        cardLayoutPanel.removeAll();
        cardLayoutPanel.add(refPanel);
        cardLayoutPanel.repaint();
        cardLayoutPanel.revalidate();
    }
    private void createUIComponents() {
        createdPagesJTable= new JTable();
    }

    private MouseAdapter createMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = createdPagesJTable.rowAtPoint(e.getPoint());
                int col = createdPagesJTable.columnAtPoint(e.getPoint());
                if (col == 0) {
                    String link = (String) createdPagesJTable.getValueAt(row, col);
                    String idString = link.substring(link.indexOf("'") + 1, link.indexOf("'", link.indexOf("'") + 1));
                    int id = Integer.parseInt(idString);
                    Controller.getWindow().getprofileWindow().setVisible(false);
                    Article article = Controller.getArticlesById(id);
                    Controller.getWindow().getPage().openPage(article);
                    Controller.getWindow().switchPanel(Controller.getWindow().getPage().getPanel());
                }
            }
        };
    }
    private void addHandCursorToTable() {
        createdPagesJTable.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int col = createdPagesJTable.columnAtPoint(e.getPoint());
                if (col == 0) {
                    createdPagesJTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    createdPagesJTable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }

    private JTable createJTable() {
        int idAuthor= Controller.getWindow().getAuthorWindow().getIdAuthor();
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
            column.setPreferredWidth(95); // Imposta la larghezza preferita della colonna
            if (i>0){ // Imposta le colonne non modificabili dalla seconda in poi
                column.setResizable(false);
            }
        }
        return table;
    }

    public void setCreatedPagesJTable(){
        createdPagesJTable = createJTable();
    }
    public void setCreatedPages() {
        switchPanel(cardCardLayoutPanel2);
        cardCardLayoutPanel2JLabel.setText("Caricamento");
        setCreatedPagesJTable();
        if (createdPagesJTable.getRowCount() > 0) {
            switchPanel(cardCardLayoutPanelJTable);
            createdPagesJTable.addMouseListener(createMouseListener());
            addHandCursorToTable();
            createdPagesJTableJScrollPane.setViewportView(createdPagesJTable);
            createdPagesJTableJScrollPane.revalidate();
            createdPagesJTableJScrollPane.repaint();
        } else{
            cardCardLayoutPanel2JLabel.setText("Nessuna pagina ancora creata dall'autore");
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
