package controller;

import dao.ArticleDAO;
import dao.ArticleVersionDAO;
import dao.AuthorDAO;
import gui.SplashScreen;
import gui.Window;
import model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;


public final class Controller {

    private static Cookie cookie;
    private static ArticleDAO articleDAO = null;
    private static ArticleVersionDAO articleVersionDAO = null;
    private static AuthorDAO authorDAO = null;
    private static final String configFolder = System.getProperty("user.home")
            .concat(FileSystems.getDefault().getSeparator())
            .concat(".MillionWiki")
            .concat(FileSystems.getDefault().getSeparator());
    private static final String lockFilePath = configFolder.concat("lockFile");


    public static Window getWindow() {
        return window;
    }

    public static String getConfigFolder() {
        return configFolder;
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
        /*if (cookie != null && cookie.getId() >= 0 && cookie.getPassword() != null) {
            System.out.println(cookie.getId());
            System.out.println(cookie.getPassword());
        }*/
    }

    public static void setCookie(Cookie cookie) {
        Controller.cookie = cookie;
    }
    public static Cookie getCookie() { return cookie; }
    public static void deleteCookie() {
        Cookie.deleteCookie();
    }

    public static Article getArticleByTitle(String articleTitle) throws SQLException {
        return articleDAO.getArticleByTitle(articleTitle);
    }

    public static Article pickRandomArticle() throws SQLException, IllegalArgumentException {
        return articleDAO.pickRandomArticle();
    }

    public static void incrementArticleViews(int idArticle) throws SQLException {
        articleDAO.incrementArticleViews(idArticle);
    }

    public static ArrayList<Article> getMostViewedArticles(int numberArticles) throws SQLException {
        return articleDAO.getMostViewedArticles(numberArticles);
    }

    public static void changeNickname(String newNickname) throws SQLException {
        authorDAO.updateNicknameAuthor(cookie.getId(),newNickname);
    }
    public static void changeEmail(String newEmail) throws SQLException {
        authorDAO.updateEmailAuthor(cookie.getId(),newEmail);
    }
    public static void changePassword(String newPassword) throws SQLException {
        authorDAO.updatePasswordAuthor(cookie.getId(), newPassword);
    }

    public static ArticleVersion getLastArticleVersionByArticleId(int idArticle) throws SQLException, IllegalArgumentException {
        return articleVersionDAO.getLastArticleVersionByArticleId(idArticle);
    }

    public static ArrayList<Integer> getRecentArticles(int numberArticles) throws SQLException {
        return articleDAO.getRecentArticles(numberArticles);
    }

    public static ArrayList<ArticleVersion> getAllArticleVersionsExcludingTextByArticleId(int idArticle) throws SQLException, IllegalArgumentException {
        return articleVersionDAO.getAllArticleVersionsExcludingTextByArticleId(idArticle);
    }

    public static ArrayList<ArticleVersion> getAllArticleVersionsExcludingTextByAuthorId(int authorId) throws SQLException, IllegalArgumentException {
        return articleVersionDAO.getAllArticleVersionsExcludingTextByAuthorId(authorId);
    }
    public static Article getArticlesById(int idArticle) throws SQLException, IllegalArgumentException {
        return articleDAO.getArticleById(idArticle);
    }

    public static ArrayList<Article> getMatchesArticlesByTitle(String articleTitle) throws SQLException {
        return articleDAO.getMatchesArticlesByTitle(articleTitle);
    }

    public static ArrayList<Author> getMatchesAuthorByNickname(String nicknameAuthor) throws SQLException, IllegalArgumentException {
        return authorDAO.getMatchesAuthorByNickname(nicknameAuthor);
    }

    public static ArrayList<Article> getArticlesByIdAuthor(int idAuthor) throws SQLException, IllegalArgumentException {
        return articleDAO.getAllArticlesByIdAuthor(idAuthor);
    }
    public static int getArticlesNumberByIdAuthor(int idAuthor) throws SQLException {
        return articleDAO.getAllArticlesNumberByIdAuthor(idAuthor);
    }
    public static int getArticlesNumberSentByIdAuthor(int idAuthor) throws SQLException {
        return articleVersionDAO.getVersionArticlesNumberSent(idAuthor);
    }
    public static ArticleVersion getArticleVersionByIdArticleVersion (int idArticleVersion) throws SQLException {
        return articleVersionDAO.getArticleVersionByIdArticleVersion(idArticleVersion);
    }
    public static Author getAuthorByNickname(String nickname) throws SQLException, IllegalArgumentException {
        return authorDAO.getAuthorByNickname(nickname);
    }
    public static float getRatingByAuthorId(int id) throws SQLException {
        return authorDAO.getRatingByAuthorId(id);
    }
    public static String getNicknameAuthorById(int idAuthor) throws SQLException {
        return authorDAO.getNicknameById(idAuthor);
    }

    public static boolean isAuthorRegisteredWithEmail(String email) throws SQLException {
        return authorDAO.isEmailUsed(email);
    }

    public static boolean isAuthorRegisteredWithNickname(String nickname) throws SQLException {
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

    public static boolean doRegistration(String email, String nickname, String password) throws SQLException {
        if (isAuthorRegisteredWithEmail(email) || isAuthorRegisteredWithNickname(nickname)){
            return false;
        }
        authorDAO.registerAuthor(nickname, password, 0, email);
        return true;
    }

    public static ArrayList<ArticleVersion> getNotifications() throws SQLException, IllegalArgumentException {
        return articleVersionDAO.getAllArticleVersionsWaiting(cookie.getId());
    }

    public static ArrayList<ArticleVersion> getNotificationsText(int articleId) throws SQLException, IllegalArgumentException {
        return articleVersionDAO.getAllArticleVersionsWaitingFull(articleId);
    }

    public static void reviewArticles(ArrayList<ArticleVersion> a) throws SQLException {
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

    public static int createArticle(String title, int idAuthor, String text) throws SQLException {
        int idArticle = articleDAO.saveArticle(title, idAuthor);

        articleVersionDAO.insertArticleVersion(idArticle, text, idAuthor, title);
        return idArticle;
    }

    public static int createProposal(int idArticle, String titleArticle,
                                     String text, int idAuthor) throws SQLException {
        return articleVersionDAO.insertArticleVersion(idArticle, text, idAuthor, titleArticle);
    }

    public static boolean checkLoggedUser(){
        return cookie != null;
    }

    public static boolean verifyAppInstances() {
        boolean thereAreInstances = true;

        File f = new File(lockFilePath);

        if (!f.exists()) {
            try {
                Files.createDirectories(Paths.get(configFolder));
                f.createNewFile();

                thereAreInstances = false;

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return  thereAreInstances;
    }


    public static void notifyOtherAppInstances() {

        System.out.println("Un'altra istanza di questa applicazione è attualmente in esecuzione, " +
                "ti preghiamo di chiudere quell'istanza prima di aprirne una nuova. Se non sono aperte altre istanze, elimina il file: "
                + lockFilePath);

        try {
            getSplashScreen().dispose();
        } catch(NullPointerException e){
            //System.out.println("Splash screen è null");
        }
    }

    public static void deleteLockFile() {
        File f = new File(lockFilePath);
        if(f.exists()) {
            f.delete();
        }
    }
    public static Author getAuthorById(int id) throws SQLException, IllegalArgumentException {
        return authorDAO.getAuthorById(id);
    }


}