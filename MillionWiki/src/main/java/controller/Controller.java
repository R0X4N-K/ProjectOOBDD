package controller;

import dao.ArticleDAO;
import dao.ArticleVersionDAO;
import dao.AuthorDAO;
import implementationDAO.*;
import model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public final class Controller {

    private static Cookie cookie;
    private static ArticleDAO articleDAO = null;
    private static ArticleVersionDAO articleVersionDAO = null;
    private static AuthorDAO authorDAO = null;
    static private String lockFilePath = Cookie.getConfigFolder().concat("lockFile");

    private Controller() {

    }

    public static void checkIfRememberedLogin() {
        try {
            setCookie(Cookie.retriveLogin());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (cookie != null && cookie.getId() >= 0 && cookie.getPassword() != null) {
            //System.out.println(cookie.getId());
            //System.out.println(cookie.getPassword());
        }
    }

    public static void setCookie(Cookie cookie) {
        Controller.cookie = cookie;
    }
    public static Cookie getCookie() { return cookie; }

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
    public static boolean checkLoggedUser(){
        return cookie != null;
    }

    public static boolean verifyAppIstances() {
        boolean thereAreIstances = true;

        File f = new File(lockFilePath);

        if (!f.exists()) {
            try {
                Files.createDirectories(Paths.get(Cookie.getConfigFolder()));
                f.createNewFile();

                thereAreIstances = false;

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return  thereAreIstances;
    }


    public static void notifyOtherAppIstances() {

        System.out.println("Un'altra istanza di questa applicazione è attualmente in esecuzione, " +
                "ti preghiamo di chiudere quell'istanza prima di aprirne una nuova. Se non sono aperte altre istanze, elimina il file: "
                + lockFilePath);
    }

    public static void deleteLockFile() {
        File f = new File(lockFilePath);
        if(f.exists()) {
            f.delete();
        }
    }
    public static Author getAuthorById(int Id){
        return new AuthorDAOImplementation().getAuthorById(Id);
    }
}