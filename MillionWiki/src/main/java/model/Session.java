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
}
