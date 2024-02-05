package model;
import database.DatabaseConnection;

import java.lang.String;
import java.lang.Exception;
import java.sql.SQLException;

public final class Session {
    private static Session instance;
    private Session(){

    }
    public Author login (String nickname, String password){
        return null;
    }

    public void logout (Author a) {

    }

    public Author editName (String nickname) {
        return null;
    }
    public void editPassword (String password) {
    }


    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }


    /*nella classe Session, abbiamo i metodi creaArticolo e proponeModifica
    che utilizzano i metodi scriviArticolo della classe Author.
    Questo è fatto per gestire le operazioni di creazione di articoli e
    proposta di modifiche attraverso la sessione dell'utente, garantendo
     che solo gli utenti autenticati possano eseguire queste operazioni.*/

}
