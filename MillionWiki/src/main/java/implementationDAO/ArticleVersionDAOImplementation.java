package implementationDAO;
import dao.ArticleVersionDAO;
import database.DatabaseConnection;
import model.Article;
import model.ArticleVersion;
import model.Author;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


public class ArticleVersionDAOImplementation implements ArticleVersionDAO {
    private DatabaseConnection dbConnection;

    public ArticleVersionDAOImplementation() throws RuntimeException {
        try {
            dbConnection = DatabaseConnection.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                ArticleVersion articleVersion = new ArticleVersion(rs);
                articleVersions.add(articleVersion);
            }
        } catch (SQLException | RuntimeException e) {
            System.err.println("Errore SQL: " + e.getMessage());
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
            }
        }

        return articleVersions;
    }
    public void insertArticleVersion(ArticleVersion articleVersion) {
        String query = "INSERT INTO article_versions (parentArticle, status, text, versionDate, revisionDate, authorProposal) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, articleVersion.getParentArticle().getTitle());
            stmt.setString(2, articleVersion.getStatus().toString());
            stmt.setString(3, articleVersion.getText());
            stmt.setDate(4, new java.sql.Date(articleVersion.getVersionDate().getTime()));
            if(articleVersion.getRevisionDate() != null) {
                stmt.setDate(5, new java.sql.Date(articleVersion.getRevisionDate().getTime()));
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }
            stmt.setString(6, articleVersion.getAuthorProposal().getNickname());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertArticleVersion(Article parentArticle, ArticleVersion.Status status, String text, Date versionDate, Date revisionDate, Author authorProposal) {
        String query = "INSERT INTO article_versions (parentArticle, status, text, versionDate, revisionDate, authorProposal) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, parentArticle.getTitle());
            stmt.setString(2, status.toString());
            stmt.setString(3, text);
            stmt.setDate(4, new java.sql.Date(versionDate.getTime()));
            if(revisionDate != null) {
                stmt.setDate(5, new java.sql.Date(revisionDate.getTime()));
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }
            stmt.setString(6, String.valueOf(authorProposal.getId()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}