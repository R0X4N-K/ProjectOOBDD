package dao;

import model.Article;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ArticleDAO {
    ResultSet getResultSetArticleByTitle(String articleTitle) throws SQLException;
    ArrayList<Article> getAllArticles() throws RuntimeException;
    ArrayList<Article> getAllArticlesByAuthor(String nicknameAuthor) throws RuntimeException;

    void saveArticle(Article article);
    void updateArticle(Article article, Article newArticle);
    void updateRevisionArticle (Article article, boolean newArticleRevisionStatus);
    void deleteArticle(Article articleToDelete);

}
