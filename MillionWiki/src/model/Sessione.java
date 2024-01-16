package model;
import java.lang.String;
import java.lang.Exception;

public final class Sessione {
    private Sessione(){}
    public Autore login (String nomeUtente, String password) {

        Autore autoreAutenticato = new Autore(nomeUtente, password);

        return autoreAutenticato;

    }

    public void logout (Autore a) {

    }

    public Autore newAutore (String nomeUtente, String password) {
        Autore nuovoAutore = new Autore(nomeUtente, password);
        return nuovoAutore;
    }

    public Autore modificaNome (String nomeUtente) {
        return null;
    }
    public void modificaPassword (String password) {
    }
}
