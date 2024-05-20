package gui.homepage;

import controller.Controller;
import gui.ErrorDisplayer;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class HomeRecentArticles {
    private JPanel homeRecentArticlesMainPanel;
    private Thread thread;

    public HomeRecentArticles() {
        homeRecentArticlesMainPanel.setLayout(new GridLayout(2, 5, 10, 10));
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(() -> {
                initRecentArticles();
                try {
                    Controller.getSplashScreen().dispose();
                }catch (NullPointerException e){
                    System.out.println("Errore creazione splash screen");
                }
                Controller.getWindow().setSize(1200, 700);
                Controller.getWindow().setMinimumSize(new Dimension(1200, 700));
                Controller.getWindow().setLocationRelativeTo(null);
            });
        }
        thread.setDaemon(true);
        thread.start();
    }

    private void initRecentArticles() {
        try {
            ArrayList<Integer> recentArticles = Controller.getRecentArticles(10);
            for (Integer articleId : recentArticles) {
                try {
                    Controller.getSplashScreen().incrementProgressBar();
                    String htmlText = Controller.getLastArticleVersionByArticleId(articleId).getText();
                    HomeArticlePanel recentArticle = new HomeArticlePanel(Controller.getArticlesById(articleId).getTitle(), htmlText, articleId);
                    homeRecentArticlesMainPanel.add(recentArticle);
                } catch (SQLException | IllegalArgumentException e) {
                    ErrorDisplayer.showError(e);
                }
                homeRecentArticlesMainPanel.repaint();
                homeRecentArticlesMainPanel.revalidate();
            }

            Controller.getWindow().getHomepage().getReloadHome().setIcon(new ImageIcon(Home.class.getResource("/icons/reload.png")));
        } catch (SQLException e) {
            ErrorDisplayer.showError(e);
        }
    }

    public JPanel getPanel() {
        return homeRecentArticlesMainPanel;
    }
    public void setHomeRecentArticles() {
        homeRecentArticlesMainPanel.removeAll();
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(this::initRecentArticles);
            thread.setDaemon(true);
            thread.start();
        }
    }
}