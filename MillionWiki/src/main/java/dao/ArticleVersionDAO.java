package dao;

import model.ArticleVersion;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ArticleVersionDAO {
    ArticleVersion getLastArticleVersionByArticleId(int idArticle) throws SQLException, IllegalArgumentException;
    ArrayList<ArticleVersion> getAllArticleVersionsWaiting(int authorId) throws SQLException, IllegalArgumentException;
    ArrayList<ArticleVersion> getAllArticleVersionsWaitingFull(int articleId) throws SQLException, IllegalArgumentException;
    void reviewArticles(ArrayList<ArticleVersion> a) throws SQLException;
    ArrayList<ArticleVersion> getAllArticleVersionsExcludingTextByArticleId(int idArticle) throws SQLException, IllegalArgumentException;
    ArrayList<ArticleVersion> getAllArticleVersionsExcludingTextByAuthorId(int authorId) throws SQLException, IllegalArgumentException;
    int getVersionArticlesNumberSent(int idAuthor) throws SQLException;
    ArticleVersion getArticleVersionByIdArticleVersion (int idArticleVersion) throws SQLException, IllegalArgumentException;
    int insertArticleVersion(int idArticle, String text, int idAuthor, String titleProposal) throws SQLException;
}
