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
    private final DatabaseConnection dbConnection;

    public ArticleVersionDAOImplementation() throws RuntimeException {
        try {
            dbConnection = DatabaseConnection.getInstance();
        } catch (SQLException e) {
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
                    return new ArticleVersion(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
    @Override
    public ArrayList<ArticleVersion> getAllArticleVersionByArticleId(int idArticle){
        ArrayList<ArticleVersion> articleVersions = new ArrayList<>();
        String getArticleQuery = "SELECT * FROM article_versions WHERE parent_article = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(getArticleQuery)){
            stmt.setInt(1, idArticle);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ArticleVersion articleVersion = new ArticleVersion(rs);
                    articleVersions.add(articleVersion);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articleVersions;
    }

    public int insertArticleVersion(int idArticle, ArticleVersion.Status status,
                                    String text, Date versionDate, Date revisionDate,
                                    int idAuthor, String titleProposal){
        int id = -1;
        String query = "INSERT INTO article_versions (parent_article, status, text, version_date, revision_date, author_proposal, title) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setInt(1, idArticle);
            stmt.setString(2, status.toString());
            stmt.setString(3, text);
            stmt.setDate(4, new java.sql.Date(versionDate.getTime()));
            if (revisionDate != null) {
                stmt.setDate(5, new java.sql.Date(revisionDate.getTime()));
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }
            stmt.setInt(6, idAuthor);
            stmt.setString(7, titleProposal);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }


    public int insertArticleVersion(ArticleVersion articleVersion) {
        int id = -1;
        String query = "INSERT INTO article_versions (parent_article, status, text, version_date, revision_date, author_proposal) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, articleVersion.getParentArticle().getTitle());
            stmt.setString(2, articleVersion.getStatus().toString());
            stmt.setString(3, articleVersion.getText());
            stmt.setDate(4, new java.sql.Date(articleVersion.getVersionDate().getTime()));
            if (articleVersion.getRevisionDate() != null) {
                stmt.setDate(5, new java.sql.Date(articleVersion.getRevisionDate().getTime()));
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }
            stmt.setString(6, articleVersion.getAuthorProposal().getNickname());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int insertArticleVersion(String title, ArticleVersion.Status status, String text, Date versionDate, Date revisionDate, Author authorProposal, String titleProposal) {
        int id = -1;
        String query = "INSERT INTO article_versions (parent_article, status, text, version_date, revision_date, author_proposal) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, status.toString());
            stmt.setString(3, text);
            stmt.setDate(4, new java.sql.Date(versionDate.getTime()));
            if (revisionDate != null) {
                stmt.setDate(5, new java.sql.Date(revisionDate.getTime()));
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }
            stmt.setString(6, String.valueOf(authorProposal.getId()));
            stmt.setString(7, titleProposal);
            ResultSet rs = stmt.executeQuery();


            if (rs.next()) {
                id = rs.getInt("id");
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void saveArticleVersion(int idArticle, ArticleVersion.Status status, String text, Date versionDate, Date revisionDate, int authorProposal, String titleProposal) {
        String query = "INSERT INTO article_versions (parent_article, status, text, version_date, revision_date, author_proposal, title) VALUES (?, ?, ?, ?, ?, ?, ?) ";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setInt(1, idArticle);
            stmt.setString(2, status.toString());
            stmt.setString(3, text);
            stmt.setDate(4, new java.sql.Date(versionDate.getTime()));
            stmt.setInt(6, authorProposal);

            if (revisionDate != null) {
                stmt.setDate(5, new java.sql.Date(revisionDate.getTime()));
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }
            stmt.setInt(6, authorProposal);
            stmt.setString(7, titleProposal);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveArticleVersion(int idArticle, ArticleVersion.Status status, String text, Date versionDate, Date revisionDate, Author authorProposal) {
        String query = "INSERT INTO article_versions (parent_article, status, text, version_date, revision_date, author_proposal) VALUES (?, ?, ?, ?, ?, ?) ";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setInt(1, idArticle);
            stmt.setString(2, status.toString());
            stmt.setString(3, text);
            stmt.setDate(4, new java.sql.Date(versionDate.getTime()));
            if (revisionDate != null) {
                stmt.setDate(5, new java.sql.Date(revisionDate.getTime()));
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }
            stmt.setString(6, String.valueOf(authorProposal.getId()));
            stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}