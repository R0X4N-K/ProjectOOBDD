package gui.profileWindow;

import controller.Controller;
import model.Article;
import model.ArticleVersion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class ProposalCard {
    private JPanel proposalCardPanel;
    private JTable proposalCardJTable;
    private JScrollPane proposalCardJTableJScrollPane;
    private JPanel subProposalCardPanelCards;
    private JLabel subProposalCardPanelCardsJLabel;
    private JPanel panelJLabelsubProposalCardPanelCards;
    private JPanel panelJTableJScrollPane;

    public JPanel getPanel() {
        return proposalCardPanel;
    }
    public void switchPanel(JPanel refPanel) {
        subProposalCardPanelCards.removeAll();
        subProposalCardPanelCards.add(refPanel);
        subProposalCardPanelCards.repaint();
        subProposalCardPanelCards.revalidate();
    }

    private void createUIComponents() {
            proposalCardJTable = new JTable();
    }
    private MouseAdapter createMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = proposalCardJTable.rowAtPoint(e.getPoint());
                int col = proposalCardJTable.columnAtPoint(e.getPoint());
                if (col == 0) {
                    String link = (String) proposalCardJTable.getValueAt(row, col);
                    String idString = link.substring(link.indexOf("'") + 1, link.lastIndexOf("'"));
                    int id = Integer.parseInt(idString);
                    Controller.getWindow().getprofileWindow().setVisible(false);
                    Article article = Controller.getArticlesById(id);
                    Controller.getWindow().getPage().setTitlePageField(article.getTitle());
                    Controller.getWindow().getPage().setTextPageField(Controller.getLastArticleVersionByArticleId(id).getText());
                    Controller.getWindow().switchPanel(Controller.getWindow().getPage().getPanel());
                }
            }
        };}

    private JTable createTable() {
        int idAuthor= Controller.getCookie().getId();
        List<ArticleVersion> versionArticles = Controller.getAllArticleVersionByAuthorId(idAuthor);
        Set<Integer> uniqueArticleIds = new HashSet<>();
        for (ArticleVersion versionArticle : versionArticles) {
            uniqueArticleIds.add(versionArticle.getParentArticle().getId());
        }
        Object[][] data = new Object[uniqueArticleIds.size()][7];
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
                data[j][1] = Controller.getNicknameAuthorById(filteredArticles.get(0).getParentArticle().getAuthor().getId());
                data[j][2] = getSentProposalCount(filteredArticles, idArticle);
                data[j][3] = getLastSentProposalDate(filteredArticles, idArticle);
                data[j][4] = getAcceptedProposalCount(filteredArticles, idArticle);
                data[j][5] = getRejectedProposalCount(filteredArticles, idArticle);
                data[j][6] = getWaitedProposalCount(filteredArticles, idArticle);
                j++;
            }
        }

        String[] columns = {"Articolo", "Autore", "Proposte inviate", "Ultima Inviata", "Proposte Accettate", "Proposte Rifiutate", "Proposte in Attesa"};
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
            column.setPreferredWidth(100); // Imposta la larghezza preferita della colonna
            if (i>0){ // Imposta le colonne non modificabili dalla seconda in poi
                column.setResizable(false);
            }
        }
        columnModel.getColumn(4).setPreferredWidth(110);
        return table;
    }

    private void setProposalCardJTable() {
        proposalCardJTable = createTable();
        if (proposalCardJTable.getRowCount() > 0) {
            switchPanel(panelJTableJScrollPane);
            proposalCardJTable.addMouseListener(createMouseListener());
            proposalCardJTableJScrollPane.setViewportView(proposalCardJTable);
            proposalCardJTableJScrollPane.revalidate();
            proposalCardJTableJScrollPane.repaint();
        } else {
            switchPanel(panelJLabelsubProposalCardPanelCards);
        }


    }
    public void setProposalCard(){
        setProposalCardJTable();
    }
    private int getSentProposalCount(List<ArticleVersion> versionArticles, int idArticle){
        int count = 0;
        for (ArticleVersion versionArticle : versionArticles) {
            if (versionArticle.getParentArticle().getId() == idArticle) {
                count++;
            }
        }
        return count;
    }

    private Date getLastSentProposalDate(List<ArticleVersion> versionArticles, int idArticle){
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
    private int getAcceptedProposalCount(List<ArticleVersion> versionArticles, int idArticle){
        int count = 0;
        for (ArticleVersion versionArticle : versionArticles) {
            if (versionArticle.getParentArticle().getId() == idArticle && versionArticle.getStatus() == ArticleVersion.Status.ACCEPTED) {
                count++;
            }
        }
        return count;
    }
    private int getRejectedProposalCount(List<ArticleVersion> versionArticles, int idArticle){
        int count = 0;
        for (ArticleVersion versionArticle : versionArticles) {
            if (versionArticle.getParentArticle().getId() == idArticle && versionArticle.getStatus() == ArticleVersion.Status.REJECTED) {
                count++;
            }
        }
        return count;
    }
    private int getWaitedProposalCount(List<ArticleVersion> versionArticles, int idArticle){
        int count = 0;
        for (ArticleVersion versionArticle : versionArticles) {
            if (versionArticle.getParentArticle().getId() == idArticle && versionArticle.getStatus() == ArticleVersion.Status.WAITING) {
                count++;
            }
        }
        return count;
    }


}
