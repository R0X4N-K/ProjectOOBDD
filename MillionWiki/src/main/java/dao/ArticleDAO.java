package dao;

import model.Article;
import model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface ArticleDAO {
    Article getArticleById(int idArticle);
    Article getArticleByTitle(String articleTitle) throws SQLException;
    String getTitleArticleByArticleId(int articleId);
    ArrayList<Article> getAllArticles() throws RuntimeException;
    ArrayList<Article> getAllArticlesByAuthor(int id) throws RuntimeException;
    int getAllArticlesNumberByIdAuthor(int idAuthor);
    ArrayList<Article> getAllArticlesByIdAuthor(int id);
    ArrayList<Integer> getRecentArticles(int numberArticles);
    ArrayList<Article> getMatchesArticlesByTitle(String title);
    int saveArticle(String title, Date creationDate, Author author);
    int saveArticle(String title, int idAuthor);
    void saveArticle(Article article);
    void deleteArticle(Article articleToDelete);
    void incrementArticleViews(int idArticle);
    ArrayList<Article> getMostViewedArticles(int numberArticles);
}
