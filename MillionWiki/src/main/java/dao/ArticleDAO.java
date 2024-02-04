package dao;

import model.Article;

import java.util.ArrayList;

public interface ArticleDAO {
    Article getArticle(String articleTitle);
    ArrayList<Article> getAllArticles();
    ArrayList<Article> getAllArticlesByAuthor(String nicknameAuthor);

    void saveArticle(Article article);
    void updateArticle(Article article, Article newArticle);
    void updateRevisionArticle (Article article, boolean newArticleRevisionStatus);
    void deleteArticle(Article articleToDelete);

}
