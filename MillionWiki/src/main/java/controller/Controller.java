package controller;

import dao.ArticleDAO;
import dao.ArticleVersionDAO;
import dao.AuthorDAO;
import implementationDAO.*;
import model.Article;
import model.ArticleVersion;
import model.Author;
import model.Cookie;

import java.sql.ResultSet;
import java.sql.SQLException;
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
            return new ArticleDAOImplementation().getArticleByTitle(articleTitle);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public static Cookie doLogin(String nicknameOrEmail, String password) throws SQLException {
        Cookie cookie = null;
        if (isAuthorRegisteredWithEmail(nicknameOrEmail)){
            cookie = new AuthorDAOImplementation().loginAuthor(nicknameOrEmail, null, password);

        }
        else if(isAuthorRegisteredWithNickname(nicknameOrEmail)){
            cookie = new AuthorDAOImplementation().loginAuthor(null, nicknameOrEmail, password);
        }
        return cookie;
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
    public static int createArticle(String title,Author author,Date creationDate,Boolean revision){
        ArticleVersion.Status status= ArticleVersion.Status.valueOf("Accepted");
        String text= "";
        return articleDAO.saveArticle(title, creationDate, revision, author, createProposal(title, status, text, creationDate, creationDate, author));
    }

    public static int createProposal(Article parentArticle, ArticleVersion.Status status,
                                      String text, Date versionDate, Date revisionDate,
                                      Author authorProposal) {
        return articleVersionDAO.insertArticleVersion(parentArticle.getTitle(), status, text, versionDate, revisionDate, authorProposal);
    }
    public static int createProposal(String title, ArticleVersion.Status status,
                                     String text, Date versionDate, Date revisionDate,
                                     Author authorProposal) {
        return articleVersionDAO.insertArticleVersion(title, status, text, versionDate, revisionDate, authorProposal);
    }


}
