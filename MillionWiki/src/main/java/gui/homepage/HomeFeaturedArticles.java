package gui.homepage;

import controller.Controller;
import gui.ErrorDisplayer;
import model.Article;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class HomeFeaturedArticles {
    private JPanel homeFeaturedArticlesMainPanel;
    private Thread thread;

    public HomeFeaturedArticles() {
        homeFeaturedArticlesMainPanel.setLayout(new GridLayout(2, 5, 10, 10));

        // Crea un nuovo font per il titolo del bordo
        Font titleFont = new Font("Times New Roman", Font.PLAIN, 30);

        // Crea un bordo con il titolo personalizzato e applica il font
        TitledBorder titledBorder = new TitledBorder("Articoli popolari");
        titledBorder.setTitleFont(titleFont);
        titledBorder.setTitleJustification(TitledBorder.LEFT);
        titledBorder.setTitlePosition(TitledBorder.TOP);

        // Crea un EmptyBorder per il padding interno
        EmptyBorder paddingBorder = new EmptyBorder(10, 10, 10, 10);  // Imposta il padding desiderato

        // Combina i due bordi
        CompoundBorder compoundBorder = new CompoundBorder(titledBorder, paddingBorder);

        // Applica il bordo composto al pannello
        homeFeaturedArticlesMainPanel.setBorder(compoundBorder);


        if (thread == null || !thread.isAlive()) {
            thread = new Thread(this::initMostViewedArticles);
        }
        thread.setDaemon(true);
        thread.start();
    }

    private void initMostViewedArticles() {

        try{
            ArrayList<Article> mostViewedArticles = Controller.getMostViewedArticles(10);
            for (Article article : mostViewedArticles) {
                try {
                    Controller.getSplashScreen().incrementProgressBar();
                    String htmlText = Controller.getLastArticleVersionByArticleId(article.getId()).getText();
                    HomeArticlePanel featuredArticle = new HomeArticlePanel(article.getTitle(), htmlText, article.getId());
                    homeFeaturedArticlesMainPanel.add(featuredArticle);
                } catch (SQLException | IllegalArgumentException e) {
                    ErrorDisplayer.showError(e);
                }
                homeFeaturedArticlesMainPanel.repaint();
                homeFeaturedArticlesMainPanel.revalidate();
            }
        } catch (SQLException e) {
            ErrorDisplayer.showError(e);
        }
    }


    public void setHomeFeaturedArticles() {
        homeFeaturedArticlesMainPanel.removeAll();
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(this::initMostViewedArticles);
            thread.setDaemon(true);
            thread.start();
        }
    }

    public JPanel getPanel() {
        return homeFeaturedArticlesMainPanel;
    }
}