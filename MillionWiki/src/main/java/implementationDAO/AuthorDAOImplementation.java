package implementationDAO;

import database.DatabaseConnection;
import model.Author;
import model.Cookie;

import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorDAOImplementation implements dao.AuthorDAO {

    public DatabaseConnection dbConnection;

    public AuthorDAOImplementation() throws SQLException, FileNotFoundException, ClassNotFoundException {
        dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public Author getAuthorByNickname(String nickname) throws SQLException, IllegalArgumentException {
        String query = "SELECT * FROM authors WHERE nickname = ?";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
        stmt.setString(1, nickname);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Author a = new Author(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("nickname")
            );
            a.setRating(rs.getFloat("rating"));
            return a;
        }
        return null;
    }

    public Author getAuthorById(int id) throws SQLException, IllegalArgumentException {
        String query = "SELECT * FROM authors WHERE id = ?";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Author a = new Author(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("nickname")
            );
            a.setRating(rs.getFloat("rating"));
            return a;
        }
        rs.close();
        stmt.close();
        return null;
    }

    public String getNicknameById(int id) throws SQLException {
        String query = "SELECT nickname FROM authors WHERE id = ?";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getString("nickname");
        }
        return null;
    }

    public float getRatingByAuthorId(int id) throws SQLException {
        String query = "SELECT rating FROM authors WHERE id = ?";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getFloat("rating");
        }
        return 0;
    }

    @Override
    public ArrayList<Author> getMatchesAuthorByNickname(String nicknameAuthor) throws SQLException, IllegalArgumentException {
        ArrayList<Author> authors = new ArrayList<>();
        String query = "";

        if (nicknameAuthor.length() >= 4)
            query = "SELECT * FROM authors WHERE nickname ILIKE ? OR nickname ILIKE ? LIMIT 10";
        else
            query = "SELECT * FROM authors WHERE nickname ILIKE ? LIMIT 10";

        PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
        if (nicknameAuthor.length() >= 4) {
            stmt.setString(1, nicknameAuthor + "%");
            stmt.setString(2, "%" + nicknameAuthor + "%");
            // deep search -> stmt.setString(2, title.subSequence(0, ((title.length() / 2))) + "%");
        } else
            stmt.setString(1, nicknameAuthor + "%");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Author a = new Author(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("nickname")
            );
            a.setRating(rs.getFloat("rating"));
            authors.add(a);
        }
        return authors;
    }

    public void updateNicknameAuthor(int id, String newNickname) throws SQLException {
        String query = "UPDATE authors SET nickname = ? WHERE id = ?";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
        stmt.setString(1, newNickname);
        stmt.setInt(2, id);
        stmt.executeUpdate();
    }

    public void updateEmailAuthor(int id, String newEmail) throws SQLException {
        String query = "UPDATE authors SET email = ? WHERE id = ?";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
        stmt.setString(1, newEmail);
        stmt.setInt(2, id);
        stmt.executeUpdate();
    }


    public void updatePasswordAuthor(int id, String newPassword) throws SQLException {

        String query = "UPDATE authors SET password = ? WHERE id = ?";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
        stmt.setString(1, newPassword);
        stmt.setInt(2, id);
        stmt.executeUpdate();
    }


    public void registerAuthor(String nickname, String password, float rating, String email) throws SQLException {
        String query = "INSERT INTO authors (nickname, password, rating, email) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
        stmt.setString(1, nickname);
        stmt.setString(2, password);
        stmt.setFloat(3, rating);
        stmt.setString(4, email);
        stmt.executeUpdate();
    }


    public Cookie loginAuthor(String email, String nickname, String password) throws SQLException {
        String query = "";
        String emailOrNickname = "";
        if (email != null) {
            query = "SELECT * FROM authors WHERE email = ?";
            emailOrNickname = email;
        } else if (nickname != null) {
            query = "SELECT * FROM authors WHERE nickname = ?";
            emailOrNickname = nickname;
        }
        PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
        stmt.setString(1, emailOrNickname);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            try { // TODO: REMOVE THIS
                return new Cookie(
                        rs.getInt("id"),
                        rs.getString("password"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }


    public boolean isEmailUsed(String email) throws SQLException {
        String query = "SELECT * FROM authors WHERE email = ?";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }

    public boolean isNicknameUsed(String nickname) throws SQLException {
        String query = "SELECT * FROM authors WHERE nickname = ?";
        PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
        stmt.setString(1, nickname);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }
}