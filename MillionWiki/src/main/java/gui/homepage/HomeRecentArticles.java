package gui.homepage;

import controller.Controller;
import gui.ErrorDisplayer;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class HomeRecentArticles {
    private JPanel homeRecentArticlesMainPanel;
    private Thread thread;

    public HomeRecentArticles() {
        homeRecentArticlesMainPanel.setLayout(new GridLayout(2, 5, 10, 10));
        Font titleFont = new Font("Times New Roman", Font.PLAIN, 30);
        TitledBorder titledBorder = null;

        try {

            URL iconUrl = getClass().getResource("/icons/new.png");
            ImageIcon icon = new ImageIcon(iconUrl);

            String titledBorderTxt = "<html><img src=\"" + iconUrl + "\" style=\"vertical-align: bottom;\">" +
                    "<span style=\"font-family: 'Times New Roman'; font-size: 22px;vertical-align: bottom;\">&nbsp;Articoli recenti</span></html>";

            titledBorder = new TitledBorder(titledBorderTxt.toString());

        }catch (Exception e){
            titledBorder = new TitledBorder("Articoli popolari");
            titledBorder.setTitleFont(titleFont);
        }

        titledBorder.setTitleJustification(TitledBorder.LEFT);
        titledBorder.setTitlePosition(TitledBorder.TOP);
        titledBorder.setTitleJustification(TitledBorder.LEFT);
        titledBorder.setTitlePosition(TitledBorder.TOP);

        EmptyBorder paddingBorder = new EmptyBorder(10, 10, 10, 10);

        CompoundBorder compoundBorder = new CompoundBorder(titledBorder, paddingBorder);

        homeRecentArticlesMainPanel.setBorder(compoundBorder);

        if (thread == null || !thread.isAlive()) {
            thread = new Thread(() -> {
                initRecentArticles();
                try {
                    Controller.getSplashScreen().dispose();
                }catch (NullPointerException e){
                    System.out.println("Errore creazione splash screen");
                }

                if(Controller.getWindow() != null) {
                    Controller.getWindow().setSize(1200, 700);
                    Controller.getWindow().setMinimumSize(new Dimension(1200, 700));
                    Controller.getWindow().setLocationRelativeTo(null);
                }
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
                    if(Controller.getWindow() != null)
                        Controller.getWindow().getHomepage().incrementProgressBarHome();
                    String htmlText = Controller.getLastArticleVersionByArticleId(articleId).getText();
                    HomeArticlePanel recentArticle = new HomeArticlePanel(Controller.getArticlesById(articleId).getTitle(), htmlText, articleId);
                    homeRecentArticlesMainPanel.add(recentArticle);
                } catch (SQLException | IllegalArgumentException e) {
                    ErrorDisplayer.showError(e);
                }
                homeRecentArticlesMainPanel.repaint();
                homeRecentArticlesMainPanel.revalidate();
            }
            if(Controller.getWindow() != null) {
                Controller.getWindow().getHomepage().getReloadHome().setIcon(new ImageIcon(Home.class.getResource("/icons/reload.png")));
                Controller.getWindow().getHomepage().getProgressBarHome().setVisible(false);
                Controller.getWindow().getHomepage().getProgressBarHome().setValue(0);
            }

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