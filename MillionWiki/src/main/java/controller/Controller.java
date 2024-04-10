package controller;

import dao.ArticleDAO;
import dao.ArticleVersionDAO;
import dao.AuthorDAO;
import gui.Window;
import implementationDAO.*;
import model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public final class Controller {

    private static Cookie cookie;
    private static ArticleDAO articleDAO = null;
    private static ArticleVersionDAO articleVersionDAO = null;
    private static AuthorDAO authorDAO = null;
    private static final String lockFilePath = Cookie.getConfigFolder().concat("lockFile");

    public static Window getWindow() {
        return window;
    }

    public static void setWindow(Window window) {
        Controller.window = window;
    }

    private static Window window;

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
    public static void deleteCookie() {
        Cookie.deleteCookie();
    }

    public static Article getArticleByTitle(String articleTitle){
        try {
            return new ArticleDAOImplementation().getArticleByTitle(articleTitle);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void incrementArticleViews(int idArticle){
        new ArticleDAOImplementation().incrementArticleViews(idArticle);
    }

    public static ArrayList<Article> getMostViewedArticles(int numberArticles){
        return new ArticleDAOImplementation().getMostViewedArticles(numberArticles);
    }

    public static void changeNickname(String newNickname){
        new AuthorDAOImplementation().updateNicknameAuthor(cookie.getId(),newNickname);
    }
    public static void changeEmail(String newEmail){
        new AuthorDAOImplementation().updateEmailAuthor(cookie.getId(),newEmail);
    }
    public static void changePassword(String newPassword){
        new AuthorDAOImplementation().updatePasswordAuthor(cookie.getId(), newPassword);
    }

    public static String getTitleByArticleId(int idArticle){
        return new ArticleDAOImplementation().getTitleArticleByArticleId(idArticle);
    }

    public static ArticleVersion getLastArticleVersionByArticleId(int idArticle){
        return new ArticleVersionDAOImplementation().getLastArticleVersionByArticleId(idArticle);
    }
    public static ArrayList<Integer> getRecentArticles(int numberArticles){
        return new ArticleDAOImplementation().getRecentArticles(numberArticles);
    }
    public static ArrayList<ArticleVersion> getAllArticleVersionByArticleId(int idArticle){
        return new ArticleVersionDAOImplementation().getAllArticleVersionByArticleId(idArticle);
    }
    public static ArrayList<ArticleVersion> getAllArticleVersionByAuthorId(int authorId){
        return new ArticleVersionDAOImplementation().getAllArticleVersionByAuthorId(authorId);
    }
    public static Article getArticlesById(int idArticle){
        return new ArticleDAOImplementation().getArticleById(idArticle);
    }

    public static ArrayList<Article> getMatchesArticlesByTitle(String articleTitle){
        return new ArticleDAOImplementation().getMatchesArticlesByTitle(articleTitle);
    }

    public static ArrayList<Author> getMatchesAuthorByNickname(String nicknameAuthor){
        return new AuthorDAOImplementation().getMatchesAuthorByNickname(nicknameAuthor);
    }

    public static ArrayList<Article> getArticlesByIdAuthor(int idAuthor){
        return new ArticleDAOImplementation().getAllArticlesByIdAuthor(idAuthor);
    }
    public static int getArticlesNumberByIdAuthor(int idAuthor){
        return new ArticleDAOImplementation().getAllArticlesNumberByIdAuthor(idAuthor);
    }
    public static int getArticlesNumberSentByIdAuthor(int idAuthor){
        return new ArticleVersionDAOImplementation().getVersionArticlesNumberSent(idAuthor);
    }
    public static Author getAuthorByNickname(String nickname){
        return new AuthorDAOImplementation().getAuthorByNickname(nickname);
    }
    public static float getRatingByAuthorId(int id){
        return new AuthorDAOImplementation().getRatingByAuthorId(id);
    }
    public static String getNicknameAuthorById(int idAuthor){
        return new AuthorDAOImplementation().getNicknameById(idAuthor);
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
        return articleDAO.saveArticle(title, creationDate, revision, author);
    }
    public static int createArticle(String title,int idAuthor, Date creationDate,Boolean revision, String text){
        int idArticle= articleDAO.saveArticle(title, creationDate, revision, idAuthor);
        ArticleVersion.Status status = ArticleVersion.Status.valueOf("ACCEPTED");
        articleVersionDAO.saveArticleVersion(idArticle, status, text, creationDate, creationDate, idAuthor, title);
        return idArticle;
    }

    public static int createProposal(int idArticle, String titleArticle, ArticleVersion.Status status,
                                     String text, Date versionDate, Date revisionDate,
                                     int idAuthor){
        /*if (idAuthor == cookie.getId()) {
            articleDAO.updateArticle(idArticle, titleArticle);
        }*/ // TODO: RIMUOVERE FUNZIONE
        return articleVersionDAO.insertArticleVersion(idArticle, status, text, versionDate, revisionDate, idAuthor, titleArticle);
    }

    public static int createProposal(Article parentArticle, ArticleVersion.Status status,
                                     String text, Date versionDate, Date revisionDate,
                                     Author authorProposal, String titleProposal) {
        return articleVersionDAO.insertArticleVersion(parentArticle.getTitle(), status, text, versionDate, revisionDate, authorProposal, titleProposal);
    }
    public static int createProposal(String title, ArticleVersion.Status status,
                                     String text, Date versionDate, Date revisionDate,
                                     Author authorProposal, String titleProposal) {
        return articleVersionDAO.insertArticleVersion(title, status, text, versionDate, revisionDate, authorProposal, titleProposal);
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
    public static Author getAuthorById(int id){
        return new AuthorDAOImplementation().getAuthorById(id);
    }


}