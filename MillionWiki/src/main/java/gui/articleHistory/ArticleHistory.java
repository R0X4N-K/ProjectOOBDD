package gui.articleHistory;
import gui.ErrorDisplayer;
import gui.page.PreviewPage;
import gui.profileWindow.CreatedPagesCard;
import gui.profileWindow.ProposalCard;
import model.Article;
import model.ArticleVersion;
import controller.Controller;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.SQLException;
import java.util.*;

public class ArticleHistory extends JDialog {
    private JPanel articleHistoryMainPanel;
    private JPanel articleHistoryCards;
    private JPanel cardReloaded;
    private JPanel cardReloading;
    private JTable articleHistoryJTable;
    private JScrollPane articleHistoryJTableJScrollPane;
    private JLabel reloadingJLabel;
    private JLabel articleTitle;
    private JLabel authorArticle;
    private int idArticle;
    public ArticleHistory(JFrame parent) {
        super(parent, true);
        setContentPane(articleHistoryMainPanel);
        pack();
        setTitle("Storico");
        setSize(700, 600);
        setMinimumSize(new Dimension(600, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    private void createUIComponents() {
        articleHistoryJTable = new JTable();
    }
    private JPanel getPanel() {
        return articleHistoryMainPanel;
    }
    private void switchPanel(JPanel panel) {
        articleHistoryCards.removeAll();
        articleHistoryCards.add(panel);
        articleHistoryCards.repaint();
        articleHistoryCards.revalidate();
    }
    private MouseAdapter createArticleMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = articleHistoryJTable.rowAtPoint(e.getPoint());
                int col = articleHistoryJTable.columnAtPoint(e.getPoint());
                if (col == 0) {
                    String link = (String) articleHistoryJTable.getValueAt(row, col);
                    String idString = link.substring(link.indexOf("'") + 1, link.indexOf("'", link.indexOf("'") + 1));
                    int id = Integer.parseInt(idString);

                    //TODO: rimuovere tutto ArticleHistoryTextWindow
                    /*Controller.getWindow().getArticleHistoryTextWindow().setIdArticleVersion(id);
                    Controller.getWindow().getArticleHistoryTextWindow().setArticleHistoryTextWindow();
                    Controller.getWindow().getArticleHistoryTextWindow().setVisible(true);*/


                    try {
                        ArticleVersion a = Controller.getArticleVersionByIdArticleVersion(id);
                        String titleDlg = "Articolo: '" + a.getParentArticle().getTitle() + "' - Revisione del " + a.getRevisionDate();


                        new PreviewPage(titleDlg, a.getTitleProposal(), a.getText());

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        };
    }

    private MouseAdapter createAuthorMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = articleHistoryJTable.rowAtPoint(e.getPoint());
                int col = articleHistoryJTable.columnAtPoint(e.getPoint());
                if (col == 2) {
                    String link = (String) articleHistoryJTable.getValueAt(row, col);
                    String idString = link.substring(link.indexOf("'") + 1, link.indexOf("'", link.indexOf("'") + 1));
                    int id = Integer.parseInt(idString);
                    Controller.getWindow().getAuthorWindow().setIdAuthor(id);
                    Controller.getWindow().getAuthorWindow().setAuthorWindow();
                    Controller.getWindow().getAuthorWindow().setVisible(true);
                }
            }
        };
    }

    private void addHandCursorToTable() {
        articleHistoryJTable.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                int col = articleHistoryJTable.columnAtPoint(e.getPoint());
                if (col == 0 || col == 2) { // Check if "Testo" or "Autore" column is hovered
                    articleHistoryJTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    articleHistoryJTable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }
    public void setArticleHistory(){
        try {
            Article article = Controller.getArticlesById(idArticle);
            articleTitle.setText(article.getTitle());
            authorArticle.setText(article.getAuthor().getNickname());
            switchPanel(cardReloading);
            reloadingJLabel.setText("");
            setTitle(article.getTitle() + " | Storico");
            setArticleHistoryJTable();
        } catch (SQLException | IllegalArgumentException e) {
            ErrorDisplayer.showError(e);
        }
    }
    public void setArticleHistoryJTable(){
        Thread thread = null;
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(() -> {
                articleHistoryJTable= createJTable();
                if (articleHistoryJTable.getRowCount() >0 ){
                    switchPanel(cardReloaded);
                    articleHistoryJTable.addMouseListener(createArticleMouseListener());
                    articleHistoryJTable.addMouseListener(createAuthorMouseListener());
                    addHandCursorToTable();
                    articleHistoryJTableJScrollPane.setViewportView(articleHistoryJTable);
                    articleHistoryJTableJScrollPane.revalidate();
                    articleHistoryJTableJScrollPane.repaint();
                }
                else{
                    switchPanel(cardReloading);
                    reloadingJLabel.setIcon(
                            new ImageIcon(ProposalCard.class.getResource("/icons/404.png"))
                    );
                    reloadingJLabel.setText("Nessuna versione trovata");
                }
            });
        }
        thread.setDaemon(true);
        thread.start();
       }
    private JTable createJTable() {
        reloadingJLabel.setIcon(new ImageIcon(CreatedPagesCard.class.getResource("/icons/loading.gif")));
        reloadingJLabel.setText("Inizio caricamento");
        Object[][] data = new Object[1][5]; // Modifica la dimensione dell'array a 5
        try {
            ArrayList<ArticleVersion> articleVersions = Controller.getAllArticleVersionsExcludingTextByArticleId(idArticle);

            // Fa il sort delle versioni dell'articolo in base alla data di revisione
            articleVersions.sort(Comparator.comparing(ArticleVersion::getVersionDate));

            data = new Object[articleVersions.size()][5]; // Modifica la dimensione dell'array a 5
            reloadingJLabel.setText("Caricamento storico");
            for (int i = 0; i < articleVersions.size(); i++) {
                ArticleVersion articleVersion = articleVersions.get(i);
                data[i][0] = "<html><a href='" + articleVersion.getId() + "'>Visualizza</a></html>";
                data[i][1] = articleVersion.getStatus();
                data[i][2] = "<html><a href='" + articleVersion.getAuthorProposal().getId() + "'>" + articleVersion.getAuthorProposal().getNickname() + "</a></html>";
                data[i][3] = articleVersion.getVersionDate();
                if (articleVersion.getRevisionDate() != null) {
                    data[i][4] = articleVersion.getRevisionDate();
                } else {
                    data[i][4] = "N/A";
                }
            }
        } catch (SQLException | IllegalArgumentException e) {
            ErrorDisplayer.showError(e);
        }

        String[] columns = {"Testo", "Stato", "Autore", "Data Invio", "Data Revisione"};

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(100);
            if (i>0){
                column.setResizable(false);
            }
        }
        return table;
    }


    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }
    public int getIdArticle() {
        return idArticle;
    }


}
