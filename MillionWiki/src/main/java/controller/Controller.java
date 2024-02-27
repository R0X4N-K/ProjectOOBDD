package controller;

import dao.ArticleDAO;
import dao.ArticleVersionDAO;
import dao.AuthorDAO;
import implementationDAO.*;
import model.Article;
import model.ArticleVersion;
import model.Author;

import java.util.Date;

public final class Controller {

    private static Controller instance;
    private static ArticleDAO articleDAO = null;
    private static ArticleVersionDAO articleVersionDAO = null;
    private static AuthorDAO authorDAO = null;

    private Controller() {

    }



    public static Article getArticleByTitle(String articleTitle){
        try {
            //Article article =  new Article(getResultSetArticleByTitle().getString(titolo),....)
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null; //return article
        //da implementare
    }


    public static Author getAuthorByNickname(String nickname){
        return new AuthorDAOImplementation().getAuthorByNickname(nickname);
    }

    public static boolean isAuthorRegisteredWithEmail(String email){
        return new AuthorDAOImplementation().isEmailUsed(email);
    }

    public static boolean isAuthorRegisteredWithNickname(String nickname){
        return new AuthorDAOImplementation().isNicknameUsed(nickname);
    }

    public static Author doLogin(String nicknameOrEmail, String password){
        if (isAuthorRegisteredWithEmail(nicknameOrEmail)){
            return new AuthorDAOImplementation().loginAuthor(nicknameOrEmail, null, password);
        }
        else if(isAuthorRegisteredWithNickname(nicknameOrEmail)){
           return new AuthorDAOImplementation().loginAuthor(null, nicknameOrEmail, password);
        }
        return null;
    }

    public static boolean doRegistration(String email, String nickname, String password){
        if (isAuthorRegisteredWithEmail(email) || isAuthorRegisteredWithNickname(nickname)){
            return false;
        }
        new AuthorDAOImplementation().registerAuthor(nickname, password, 0, email);
        return true;
    }

    public static void setArticleDAO (ArticleDAO dao) throws NullPointerException {
        if (dao == null) {
            throw new NullPointerException();
        } else {
            articleDAO = dao;
        }
    }

    public static void setArticleVersionDAO (ArticleVersionDAO dao) throws NullPointerException {
        if (dao == null) {
            throw new NullPointerException();
        } else {
            articleVersionDAO = dao;
        }
    }

    public static void setAuthorDAO (AuthorDAO dao) throws NullPointerException {
        if (dao == null) {
            throw new NullPointerException();
        } else {
            authorDAO = dao;
        }
    }

    public static void createProposal(Article parentArticle, ArticleVersion.Status status,
                                      String text, Date versionDate, Date revisionDate,
                                      Author authorProposal) {
        articleVersionDAO.insertArticleVersion(parentArticle, status, text, versionDate, revisionDate, authorProposal);
    }

}
