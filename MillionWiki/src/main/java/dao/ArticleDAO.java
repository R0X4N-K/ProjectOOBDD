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

    public int saveArticle (String title, Date creationDate, boolean revision, Author author);
    public int saveArticle(String title, Date creationDate, boolean revision, int idAuthor);
    public void saveArticle(Article article);
    public void updateArticle(Article article, Article newArticle);
    public void updateRevisionArticle (Article article, boolean newArticleRevisionStatus);
    public void deleteArticle(Article articleToDelete);

}
