package implementationDAO;

import database.DatabaseConnection;
import model.Article;
import model.Author;

import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorDAOImplementation implements dao.AuthorDAO{
    public DatabaseConnection dbConnection;
    public AuthorDAOImplementation() throws RuntimeException {
        try {
            dbConnection = DatabaseConnection.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Author getAuthorByNickname(String nickname) {
        return null;
    }

    @Override
    public ArrayList<Author> getAllAuthors() {
        return null;
    }

    @Override
    public void saveAuthors(Author author) {

    }

    @Override
    public void updateAuthor(Author author, Author newAuthor) {

    }

    @Override
    public void updateNicknameAuthor(Author author, String newNickname) {

    }

    @Override
    public void updatePasswordAuthor(Author author, String newPassword) {

    }

    @Override
    public void updateAuthorRating(Author author, float newRating) {

    }

    @Override
    public void updateAuthorArticles(Author author, ArrayList<Article> newArticles) {

    }

    @Override
    public void deleteAuthor(String nickname) {

    }
}
