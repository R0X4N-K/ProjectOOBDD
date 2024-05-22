package gui.homepage;

import controller.Controller;
import gui.ErrorDisplayer;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class HomeRecentArticles {
    private JPanel homeRecentArticlesMainPanel;
    private Thread thread;

    public HomeRecentArticles() {
        homeRecentArticlesMainPanel.setLayout(new GridLayout(2, 5, 10, 10));

        // Crea un nuovo font per il titolo del bordo
        Font titleFont = new Font("Times New Roman", Font.PLAIN, 30);

        // Crea un bordo con il titolo personalizzato e applica il font
        TitledBorder titledBorder = new TitledBorder("Articoli recenti");
        titledBorder.setTitleFont(titleFont);
        titledBorder.setTitleJustification(TitledBorder.LEFT);
        titledBorder.setTitlePosition(TitledBorder.TOP);

        // Crea un EmptyBorder per il padding interno
        EmptyBorder paddingBorder = new EmptyBorder(10, 10, 10, 10);  // Imposta il padding desiderato

        // Combina i due bordi
        CompoundBorder compoundBorder = new CompoundBorder(titledBorder, paddingBorder);

        // Applica il bordo composto al pannello
        homeRecentArticlesMainPanel.setBorder(compoundBorder);

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