package controller;

import implementationDAO.ArticleDAOImplementation;
import model.Article;

public class Controller extends ArticleDAOImplementation {
    public Controller() {

    }

    public Article getArticleByTitle(String articleTitle){
        try {
            //Article article =  new Article(getResultSetArticleByTitle().getString(titolo),....)
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null; //return article
    }
}
