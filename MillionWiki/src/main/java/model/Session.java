package model;
import java.lang.String;
import java.lang.Exception;

public final class Session {
    private Session(){

    }
    public Author login (String nomeUtente, String password) throws Exception {

        Author authorAutenticato = new Author(nomeUtente, password);

        return authorAutenticato;

    }

    public void logout (Author a) {

    }

    public Author newAutore (String nomeUtente, String password) throws Exception {
        Author nuovoAuthor = new Author(nomeUtente, password);
        return nuovoAuthor;
    }

    public Author modificaNome (String nomeUtente) {
        return null;
    }
    public void modificaPassword (String password) {
    }

    /*nella classe Session, abbiamo i metodi creaArticolo e proponeModifica
    che utilizzano i metodi scriviArticolo della classe Author.
    Questo è fatto per gestire le operazioni di creazione di articoli e
    proposta di modifiche attraverso la sessione dell'utente, garantendo
     che solo gli utenti autenticati possano eseguire queste operazioni.*/
}
