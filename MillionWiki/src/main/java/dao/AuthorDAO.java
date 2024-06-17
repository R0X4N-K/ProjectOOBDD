package dao;

import model.Author;
import model.Cookie;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AuthorDAO {
    Author getAuthorByNickname(String nickname) throws SQLException, IllegalArgumentException;

    Author getAuthorById(int id) throws SQLException, IllegalArgumentException;

    String getNicknameById(int id) throws SQLException;

    float getRatingByAuthorId(int id) throws SQLException;

    ArrayList<Author> getMatchesAuthorByNickname(String nicknameAuthor) throws SQLException, IllegalArgumentException;

    void updateNicknameAuthor(int id, String newNickname) throws SQLException;

    void updatePasswordAuthor(int id, String newPassword) throws SQLException;

    void updateEmailAuthor(int id, String newEmail) throws SQLException;

    Cookie loginAuthor(String email, String nickname, String password) throws SQLException;

    void registerAuthor(String nickname, String password, float rating, String email) throws SQLException;

    boolean isEmailUsed(String email) throws SQLException;

    boolean isNicknameUsed(String nickname) throws SQLException;
}
