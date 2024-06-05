package implementationDAO;
import controller.Controller;
import dao.ArticleDAO;
import database.DatabaseConnection;
import model.Article;

import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArticleDAOImplementation implements ArticleDAO {
    public DatabaseConnection dbConnection;

    public ArticleDAOImplementation() throws SQLException, FileNotFoundException, ClassNotFoundException {
        dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public Article getArticleById(int idArticle) throws SQLException, IllegalArgumentException {
        String getArticleQuery = "SELECT * FROM articles WHERE id = ?";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(getArticleQuery);
        stmt.setInt(1, idArticle);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Article(rs.getInt("id"),
                    rs.getString("title"),
                    Controller.getAuthorById(rs.getInt("author")),
                    rs.getDate("creation_date"));
        }
        return null;
    }

    @Override
    public Article getArticleByTitle(String articleTitle) throws SQLException, IllegalArgumentException {
        String getArticleQuery = "SELECT * FROM articles WHERE title = ?";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(getArticleQuery);
        stmt.setString(1, articleTitle);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Article(rs.getInt("id"),
                    rs.getString("title"),
                    Controller.getAuthorById(rs.getInt("author")),
                    rs.getDate("creation_date"));
        }
        return null;
    }

    public int getAllArticlesNumberByIdAuthor(int idAuthor) throws SQLException{
        String query = "SELECT COUNT(*) FROM articles WHERE author = ?";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
        stmt.setInt(1, idAuthor);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public ArrayList<Integer> getRecentArticles(int numberArticles) throws SQLException {
        ArrayList<Integer> articles = new ArrayList<>();
        String query = "SELECT id FROM articles ORDER BY creation_date DESC LIMIT ?";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
        stmt.setInt(1, numberArticles);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            articles.add(rs.getInt("id"));
        }
        return articles;
    }

    public ArrayList<Article> getMostViewedArticles(int numberArticles) throws SQLException, IllegalArgumentException {
        ArrayList<Article> articles = new ArrayList<>();
        String query = "SELECT * FROM articles ORDER BY views DESC LIMIT ?";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
        stmt.setInt(1, numberArticles);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            articles.add(new Article(rs.getInt("id"),
                    rs.getString("title"),
                    Controller.getAuthorById(rs.getInt("author")),
                    rs.getDate("creation_date")));
        }
        return articles;
    }


    @Override
    public ArrayList<Article> getMatchesArticlesByTitle(String title) throws SQLException, IllegalArgumentException {
        ArrayList<Article> articles = new ArrayList<>();
        String query = "";

        if(title.length() >= 4)
            query = "SELECT * FROM articles WHERE title ILIKE ? OR title ILIKE ? ORDER BY views DESC LIMIT 10";
        else
            query = "SELECT * FROM articles WHERE title ILIKE ? ORDER BY views DESC LIMIT 10";


        PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
        if(title.length() >= 4) {
            stmt.setString(1,title + "%");
            stmt.setString(2, "%" + title + "%");
            // deep search -> stmt.setString(2, title.subSequence(0, ((title.length() / 2))) + "%");
        }
        else
            stmt.setString(1,title + "%");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            articles.add(new Article(rs.getInt("id"),
                rs.getString("title"),
                Controller.getAuthorById(rs.getInt("author")),
                rs.getDate("creation_date")));
        }
        return articles;
    }

    public ArrayList<Article> getAllArticlesByIdAuthor(int id) throws SQLException, IllegalArgumentException {
        ArrayList<Article> articles = new ArrayList<>();
        String query = "SELECT * FROM articles WHERE author = ?";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            articles.add(new Article(rs.getInt("id"),
                rs.getString("title"),
                Controller.getAuthorById(rs.getInt("author")),
                rs.getDate("creation_date")));
        }

        return articles;
    }

    public int saveArticle(String title, int idAuthor) throws SQLException {
        int id = -1;
        String query = "INSERT INTO articles (title, author) VALUES (?, ?) RETURNING id";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
        stmt.setString(1, title);
        stmt.setInt(2, idAuthor);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            id = rs.getInt("id");
        }
        return id;
    }

    public void incrementArticleViews(int idArticle) throws SQLException{
        String query = "UPDATE articles SET views = views + 1 WHERE id = ?";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
        stmt.setInt(1, idArticle);
        stmt.executeUpdate();
    }

    public Article pickRandomArticle() throws SQLException, IllegalArgumentException {
        String query = "SELECT id FROM articles ORDER BY RANDOM() LIMIT 1";
        int randomId = -1;
        PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
        stmt.executeQuery();
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            randomId = rs.getInt("id");
        }
        return Controller.getArticlesById(randomId);
    }
}
