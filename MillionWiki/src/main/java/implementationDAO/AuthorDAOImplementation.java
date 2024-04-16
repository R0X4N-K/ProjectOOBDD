package implementationDAO;

import database.DatabaseConnection;
import model.Article;
import model.Author;
import model.Cookie;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorDAOImplementation implements dao.AuthorDAO{

    public DatabaseConnection dbConnection;
    public AuthorDAOImplementation() {
        try {
            dbConnection = DatabaseConnection.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public Author getAuthorByNickname(String nickname) {
        String query = "SELECT * FROM authors WHERE nickname = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, nickname);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    try {
                        return new Author(rs);
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Author getAuthorById(int id) {
        String query = "SELECT * FROM authors WHERE id = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Author(rs);
                }
                rs.close();
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public String getNicknameById(int id) {
        String query = "SELECT nickname FROM authors WHERE id = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nickname");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    public float getRatingByAuthorId(int id) {
        String query = "SELECT rating FROM authors WHERE id = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getFloat("rating");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    @Override
    public ArrayList<Author> getAllAuthors() {
        ArrayList<Author> authors = new ArrayList<>();
        String query = "SELECT * FROM authors";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                try {
                    authors.add(new Author(rs));
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return authors;
    }

    @Override
    public ArrayList<Author> getMatchesAuthorByNickname(String nicknameAuthor){
        ArrayList<Author> authors = new ArrayList<>();
        String query = "";

        if(nicknameAuthor.length() >= 4)
            query = "SELECT * FROM authors WHERE nickname ILIKE ? OR nickname ILIKE ? LIMIT 10";
        else
            query = "SELECT * FROM authors WHERE nickname ILIKE ? LIMIT 10";

        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            if(nicknameAuthor.length() >= 4) {
                stmt.setString(1, nicknameAuthor + "%");
                stmt.setString(2, "%" + nicknameAuthor + "%");
                // deep search -> stmt.setString(2, title.subSequence(0, ((title.length() / 2))) + "%");
            }
            else
                stmt.setString(1,nicknameAuthor + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                try {
                    authors.add(new Author(rs));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(e);


                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return authors;
    }

    @Override
    public void saveAuthor(Author author, String password) {
        String query = "INSERT INTO authors (nickname, password, rating, id, email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, author.getNickname());
            stmt.setString(2, password);
            stmt.setFloat(3, author.getRating());
            stmt.setInt(4, author.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    public void updateAuthor(Author author, Author newAuthor, String password) {
        String query = "UPDATE authors (nickname, password, rating, id, email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, author.getNickname());
            stmt.setString(2, password);
            stmt.setFloat(3, author.getRating());
            stmt.setInt(4, author.getId());
            stmt.setString(5, author.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    public void updateNicknameAuthor(Author author, String newNickname) {
        String query = "UPDATE authors SET nickname = ? WHERE id = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, newNickname);
            stmt.setInt(2, author.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void updateNicknameAuthor(int id, String newNickname){
            String query = "UPDATE authors SET nickname = ? WHERE id = ?";
            try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
                stmt.setString(1, newNickname);
                stmt.setInt(2, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
    }

    public void updateEmailAuthor(Author author, String newEmail) {
        String query = "UPDATE authors SET email = ? WHERE id = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, newEmail);
            stmt.setInt(2, author.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateEmailAuthor(int id, String newEmail) {
        String query = "UPDATE authors SET email = ? WHERE id = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, newEmail);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void updatePasswordAuthor(Author author, String newPassword) {

        String query = "UPDATE authors SET password = ? WHERE id = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, author.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updatePasswordAuthor(int id, String newPassword) {

        String query = "UPDATE authors SET password = ? WHERE id = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void updateAuthorRating(Author author, float newRating) {
        String query = "UPDATE authors SET rating = ? WHERE id = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setFloat(1, newRating);
            stmt.setInt(2, author.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void updateAuthorArticles(Author author, ArrayList<Article> newArticles, String password) {
        String query = "UPDATE authors SET articles = ? WHERE id = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setArray(1, dbConnection.connection.createArrayOf("articles", newArticles.toArray()));
            stmt.setInt(2, author.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

        @Override
        public void deleteAuthor (int id){
            String query = "DELETE FROM authors WHERE id = ?";
            try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    public void registerAuthor(String nickname, String password, float rating, String email) {
        String query = "INSERT INTO authors (nickname, password, rating, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, nickname);
            stmt.setString(2, password);
            stmt.setFloat(3, rating);
            stmt.setString(4, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }


    public Cookie loginAuthor(String email, String nickname, String password) {
        String query = "";
        String emailOrNickname = "";
        if (email != null) {
            query = "SELECT * FROM authors WHERE email = ?";
            emailOrNickname = email;
        }
        else if (nickname != null){
            query = "SELECT * FROM authors WHERE nickname = ?";
            emailOrNickname = nickname;
        }
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setString(1, emailOrNickname);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    try {
                        return new Cookie(rs);
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    @Override
    public Cookie loginAuthor(int id, String password) {
        String query = "SELECT password FROM authors WHERE id = ? AND password = ?";
        try (PreparedStatement stmt = dbConnection.connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cookie(id, password);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
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
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}