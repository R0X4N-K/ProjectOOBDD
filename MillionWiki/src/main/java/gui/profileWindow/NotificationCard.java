package gui.profileWindow;

import controller.Controller;
import gui.ErrorDisplayer;
import model.ArticleVersion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.*;

import static gui.profileWindow.ProfileWindow.getMouseAdapter;
import static gui.profileWindow.ProfileWindow.getMouseMotionListener;

public class NotificationCard {
    private JPanel notificationCardMainPanel;
    private JPanel notificationCards;
    private JPanel reloaded;
    private JPanel reloading;
    private JTable notificationJTable;
    private JScrollPane scrollPaneJTable;
    private JLabel reloadingJLabel;

    private MouseAdapter createArticleMouseListener() {
        return getMouseAdapter(notificationJTable);
    }

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
        };
    }

    private void addHandCursorToTable() {
        getMouseMotionListener(notificationJTable);
    }

    public JPanel getPanel() {
        return notificationCardMainPanel;
    }

    public void switchPanel(JPanel refPanel) {
        notificationCards.removeAll();
        notificationCards.add(refPanel);
        notificationCards.repaint();
        notificationCards.revalidate();
    }

    public void setNotificationCard() {
        switchPanel(reloading);
        notificationJTable = createJTable();
        if (notificationJTable.getRowCount() > 0) {
            switchPanel(reloaded);
            notificationJTable.addMouseListener(createArticleMouseListener());
            notificationJTable.addMouseListener(createOpenNotificationMouseListener());
            addHandCursorToTable();
            scrollPaneJTable.setViewportView(notificationJTable);
            scrollPaneJTable.revalidate();
            scrollPaneJTable.repaint();
        } else {
            reloadingJLabel.setIcon(
                    new ImageIcon(ProposalCard.class.getResource("/icons/404.png"))
            );
            reloadingJLabel.setText("Nessuna notifica");
        }


    }

    private void createUIComponents() {
        notificationJTable = new JTable();
    }

    private JTable createJTable() {
        reloadingJLabel.setIcon(new ImageIcon(
                NotificationCard.class.getResource("/icons/loading.gif")
        ));

        reloadingJLabel.setText("Caricamento notifiche");
        Object[][] data = new Object[1][4];

        try {
            List<ArticleVersion> versionArticles = Controller.getNotifications();
            Set<Integer> uniqueArticleIds = new HashSet<>();
            for (ArticleVersion versionArticle : versionArticles) {
                uniqueArticleIds.add(versionArticle.getParentArticle().getId());
            }
            data = new Object[uniqueArticleIds.size()][4];
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
        } catch (SQLException | IllegalArgumentException e) {
            ErrorDisplayer.showError(e);
        }

        String[] columns = {"Articolo", "Apri", "Modifiche non valutate", "Ultima modifica non valutata"};
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
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
