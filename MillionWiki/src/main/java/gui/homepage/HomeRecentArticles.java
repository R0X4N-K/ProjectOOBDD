package gui.homepage;

import controller.Controller;
import org.jsoup.Jsoup;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HomeRecentArticles {
    private JPanel homeRecentArticlesMainPanel;

    public HomeRecentArticles() {
        homeRecentArticlesMainPanel.setLayout(new GridLayout(2, 5)); // Set layout to GridLayout with 2 rows and 6 columns

        /*for (int i = 1; i <= 12; i++) {
            HomeArticlePanel recentArticle = new HomeArticlePanel("Titolo " + i, "Titanic è un film del 1997 diretto, scritto, prodotto e co-montato da James Cameron.\n" +
                    "\n" +
                    "La pellicola è un colossal epico-romantico[2][3][4] di carattere storico, interpretato da Leonardo DiCaprio e Kate Winslet nei ruoli di Jack e Rose, due giovani provenienti da classi sociali opposte che si innamorano a bordo della sfortunata nave RMS Titanic, realmente naufragata il 15 aprile 1912, durante il suo viaggio inaugurale, a seguito dello schianto contro un iceberg. L'ispirazione di Cameron per il film è venuta dalla sua passione per i naufragi e dalla sua percezione del fatto che una storia d'amore intervallata da una perdita umana sarebbe stata essenziale per trasmettere l'impatto emotivo del disastro. La produzione è iniziata nel 1995, quando Cameron ha effettuato le riprese nel vero relitto del Titanic. Le scene moderne sulla nave da ricerca sono state girate a bordo dell'Akademik Mstislav Keldyš, usata come base durante le riprese del relitto sia nella finzione scenica che nella produzione reale del film. Per ricreare il naufragio sono stati utilizzati modelli in scala, immagini generate al computer e una ricostruzione del Titanic eretta presso i Baja Studios.\n" +
                    "\n" +
                    "Il film è uscito il 19 dicembre 1997. È stato elogiato per i suoi effetti speciali, le performance (in particolare quelle di DiCaprio, Winslet e Stuart), i valori di produzione, la regia, la colonna sonora, la fotografia, la storia e la profondità emotiva. Detiene il record di vittorie ai Premi Oscar (undici), insieme a Ben-Hur e Il Signore degli Anelli - Il ritorno del re, nonché quello di candidature (quattordici), insieme a Eva contro Eva e La La Land. Divenne il film con maggiori incassi nella storia del cinema, superando Jurassic Park di Steven Spielberg del 1993, finché non venne superato nel 2009 da Avatar, il film diretto da Cameron dopo Titanic stesso; al 2023, la pellicola occupa il quarto posto dopo Avatar, Avengers: Endgame e Avatar - La via dell'acqua (pellicola, quest'ultima, sempre di Cameron).\n" +
                    "\n" +
                    "Nel 2007 l'American Film Institute l'ha inserito all'83º posto nella classifica dei cento migliori film americani di tutti i tempi (nella classifica originaria del 1998 non era presente).[5] Si trova inoltre al sesto posto della classifica dei migliori film epici di tutti i tempi nella AFI's 10 Top 10 dell'American Film Institute.\n" +
                    "\n" +
                    "Nel 2017 è stato scelto per la conservazione nel National Film Registry della Biblioteca del Congresso degli Stati Uniti in quanto \"culturalmente, storicamente o esteticamente significativo\".[6] " + i);
            homeRecentArticlesMainPanel.add(recentArticle);
        }*/
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