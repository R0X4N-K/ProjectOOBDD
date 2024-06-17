package gui.profileWindow;

import controller.Controller;
import gui.ErrorDisplayer;
import gui.articleHistory.ArticleHistory;
import model.ArticleVersion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.SQLException;
import java.util.List;
import java.util.*;

import static gui.profileWindow.ProfileWindow.getMouseAdapter;

public class ProposalCard {

    private JPanel proposalCardMainPanel;
    private JTable reloadedJTable;
    private JScrollPane reloadedJScrollPane;
    private JPanel cardLayoutPanel;
    private JLabel reloadingJLabel;
    private JPanel reloading;
    private JPanel reloaded;

    public JPanel getPanel() {
        return proposalCardMainPanel;
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

    private MouseAdapter createArticleMouseListener() {
        return getMouseAdapter(reloadedJTable);
    }

    private MouseAdapter createArticleHistoryMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = reloadedJTable.rowAtPoint(e.getPoint());
                int col = reloadedJTable.columnAtPoint(e.getPoint());
                if (col == 1) {
                    String link = (String) reloadedJTable.getValueAt(row, col);
                    String idString = link.substring(link.indexOf("'") + 1, link.indexOf("'", link.indexOf("'") + 1));
                    int id = Integer.parseInt(idString);
                    ArticleHistory articleHistory = Controller.getWindow().getArticleHistory();
                    articleHistory.setIdArticle(id);

                    articleHistory.setArticleHistory();
                    articleHistory.setVisible(true);
                }
            }
        };
    }

    private MouseAdapter createAuthorMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = reloadedJTable.rowAtPoint(e.getPoint());
                int col = reloadedJTable.columnAtPoint(e.getPoint());
                if (col == 2) {
                    String link = (String) reloadedJTable.getValueAt(row, col);
                    String idString = link.substring(link.indexOf("'") + 1, link.indexOf("'", link.indexOf("'") + 1));
                    int id = Integer.parseInt(idString);
                    // Codice per aprire la finestra AuthorWindow
                    Controller.getWindow().getAuthorWindow().setIdAuthor(id);
                    Controller.getWindow().getAuthorWindow().setAuthorWindow();
                    Controller.getWindow().getAuthorWindow().setVisible(true);
                }
            }
        };
    }

    private void addHandCursorToTable() {
        reloadedJTable.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int col = reloadedJTable.columnAtPoint(e.getPoint());
                if (col == 0 || col == 1 || col == 2) {
                    reloadedJTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    reloadedJTable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }

    private JTable createJTable() {
        int idAuthor = Controller.getCookie().getId();
        reloadingJLabel.setText("Caricamento proposte");
        try {
            List<ArticleVersion> versionArticles = Controller.getAllArticleVersionsExcludingTextByAuthorId(idAuthor);
            Set<Integer> uniqueArticleIds = new HashSet<>();
            for (ArticleVersion versionArticle : versionArticles) {
                if (versionArticle.getParentArticle().getAuthor().getId() != idAuthor) {
                    uniqueArticleIds.add(versionArticle.getParentArticle().getId());
                }
            }
            Object[][] data = new Object[uniqueArticleIds.size()][8];
            int j = 0;
            for (Integer idArticle : uniqueArticleIds) {
                List<ArticleVersion> filteredArticles = new ArrayList<>();
                for (ArticleVersion versionArticle : versionArticles) {
                    if (versionArticle.getParentArticle().getId() == idArticle) {
                        filteredArticles.add(versionArticle);
                    }
                }
                if (!filteredArticles.isEmpty()) {
                    data[j][0] = "<html><a href='" + filteredArticles.getFirst().getParentArticle().getId() + "'>" + filteredArticles.getFirst().getParentArticle().getTitle() + "</a></html>";
                    data[j][1] = "<html><a href='" + filteredArticles.getFirst().getParentArticle().getId() + "'>Link</a></html>"; // Aggiunge la nuova colonna "Storico"
                    data[j][2] = "<html><a href='" + filteredArticles.getFirst().getParentArticle().getAuthor().getId() + "'>" + Controller.getNicknameAuthorById(filteredArticles.getFirst().getParentArticle().getAuthor().getId()) + "</a></html>";
                    data[j][3] = getSentProposalCount(filteredArticles, idArticle);
                    data[j][4] = getLastSentProposalDate(filteredArticles, idArticle);
                    data[j][5] = getAcceptedProposalCount(filteredArticles, idArticle);
                    data[j][6] = getRejectedProposalCount(filteredArticles, idArticle);
                    data[j][7] = getWaitedProposalCount(filteredArticles, idArticle);
                    j++;
                }
            }

            String[] columns = {"Articolo", "Storico", "Autore", "Proposte inviate", "Ultima Inviata", "Proposte Accettate", "Proposte Rifiutate", "Proposte in Attesa"}; // Aggiunge la nuova colonna "Storico"
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
                if (i > 0) {
                    column.setResizable(false);
                }
            }
            columnModel.getColumn(1).setPreferredWidth(50);
            columnModel.getColumn(5).setPreferredWidth(110);
            return table;
        } catch (SQLException | IllegalArgumentException e) {
            ErrorDisplayer.showError(e);
            reloadingJLabel.setText("Caricamento proposte fallito");
        }
        return null;
    }

    private void setProposalCardJTable() {
        switchPanel(reloading);
        reloadingJLabel.setText("");
        reloadingJLabel.setIcon(new ImageIcon(ProposalCard.class.getResource("/icons/loading.gif")));
        reloadedJTable = createJTable();
        if (reloadedJTable != null) {
            if (reloadedJTable.getRowCount() > 0) {
                switchPanel(reloaded);
                reloadedJTable.addMouseListener(createArticleMouseListener());
                reloadedJTable.addMouseListener(createAuthorMouseListener());
                reloadedJTable.addMouseListener(createArticleHistoryMouseListener());
                addHandCursorToTable();
                reloadedJScrollPane.setViewportView(reloadedJTable);
                reloadedJScrollPane.revalidate();
                reloadedJScrollPane.repaint();
            } else {
                reloadingJLabel.setIcon(
                        new ImageIcon(ProposalCard.class.getResource("/icons/404.png"))
                );
                reloadingJLabel.setText("Nessuna Proposta di modifica inviata");
            }
        } else {
            reloadingJLabel.setText("Caricamento Fallito");
        }
    }

    public void setProposalCard() {
        setProposalCardJTable();
    }

    private int getSentProposalCount(List<ArticleVersion> versionArticles, int idArticle) {
        int count = 0;
        for (ArticleVersion versionArticle : versionArticles) {
            if (versionArticle.getParentArticle().getId() == idArticle) {
                count++;
            }
        }
        return count;
    }

    private Date getLastSentProposalDate(List<ArticleVersion> versionArticles, int idArticle) {
        Date lastProposalDate = null;
        for (ArticleVersion versionArticle : versionArticles) {
            if (versionArticle.getParentArticle().getId() == idArticle) {
                Date proposalDate = versionArticle.getVersionDate();
                if (proposalDate != null && (lastProposalDate == null || proposalDate.after(lastProposalDate))) {
                    lastProposalDate = proposalDate;
                }
            }
        }
        return lastProposalDate;
    }

    private int getAcceptedProposalCount(List<ArticleVersion> versionArticles, int idArticle) {
        int count = 0;
        for (ArticleVersion versionArticle : versionArticles) {
            if (versionArticle.getParentArticle().getId() == idArticle && versionArticle.getStatus() == ArticleVersion.Status.ACCEPTED) {
                count++;
            }
        }
        return count;
    }

    private int getRejectedProposalCount(List<ArticleVersion> versionArticles, int idArticle) {
        int count = 0;
        for (ArticleVersion versionArticle : versionArticles) {
            if (versionArticle.getParentArticle().getId() == idArticle && versionArticle.getStatus() == ArticleVersion.Status.REJECTED) {
                count++;
            }
        }
        return count;
    }

    private int getWaitedProposalCount(List<ArticleVersion> versionArticles, int idArticle) {
        int count = 0;
        for (ArticleVersion versionArticle : versionArticles) {
            if (versionArticle.getParentArticle().getId() == idArticle && versionArticle.getStatus() == ArticleVersion.Status.WAITING) {
                count++;
            }
        }
        return count;
    }


}
