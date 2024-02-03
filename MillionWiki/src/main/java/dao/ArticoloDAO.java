package dao;

import model.Articolo;

import java.util.ArrayList;

public interface ArticoloDAO {
    Articolo getArticolo(String titolo);
    ArrayList<Articolo> getAllArticoli();
    ArrayList<Articolo> getAllArticoliByAutore(String nicknameAutore);

    void saveArticolo(Articolo articolo);
    void updateArticolo(Articolo articolo, Articolo nuovoArticolo);
    void updateRevisioneArticolo(Articolo articolo, boolean nuovoStatoRevisione);
    void deleteArticolo(Articolo articolo);

}
