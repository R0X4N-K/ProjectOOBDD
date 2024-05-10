package gui.profileWindow;

import controller.Controller;
import gui.articleHistory.ArticleHistory;
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
import java.util.*;
import java.util.List;

public class NotificationCard {
    private JPanel notificationCardMainPanel;
    private JPanel notificationCards;
    private JPanel reloaded;
    private JPanel reloading;
    private JTable notificationJTable;
    private JScrollPane scrollPaneJTable;
    private JLabel reloadingJLabel;

    private MouseAdapter createArticleMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = notificationJTable.rowAtPoint(e.getPoint());
                int col = notificationJTable.columnAtPoint(e.getPoint());
                if (col == 0) {
                    String link = (String) notificationJTable.getValueAt(row, col);
                    String idString = link.substring(link.indexOf("'") + 1, link.indexOf("'", link.indexOf("'") + 1));
                    int id = Integer.parseInt(idString);
                    Controller.getWindow().getprofileWindow().setVisible(false);
                    Article article = Controller.getArticlesById(id);
                    Controller.getWindow().getPage().setTitlePageField(article.getTitle());
                    Controller.getWindow().getPage().setTextPageField(Controller.getLastArticleVersionByArticleId(id).getText());
                    Controller.getWindow().switchPanel(Controller.getWindow().getPage().getPanel());
                }
            }
        };}
    private MouseAdapter createOpenNotificationMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = notificationJTable.rowAtPoint(e.getPoint());
                int col = notificationJTable.columnAtPoint(e.getPoint());
                if (col == 1) {
                    String link = (String) notificationJTable.getValueAt(row, col);
                    String idString = link.substring(link.indexOf("'") + 1, link.indexOf("'", link.indexOf("'") + 1));
                    int id = Integer.parseInt(idString);
                    Controller.getWindow().getprofileWindow().setVisible(false);
                    Controller.getWindow().getPage().setReviewerMode(id);
                }
            }
        };}
    private void addHandCursorToTable() {
        notificationJTable.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int col = notificationJTable.columnAtPoint(e.getPoint());
                if (col == 0 || col == 1) {
                    notificationJTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    notificationJTable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }
    public JPanel getPanel(){
        return notificationCardMainPanel;
    }
    public void switchPanel(JPanel refPanel){
        notificationCards.removeAll();
        notificationCards.add(refPanel);
        notificationCards.repaint();
        notificationCards.revalidate();
    }

    public void setNotificationCard(){
        switchPanel(reloading);
        reloadingJLabel.setText("Caricamento");
        notificationJTable = createJTable();
        if(notificationJTable.getRowCount() > 0){
            switchPanel(reloaded);
            notificationJTable.addMouseListener(createArticleMouseListener());
            notificationJTable.addMouseListener(createOpenNotificationMouseListener());
            addHandCursorToTable();
            scrollPaneJTable.setViewportView(notificationJTable);
            scrollPaneJTable.revalidate();
            scrollPaneJTable.repaint();
        } else{reloadingJLabel.setText("Nessuna notifica");}


    }

    private void createUIComponents() {
        notificationJTable = new JTable();
    }
    private JTable createJTable() {
        List<ArticleVersion> versionArticles = Controller.getNotifications();
        Set<Integer> uniqueArticleIds = new HashSet<>();
        for (ArticleVersion versionArticle : versionArticles) {
            uniqueArticleIds.add(versionArticle.getParentArticle().getId());
        }
        Object[][] data = new Object[uniqueArticleIds.size()][4];
        int j = 0;
        for (Integer idArticle : uniqueArticleIds) {
            List<ArticleVersion> filteredArticles = new ArrayList<>();
            for (ArticleVersion versionArticle : versionArticles) {
                if (versionArticle.getParentArticle().getId() == idArticle) {
                    filteredArticles.add(versionArticle);
                }
            }
            if (!filteredArticles.isEmpty()) {
                data[j][0] = "<html><a href='" + filteredArticles.get(0).getParentArticle().getId() + "'>" + filteredArticles.get(0).getParentArticle().getTitle() + "</a></html>";
                data[j][1] = "<html><a href='" + filteredArticles.get(0).getParentArticle().getId() + "'>" + "Apri";
                data[j][2] = getUnreviewedModificationsCount(filteredArticles, idArticle);
                data[j][3] = getLastUnreviewedModificationDate(filteredArticles, idArticle);
                j++;
            }
        }

        String[] columns = {"Articolo", "Apri", "Modifiche non valutate", "Ultima modifica non valutata"};
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
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(1).setPreferredWidth(50);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(170);
        return table;
    }
    private int getUnreviewedModificationsCount(List<ArticleVersion> versionArticles, int idArticle) {
        int count = 0;
        for (ArticleVersion versionArticle : versionArticles) {
            if (versionArticle.getParentArticle().getId() == idArticle && versionArticle.getStatus() == ArticleVersion.Status.WAITING) {
                count++;
            }
        }
        return count;
    }

    private Date getLastUnreviewedModificationDate(List<ArticleVersion> versionArticles, int idArticle) {
        Date latestDate = null;
        for (ArticleVersion versionArticle : versionArticles) {
            if (versionArticle.getParentArticle().getId() == idArticle && versionArticle.getStatus() == ArticleVersion.Status.WAITING) {
                Date versionDate = versionArticle.getVersionDate();
                if (versionDate != null && (latestDate == null || versionDate.after(latestDate))) {
                    latestDate = versionDate;
                }
            }
        }
        return latestDate;
    }

}
