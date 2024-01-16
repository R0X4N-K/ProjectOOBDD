package model;
import java.lang.String;

public final class Sessione {
    private Sessione(){}
    public Autore login (String nomeUtente, String password) {

        Autore autoreAutenticato = new Autore(nomeUtente, password);

        return autoreAutenticato;

    }

    public void logout (Autore a) {

    }

    public Autore newAutore (String nomeUtente, String password) {
        float valutazione = 0.0f;
        Autore nuovoAutore = new Autore(nomeUtente, password, valutazione);
        return nuovoAutore;
    }

    public Autore modificaNome (String nomeUtente) {

    }
    public void modificaPassword (String password) { }
}
