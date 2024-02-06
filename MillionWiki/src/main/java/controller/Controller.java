package controller;

import implementationDAO.*;
import model.Article;

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

    public boolean isAuthorRegistered(String email){
        return false; //da implementare
    }


}
