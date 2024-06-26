package gui.homepage;

import controller.Controller;
import gui.ErrorDisplayer;
import model.Article;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class HomeFeaturedArticles {
    private JPanel homeFeaturedArticlesMainPanel;
    private Thread thread;

    public HomeFeaturedArticles() {
        homeFeaturedArticlesMainPanel.setLayout(new GridLayout(2, 5, 10, 10));

        Font titleFont = new Font("Times New Roman", Font.PLAIN, 30);
        TitledBorder titledBorder = null;

        try {

            URL iconUrl = getClass().getResource("/icons/trending.png");
            ImageIcon icon = new ImageIcon(iconUrl);

            String titledBorderTxt = "<html><img src=\"" + iconUrl + "\" style=\"vertical-align: bottom;\">" +
                    "<span style=\"font-family: 'Times New Roman'; font-size: 22px;vertical-align: bottom;\">&nbsp;Articoli popolari</span></html>";

            titledBorder = new TitledBorder(titledBorderTxt);

        } catch (Exception e) {
            titledBorder = new TitledBorder("Articoli popolari");
            titledBorder.setTitleFont(titleFont);
        }

        titledBorder.setTitleJustification(TitledBorder.LEFT);
        titledBorder.setTitlePosition(TitledBorder.TOP);

        EmptyBorder paddingBorder = new EmptyBorder(10, 10, 10, 10);

        CompoundBorder compoundBorder = new CompoundBorder(titledBorder, paddingBorder);

        homeFeaturedArticlesMainPanel.setBorder(compoundBorder);

        if (thread == null || !thread.isAlive()) {
            thread = new Thread(this::initMostViewedArticles);
        }
        thread.setDaemon(true);
        thread.start();
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    private void initMostViewedArticles() {

        try {
            ArrayList<Article> mostViewedArticles = Controller.getMostViewedArticles(10);
            for (Article article : mostViewedArticles) {
                try {
                    Controller.getSplashScreen().incrementProgressBar();
                    if (Controller.getWindow() != null)
                        Controller.getWindow().getHomepage().incrementProgressBarHome();
                    String htmlText = Controller.getLastArticleVersionByArticleId(article.getId()).getText();
                    HomeArticlePanel featuredArticle = new HomeArticlePanel(article.getTitle(), htmlText, article.getId());
                    featuredArticle.setBackground(Color.decode("#FF641A"));
                    homeFeaturedArticlesMainPanel.add(featuredArticle);
                } catch (SQLException | IllegalArgumentException e) {
                    ErrorDisplayer.showErrorWithActions(e, null, null, Controller.getWindow());
                }
                homeFeaturedArticlesMainPanel.repaint();
                homeFeaturedArticlesMainPanel.revalidate();
            }
        } catch (SQLException e) {
            ErrorDisplayer.showErrorWithActions(e, null, null, Controller.getWindow());
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