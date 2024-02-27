package implementationDAO;
import database.DatabaseConnection;
import model.Article;
import model.Author;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class ArticleDAOImplementation implements dao.ArticleDAO {
    public DatabaseConnection dbConnection;

    public ArticleDAOImplementation() throws RuntimeException {
        try {
            dbConnection = DatabaseConnection.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResultSet getResultSetArticleByTitle(String articleTitle) throws SQLException {
        String getArticleQuery = "SELECT * FROM articoles WHERE title = ?";
        ResultSet resultSet = null;
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(getArticleQuery)){
            stmt.setString(1, articleTitle);
            try (ResultSet rs = stmt.executeQuery()) {
                resultSet = stmt.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet; //Da ritornare un nuovo oggetto di tipo Article oppure il resultSet e
                     // poi nel controller creare l'oggetto Article

    }

    @Override
    public ArrayList<Article> getAllArticles() {
        ArrayList<Article> articles = new ArrayList<>();
        String query = "SELECT * FROM articles";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                try {
                    articles.add(new Article(rs));
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public ArrayList<Article> getAllArticlesByAuthor(int id) {
        ArrayList<Article> articles = new ArrayList<>();
        String query = "SELECT * FROM articles WHERE author = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                try {
                    articles.add(new Article(rs));
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public void saveArticle(Article article) {
        String query = "INSERT INTO articles (title, author, creation_date, revision, current_version_article) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, article.getTitle());
            stmt.setString(2, String.valueOf(article.getAuthor().getId()));
            stmt.setDate(3, new java.sql.Date(article.getCreationDate().getTime()));
            stmt.setBoolean(4, article.isRevision());
            stmt.setInt(5, article.getCurrentVersionArticle().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int saveArticle(String title, Date creationDate, boolean revision, Author author, int idCurrentVersionArticle) {
        int id=-1;
        String query = "INSERT INTO articles (title, author, creation_date, revision, current_version_article) VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, String.valueOf(author.getId()));
            stmt.setDate(3, new java.sql.Date(creationDate.getTime()));
            stmt.setBoolean(4, revision);
            stmt.setInt(5, idCurrentVersionArticle);
            stmt.executeUpdate();
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void updateArticle(Article article, Article newArticle) {
        String query = "UPDATE articles (title, author, creation_date, revision, current_version_article) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, article.getTitle());
            stmt.setString(2, String.valueOf(article.getAuthor().getId()));
            stmt.setDate(3, new java.sql.Date(article.getCreationDate().getTime()));
            stmt.setBoolean(4, article.isRevision());
            stmt.setInt(5, article.getCurrentVersionArticle().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateRevisionArticle(Article article, boolean newArticleRevisionStatus) {
        String query = "UPDATE articles SET revision = ? WHERE title = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setBoolean(1, newArticleRevisionStatus);
            stmt.setString(2, article.getTitle());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteArticle(Article articleToDelete) {
        String query = "DELETE FROM articles WHERE title = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, articleToDelete.getTitle());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
