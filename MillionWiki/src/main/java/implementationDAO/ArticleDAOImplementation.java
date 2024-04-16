package implementationDAO;
import controller.Controller;
import database.DatabaseConnection;
import model.Article;
import model.Author;
import model.Cookie;

import javax.swing.*;
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
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    public String getTitleArticleByArticleId(int articleId){
        String query = "SELECT title FROM articles WHERE id = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)){
            stmt.setInt(1, articleId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("title");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public ArrayList<Integer> getRecentArticles(int numberArticles) {
        ArrayList<Integer> articles = new ArrayList<>();
        String query = "SELECT id FROM articles ORDER BY creation_date DESC LIMIT ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setInt(1, numberArticles);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                try {
                    articles.add(rs.getInt("id"));
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return articles;
    }

    public ArrayList<Article> getMostViewedArticles(int numberArticles) {
        ArrayList<Article> articles = new ArrayList<>();
        String query = "SELECT * FROM articles ORDER BY views DESC LIMIT ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setInt(1, numberArticles);
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
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return articles;
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
                    JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return articles;
    }

    @Override
    public ArrayList<Article> getAllArticlesByAuthor(int id) throws RuntimeException {
        return null;
    }
    public int getAllArticlesNumberByIdAuthor(int idAuthor){
        String query = "SELECT COUNT(*) FROM articles WHERE author = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setInt(1, idAuthor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
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
                    JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return id;
    }

    public int saveArticle(String title, boolean revision, int idAuthor) {
        int id=-1;
        String query = "INSERT INTO articles (title, author, revision) VALUES (?, ?, ?) RETURNING id";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setInt(2, idAuthor);
            stmt.setBoolean(3, revision);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void incrementArticleViews(int idArticle) {
        Article article = getArticleById(idArticle);

        //Verifico se l'articolo aperto dall'utente è un articolo creato da se stesso
            int currentViews = article.getViews() + 1;
        String query = "UPDATE articles SET views = ? WHERE id = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setInt(1, currentViews);
            stmt.setInt(2, idArticle);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}
