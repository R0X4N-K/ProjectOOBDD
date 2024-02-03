package dao;

import model.Article;
import model.Author;

import java.util.ArrayList;

public interface AutoreDAO {
    Author getAutoreByNickname(String nickname);
    ArrayList<Author> getAllAutori();
    ArrayList<Author> getAllAutoriByRating();
    void saveAutore(Author author);
    void updateAutore(Author author, Author nuovoAuthor);
    void updateNicknameAutore(Author author, String nuovoNickname);
    void updatePasswordAutore(Author author, String nuovaPassword);
    void updateValutazioneAutore(Author author, float nuovaValutazione);
    void updateArticoliAutore(Author author, ArrayList<Article> nuoviArticoli);
    void deleteAutore(String nickname);
}
