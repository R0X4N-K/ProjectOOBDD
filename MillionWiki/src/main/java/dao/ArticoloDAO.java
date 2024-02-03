package dao;

import model.Article;

import java.util.ArrayList;

public interface ArticoloDAO {
    Article getArticolo(String titolo);
    ArrayList<Article> getAllArticoli();
    ArrayList<Article> getAllArticoliByAutore(String nicknameAutore);

    void saveArticolo(Article article);
    void updateArticolo(Article article, Article nuovoArticle);
    void updateRevisioneArticolo(Article article, boolean nuovoStatoRevisione);
    void deleteArticolo(Article article);

}
