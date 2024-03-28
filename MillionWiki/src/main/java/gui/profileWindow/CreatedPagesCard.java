package gui.profileWindow;

import controller.Controller;
import model.Article;

import javax.swing.*;
import java.util.List;

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
            data[i][0] = article.getTitle();
            data[i][1] = article.getCreationDate();
            data[i][2] = "Ultima Revisione" + (i+1); //TODO: fare la query per trovare l'ultima revisione...
            data[i][3] = "Modifiche Ricevute" + (i+1);
            data[i][4] = "Modifiche in Attesa" + (i+1);
            data[i][5] = "Modifiche Apportate" + (i+1);
        }

        String[] columns = {"Titolo", "Data Creazione", "Ultima Revisione", "Modifiche Ricevute","Modifiche in Attesa", "Modifiche Apportate"};

        JTable table = new JTable(data, columns);
        return table;
    }
}
