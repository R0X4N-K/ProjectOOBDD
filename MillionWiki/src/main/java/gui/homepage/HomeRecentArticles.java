package gui.homepage;

import controller.Controller;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HomeRecentArticles {
    private JPanel homeRecentArticlesMainPanel;
    private Thread thread;

    public HomeRecentArticles() {
        homeRecentArticlesMainPanel.setLayout(new GridLayout(2, 5));
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(() -> {
                initRecentArticles();
            });
        }
        thread.setDaemon(true);
        thread.start();
    }

    private void initRecentArticles() {
        ArrayList<Integer> recentArticles = Controller.getRecentArticles(10);
        for (Integer articleId : recentArticles) {
            String htmlText = Controller.getLastArticleVersionByArticleId(articleId).getText();
            HomeArticlePanel recentArticle = new HomeArticlePanel(Controller.getArticlesById(articleId).getTitle(), htmlText, articleId);
            homeRecentArticlesMainPanel.add(recentArticle);
        }
        homeRecentArticlesMainPanel.repaint();
        homeRecentArticlesMainPanel.revalidate();
    }

    public JPanel getPanel() {
        return homeRecentArticlesMainPanel;
    }
}