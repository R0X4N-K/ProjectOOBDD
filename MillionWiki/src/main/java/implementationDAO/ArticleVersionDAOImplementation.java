package implementationDAO;

import database.DatabaseConnection;
import model.ArticleVersion;

import java.sql.SQLException;
import java.util.ArrayList;

public class ArticleVersionDAOImplementation implements dao.ArticleVersionDAO{
    public DatabaseConnection dbConnection;
    public ArticleVersionDAOImplementation() throws RuntimeException {
        try {
            dbConnection = DatabaseConnection.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<ArticleVersion> getAllArticleVersions() {
        return null;
    }
}
