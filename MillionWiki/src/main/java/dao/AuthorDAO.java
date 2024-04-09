package dao;

import model.Article;
import model.Author;
import model.Cookie;

import java.util.ArrayList;

public interface AuthorDAO {
    Author getAuthorByNickname(String nickname);
    Author getAuthorById(int id);
    String getNicknameById(int id);
    ArrayList<Author> getAllAuthors();

    ArrayList<Author> getMatchesAuthorByNickname(String nicknameAuthor);

    void saveAuthor(Author author, String password);

    void updateAuthor(Author author, Author newAuthor, String password);

    void updateNicknameAuthor(Author author, String newNickname);
    void updateNicknameAuthor(int id, String newNickname);
    void updatePasswordAuthor(Author author, String newPassword);
    void updatePasswordAuthor(int id, String newPassword);
    void updateEmailAuthor(int id, String newEmail);
    void updateAuthorRating(Author author, float newRating);
    void updateAuthorArticles(Author author, ArrayList<Article> newArticles, String password);
    void deleteAuthor(int id);
    Cookie loginAuthor(String email, String nickname, String password);
    Cookie loginAuthor(int id, String password);
    void registerAuthor(String nickname, String password, float rating, String email);
    boolean isEmailUsed(String email);
    boolean isNicknameUsed(String nickname);
}
