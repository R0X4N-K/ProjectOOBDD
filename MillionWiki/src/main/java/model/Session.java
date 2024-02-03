package model;
import java.lang.String;
import java.lang.Exception;

public final class Session {
    private Session(){

    }
    public Author login (String nickname, String password) throws Exception {
        return null;
    }

    public void logout (Author a) {

    }

    public Author newAuthor (String nickname, String password) throws Exception {
        return null;
    }

    public Author editName (String nickname) {
        return null;
    }
    public void editPassword (String password) {
    }

    /*nella classe Session, abbiamo i metodi creaArticolo e proponeModifica
    che utilizzano i metodi scriviArticolo della classe Author.
    Questo è fatto per gestire le operazioni di creazione di articoli e
    proposta di modifiche attraverso la sessione dell'utente, garantendo
     che solo gli utenti autenticati possano eseguire queste operazioni.*/
}
