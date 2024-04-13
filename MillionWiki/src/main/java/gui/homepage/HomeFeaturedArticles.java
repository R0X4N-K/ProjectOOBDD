package gui.homepage;

import controller.Controller;
import model.Article;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HomeFeaturedArticles {
    private JPanel homeFeaturedArticlesMainPanel;
    private Thread thread;

    public HomeFeaturedArticles() {
        homeFeaturedArticlesMainPanel.setLayout(new GridLayout(2, 5, 10, 10));
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(this::initMostViewedArticles);
        }
        thread.setDaemon(true);
        thread.start();
    }

    private void initMostViewedArticles() {
        ArrayList<Article> mostViewedArticles = Controller.getMostViewedArticles(10);
        for (Article article : mostViewedArticles) {
            String htmlText = Controller.getLastArticleVersionByArticleId(article.getId()).getText();
            HomeArticlePanel featuredArticle = new HomeArticlePanel(article.getTitle(), htmlText, article.getId());
            homeFeaturedArticlesMainPanel.add(featuredArticle);
        }
        homeFeaturedArticlesMainPanel.repaint();
        homeFeaturedArticlesMainPanel.revalidate();
    }

    public JPanel getPanel() {
        return homeFeaturedArticlesMainPanel;
    }
}