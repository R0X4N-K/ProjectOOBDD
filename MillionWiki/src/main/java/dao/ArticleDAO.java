package dao;

import model.Article;
import model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public interface ArticleDAO {
    Article getArticleByTitle(String articleTitle) throws SQLException;

    ArrayList<Article> getAllArticles() throws RuntimeException;
    ArrayList<Article> getAllArticlesByAuthor(int id) throws RuntimeException;

    int saveArticle (String title, Date creationDate, boolean revision, Author author, int idCurrentVersionArticle);
    void saveArticle(Article article);
    void updateArticle(Article article, Article newArticle);
    void updateRevisionArticle (Article article, boolean newArticleRevisionStatus);
    void deleteArticle(Article articleToDelete);

}
