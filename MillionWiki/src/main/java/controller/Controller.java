package controller;

import dao.ArticleDAO;
import dao.ArticleVersionDAO;
import dao.AuthorDAO;
import gui.SplashScreen;
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
import java.util.Objects;

public final class Controller {

    private static Cookie cookie;
    private static ArticleDAO articleDAO = null;
    private static ArticleVersionDAO articleVersionDAO = null;
    private static AuthorDAO authorDAO = null;
    private static final String lockFilePath = Cookie.getConfigFolder().concat("lockFile");

    public static Window getWindow() {
        return window;
    }

    public static SplashScreen getSplashScreen() {
        return splashScreen;
    }

    public static void setSplashScreen(SplashScreen splashScreen) {
        Controller.splashScreen = splashScreen;
    }

    public static void setWindow(Window window) {
        Controller.window = window;
    }

    private static SplashScreen splashScreen;
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
        articleDAO.incrementArticleViews(idArticle);
    }

    public static ArrayList<Article> getMostViewedArticles(int numberArticles){
        return articleDAO.getMostViewedArticles(numberArticles);
    }

    public static void changeNickname(String newNickname){
        authorDAO.updateNicknameAuthor(cookie.getId(),newNickname);
    }
    public static void changeEmail(String newEmail){
        authorDAO.updateEmailAuthor(cookie.getId(),newEmail);
    }
    public static void changePassword(String newPassword){
        authorDAO.updatePasswordAuthor(cookie.getId(), newPassword);
    }

    public static String getTitleByArticleId(int idArticle){
        return articleDAO.getTitleArticleByArticleId(idArticle);
    }

    public static ArticleVersion getLastArticleVersionByArticleId(int idArticle){
        return articleVersionDAO.getLastArticleVersionByArticleId(idArticle);
    }

    public static ArrayList<Integer> getRecentArticles(int numberArticles){
        return articleDAO.getRecentArticles(numberArticles);
    }
    public static ArrayList<ArticleVersion> getAllArticleVersionByArticleId(int idArticle){
        return articleVersionDAO.getAllArticleVersionByArticleId(idArticle);
    }
    public static ArrayList<ArticleVersion> getAllArticleVersionByAuthorId(int authorId){
        return articleVersionDAO.getAllArticleVersionByAuthorId(authorId);
    }
    public static Article getArticlesById(int idArticle){
        return articleDAO.getArticleById(idArticle);
    }

    public static ArrayList<Article> getMatchesArticlesByTitle(String articleTitle){
        return articleDAO.getMatchesArticlesByTitle(articleTitle);
    }

    public static ArrayList<Author> getMatchesAuthorByNickname(String nicknameAuthor){
        return authorDAO.getMatchesAuthorByNickname(nicknameAuthor);
    }

    public static ArrayList<Article> getArticlesByIdAuthor(int idAuthor){
        return articleDAO.getAllArticlesByIdAuthor(idAuthor);
    }
    public static int getArticlesNumberByIdAuthor(int idAuthor){
        return articleDAO.getAllArticlesNumberByIdAuthor(idAuthor);
    }
    public static int getArticlesNumberSentByIdAuthor(int idAuthor){
        return articleVersionDAO.getVersionArticlesNumberSent(idAuthor);
    }
    public static ArticleVersion getArticleVersionByIdArticleVersion (int idArticleVersion){
        return articleVersionDAO.getArticleVersionByIdArticleVersion(idArticleVersion);
    }
    public static Author getAuthorByNickname(String nickname){
        return authorDAO.getAuthorByNickname(nickname);
    }
    public static float getRatingByAuthorId(int id){
        return authorDAO.getRatingByAuthorId(id);
    }
    public static String getNicknameAuthorById(int idAuthor){
        return authorDAO.getNicknameById(idAuthor);
    }

    public static boolean isAuthorRegisteredWithEmail(String email){
        return authorDAO.isEmailUsed(email);
    }

    public static boolean isAuthorRegisteredWithNickname(String nickname){
        return authorDAO.isNicknameUsed(nickname);
    }

    public static Cookie doLogin(String nicknameOrEmail, String password) throws SQLException {
        Cookie cookie = null;
        if (isAuthorRegisteredWithEmail(nicknameOrEmail)){
            cookie = authorDAO.loginAuthor(nicknameOrEmail, null, password);

        }
        else if(isAuthorRegisteredWithNickname(nicknameOrEmail)){
            cookie = authorDAO.loginAuthor(null, nicknameOrEmail, password);
        }
        return cookie;
    }

    public static boolean doRegistration(String email, String nickname, String password){
        if (isAuthorRegisteredWithEmail(email) || isAuthorRegisteredWithNickname(nickname)){
            return false;
        }
        authorDAO.registerAuthor(nickname, password, 0, email);
        return true;
    }

    public static ArrayList<ArticleVersion> getNotifications() {
        return articleVersionDAO.getAllArticleVersionsWaiting(cookie.getId());
    }

    public static ArrayList<ArticleVersion> getNotificationsText(int articleId) {
        return articleVersionDAO.getAllArticleVersionsWaitingFull(articleId);
    }

    public static void reviewArticles(ArrayList<ArticleVersion> a) {
        articleVersionDAO.reviewArticles(a);
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

    public static int createArticle(String title,int idAuthor,Boolean revision, String text){
        int idArticle = articleDAO.saveArticle(title, revision, idAuthor);

        articleVersionDAO.insertArticleVersion(idArticle, text, idAuthor, title);
        return idArticle;
    }

    public static int createProposal(int idArticle, String titleArticle,
                                     String text, int idAuthor){
        /*if (idAuthor == cookie.getId()) {
            articleDAO.updateArticle(idArticle, titleArticle);
        }*/ // TODO: RIMUOVERE FUNZIONE
        return articleVersionDAO.insertArticleVersion(idArticle, text, idAuthor, titleArticle);
    }

    public static int createProposal(Article parentArticle, String text,
                                     Author authorProposal, String titleProposal) {
        return articleVersionDAO.insertArticleVersion(parentArticle.getId(), text, authorProposal.getId(), titleProposal);
    }
    public static int createProposal(String title,String text,
                                     Author authorProposal, String titleProposal) {
        return articleVersionDAO.insertArticleVersion(authorProposal.getId(), text, Objects.requireNonNull(getArticleByTitle(title)).getId(), titleProposal);
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

        try {
            getSplashScreen().dispose();
        }catch(NullPointerException e){
            System.out.println("Splash screen è null");
        }
    }

    public static void deleteLockFile() {
        File f = new File(lockFilePath);
        if(f.exists()) {
            f.delete();
        }
    }
    public static Author getAuthorById(int id){
        return authorDAO.getAuthorById(id);
    }


}