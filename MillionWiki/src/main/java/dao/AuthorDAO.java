package dao;

import model.Article;
import model.Author;

import java.util.ArrayList;

public interface AuthorDAO {
    Author getAuthorByNickname(String nickname);
    ArrayList<Author> getAllAuthors();

    void saveAuthor(Author author, String password);

    void updateAuthor(Author author, Author newAuthor, String password);

    void updateNicknameAuthor(Author author, String newNickname);
    void updatePasswordAuthor(Author author, String newPassword);
    void updateAuthorRating(Author author, float newRating);
    void updateAuthorArticles(Author author, ArrayList<Article> newArticles, String password);
    void deleteAuthor(int id);
    public Author loginAuthor(String email, String nickname, String password);
    public String loginAuthor(int id);
    public void registerAuthor(String nickname, String password, float rating, String email);
    public boolean isEmailUsed(String email);
    public boolean isNicknameUsed(String nickname);
}
