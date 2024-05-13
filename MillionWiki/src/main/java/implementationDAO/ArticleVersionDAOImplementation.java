package implementationDAO;
import controller.Controller;
import dao.ArticleVersionDAO;
import database.DatabaseConnection;
import model.Article;
import model.ArticleVersion;
import model.Author;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;


public class ArticleVersionDAOImplementation implements ArticleVersionDAO {
    private final DatabaseConnection dbConnection;

    public ArticleVersionDAOImplementation() throws RuntimeException {
        try {
            dbConnection = DatabaseConnection.getInstance();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArticleVersion getLastArticleVersionByArticleId(int idArticle){
        String getArticleQuery = "SELECT * FROM article_versions WHERE parent_article = ? and status = ? ORDER BY revision_date DESC LIMIT 1";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(getArticleQuery)){
            stmt.setInt(1, idArticle);
            stmt.setString(2, "ACCEPTED");
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ArticleVersion(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("text"),
                            rs.getDate("version_date"),
                            rs.getDate("revision_date"),
                            Controller.getArticlesById(rs.getInt("parent_article")),
                            Controller.getAuthorById(rs.getInt("author_proposal")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public ArticleVersion getArticleVersionByIdArticleVersion(int idArticleVersion) {
        ArticleVersion articleVersion = null;
        String query = "SELECT * FROM article_versions WHERE id = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setInt(1, idArticleVersion);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    articleVersion = new ArticleVersion(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("text"),
                        rs.getDate("version_date"),
                        rs.getDate("revision_date"),
                        Controller.getArticlesById(rs.getInt("parent_article")),
                        Controller.getAuthorById(rs.getInt("author_proposal")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return articleVersion;
    }

    public ArrayList<ArticleVersion> getAllArticleVersionByAuthorId(int authorId){
        ArrayList<ArticleVersion> articleVersions = new ArrayList<>();
        String getArticleQuery = "SELECT * FROM article_versions WHERE author_proposal = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(getArticleQuery)){
            stmt.setInt(1, authorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ArticleVersion articleVersion = new ArticleVersion(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("text"),
                            rs.getDate("version_date"),
                            rs.getDate("revision_date"),
                            Controller.getArticlesById(rs.getInt("parent_article")),
                            Controller.getAuthorById(rs.getInt("author_proposal")));;
                    articleVersions.add(articleVersion);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return articleVersions;
    }
    public ArrayList<ArticleVersion> getAllArticleVersionExcludingTextByAuthorId(int authorId){
        ArrayList<ArticleVersion> articleVersions = new ArrayList<>();
        String getArticleQuery = "SELECT id, title, version_date, revision_date, parent_article FROM article_versions WHERE author_proposal = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(getArticleQuery)){
            stmt.setInt(1, authorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ArticleVersion articleVersion = new ArticleVersion(
                            rs.getInt("id"),
                            rs.getString("title"),
                            "",
                            rs.getDate("version_date"),
                            rs.getDate("revision_date"),
                            Controller.getArticlesById(rs.getInt("parent_article")),
                            Controller.getAuthorById(authorId));;
                    articleVersions.add(articleVersion);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return articleVersions;
    }

    public int getVersionArticlesNumberSent(int idAuthor) {
        int count = 0;
        String query = "SELECT COUNT(*) FROM article_versions av JOIN articles a ON av.parent_article = a.id WHERE av.author_proposal = ? AND a.author != ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setInt(1, idAuthor);
            stmt.setInt(2, idAuthor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return count;
    }

    @Override
    public ArrayList<ArticleVersion> getAllArticleVersions() {
        ArrayList<ArticleVersion> articleVersions = new ArrayList<>();
        String query = "SELECT * FROM article_versions";
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = dbConnection.connection.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ArticleVersion articleVersion = new ArticleVersion(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("text"),
                        rs.getDate("version_date"),
                        rs.getDate("revision_date"),
                        Controller.getArticlesById(rs.getInt("parent_article")),
                        Controller.getAuthorById(rs.getInt("author_proposal")));
                articleVersions.add(articleVersion);
            }
        } catch (SQLException | RuntimeException e) {
            System.err.println("Errore SQL: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Errore durante la chiusura delle risorse del database: " + e.getMessage());
                JOptionPane.showMessageDialog(null, "Errore durante la chiusura delle risorse del database: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }

        return articleVersions;
    }
    @Override
    public ArrayList<ArticleVersion> getAllArticleVersionByArticleId(int idArticle){
        ArrayList<ArticleVersion> articleVersions = new ArrayList<>();
        String getArticleQuery = "SELECT * FROM article_versions WHERE parent_article = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(getArticleQuery)){
            stmt.setInt(1, idArticle);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ArticleVersion articleVersion = new ArticleVersion(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("text"),
                            rs.getDate("version_date"),
                            rs.getDate("revision_date"),
                            Controller.getArticlesById(rs.getInt("parent_article")),
                            Controller.getAuthorById(rs.getInt("author_proposal")));
                    articleVersions.add(articleVersion);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return articleVersions;
    }
    public ArrayList<ArticleVersion> getAllArticleVersionExcludingTextByArticleId(int idArticle){
        ArrayList<ArticleVersion> articleVersions = new ArrayList<>();
        String getArticleQuery = "SELECT id, title, version_date, revision_date, parent_article, author_proposal FROM article_versions WHERE parent_article = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(getArticleQuery)){
            stmt.setInt(1, idArticle);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ArticleVersion articleVersion = new ArticleVersion(
                            rs.getInt("id"),
                            rs.getString("title"),
                            "",
                            rs.getDate("version_date"),
                            rs.getDate("revision_date"),
                            Controller.getArticlesById(idArticle),
                            Controller.getAuthorById(rs.getInt("author_proposal")));
                    articleVersions.add(articleVersion);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return articleVersions;
    }

    public int insertArticleVersion(int idArticle,
                                    String text,
                                    int idAuthor, String titleProposal){
        int id = -1;
        String query = "INSERT INTO article_versions (parent_article, text, author_proposal, title) VALUES (?, ?, ?, ?) RETURNING id";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setInt(1, idArticle);
            stmt.setString(2, text);
            stmt.setInt(3, idAuthor);
            stmt.setString(4, titleProposal);
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


    public int insertArticleVersion(ArticleVersion articleVersion) {
        int id = insertArticleVersion(articleVersion.getParentArticle().getId(),
                articleVersion.getText(),
                articleVersion.getAuthorProposal().getId(),
                articleVersion.getTitleProposal());
        return id;
    }


    public ArrayList<ArticleVersion> getAllArticleVersionsWaiting(int authorId) {
        ArrayList<ArticleVersion> versionsWaiting = new ArrayList<>();
        String getArticleQuery = "SELECT article_versions.id, article_versions.status, version_date, author_proposal, parent_article, article_versions.title FROM article_versions\n" +
                "INNER JOIN articles ON articles.id = article_versions.parent_article\n" +
                "WHERE articles.author = ? AND article_versions.status = \'WAITING\' ORDER BY  article_versions.version_date ASC";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(getArticleQuery)){
            stmt.setInt(1, authorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ArticleVersion articleVersion = new ArticleVersion(rs.getInt("id"),
                            rs.getDate("version_date"),
                            Controller.getArticlesById(rs.getInt("parent_article")),
                            Controller.getAuthorById(rs.getInt("author_proposal"))
                    );
                    versionsWaiting.add(articleVersion);
                }
                rs.close();
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return versionsWaiting;
    }

    public ArrayList<ArticleVersion> getAllArticleVersionsWaitingFull(int articleId) {
        ArrayList<ArticleVersion> versionsWaiting = new ArrayList<>();
        String getArticleQuery = "SELECT article_versions.* FROM article_versions\n" +
                "INNER JOIN articles ON articles.id = article_versions.parent_article\n" +
                "WHERE articles.id = ? AND article_versions.status = \'WAITING\' ORDER BY article_versions.version_date ASC";

        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(getArticleQuery)){
            stmt.setInt(1, articleId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ArticleVersion articleVersion = new ArticleVersion(rs.getInt("id"),
                            Controller.getArticlesById(rs.getInt("parent_article")),
                            rs.getString("text"),
                            rs.getString("title"),
                            Controller.getAuthorById(rs.getInt("author_proposal")),
                            rs.getDate("version_date"),
                            rs.getString("status")
                    );
                    versionsWaiting.add(articleVersion);
                }
                rs.close();
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return versionsWaiting;
    }


    public void reviewArticles(ArrayList<ArticleVersion> a) {
        try {
            dbConnection.connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String query = "BEGIN;\n";
        for (ArticleVersion version : a) {
            query = query.concat("UPDATE article_versions\n" +
                    "SET status = \'" + version.getStatus().toString() + "\'\n" +
                    "WHERE article_versions.id = " + version.getId() + ";\n");
        }
        query = query.concat("COMMIT;");
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            try {
                dbConnection.connection.rollback();
                dbConnection.connection.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);

            }
        }

        try {
            dbConnection.connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}