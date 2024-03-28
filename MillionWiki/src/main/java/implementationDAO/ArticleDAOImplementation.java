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
    public Article getArticleById(int idArticle){
        String getArticleQuery = "SELECT * FROM articles WHERE id = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(getArticleQuery)){
            stmt.setInt(1, idArticle);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Article(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Article getArticleByTitle(String articleTitle) throws SQLException {
        String getArticleQuery = "SELECT * FROM articles WHERE title = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(getArticleQuery)){
            stmt.setString(1, articleTitle);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Article(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public ArrayList<Article> getMatchesArticlesByTitle(String title) {
        ArrayList<Article> articles = new ArrayList<>();
        String query = "";

        if(title.length() >= 4)
            query = "SELECT * FROM articles WHERE title ILIKE ? OR title ILIKE ? LIMIT 10";
        else
            query = "SELECT * FROM articles WHERE title ILIKE ? LIMIT 10";


        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            if(title.length() >= 4) {
                stmt.setString(1,title + "%");
                stmt.setString(2, "%" + title + "%");
                // deep search -> stmt.setString(2, title.subSequence(0, ((title.length() / 2))) + "%");
            }
            else
                stmt.setString(1,title + "%");
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
    public ArrayList<Article> getAllArticlesByAuthor(int id) throws RuntimeException {
        return null;
    }

    public ArrayList<Article> getAllArticlesByIdAuthor(int id) {
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
        String query = "INSERT INTO articles (title, author, creation_date, revision) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, article.getTitle());
            stmt.setString(2, String.valueOf(article.getAuthor().getId()));
            stmt.setDate(3, new java.sql.Date(article.getCreationDate().getTime()));
            stmt.setBoolean(4, article.isRevision());
            stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int saveArticle(String title, Date creationDate, boolean revision, Author author) {
        int id=-1;
        String query = "INSERT INTO articles (title, author, creation_date, revision) VALUES (?, ?, ?, ?) RETURNING id";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, String.valueOf(author.getId()));
            stmt.setDate(3, new java.sql.Date(creationDate.getTime()));
            stmt.setBoolean(4, revision);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int saveArticle(String title, Date creationDate, boolean revision, int idAuthor) {
        int id=-1;
        String query = "INSERT INTO articles (title, author, creation_date, revision) VALUES (?, ?, ?, ?) RETURNING id";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setInt(2, idAuthor);
            stmt.setDate(3, new java.sql.Date(creationDate.getTime()));
            stmt.setBoolean(4, revision);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void updateArticle(int idArticle, String title){
        String query = "UPDATE articles SET title = ? WHERE id = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setInt(2, idArticle);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateArticle(Article article, Article newArticle) {
        String query = "UPDATE articles (title, author, creation_date, revision, current_version_article) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, article.getTitle());
            stmt.setString(2, String.valueOf(article.getAuthor().getId()));
            stmt.setDate(3, new java.sql.Date(article.getCreationDate().getTime()));
            stmt.setBoolean(4, article.isRevision());
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
