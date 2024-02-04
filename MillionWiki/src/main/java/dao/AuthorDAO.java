package dao;

import model.Article;
import model.Author;

import java.util.ArrayList;

public interface AuthorDAO {
    Author getAuthorByNickname(String nickname);
    ArrayList<Author> getAllAuthors();
    void saveAuthors(Author author);
    void updateAuthor(Author author, Author newAuthor);
    void updateNicknameAuthor(Author author, String newNickname);
    void updatePasswordAuthor(Author author, String newPassword);
    void updateAuthorRating(Author author, float newRating);
    void updateAuthorArticles(Author author, ArrayList<Article> newArticles);
    void deleteAuthor(String nickname);
}
