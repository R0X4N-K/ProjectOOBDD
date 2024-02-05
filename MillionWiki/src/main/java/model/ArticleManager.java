package model;

import java.util.ArrayList;

public final class  ArticleManager {
    private static ArrayList<Article> articles = new ArrayList<>(); //Aggiunto per memorizzare gli articoli
    private ArticleManager(){

    }
    public static Article provideArticle(String articleName) {
        for (Article article : articles) {
            if (article.getTitle().equals(articleName)) {
                return article;
            }
        }
        return null;
    }


    public static ArticleVersion fornisciVersioneArticolo(String nomeArticolo) {
        Article article = provideArticle(nomeArticolo);
        if (article != null) {
            ArrayList<ArticleVersion> modifiche = article.getProposedChanges();
            if (!modifiche.isEmpty()) {
                return modifiche.get(modifiche.size() - 1); // Restituisce l'ultima modifica
            }
        }
        return null;
    }

    public static ArrayList<ArticleVersion> notificationAuthor(Author author) {
        ArrayList<ArticleVersion> modificheDaRivedere = new ArrayList<>();
        for (Article article : author.getCreatedPages()) {
            for (ArticleVersion edit : article.getProposedChanges()) {
                if (edit.getStatus() == ArticleVersion.Status.WAITING) {
                    modificheDaRivedere.add(edit);
                }
            }
        }
        return modificheDaRivedere;
    }

    public static void saveArticle(Article articleToMemorize){
        articles.add(articleToMemorize);
    }

    /*
    CONTROLLO PLAGIO  - DA IMPLEMENTARE
    public static void controllaValiditaArticolo(Article articoloDaValidare){

    }

    public static void controllaValiditaArticolo(ArticleVersion versioneArticoloDaValidare){

    }
    */
}
