package dao;

import model.Articolo;
import model.Autore;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface AutoreDAO {
    Autore getAutoreByNickname(String nickname);
    ArrayList<Autore> getAllAutori();
    ArrayList<Autore> getAllAutoriByRating();
    void saveAutore(Autore autore);
    void updateAutore(Autore autore, Autore nuovoAutore);
    void updateNicknameAutore(Autore autore, String nuovoNickname);
    void updatePasswordAutore(Autore autore, String nuovaPassword);
    void updateValutazioneAutore(Autore autore, float nuovaValutazione);
    void updateArticoliAutore(Autore autore, ArrayList<Articolo> nuoviArticoli);
    void deleteAutore(String nickname);
}
