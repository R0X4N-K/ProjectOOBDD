package controller;

import implementationDAO.*;
import model.Article;
import model.Author;

public class Controller {
    public Controller() {

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

    public boolean isAuthorRegistered(String email){
        return new AuthorDAOImplementation().isEmailUsed(email);
    }


}
