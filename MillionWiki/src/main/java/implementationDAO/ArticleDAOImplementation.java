package implementationDAO;


import database.DatabaseConnection;
import model.Article;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
1 connection to db
2 create statement object
3 execute statement object to get a ResultSet
*/

public class ArticleDAOImplementation implements dao.ArticleDAO {
    public DatabaseConnection dbConnection;

    public ArticleDAOImplementation() throws RuntimeException {
        //connection to db
        try {
            dbConnection = DatabaseConnection.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResultSet getResultSetArticleByTitle(String articleTitle) throws SQLException {
        String getArticleQuery = "SELECT * FROM articoli WHERE titolo = ?";
        ResultSet resultSet = null;
        PreparedStatement stmt = null;
        try{
            stmt = dbConnection.connection.prepareStatement(getArticleQuery);
            stmt.setString(1, articleTitle);

            resultSet = stmt.executeQuery();

        }catch (SQLException e){
            e.printStackTrace();
        }

        if(!resultSet.next()){
            System.out.println("Nessun articolo con questo titolo");
        }

        return resultSet; //Da ritornare un nuovo oggetto di tipo Article oppure il resultSet e
                     // poi nel controller creare l'oggetto Article

    }

    @Override
    public ArrayList<Article> getAllArticles() {
        return null;
    }

    @Override
    public ArrayList<Article> getAllArticlesByAuthor(String nicknameAuthor) {
        return null;
    }

    @Override
    public void saveArticle(Article article) {

    }

    @Override
    public void updateArticle(Article article, Article newArticle) {

    }

    @Override
    public void updateRevisionArticle(Article article, boolean newArticleRevisionStatus) {

    }

    @Override
    public void deleteArticle(Article articleToDelete) {

    }
}
