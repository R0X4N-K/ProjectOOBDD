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
    int saveArticle(String title, Date creationDate, boolean revision, Author author);
    int saveArticle(String title, Date creationDate, boolean revision, int idAuthor);
    void saveArticle(Article article);
    void updateArticle(int idArticle, String title); // TODO: RIMUOVERE FUNZIONE PERCHÉ NON PIÙ UTILE (SOSTITUITA DA IMPLEMENTAZIONE PIÙ SICURA SUL DATABASE)
    void updateArticle(Article article, Article newArticle);
    void updateRevisionArticle(Article article, boolean newArticleRevisionStatus);
    void deleteArticle(Article articleToDelete);
    void incrementArticleViews(int idArticle);
    ArrayList<Article> getMostViewedArticles(int numberArticles);
}
