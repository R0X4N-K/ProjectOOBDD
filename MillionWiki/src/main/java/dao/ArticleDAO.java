package dao;

import model.Article;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ArticleDAO {
    Article getArticleById(int idArticle) throws SQLException, IllegalArgumentException;

    Article getArticleByTitle(String articleTitle) throws SQLException, IllegalArgumentException;

    int getAllArticlesNumberByIdAuthor(int idAuthor) throws SQLException;

    ArrayList<Article> getAllArticlesByIdAuthor(int id) throws SQLException, IllegalArgumentException;

    ArrayList<Integer> getRecentArticles(int numberArticles) throws SQLException;

    ArrayList<Article> getMatchesArticlesByTitle(String title) throws SQLException, IllegalArgumentException;

    int saveArticle(String title, int idAuthor) throws SQLException;

    void incrementArticleViews(int idArticle) throws SQLException;

    ArrayList<Article> getMostViewedArticles(int numberArticles) throws SQLException, IllegalArgumentException;

    Article pickRandomArticle() throws SQLException, IllegalArgumentException;
}
