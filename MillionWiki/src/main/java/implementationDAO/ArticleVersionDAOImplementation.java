package implementationDAO;
import dao.ArticleVersionDAO;
import database.DatabaseConnection;
import model.ArticleVersion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



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

}