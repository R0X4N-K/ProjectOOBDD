package gui.homepage;

import controller.Controller;
import org.jsoup.Jsoup;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HomeRecentArticles {
    private JPanel homeRecentArticlesMainPanel;

    public HomeRecentArticles() {
        homeRecentArticlesMainPanel.setLayout(new GridLayout(2, 5));

        ArrayList<Integer> recentArticles = new ArrayList<>();
        int attempts = 0;
        while (recentArticles.size() < 10 && attempts < 5) {
            ArrayList<Integer> fetchedArticles = Controller.getRecentArticles(20);
            for (int i = 0; i < fetchedArticles.size() && recentArticles.size() < 10; i++){
                String htmlText = Controller.getLastArticleVersionByArticleId(fetchedArticles.get(i)).getText();
                String plainText = Jsoup.parse(htmlText).text().trim(); // rimuove gli spazi bianchi all'inizio e alla fine
                if (plainText != null && !plainText.isEmpty()) {
                    recentArticles.add(fetchedArticles.get(i));
                }
            }
            attempts++;
        }

        for (Integer articleId : recentArticles) {
            String htmlText = Controller.getLastArticleVersionByArticleId(articleId).getText();
            String plainText = Jsoup.parse(htmlText).text().trim();
            HomeArticlePanel recentArticle = new HomeArticlePanel(Controller.getArticlesById(articleId).getTitle(), plainText);
            homeRecentArticlesMainPanel.add(recentArticle);
        }

    }

    public JPanel getPanel() {
        return homeRecentArticlesMainPanel;
    }
}