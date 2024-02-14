package controller;

import dao.AuthorDAO;
import implementationDAO.*;
import model.Article;
import model.Author;

public class Controller {

    private static Controller instance;

    private Controller() {
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }


    public Article getArticleByTitle(String articleTitle){
        try {
            //Article article =  new Article(getResultSetArticleByTitle().getString(titolo),....)
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null; //return article
        //da implementare
    }


    public Author getAuthorByNickname(String nickname){
        return new AuthorDAOImplementation().getAuthorByNickname(nickname);
    }

    public boolean isAuthorRegisteredWithEmail(String email){
        return new AuthorDAOImplementation().isEmailUsed(email);
    }
    public boolean isAuthorRegisteredWithNickname(String nickname){
        return new AuthorDAOImplementation().isNicknameUsed(nickname);
    }
    public boolean doRegistration(String email, String nickname, String password){
        if (isAuthorRegisteredWithEmail(email) || isAuthorRegisteredWithNickname(nickname)){
            return false;
        }
        new AuthorDAOImplementation().registerAuthor(nickname, password, 0, email);
        return true;
    }

}
