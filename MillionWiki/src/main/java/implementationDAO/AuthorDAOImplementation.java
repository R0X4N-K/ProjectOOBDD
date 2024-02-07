package implementationDAO;

import database.DatabaseConnection;
import model.Article;
import model.Author;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        String query = "SELECT * FROM authors WHERE nickname = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, nickname);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Author(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Author> getAllAuthors() {
        ArrayList<Author> authors = new ArrayList<>();
        String query = "SELECT * FROM authors";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                authors.add(new Author(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    @Override
    public void saveAuthor(Author author, String password) {
        String query = "INSERT INTO authors (nickname, password, rating) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, author.getNickname());
            stmt.setString(2, password);
            stmt.setFloat(3, author.getRating());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateAuthor(Author author, Author newAuthor, String password) {
        String query = "UPDATE authors (nickname, password, rating) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, author.getNickname());
            stmt.setString(2, password);
            stmt.setFloat(3, author.getRating());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateNicknameAuthor(Author author, String newNickname) {
        String query = "UPDATE authors SET nickname = ? WHERE nickname = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, newNickname);
            stmt.setString(2, author.getNickname());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updatePasswordAuthor(Author author, String newPassword) {

        String query = "UPDATE authors SET password = ? WHERE nickname = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, author.getNickname());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAuthorRating(Author author, float newRating) {
        String query = "UPDATE authors SET rating = ? WHERE nickname = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setFloat(1, newRating);
            stmt.setString(2, author.getNickname());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAuthorArticles(Author author, ArrayList<Article> newArticles, String password) {
        String query = "UPDATE authors SET articles = ? WHERE nickname = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setArray(1, dbConnection.connection.createArrayOf("articles", newArticles.toArray()));
            stmt.setString(2, author.getNickname());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        @Override
        public void deleteAuthor (String nickname){
            String query = "DELETE FROM authors WHERE nickname = ?";
            try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
                stmt.setString(1, nickname);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    public void registerAuthor(Author author, String password) {
        String query = "INSERT INTO authors (nickname, password, rating) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, author.getNickname());
            stmt.setString(2, password);
            stmt.setFloat(3, author.getRating());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Author loginAuthor(String nickname, String password) {
        String query = "SELECT * FROM authors WHERE nickname = ? AND password = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, nickname);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Author(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Questo metodo restituisce Author se l'autore è presente nel database e la password è corretta
        // Altrimenti restituisce null
        return null;
    }
    public boolean isEmailUsed(String email) {
        String query = "SELECT * FROM authors WHERE email = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isNicknameUsed(String nickname) {
        String query = "SELECT * FROM authors WHERE nickname = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, nickname);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    }