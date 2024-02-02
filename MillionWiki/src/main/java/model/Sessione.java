package model;
import java.lang.String;
import java.lang.Exception;

public final class Sessione {
    private Sessione(){

    }
    public Autore login (String nomeUtente, String password) throws Exception {

        Autore autoreAutenticato = new Autore(nomeUtente, password);

        return autoreAutenticato;

    }

    public void logout (Autore a) {

    }

    public Autore newAutore (String nomeUtente, String password) throws Exception {
        Autore nuovoAutore = new Autore(nomeUtente, password);
        return nuovoAutore;
    }

    public Autore modificaNome (String nomeUtente) {
        return null;
    }
    public void modificaPassword (String password) {
    }

    /*nella classe Sessione, abbiamo i metodi creaArticolo e proponeModifica
    che utilizzano i metodi scriviArticolo della classe Autore.
    Questo è fatto per gestire le operazioni di creazione di articoli e
    proposta di modifiche attraverso la sessione dell'utente, garantendo
     che solo gli utenti autenticati possano eseguire queste operazioni.*/
}
