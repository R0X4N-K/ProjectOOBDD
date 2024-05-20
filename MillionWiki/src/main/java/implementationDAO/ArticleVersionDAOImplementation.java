package implementationDAO;
import controller.Controller;
import dao.ArticleVersionDAO;
import database.DatabaseConnection;
import model.ArticleVersion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArticleVersionDAOImplementation implements ArticleVersionDAO {
    private final DatabaseConnection dbConnection;

    public ArticleVersionDAOImplementation() throws SQLException {
        dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public ArticleVersion getLastArticleVersionByArticleId(int idArticle) throws SQLException, IllegalArgumentException {
        String getArticleQuery = "SELECT * FROM article_versions WHERE parent_article = ? and status = ? ORDER BY revision_date DESC LIMIT 1";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(getArticleQuery);
        stmt.setInt(1, idArticle);
        stmt.setString(2, "ACCEPTED");
        ResultSet rs = stmt.executeQuery();
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
        return null;
    }

    @Override
    public ArticleVersion getArticleVersionByIdArticleVersion(int idArticleVersion) throws SQLException, IllegalArgumentException {
        ArticleVersion articleVersion = null;
        String query = "SELECT * FROM article_versions WHERE id = ?";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
        stmt.setInt(1, idArticleVersion);
        ResultSet rs = stmt.executeQuery();
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
        return articleVersion;
    }

    public ArrayList<ArticleVersion> getAllArticleVersionsExcludingTextByAuthorId(int authorId) throws SQLException, IllegalArgumentException {
        ArrayList<ArticleVersion> articleVersions = new ArrayList<>();
        String getArticleQuery = "SELECT id, title, version_date, revision_date, parent_article FROM article_versions WHERE author_proposal = ?";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(getArticleQuery);
        stmt.setInt(1, authorId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            ArticleVersion articleVersion = new ArticleVersion(
                    rs.getInt("id"),
                    rs.getString("title"),
                    "",
                    rs.getDate("version_date"),
                    rs.getDate("revision_date"),
                    Controller.getArticlesById(rs.getInt("parent_article")),
                    Controller.getAuthorById(authorId));
            articleVersions.add(articleVersion);
        }
        return articleVersions;
    }

    public int getVersionArticlesNumberSent(int idAuthor) throws SQLException {
        int count = 0;
        String query = "SELECT COUNT(*) FROM article_versions av JOIN articles a ON av.parent_article = a.id WHERE av.author_proposal = ? AND a.author != ?";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
        stmt.setInt(1, idAuthor);
        stmt.setInt(2, idAuthor);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }


    public ArrayList<ArticleVersion> getAllArticleVersionsExcludingTextByArticleId(int idArticle) throws SQLException, IllegalArgumentException {
        ArrayList<ArticleVersion> articleVersions = new ArrayList<>();
        String getArticleQuery = "SELECT id, title, version_date, revision_date, parent_article, author_proposal FROM article_versions WHERE parent_article = ?";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(getArticleQuery);
        stmt.setInt(1, idArticle);
        ResultSet rs = stmt.executeQuery();
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
        return articleVersions;
    }

    public int insertArticleVersion(int idArticle,
                                    String text,
                                    int idAuthor, String titleProposal) throws SQLException {
        int id = -1;
        String query = "INSERT INTO article_versions (parent_article, text, author_proposal, title) VALUES (?, ?, ?, ?) RETURNING id";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
        stmt.setInt(1, idArticle);
        stmt.setString(2, text);
        stmt.setInt(3, idAuthor);
        stmt.setString(4, titleProposal);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            id = rs.getInt("id");
        }
        return id;
    }


    public ArrayList<ArticleVersion> getAllArticleVersionsWaiting(int authorId) throws SQLException, IllegalArgumentException {
        ArrayList<ArticleVersion> versionsWaiting = new ArrayList<>();
        String getArticleQuery = """
                SELECT article_versions.id, article_versions.status, version_date, author_proposal, parent_article, article_versions.title FROM article_versions
                INNER JOIN articles ON articles.id = article_versions.parent_article
                WHERE articles.author = ? AND article_versions.status = 'WAITING' ORDER BY article_versions.version_date""";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(getArticleQuery);
        stmt.setInt(1, authorId);
        ResultSet rs = stmt.executeQuery();
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
        return versionsWaiting;
    }

    public ArrayList<ArticleVersion> getAllArticleVersionsWaitingFull(int articleId) throws SQLException, IllegalArgumentException {
        ArrayList<ArticleVersion> versionsWaiting = new ArrayList<>();
        String getArticleQuery = """
                SELECT article_versions.* FROM article_versions
                INNER JOIN articles ON articles.id = article_versions.parent_article
                WHERE articles.id = ? AND article_versions.status = 'WAITING' ORDER BY article_versions.version_date""";

        PreparedStatement stmt = dbConnection.connection.prepareStatement(getArticleQuery);
        stmt.setInt(1, articleId);
        ResultSet rs = stmt.executeQuery();
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
        return versionsWaiting;
    }


    public void reviewArticles(ArrayList<ArticleVersion> a) throws SQLException {
        dbConnection.connection.setAutoCommit(false);
        String query = "BEGIN;\n";
        for (ArticleVersion version : a) {
            query = query.concat("UPDATE article_versions\n" +
                    "SET status = '" + version.getStatus().toString() + "'\n" +
                    "WHERE article_versions.id = " + version.getId() + ";\n");
        }
        query = query.concat("COMMIT;");
        PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
        stmt.execute();
        dbConnection.connection.rollback();
        dbConnection.connection.commit();

        dbConnection.connection.setAutoCommit(false);
    }
}